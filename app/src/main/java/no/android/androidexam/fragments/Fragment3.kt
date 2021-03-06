package no.android.androidexam.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.*
import no.android.androidexam.adapters.ParentRecyclerViewAdapter

class Fragment3 : Fragment() {
    private var parentRecyclerView: RecyclerView? = null
    private var parentAdapter: RecyclerView.Adapter<*>? = null
    private var parentModelArrayList: ArrayList<ParentModel> = ArrayList()
    private var parentLayoutManager: RecyclerView.LayoutManager? = null
    private var listOfResultImages = ArrayList<ResultImages>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DbHelper(requireContext())

        var idList = ArrayList<Int>()
        parentFragmentManager.setFragmentResultListener(
            "selectedImagesId",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            idList = bundle.get("idList") as ArrayList<Int>
            Log.i("IDs", idList.toString())
        }


        val view = inflater.inflate(R.layout.fragment3, container, false)
        loadRecyclerView(view)

        val button = view.findViewById<Button>(R.id.submitButton)
        button.setOnClickListener {
            for (item in idList){
                dbHelper.writableDatabase.delete("bitmapsChildren", "id = $item", null)
            }
            parentRecyclerView =  null
            parentAdapter = null
            parentModelArrayList = ArrayList()
            parentLayoutManager= null
            listOfResultImages = ArrayList<ResultImages>()
            loadDatabase()
            loadRecyclerView(view)
        }

        return view
    }

    private fun loadRecyclerView(view: View){
        parentRecyclerView = view.findViewById(R.id.Parent_recyclerView)
        parentRecyclerView!!.setHasFixedSize(true)
        parentLayoutManager = LinearLayoutManager(context)
        parentAdapter = context?.let {
            ParentRecyclerViewAdapter(parentModelArrayList,
                it, listOfResultImages, parentFragmentManager)
        }
        parentRecyclerView!!.layoutManager = parentLayoutManager
        parentRecyclerView!!.adapter = parentAdapter
        parentAdapter?.notifyDataSetChanged()
    }

    private fun loadDatabase(){
        val dbHelper = DbHelper(requireContext())

        var cursor = dbHelper.writableDatabase.query(
            "bitmapsParent",
            arrayOf("id", "bitmapImage"),
            null,null,null,null,null,null
        )
        while (cursor.moveToNext()){
            val bitmapImage = cursor.getBlob(cursor.getColumnIndexOrThrow("bitmapImage"))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))

            val bitmap = BitmapFactory.decodeByteArray(bitmapImage,0,bitmapImage.size)
            parentModelArrayList.add(ParentModel(bitmap, id))
        }
        cursor.close()

        cursor = dbHelper.writableDatabase.query(
            "bitmapsChildren",
            arrayOf("bitmapImage", "parentId", "id"),
            null,null,null,null,null,null
        )


        while (cursor.moveToNext()){
            val bitmapImage = cursor.getBlob(cursor.getColumnIndexOrThrow("bitmapImage"))
            val parentId = cursor.getInt(cursor.getColumnIndexOrThrow("parentId"))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))

            val bitmap = BitmapFactory.decodeByteArray(bitmapImage,0,bitmapImage.size)
            var edited = false

            for(item in listOfResultImages){
                if (item.foreignKey == parentId){
                    item.bitmaps.add(ResultImage(id, bitmap))
                    edited = true
                }
            }
            if(!edited){
                val newListOfBitmaps = ArrayList<ResultImage>()
                newListOfBitmaps.add(ResultImage(id, bitmap))
                val listOfBitmaps2 = ResultImages(parentId, newListOfBitmaps)
                listOfResultImages.add(listOfBitmaps2)
            }
        }
        cursor.close()
    }
}
