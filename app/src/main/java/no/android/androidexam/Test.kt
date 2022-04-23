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
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.drawToBitmap
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.parcelize.Parcelize


@Parcelize
class ImageLinks(val storeLink: String, val thumbNailLink: String):Parcelable
@Parcelize
class ResData(
    val originalImage: OriginalImage, val searchEngine: String? = "",
    val links: ArrayList<ImageLinks> = ArrayList(),
):Parcelable

@Parcelize
class OriginalImage(
    val bitmap: Bitmap,
    val link: String? = ""
):Parcelable

@Parcelize
class ResultImages(
    val foreignKey: Int,
    val bitmaps: ArrayList<ResultImage> = ArrayList(),
):Parcelable

@Parcelize
class ResultImage(
    val id: Int,
    val bitmap: Bitmap,
):Parcelable

class ParentModel(private var imageBitmap: Bitmap, private var id: Int) {
    fun parentBitmap(): Bitmap {
        return imageBitmap
    }
    fun parentId(): Int {
        return id
    }
}

class ChildRecyclerViewAdapter(arrayList: ArrayList<ImageLinks>, var fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder>() {
    private var childModelArrayList: ArrayList<ImageLinks> = arrayList
    private var list: ArrayList<Bitmap> = ArrayList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heroImage: ImageView = itemView.findViewById(R.id.hero_image)
        var checkBox : CheckBox = itemView.findViewById(R.id.checkbox)
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
        val heroImage = holder.heroImage
        var checkBox = holder.checkBox
        var bitmapImage: Bitmap? = null

        Picasso.get().load(currentItem.storeLink).into(heroImage)
        heroImage.setOnClickListener{
            if(clicked){
                checkBox.isChecked = false
                list.remove(bitmapImage)
                fragmentManager.setFragmentResult("selectedImages",  bundleOf("bitmapList" to list))
                clicked = false
            }else{
                bitmapImage = heroImage.drawToBitmap()
                checkBox.isChecked = true
                list.add(bitmapImage!!)
                fragmentManager.setFragmentResult("selectedImages",  bundleOf("bitmapList" to list))
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
    private val listOfBitmaps: ArrayList<ResultImages>,
    var fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var childRecyclerView: RecyclerView = itemView.findViewById(R.id.Child_RV)
    }

    private var list: ArrayList<Int> = ArrayList()


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
        val id = currentItem.parentId()

        for(item in listOfBitmaps){
            Log.i("Items", item.foreignKey.toString())

            if(item.foreignKey == id){
                item.bitmaps.add(0, ResultImage(id, currentItem.parentBitmap()))
                holder.childRecyclerView.layoutManager = layoutManager
                holder.childRecyclerView.setHasFixedSize(true)
                val childRecyclerViewAdapter = ChildRecyclerViewAdapterFrag3(item, fragmentManager, list)
                holder.childRecyclerView.adapter = childRecyclerViewAdapter
            }
        }
    }
}

class ChildRecyclerViewAdapterFrag3(arrayList: ResultImages, var fragmentManager: FragmentManager, var list: ArrayList<Int>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapterFrag3.MyViewHolder>() {
    private var childModelArrayList = arrayList




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heroImage: ImageView = itemView.findViewById(R.id.hero_image)
        var checkBox : CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_recyclerview_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = childModelArrayList.bitmaps[position]
        val heroImage = holder.heroImage
        var checkBox = holder.checkBox
        var clicked = false

        heroImage.setImageBitmap(currentItem.bitmap)

        heroImage.setOnClickListener{
            if(position != 0){
                if(clicked){
                    checkBox.isChecked = false
                    list.remove(currentItem.id)
                    fragmentManager.setFragmentResult("selectedImagesId",  bundleOf("idList" to list))

                    clicked = false
                }else{
                    checkBox.isChecked = true
                    list.add(currentItem.id)
                    fragmentManager.setFragmentResult("selectedImagesId",  bundleOf("idList" to list))

                    clicked = true
                }
                Log.i("id", currentItem.id.toString())

            }
        }
    }

    override fun getItemCount(): Int {
        return childModelArrayList.bitmaps.size
    }

}