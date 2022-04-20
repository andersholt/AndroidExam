package no.android.androidexam.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.android.androidexam.*


class Fragment2 : Fragment() {
    private var childRecyclerView: RecyclerView? = null
    private var childAdapter: RecyclerView.Adapter<*>? = null
    var childModelArrayList: ArrayList<ImageLinks> = ArrayList()
    private var childLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childModelArrayList.add(ImageLinks("https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png", "https://user-images.githubusercontent.com/12670730/113008567-6ebdcb80-9177-11eb-91bd-6863196d9cd3.png"))

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

            childRecyclerView = view?.findViewById(R.id.Parent_recyclerView1)
            childRecyclerView!!.setHasFixedSize(true)
            childLayoutManager = GridLayoutManager(activity, 2)
            childAdapter =
                context?.let { ChildRecyclerViewAdapter( res) }
            childRecyclerView!!.layoutManager = childLayoutManager
            childRecyclerView!!.adapter = childAdapter
            childAdapter?.notifyDataSetChanged()
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment2, container, false)
    }
}
