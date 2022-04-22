package no.android.androidexam.fragments


import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.*
import java.io.ByteArrayOutputStream


class Fragment2 : Fragment() {
    private var childRecyclerView: RecyclerView? = null
    private var childAdapter: RecyclerView.Adapter<*>? = null
    var childModelArrayList: ArrayList<ImageLinks> = ArrayList()
    private var childLayoutManager: RecyclerView.LayoutManager? = null
    lateinit var res: ResData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState
        childModelArrayList.add(ImageLinks("https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png", "https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment2, container, false)
        var bitmaps: ArrayList<Bitmap> = ArrayList()
        var dbHelper = DbHelper(requireContext())

        parentFragmentManager.setFragmentResultListener(
            "requestKey2",
            viewLifecycleOwner
        ) { requestKey, bundle ->
                res = bundle.get("bundleKey2") as ResData
                val imageView:ImageView = view.findViewById(R.id.loaded_image)
                imageView.setImageBitmap(res.originalImage.bitmap)
                childRecyclerView = view?.findViewById(R.id.Parent_recyclerView1)
                childRecyclerView!!.setHasFixedSize(true)
                childLayoutManager = GridLayoutManager(activity, 2)
                childAdapter =
                    context?.let { ChildRecyclerViewAdapter(res.links, parentFragmentManager) }
                childRecyclerView!!.layoutManager = childLayoutManager
                childRecyclerView!!.adapter = childAdapter
                childAdapter?.notifyDataSetChanged()

                val textView:TextView = view.findViewById(R.id.search_engine)
                textView.text = "Searched with: " + res.searchEngine
        }
        parentFragmentManager.setFragmentResultListener(
            "selectedImages",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            bitmaps = bundle.get("bitmapList") as ArrayList<Bitmap>
            Log.i("Bitmap", bitmaps.toString())
        }
        val button: Button = view.findViewById(R.id.submitButton)
        button.setOnClickListener {
            val stream = ByteArrayOutputStream()
            res.originalImage.bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            //This was an issue with sqllite on oneplus phones, had to open and close the app.
            dbHelper.readableDatabase.close();
            dbHelper.writableDatabase.insert("bitmapsParent", null, ContentValues().apply {
                put("bitmapImage", image)
            })

        }
        return view
   }
}

