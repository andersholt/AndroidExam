package no.android.androidexam

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Fragment1Child2: Fragment() {
    private lateinit var link: String
    val apiClient = ApiClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment1_child2, container, false)
        val linkText:TextView = view.findViewById(R.id.linkText)

        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { requestKey, bundle ->
            val result = bundle.getString("bundleKey")
            if (result != null) {
                link = result
            }
            linkText.text = result
        }



        val bing: Button = view.findViewById(R.id.bing)
        bing.setOnClickListener{
            onClick(view.findViewWithTag("bing"))
        }

        val google: Button = view.findViewById(R.id.google)
        google.setOnClickListener{
            onClick(view.findViewWithTag("google"))
        }
        val tineye: Button = view.findViewById(R.id.tineye)
        tineye.setOnClickListener{
            onClick(view.findViewWithTag("tineye"))
        }
        return view
    }

    private fun onClick(v: View) {
        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {apiClient.getByWebUrl(link, v.tag as String) }
        }
    }
}