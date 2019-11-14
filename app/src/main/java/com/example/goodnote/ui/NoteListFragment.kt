package com.example.goodnote.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    private lateinit var notesAdapter: NoteListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parent = requireActivity()
        noteViewModel = Injectors.getNoteViewModel1(parent)
        notesAdapter = NoteListRecyclerViewAdapter()

        noteViewModel.notes.observe(this, Observer { notes ->
            notes ?: return@Observer

            notesAdapter.setNotes(notes)

        })

        /*noteViewModel.getNotes2().observe(this, Observer { notes ->
            notesAdapter.setNotes(notes)
        })*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.notes_list_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.notes_list_recycler_view)
        recyclerView.apply {
            this.adapter = notesAdapter
            layoutManager = LinearLayoutManager(requireActivity())
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
