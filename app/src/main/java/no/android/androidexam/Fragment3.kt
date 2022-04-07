package no.android.androidexam


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment3 : Fragment() {

    var imageAdapter: ImageAdapter? = null
    var Horizontallayout: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 3 onCreateView", Toast.LENGTH_SHORT).show()

        Horizontallayout = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment3, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view1)

        recyclerView.layoutManager = GridLayoutManager(activity, 2)

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

        recyclerView.layoutManager = Horizontallayout
        recyclerView.setAdapter(imageAdapter)
        return view
    }
}
