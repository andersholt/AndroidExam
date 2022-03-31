package no.android.androidexam


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class Fragment2 : Fragment() {

    var imageAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 2 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment2, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.setAdapter(imageAdapter)
        return view
    }
}
