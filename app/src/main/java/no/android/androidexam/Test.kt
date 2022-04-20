package no.android.androidexam

import android.content.Context
import android.media.Image
import android.util.Log

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso

class ImageLinks(val storeLink: String, val thumbNailLink: String)
class ResData(val originalLink: String, val searchEngine: String, val links: ArrayList<ImageLinks>)

class ParentModel(private var movieCategory: String) {
    fun movieCategory(): String {
        return movieCategory
    }
}

class ChildRecyclerViewAdapter(arrayList: ArrayList<ImageLinks>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder>() {
    private var childModelArrayList: ArrayList<ImageLinks> = arrayList

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heroImage: ImageView = itemView.findViewById(R.id.hero_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_recyclerview_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = childModelArrayList[position]
        Log.i("link", currentItem.storeLink)
        Picasso.get().load(currentItem.storeLink).into(holder.heroImage);
    }

    override fun getItemCount(): Int {
        return childModelArrayList.size
    }

}


class ParentRecyclerViewAdapter(
    private val parentModelArrayList: ArrayList<ParentModel>,
    private var cxt: Context,
    private val arrayList: ArrayList<ImageLinks> = ArrayList()
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

        val childRecyclerViewAdapter =
            ChildRecyclerViewAdapter(arrayList)
        holder.childRecyclerView.adapter = childRecyclerViewAdapter
    }

}