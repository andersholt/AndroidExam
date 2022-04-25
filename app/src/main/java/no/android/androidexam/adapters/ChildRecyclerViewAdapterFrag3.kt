package no.android.androidexam.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.R
import no.android.androidexam.ResultImages

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
                    fragmentManager.setFragmentResult("selectedImagesId",
                        bundleOf("idList" to list)
                    )

                    clicked = false
                }else{
                    checkBox.isChecked = true
                    list.add(currentItem.id)
                    fragmentManager.setFragmentResult("selectedImagesId",
                        bundleOf("idList" to list)
                    )

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