package no.android.androidexam.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.ImageLinks
import no.android.androidexam.ParentModel
import no.android.androidexam.ParentRecyclerViewAdapter
import no.android.androidexam.R


class Fragment2 : Fragment() {
    private var parentRecyclerView: RecyclerView? = null
    private var parentAdapter: RecyclerView.Adapter<*>? = null
    var parentModelArrayList: ArrayList<ParentModel> = ArrayList()
    private var parentLayoutManager: RecyclerView.LayoutManager? = null
    lateinit var result: ArrayList<ImageLinks>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentModelArrayList.add(ParentModel("Category1"))

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var res: ArrayList<ImageLinks> = ArrayList()
        parentFragmentManager.setFragmentResultListener(
            "requestKey2",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            res = bundle.get("bundleKey2") as ArrayList<ImageLinks>

            parentRecyclerView = view?.findViewById(R.id.Parent_recyclerView)
            parentRecyclerView!!.setHasFixedSize(true)
            parentLayoutManager = LinearLayoutManager(context)
            parentAdapter =
                context?.let { ParentRecyclerViewAdapter(parentModelArrayList, it, res) }
            parentRecyclerView!!.layoutManager = parentLayoutManager
            parentRecyclerView!!.adapter = parentAdapter
            parentAdapter?.notifyDataSetChanged()
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment2, container, false)
    }
}
