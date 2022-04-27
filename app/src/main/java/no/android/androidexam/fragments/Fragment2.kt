package no.android.androidexam.fragments


import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.*
import no.android.androidexam.adapters.ChildRecyclerViewAdapter
import java.io.ByteArrayOutputStream


class Fragment2 : Fragment() {
    private var childRecyclerView: RecyclerView? = null
    private var childAdapter: RecyclerView.Adapter<*>? = null
    private var childModelArrayList: ArrayList<ImageLinks> = ArrayList()
    private var childLayoutManager: RecyclerView.LayoutManager? = null
    private var bundleRecyclerViewState : Bundle? = null
    lateinit var res: ResData
    private var listState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childModelArrayList.add(ImageLinks("https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png", "https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2, container, false)
        var bitmaps: ArrayList<Bitmap> = ArrayList()
        val dbHelper = DbHelper(requireContext())

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
            try {
                val stream = ByteArrayOutputStream()
                res.originalImage.bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image = stream.toByteArray()

                dbHelper.writableDatabase.insert("bitmapsParent", null, ContentValues().apply {
                    put("bitmapImage", image)
                })
                //Getting the primarykey
                val cursor = dbHelper.writableDatabase.query(
                    "bitmapsParent",
                    arrayOf("id"),
                    null, null, null, null, "id desc", "1"
                )
                cursor.moveToFirst()
                val id = cursor.getInt(0)
                cursor.close()

                for (item in bitmaps) {
                    val stream = ByteArrayOutputStream()
                    item.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image = stream.toByteArray()

                    dbHelper.writableDatabase.insert(
                        "bitmapsChildren",
                        null,
                        ContentValues().apply {
                            put("bitmapImage", image)
                            put("parentId", id)
                        })
                }
            }catch (e : UninitializedPropertyAccessException){
                Toast.makeText(activity,"Please select pictures to save",Toast.LENGTH_SHORT).show()
            }
        }
        return view
   }


/*

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
         listState = childLayoutManager?.onSaveInstanceState();
        outState.putParcelable("LIST_STATE_KEY", listState);
        Log.i("onsavestate", "OnSaveState")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            listState = savedInstanceState.getParcelable("LIST_STATE_KEY")
        else{
            Log.i("error", "state restored error")
        }

    }


    */
/*override fun onPause() {
        super.onPause()
        bundleRecyclerViewState = Bundle()
        val listState: Parcelable? = childRecyclerView?.getLayoutManager()?.onSaveInstanceState()
        bundleRecyclerViewState!!.putParcelable("recycler_state", listState)
        Log.i("onPause", "Fragment2")
    }
*//*

    override fun onResume() {
        super.onResume()

        if (listState != null) {
           childLayoutManager.onre
        }
        Log.i("onResume", "Fragment2")
    }
*/

}

