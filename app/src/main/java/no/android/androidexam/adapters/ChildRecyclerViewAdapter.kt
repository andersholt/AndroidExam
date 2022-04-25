package no.android.androidexam.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.drawToBitmap
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.android.androidexam.ImageLinks
import no.android.androidexam.R

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
                fragmentManager.setFragmentResult("selectedImages", bundleOf("bitmapList" to list))
                clicked = false
            }else{
                bitmapImage = heroImage.drawToBitmap()
                checkBox.isChecked = true
                list.add(bitmapImage!!)
                fragmentManager.setFragmentResult("selectedImages", bundleOf("bitmapList" to list))
                clicked = true
            }
        }
    }

    override fun getItemCount(): Int {
        return childModelArrayList.size
    }
}