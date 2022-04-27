package no.android.androidexam.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.ParentModel
import no.android.androidexam.R
import no.android.androidexam.ResultImage
import no.android.androidexam.ResultImages

class ParentRecyclerViewAdapter(
    private val parentModelArrayList: ArrayList<ParentModel>,
    private var cxt: Context,
    private val listOfResultImages: ArrayList<ResultImages>,
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

        for(item in listOfResultImages){
            Log.i("Items", item.foreignKey.toString())

            if(item.foreignKey == id){
                item.bitmaps.add(0, ResultImage(id, currentItem.parentBitmap()))
                holder.childRecyclerView.layoutManager = layoutManager
                holder.childRecyclerView.setHasFixedSize(true)
                val childRecyclerViewAdapter =
                    ChildRecyclerViewAdapterFrag3(item, fragmentManager, list)
                holder.childRecyclerView.adapter = childRecyclerViewAdapter
            }
        }
    }
}