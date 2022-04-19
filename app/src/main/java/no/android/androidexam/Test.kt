package no.android.androidexam

import android.content.Context

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class ChildModel(val heroImage: Int, val movieName: String)

class ParentModel(private var movieCategory: String) {
    fun movieCategory(): String {
        return movieCategory
    }
}

class ChildRecyclerViewAdapter(arrayList: ArrayList<ChildModel>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder>() {
    private var childModelArrayList: ArrayList<ChildModel> = arrayList

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heroImage: ImageView = itemView.findViewById(R.id.hero_image)
        var movieName: TextView = itemView.findViewById(R.id.movie_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_recyclerview_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = childModelArrayList[position]
        holder.heroImage.setImageResource(currentItem.heroImage)
        holder.movieName.text = currentItem.movieName
    }

    override fun getItemCount(): Int {
        return childModelArrayList.size
    }

}


class ParentRecyclerViewAdapter(
    private val parentModelArrayList: ArrayList<ParentModel>,
    private var cxt: Context
) :
    RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.Movie_category)
        var childRecyclerView: RecyclerView = itemView.findViewById(R.id.Child_RV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_recyclerview_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parentModelArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = parentModelArrayList[position]
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false)
        holder.childRecyclerView.layoutManager = layoutManager
        holder.childRecyclerView.setHasFixedSize(true)
        holder.category.text = currentItem.movieCategory()
        val arrayList: ArrayList<ChildModel> = ArrayList()

        // added the first child row
        if (parentModelArrayList[position].movieCategory() == "Category1") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }

        if (parentModelArrayList[position].movieCategory() == "Category2") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }


        if (parentModelArrayList[position].movieCategory() == "Category3") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }

        if (parentModelArrayList[position].movieCategory() == "Category4") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }
        if (parentModelArrayList[position].movieCategory() == "Category5") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }
        if (parentModelArrayList[position].movieCategory() == "Category6") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }
        if (parentModelArrayList[position].movieCategory() == "Category7") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }
        if (parentModelArrayList[position].movieCategory() == "Category8") {
            arrayList.add(ChildModel(R.drawable.img_1, "Cat1"))
            arrayList.add(ChildModel(R.drawable.img_2, "Cat2"))
            arrayList.add(ChildModel(R.drawable.img_3, "Cat3"))
            arrayList.add(ChildModel(R.drawable.img_4, "Cat4"))
        }
        val childRecyclerViewAdapter =
            ChildRecyclerViewAdapter(arrayList)
        holder.childRecyclerView.adapter = childRecyclerViewAdapter
    }
}