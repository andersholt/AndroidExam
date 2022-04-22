package no.android.androidexam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.DbHelper
import no.android.androidexam.ParentModel
import no.android.androidexam.ParentRecyclerViewAdapter
import no.android.androidexam.R

class Fragment3 : Fragment() {
    private var parentRecyclerView: RecyclerView? = null
    private var ParentAdapter: RecyclerView.Adapter<*>? = null
    var parentModelArrayList: ArrayList<ParentModel> = ArrayList()
    private var parentLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentModelArrayList.add(ParentModel("Category1"));
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "Fragment 3 onCreateView", Toast.LENGTH_SHORT).show()
        var dbHelper = DbHelper(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment3, container, false)



        val cursor = dbHelper.writableDatabase.query(
            "bitmapsParent",
            arrayOf("id", "bitmapImage"),
            null,null,null,null,null,null
        )
        while (cursor.moveToNext()){
            val test = cursor.getBlob(cursor.getColumnIndexOrThrow("bitmapImage"))
            Log.i("Test", test.toString())
        }


/*
        parentRecyclerView = view?.findViewById(R.id.Parent_recyclerView);
        parentRecyclerView!!.setHasFixedSize(true)
        parentLayoutManager = LinearLayoutManager(context)
        ParentAdapter = context?.let { ParentRecyclerViewAdapter(parentModelArrayList, it) }
        parentRecyclerView!!.layoutManager = parentLayoutManager
        parentRecyclerView!!.adapter = ParentAdapter
        ParentAdapter?.notifyDataSetChanged()

 */

        return view
    }
}
