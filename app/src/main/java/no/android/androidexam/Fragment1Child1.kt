package no.android.androidexam

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Fragment1Child1: Fragment() {
    var imageUri: String = ""
    lateinit var image: ImageView
    var apiClient = ApiClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 child 1", Toast.LENGTH_SHORT).show()

        val view = inflater.inflate(R.layout.fragment1_child1, container, false)
        val button = view.findViewById<Button>(R.id.select_image)
        button.setOnClickListener {
            image = view.findViewById(R.id.image)
            var i = Intent()
            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"
            startForResult.launch(i)
        }
        return view
    }



    private var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        imageUri = it.data?.data.toString()

        Log.i("Image", imageUri)

        var bitmapImage = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

        image.layoutParams = image.layoutParams.apply {

            width = bitmapImage.width
            height = bitmapImage.height
        }

        image.setImageBitmap(bitmapImage)
        image.background = BitmapDrawable(resources, bitmapImage)


        val sd: File? = context?.getCacheDir()
        val folder = File(sd, "/myfolder/")
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
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.i("Exists", fileName.exists().toString())
        Log.i("Location", fileName.path.toString())
        Log.i("Name", fileName.name)

        val bundle = Bundle()

        fun main() = runBlocking{
            var result = apiClient.getBySendingImage(fileName)

            bundle.putString("link", result)

            requireActivity().supportFragmentManager.setFragmentResult("request_Key", bundle)
            Log.i("Result from API", result)
        }
        main()

    }
}

