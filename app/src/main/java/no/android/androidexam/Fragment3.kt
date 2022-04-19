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

    private var mainImageAdapter : RecyclerView? = null
    private var childAdapter: ChildAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 3 onCreateView", Toast.LENGTH_SHORT).show()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment3, container, false)

        mainImageAdapter = view.findViewById(R.id.recycler_view1)

        val categoryItemList2 = listOf(
            Cats(1, R.drawable.img),
            Cats(1, R.drawable.img_1),
            Cats(1, R.drawable.img_2),
            Cats(1, R.drawable.img_3),
            Cats(1, R.drawable.img_4),
            Cats(1, R.drawable.img_5))


        // added in 3rd category
        val categoryItemList3 = listOf(
            Cats(1, R.drawable.img),
            Cats(1, R.drawable.img_1),
            Cats(1, R.drawable.img_2),
            Cats(1, R.drawable.img_3),
            Cats(1, R.drawable.img_4),
            Cats(1, R.drawable.img_5))


        // added in 4th category
        val categoryItemList4 = listOf(
            Cats(1, R.drawable.img),
            Cats(1, R.drawable.img_1),
            Cats(1, R.drawable.img_2),
            Cats(1, R.drawable.img_3),
            Cats(1, R.drawable.img_4),
            Cats(1, R.drawable.img_5))


        // added in 5th category
        val categoryItemList5 = listOf(
            Cats(1, R.drawable.img),
            Cats(1, R.drawable.img_1),
            Cats(1, R.drawable.img_2),
            Cats(1, R.drawable.img_3),
            Cats(1, R.drawable.img_4),
            Cats(1, R.drawable.img_5))

        val allCategoryList = listOf(
            AllLists("Best of Oscars", categoryItemList2),
            AllLists("Movies Dubbed in Hindi", categoryItemList3),
            AllLists("Category 4th", categoryItemList4),
            AllLists("Category 5th", categoryItemList5))

        setMainImageAdapter(allCategoryList)

        return view
    }

    private fun setMainImageAdapter(allCategory: List<AllLists>){
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        mainImageAdapter!!.layoutManager = layoutManager
        childAdapter = this.context?.let { ChildAdapter(it, allCategory) }
        mainImageAdapter!!.adapter = childAdapter
    }

}
