package no.android.androidexam

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ItemViewHolder>() {

class ItemViewHolder(private val view:View) : RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val liner1 = LinearLayout(parent.context)
        liner1.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f)

        val nameView: TextView = TextView(parent.context)
        nameView.setPadding(10, 0, 10, 0)

        val imageView: ImageView = ImageView(parent.context)
        imageView.setPadding(10,0,10,0)

        liner1.addView(imageView)
        liner1.addView(nameView)

        return ItemViewHolder(liner1)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setTag(position)
        (((holder.itemView as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0) as TextView).setText("Mjau")
        (((holder.itemView as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(1) as ImageView).setBackgroundColor(Color.BLACK)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}