package no.android.androidexam

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {
    public var imageUri: String? = null
    lateinit var image: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment1, container, false)



        var clicked = false;
        image = view.findViewById(R.id.image)
        image.setOnClickListener {
            if(clicked){
                Log.i("Info", "Already selected image")
            }else{
                Log.i("Clicked", "Image")
                var i = Intent()
                i.action = Intent.ACTION_GET_CONTENT
                i.type = "*/*"
                startForResult.launch(i)
                clicked = true;
            }
        }
        return view
    }

    var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        imageUri = it.data?.data.toString()

        var bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

        image.layoutParams = image.layoutParams.apply {

            width = bitmap_image.width
            height = bitmap_image.height
        }

        image.setImageBitmap(bitmap_image)
        image.background = BitmapDrawable(resources, bitmap_image)
    }
}
