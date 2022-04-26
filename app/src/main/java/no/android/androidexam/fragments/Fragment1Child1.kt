package no.android.androidexam.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.TransactionTooLargeException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import kotlinx.coroutines.*
import no.android.androidexam.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.NullPointerException


class Fragment1Child1 : Fragment() {
    private var imageUri: String = ""
    lateinit var image: CropImageView2
    private var apiClient = ApiClient()
    lateinit var bitmapImage: Bitmap

    var actualCropRect: Rect? = null

    interface OnImageSizeChangedListener {
        fun onImageSizeChanged(rec: Rect)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("Saving", "saving")
        if (this::bitmapImage.isInitialized) {
            outState.putBundle("stateKey", bundleOf("bitmapKey" to bitmapImage))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1_child1, container, false)
        Log.i("Saved?", savedInstanceState.toString())


        Toast.makeText(activity, "Fragment 1 child 1", Toast.LENGTH_SHORT).show()

        val button = view.findViewById<Button>(R.id.select_image)
        val submitButton = view.findViewById<Button>(R.id.upload_cropped_image)



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

            val aspectRatio = bitmapImage.width.toFloat() / bitmapImage.height.toFloat()

            Log.i("Height", view?.height.toString())
            image.layoutParams.apply {
                width = -1
                height = (image.width / aspectRatio).toInt()
            }.also { image.layoutParams = it }



            image.setImageBitmap(bitmapImage)
            image.background = BitmapDrawable(resources, bitmapImage)
            //Crop Before this -- Create a method

        }

    private fun submitCroppedImage() {
        var rect = actualCropRect

        try {
            var bufferBitmap = Bitmap.createBitmap(
                bitmapImage,
                rect?.left!!,
                rect.top,
                rect.right,
                rect.bottom
            )
            uploadBitmap(bufferBitmap)
        } catch (e: NullPointerException) {
            uploadBitmap(bitmapImage)
        }

    }

    private fun uploadBitmap(bitmapImage: Bitmap) {
        val loadingDialog = this.context?.let {
            MaterialDialog(it).noAutoDismiss().customView(R.layout.adding_loading_layout)
        }

        @WorkerThread
        fun workerThread() {
            context?.let {
                ContextCompat.getMainExecutor(it).execute {
                    loadingDialog?.show()
                }
            }
        }

        fun workerThreadStop() {
            context?.let {
                ContextCompat.getMainExecutor(it).execute {
                    loadingDialog?.dismiss()
                }
            }
        }


        val sd: File? = context?.cacheDir
        val folder = File(sd, "/images/")
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.e("ERROR", "Cannot create a directory!")
            } else {
                folder.mkdirs()
            }
        }

        val fileName = File(folder, "img.png")

        try {
            val outputStream = FileOutputStream(fileName.toString())
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: TransactionTooLargeException) {
            Toast.makeText(activity, "File to large, try again", Toast.LENGTH_SHORT).show()
        }
        Log.i("Exists", fileName.exists().toString())
        Log.i("Location", fileName.path.toString())
        Log.i("Name", fileName.name)

        GlobalScope.launch(Dispatchers.IO) {
            workerThread()

            val result = runBlocking { apiClient.getBySendingImage(fileName) }

            while (result.isEmpty()){
                delay(100)
            }

            workerThreadStop()

            val originalImage = OriginalImage(bitmapImage, result)
            parentFragmentManager.setFragmentResult(
                "requestKey",
                bundleOf("bundleKey" to originalImage)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("Fragment2Child2", "Fragment2Child2")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Fragment2Child2", "Fragment2Child2")
    }


}

