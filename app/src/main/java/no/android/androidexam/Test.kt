package no.android.androidexam

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.parcelize.Parcelize


@Parcelize
class ImageLinks(val storeLink: String, val thumbNailLink: String):Parcelable
@Parcelize
class ResData(
    val originalImage: OriginalImage?, val searchEngine: String? = "",
    val links: ArrayList<ImageLinks> = ArrayList(),
):Parcelable

@Parcelize
class OriginalImage(
    val bitmap: Bitmap?,
    val link: String? = ""
):Parcelable


@Parcelize
class ListOfBitmaps(
    val originalImage: Bitmap,
    val resBitmaps: ArrayList<Bitmap> = ArrayList(),
):Parcelable


class ParentModel(private var movieCategory: String) {
    fun movieCategory(): String {
        return movieCategory
    }
}

class ChildRecyclerViewAdapter(arrayList: ArrayList<ImageLinks>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder>() {
    private var childModelArrayList: ArrayList<ImageLinks> = arrayList
    interface OnClickListener {
        fun onClick(clickedItem: Bitmap?)
    }

    private var mCallback: OnClickListener? = null
    private var list: ArrayList<Bitmap> = ArrayList()


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
        var clicked = false
        Log.i("link", currentItem.storeLink)
        Picasso.get().load(currentItem.storeLink).into(holder.heroImage);


        holder.heroImage.setOnClickListener{
            if(clicked){
                holder.heroImage.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN)
                clicked = false

            }else{
                holder.heroImage.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN)
                holder.itemView.setOnClickListener { if (mCallback != null) mCallback!!.onClick(holder.heroImage.drawToBitmap()) }
                clicked = true
            }


        }
    }

    override fun getItemCount(): Int {
        return childModelArrayList.size
    }
}

class ParentRecyclerViewAdapter(
    private val parentModelArrayList: ArrayList<ParentModel>,
    private var cxt: Context,
    private val resData: ResData
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
            ChildRecyclerViewAdapterFrag3(resData.links)
        holder.childRecyclerView.adapter = childRecyclerViewAdapter
    }
}

class ChildRecyclerViewAdapterFrag3(arrayList: ArrayList<ImageLinks>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapterFrag3.MyViewHolder>() {
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