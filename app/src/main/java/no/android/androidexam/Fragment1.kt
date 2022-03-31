package no.android.androidexam

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream
import java.io.File


class Fragment1 : Fragment() {
    public var imageUri: String = ""
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

    var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        imageUri = it.data?.data.toString()

        var bitmapImage = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

        image.layoutParams = image.layoutParams.apply {

            width = bitmapImage.width
            height = bitmapImage.height
        }

        image.setImageBitmap(bitmapImage)
        image.background = BitmapDrawable(resources, bitmapImage)


        val immagex: Bitmap = bitmapImage






        val baos = ByteArrayOutputStream()
        immagex.compress(Bitmap.CompressFormat.PNG, 1, baos)
        val b = baos.toByteArray()
        var encodedImage = Base64.encodeToString(b, Base64.DEFAULT)


        val imgFile = File(imageUri)

        var result = apiClient.getBySendingImage(imgFile)




        Log.i("Result from API", imgFile.absolutePath)
    }
}
