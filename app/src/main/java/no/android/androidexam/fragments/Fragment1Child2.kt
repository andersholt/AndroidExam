package no.android.androidexam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import no.android.androidexam.*


class Fragment1Child2 : Fragment() {
    private lateinit var link: OriginalImage
    val apiClient = ApiClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1_child2, container, false)
        val resultText: TextView = view.findViewById(R.id.linkText)

        parentFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val result: OriginalImage? = bundle.getParcelable<OriginalImage>("bundleKey")
            link = result!!
            resultText.text = "Click on Results after the loading is done"
        }


        val bing: Button = view.findViewById(R.id.bing)
        bing.setOnClickListener {
            onClick(view.findViewWithTag("bing"))
        }

        val google: Button = view.findViewById(R.id.google)
        google.setOnClickListener {
            onClick(view.findViewWithTag("google"))
        }
        val tineye: Button = view.findViewById(R.id.tineye)
        tineye.setOnClickListener {
            onClick(view.findViewWithTag("tineye"))
        }
        return view
    }


    private fun onClick(v: View) {

        if (this::link.isInitialized) {

            val workerThread = MyWorkerThread(requireContext())
            var notToLong = true
            GlobalScope.launch(Dispatchers.IO) {

                workerThread.start(requireContext())
                var result =
                    runBlocking { apiClient.getByWebUrl(link.link.toString(), v.tag as String) }
                var counter = 0
                while (result.isEmpty() && notToLong) {
                    if (counter >= 150) {
                        notToLong = false
                    }
                    counter++
                    delay(100)
                }
                workerThread.stop(requireContext())

                if(notToLong){
                    val resData = ResData(link, v.tag as String, result)
                    requireActivity().supportFragmentManager.setFragmentResult(
                        "requestKey2",
                        bundleOf("bundleKey2" to resData)
                    )
                }
            }
        } else {
            Toast.makeText(activity, "No image link detected", Toast.LENGTH_LONG).show()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("Fragment2Child2", "Fragment2Child2")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Fragment2Child2", "Fragment2Child2")
    }

}

