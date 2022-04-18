package no.android.androidexam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment





class Fragment1Child2: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()


        Log.i("Args", arguments.toString())


        val view = inflater.inflate(R.layout.fragment1_child2, container, false)

        val bing: Button = view.findViewById(R.id.bing)
        val google: Button = view.findViewById(R.id.google)
        val tineye: Button = view.findViewById(R.id.tineye)

        return view
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.bing -> {}
            R.id.google -> {}
            R.id.tineye -> {}
            else -> {}
        }
    }
}