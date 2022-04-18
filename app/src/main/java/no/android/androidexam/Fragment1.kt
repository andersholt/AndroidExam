package no.android.androidexam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner


class Fragment1 : Fragment() {
    lateinit var image: ImageView
    var link = Bundle()


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
        childFragmentManager.setFragmentResultListener("link", viewLifecycleOwner, FragmentResultListener { requestKey, result ->
            link = result
        })

        switchFragment(view.findViewWithTag("1"))

        return view
    }


    private fun switchFragment(v: View) {

        Log.i("REsultggg", link.toString())

        if (Integer.parseInt(v.tag.toString()) == 1) {
            val fragment = Fragment1Child1()

            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.child_fragment_container,
                    Fragment1Child1(),
                    "Fragment1child1"
                )
                .commit()
        } else if (Integer.parseInt(v.tag.toString()) == 2) {
            val fragment = Fragment1Child2()
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
