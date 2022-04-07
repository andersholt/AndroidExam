package no.android.androidexam

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ImageAdapter( val cats: List<Cats>) : RecyclerView.Adapter<ImageAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView

        init {
            image = view.findViewById(R.id.image)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.imagerow, parent, false)
       /* val ll1: LinearLayout = LinearLayout(parent.context)
        ll1.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f)

        val imageView: ImageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(400, 400)
        imageView.setBackgroundColor(Color.BLACK)
        imageView.setPadding(50,0,20,0)

        ll1.addView(imageView)

        val ll3: LinearLayout = LinearLayout(parent.context)
        ll3.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )

        ll3.weightSum = 1.0f


        ll3.addView(ll1)*/

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val studentInfo: Cats = cats[position]

        holder.image.setImageResource(studentInfo.picture)

/*
        (((holder.itemView as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0) as ImageView).setImageDrawable(holder.itemView.context.getDrawable(studentInfo.picture))
*/

    }

    override fun getItemCount(): Int {
        return 10
    }
}