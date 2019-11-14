package com.example.goodnote.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.goodnote.R
import com.example.goodnote.database.models.Note
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.utils.DUMMY_TEXT
import com.example.goodnote.utils.Injectors
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListFragment : Fragment() {

    private val TAG = NoteListFragment::class.java.simpleName

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let{
            noteViewModel = Injectors.getNoteViewModel1(it)
            adapter = NoteListRecyclerViewAdapter(it)
        }

        noteViewModel.notes.observe(this, Observer { notes ->
            notes?.let {
                adapter.setNotes(it)
            }
        })

        /*noteViewModel.getNotes2().observe(this, Observer { notes ->
            adapter.setNotes(notes)
        })*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.notes_list_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.notes_list_recycler_view)
        recyclerView.let{
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }

        val fab: FloatingActionButton = rootView.findViewById(R.id.fabAdd)
        fab.setOnClickListener {
            Toast.makeText(context, "Open add note!", Toast.LENGTH_LONG).show()
            noteViewModel.addNote(Note("epistolary?", DUMMY_TEXT))
        }

        return rootView
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun getInstance() =
            NoteListFragment().apply {
                arguments = Bundle().apply {
                    putString("arg1", "")
                    putString("arg1", "")
                }
            }
    }
}
