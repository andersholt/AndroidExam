package no.android.androidexam.fragments

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
import no.android.androidexam.*
import java.util.*


class Fragment1Child2: Fragment() {
    private lateinit var link: OriginalImage
    val apiClient = ApiClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment1_child2, container, false)
        val linkText:TextView = view.findViewById(R.id.linkText)

        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { requestKey, bundle ->
            val result: OriginalImage? = bundle.getParcelable<OriginalImage>("bundleKey")
            link = result!!
            linkText.text = result.link
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
        if(this::link.isInitialized){
            GlobalScope.launch(Dispatchers.IO) {
                //val intent = Intent(activity, SplashActivity::class.java)
                //startActivity(intent)

                var result = runBlocking {apiClient.getByWebUrl(link.link.toString(), v.tag as String) }
                val resData = ResData(link, v.tag as String, result)
                //activity?.finish()

                requireActivity().supportFragmentManager.setFragmentResult("requestKey2", bundleOf("bundleKey2" to resData))
            }
        }
        else{
            Toast.makeText(activity, "No image link detected", Toast.LENGTH_LONG).show()
        }

    }
}

interface OnTextClickListener {
    fun onTextClick(data: Objects)
}