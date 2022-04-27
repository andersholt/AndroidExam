package no.android.androidexam.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import no.android.androidexam.*
import java.io.File
import java.io.FileOutputStream
import kotlin.reflect.KFunction3


class Fragment1Child1 : Fragment() {
    private var imageUri: String = ""
    lateinit var image: CropImageView
    private var apiClient = ApiClient()
    var bitmapImage: Bitmap? = null
    var imageHeight = 0
    var imageWidth = 0
    lateinit var submitButton: Button

    var actualCropRect: Rect? = null

    interface OnImageSizeChangedListener {
        fun onImageSizeChanged(rec: Rect)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1_child1, container, false)


        Toast.makeText(activity, "Fragment 1 child 1", Toast.LENGTH_SHORT).show()

        val button = view.findViewById<Button>(R.id.select_image)
        submitButton = view.findViewById<Button>(R.id.upload_cropped_image)

        button.setOnClickListener {
            image = view.findViewById(R.id.image)
            val i = Intent()
            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"
            startForResult.launch(i)
        }

        image = view.findViewById(R.id.image)
        image.setListeners(object : View.OnClickListener {
            override fun onClick(p0: View?) {}
        }, object : OnImageSizeChangedListener {
            override fun onImageSizeChanged(rec: Rect) {
                Log.i("Rect tag", rec.flattenToString())
                actualCropRect = rec
            }

        })

        submitButton.setOnClickListener {
            try {
                submitCroppedImage()
            } catch (e: UninitializedPropertyAccessException) {

                Toast.makeText(activity, "Please choose an Image", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private var startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->

            imageUri = it.data?.data.toString()

            Log.i("Image", imageUri)

            bitmapImage = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)
            if (bitmapImage != null) {
                if (imageWidth == 0) {
                    imageHeight = image.height
                    imageWidth = image.width
                }

                Log.i("Height", image.height.toString())
                val aspectRatio = bitmapImage!!.width.toFloat() / bitmapImage!!.height.toFloat()
                val aspectRatioImage = imageWidth.toFloat() / imageHeight.toFloat()

                if (aspectRatio < aspectRatioImage) {
                    image.layoutParams.apply {
                        width = (imageHeight * aspectRatio).toInt()
                        height = -1
                    }.also { image.layoutParams = it }
                } else {
                    image.layoutParams.apply {
                        width = -1
                        height = (imageWidth / aspectRatio).toInt()
                    }.also { image.layoutParams = it }
                }
                image.setImageBitmap(bitmapImage)
                image.background = BitmapDrawable(resources, bitmapImage)
                image.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE
            } else {
                Toast.makeText(activity, "Not a image", Toast.LENGTH_SHORT).show()
            }
        }

    private fun submitCroppedImage() {
        var rect = actualCropRect
        if (bitmapImage != null) {
            try {
                var bufferBitmap = Bitmap.createBitmap(
                    bitmapImage!!,
                    rect?.left!!,
                    rect.top,
                    rect.right,
                    rect.bottom
                )
                uploadBitmap(bufferBitmap)

            } catch (e: NullPointerException) {
                uploadBitmap(bitmapImage!!)
            }
        }

    }

    private fun uploadBitmap(bitmapImage: Bitmap) {
        val sd: File? = context?.cacheDir
        val folder = File(sd, "/myfolder/")
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.e("ERROR", "Cannot create a directory!")
            } else {
                folder.mkdirs()
            }
        }

        val fileName = File(folder, "img.png")
        val outputStream = FileOutputStream(fileName.toString())
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
        outputStream.close()

        val imageSize = fileName.length()
        Log.i("", imageSize.toString())
        if (imageSize > 1048576) {
            Toast.makeText(activity, "Image to big -> Resize or select new", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val workerThread = MyWorkerThread(requireContext())
        var notToLong = true


        GlobalScope.launch(Dispatchers.IO) {

            workerThread.start(requireContext())
            val result = runBlocking { apiClient.getBySendingImage(fileName) }
            var counter = 0
            while (result.isEmpty() && notToLong) {
                if (counter >= 40) {
                    notToLong = false
                }
                counter++
                delay(100)
            }
            workerThread.stop(requireContext())

            if (notToLong) {
                val originalImage = OriginalImage(bitmapImage, result)
                parentFragmentManager.setFragmentResult(
                    "requestKey",
                    bundleOf("bundleKey" to originalImage)
                )
            }
        }
    }

    fun UriToBitmap(context: Context, id: Int?, uri: String?): Bitmap? {
        try {
            val image: Bitmap =
                MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(uri))
            return image
        } catch (e: Exception) {
            Log.i("Exception", "Not image type")
        }
        return null
    }

    fun getBitmap(
        context: Context,
        id: Int?,
        uri: String?,
        decoder: KFunction3<Context, Int?, String?, Bitmap?>
    ): Bitmap? {
        return decoder(context, id, uri)
    }
}

