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
        val view = inflater.inflate(R.layout.fragment1, container, false)


        val selectImage: Button = view.findViewById(R.id.select_image)
        selectImage.setOnClickListener{
            switchFragment(view.findViewWithTag("1"))
        }
        val uploadImage: Button = view.findViewById(R.id.upload_image)
        uploadImage.setOnClickListener{
            switchFragment(view.findViewWithTag("2"))
        }
        return view
    }


    fun switchFragment(v: View) {
        if (Integer.parseInt(v.tag.toString()) == 1) {
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.child_fragment_container,
                    Fragment1Child1(),
                    "Fragment1child1"
                )
                .commit()
        } else if (Integer.parseInt(v.tag.toString()) == 2) {
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.child_fragment_container,
                    Fragment1Child2(),
                    "Fragment1Child2"
                )
                .commit()
        }
    }
}
