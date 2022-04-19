package no.android.androidexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChildAdapter(private val context: Context, private val allLists : List<AllLists>) : RecyclerView.Adapter<ChildAdapter.MainViewHolder>(){

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var listName : TextView
        var itemRecycler : RecyclerView

        init {
            listName = itemView.findViewById(R.id.list_name)
            itemRecycler = itemView.findViewById(R.id.list_recycler)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.childrecycle, parent, false)

        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.listName.text = allLists[position].listName

        setListsRecycler(holder.itemRecycler, allLists[position].listV)

    }

    override fun getItemCount(): Int {
        return allLists.size
    }

    private fun setListsRecycler(
        recyclerView: RecyclerView,
        catList: List<Cats>
    ) {
        val itemRecyclerAdapter = ImageAdapter(context, catList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
    }
}