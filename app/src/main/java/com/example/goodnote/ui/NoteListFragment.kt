package com.example.goodnote.ui

import android.content.Intent
import android.net.Uri
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
        noteViewModel = Injectors.getNoteViewModel(parent)
        notesAdapter = NoteListRecyclerViewAdapter(clickedNote)

        noteViewModel.notes.observe(this, Observer { notes ->
            notes ?: return@Observer

            notesAdapter.setNotes(notes)

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // should observer be here with
        // getLifecycleOwner() as owner?

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

    companion object {
        fun getInstance() =
            NoteListFragment().apply {
                arguments = Bundle().apply {
                    putString("arg1", "")
                    putString("arg1", "")
                }
            }
    }

    interface onNoteClick {
        fun onNoteClick(id: Int)
        fun onNoteLongPress(id: Int)
    }

    val clickedNote = object: onNoteClick {
        override fun onNoteClick(id: Int) {
            Log.e("FRAGMENT", "on note click called")
            val intent = Intent(activity, NoteDetails::class.java).also {
                it.putExtra("noteId", id)
            }
            startActivity(intent)
        }

        override fun onNoteLongPress(id: Int) {
            // delete from DB, update list
        }
    }

}
