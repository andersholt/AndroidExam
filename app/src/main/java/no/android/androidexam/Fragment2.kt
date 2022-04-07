package no.android.androidexam


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment2 : Fragment() {

    var imageAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(activity, "Fragment 2 onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 2 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment2, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(activity, 3)

        val cats = listOf(
            Cats("Simba", R.drawable.img),
            Cats("Scar", R.drawable.img_1),
            Cats("Scar", R.drawable.img_2),
            Cats("Scar", R.drawable.img_3),
            Cats("Scar", R.drawable.img_4),
            Cats("Scar", R.drawable.img_5),
            Cats("Scar", R.drawable.img_6),
            Cats("Scar", R.drawable.img_7),
            Cats("Scar", R.drawable.img_8),
            Cats("Scar", R.drawable.img_9),
            Cats("Scar", R.drawable.img_10),
        )
        imageAdapter = ImageAdapter(cats)

        recyclerView.setAdapter(imageAdapter)
        return view
    }


}
