package no.android.androidexam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import no.android.androidexam.R


class Fragment1 : Fragment() {
    val bundle = Bundle()

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

        switchFragment(view.findViewWithTag("1"))

        return view
    }

    private fun switchFragment(v: View) {
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
            val fragment1Child2 = Fragment1Child2()
            fragment1Child2.arguments = bundle
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.child_fragment_container,
                    fragment1Child2,
                    "Fragment1Child2"
                )
                .commit()
        }
    }
}
