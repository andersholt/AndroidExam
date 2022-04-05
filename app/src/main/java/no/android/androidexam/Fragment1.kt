package no.android.androidexam

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
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
import java.net.URI
import kotlin.math.log


class Fragment1 : Fragment() {
    var imageUri: String = ""
    lateinit var image: ImageView
    lateinit var button: Button
    var apiClient = ApiClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()

        val view = inflater.inflate(R.layout.fragment1, container, false)

        image = view.findViewById(R.id.image)
        button = view.findViewById(R.id.addImage)

        button.setOnClickListener {
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

        getImageLinkByPost(it.data?.data?.path)
    }


    fun getImageLinkByPost(uri: String?){

        val path = getContext()?.getExternalFilesDir(null)?.absolutePath
        val file = File("${path?.substringBefore("0/")}/${uri?.substringAfterLast("emulated/")}")


        Log.i("File size", (file.path))
        Log.i("File size", (file.toString()))

        if(file.extension != "png"){
            Log.w("Warning", "Image is of wrong file type: ${file.extension}")
            return
        }


        Log.i("FIlecompare", file.length().compareTo(1000000).toString())

        if (file.length().compareTo(1048576) != -1){
        }



        var result = apiClient.getBySendingImage(file)

        Log.i("Result from API", result)
    }
}
