package no.android.androidexam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import kotlinx.coroutines.*

import no.android.androidexam.ApiClient
import no.android.androidexam.OriginalImage
import no.android.androidexam.ResData

import no.android.androidexam.R
import java.util.*


class Fragment1Child2 : Fragment() {
    private lateinit var link: OriginalImage
    val apiClient = ApiClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment1_child2, container, false)
        val linkText: TextView = view.findViewById(R.id.linkText)

        parentFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val result: OriginalImage? = bundle.getParcelable<OriginalImage>("bundleKey")
            link = result!!
            linkText.text = result.link
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
        val loadingDialog = this.context?.let { MaterialDialog(it).noAutoDismiss().customView(R.layout.loading_layout) }

        if (this::link.isInitialized) {


            GlobalScope.launch(Dispatchers.IO) {
                //val intent = Intent(activity, SplashActivity::class.java)
                //startActivity(intent)

                @WorkerThread
                fun workerThread() {
                    ContextCompat.getMainExecutor(v.context).execute {
                        loadingDialog?.show()
                    }
                }

                fun workerThreadStop() {
                    ContextCompat.getMainExecutor(v.context).execute {
                        loadingDialog?.dismiss()
                    }
                }

                workerThread()

                var result =
                    runBlocking { apiClient.getByWebUrl(link.link.toString(), v.tag as String) }

                while (result.size == 0){
                    delay(100)
                }

                workerThreadStop()

                val resData = ResData(link, v.tag as String, result)

                //activity?.finish()

                requireActivity().supportFragmentManager.setFragmentResult(
                    "requestKey2",
                    bundleOf("bundleKey2" to resData)
                )
            }
        } else {
            Toast.makeText(activity, "No image link detected", Toast.LENGTH_LONG).show()
        }

    }
}

interface OnTextClickListener {
    fun onTextClick(data: Objects)
}