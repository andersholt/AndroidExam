package no.android.androidexam.fragments

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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import no.android.androidexam.ApiClient
import no.android.androidexam.R
import no.android.androidexam.UriToBitmap
import no.android.androidexam.getBitmap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class Fragment1Child1: Fragment() {
    private var imageUri: String = ""
    lateinit var image: ImageView
    private var apiClient = ApiClient()
    lateinit var bitmapImage: Bitmap


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("Saving", "saving")
        if (this::bitmapImage.isInitialized){
            outState.putBundle("stateKey", bundleOf("bitmapKey" to bitmapImage))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1_child1, container, false)


        if (savedInstanceState != null){
            bitmapImage = savedInstanceState.getBundle("stateKey")?.get("bitmapKey") as Bitmap;
            image = view.findViewById(R.id.image)
            image.layoutParams.apply {
                width = bitmapImage.width
                height = bitmapImage.height
            }.also { it -> image.layoutParams = it }
            image.setImageBitmap(bitmapImage)
            image.background = BitmapDrawable(resources, bitmapImage)
        }



        Toast.makeText(activity, "Fragment 1 child 1", Toast.LENGTH_SHORT).show()

        val button = view.findViewById<Button>(R.id.select_image)
        button.setOnClickListener {
            image = view.findViewById(R.id.image)
            val i = Intent()
            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"
            startForResult.launch(i)
        }
        return view
    }

    private var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->

        imageUri = it.data?.data.toString()

        bitmapImage = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

        image.layoutParams.apply {

            width = bitmapImage.width
            height = bitmapImage.height
        }.also { it -> image.layoutParams = it }

        image.setImageBitmap(bitmapImage)
        image.background = BitmapDrawable(resources, bitmapImage)

        //Crop Before this -- Create a method
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

        GlobalScope.launch(Dispatchers.IO) {

            val result = runBlocking {apiClient.getBySendingImage(fileName)}

            parentFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
    }
}
