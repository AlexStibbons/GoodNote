package com.example.goodnote.ui.tagList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.ui.viewModels.TagViewModel
import com.example.goodnote.utils.Injectors
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TagListFragment : Fragment() {

    private val TAG = TagListFragment::class.java.simpleName

    private lateinit var tagViewModel: TagViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var tagsAdapter: TagListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parent = requireActivity()
        tagViewModel = Injectors.getTagViewModel(parent)
        tagsAdapter = TagListRecyclerViewAdapter(clickedTag)

        tagViewModel.tags.observe(this, Observer { tags ->
            tags ?: return@Observer

            tagsAdapter.setTags(tags)
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // should observer be here with
        // getLifecycleOwner() as owner?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.notes_list_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.notes_list_recycler_view)
        recyclerView.apply {
            adapter = tagsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        val fab: FloatingActionButton = rootView.findViewById(R.id.fabAdd)
        fab.hide()

        return rootView
    }

    companion object {
        fun getInstance() =
            TagListFragment().apply {
                arguments = Bundle().apply {
                    putString("a", "a")
                }
            }
    }

    interface onTagClicked {
        fun onTagClick(id: String)
        fun onTagLongPress(id: String)
    }

    val clickedTag = object: onTagClicked {
        override fun onTagClick(id: String) {
            Log.e("FRGM:", "Tag id is $id")
            Toast.makeText(requireActivity(), "Tag is clicked", Toast.LENGTH_SHORT).show()
        }

        override fun onTagLongPress(id: String) {
            Log.e("FRGM: before", "Tag id is $id")
            tagViewModel.deleteTag(id)
            Toast.makeText(requireActivity(), "long click", Toast.LENGTH_SHORT).show()
            Log.e("FRGM: after", "Tag id is $id")
        }

    }
}