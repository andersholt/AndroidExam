package no.android.androidexam

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.io.File
import android.provider.MediaStore
import android.provider.MediaStore.Images

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class Fragment1 : Fragment() {
    var imageUri: String = ""
    lateinit var image: ImageView
    lateinit var button: Button
    var apiClient = ApiClient()
    lateinit var result: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1, container, false)


        val selectImage: Button = view.findViewById(R.id.select_image)
        val uploadImage: Button = view.findViewById(R.id.upload_image)

        selectImage.setOnClickListener {
            image = view.findViewById(R.id.image)
            var i = Intent()
            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"
            startForResult.launch(i)
        }

        uploadImage.setOnClickListener {
            val i = Intent(context, SecondActivity::class.java)
            i.putExtra("url", result)
            startActivity(i)
        }
        return view
    }


    private var startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            var bitmapImage = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)
            Log.i("Image", imageUri)

            image.layoutParams = image.layoutParams.apply {

                width = bitmapImage.width
                height = bitmapImage.height
            }

            image.setImageBitmap(bitmapImage)
            image.background = BitmapDrawable(resources, bitmapImage)


            val tempUri: Uri = getImageUri(requireContext(), bitmapImage)
            val finalFile: File = File(getRealPathFromURI(tempUri))

            Log.i("Path", finalFile.absolutePath)
            Log.i("Exists", finalFile.exists().toString())



            getImageLinkByPost(it.data?.data?.path)
        }


    fun getImageLinkByPost(uri: String?) {

        val path = context?.getExternalFilesDir(null)?.absolutePath
        val file = File("${path?.substringBefore("0/")}/${uri?.substringAfterLast("emulated/")}")


        Log.i("File size", (path.toString()))
        Log.i("File size", (file.toString()))

        if (file.extension != "png") {
            Log.w("Warning", "Image is of wrong file type: ${file.extension}")
            return
        }

        if (file.length().compareTo(1048576) != -1) {
        }

        result = apiClient.getBySendingImage(file)
        Log.i("Result from API", result)
    }


    fun getRealPathFromURI(uri: Uri): String? {
        val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val idx: Int? = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor?.getString(it) }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }
}

