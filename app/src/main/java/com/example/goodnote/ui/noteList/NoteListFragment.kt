package com.example.goodnote.ui.noteList

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.noteDetails.NoteDetails
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.utils.EMPTY_NONTE_ID
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.example.goodnote.utils.RESULT_NOTE_SAVED
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

        noteViewModel.repoNotes.observe(this, Observer { notes ->
            notes ?: return@Observer
            Log.e("FRGM OBSERVER", "Notes are ${notes.size}")
            notesAdapter.setNotes(notes)

        })
/*
        noteViewModel.addedNote.observe(this, Observer { note ->
            note ?: return@Observer
            noteAdapter.insertNote(note)
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
            // change to start activity for result
            startActivityForResult(Intent(activity, NoteDetails::class.java), RESULT_NOTE_SAVED)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_NOTE_SAVED ) noteViewModel.getAllNotes()
    }

    // can be onItemClicked and extracted as an internal interface
    interface onNoteClick {
        fun onNoteClick(id: String)
        fun onNoteLongPress(id: String)
    }

    val clickedNote = object: onNoteClick {
        override fun onNoteClick(id: String) {
            Log.e("FRAGMENT", "on note click called")
            val intent = Intent(activity, NoteDetails::class.java).apply {
                putExtra(EXTRA_NOTE_ID, id)
            }
            startActivityForResult(intent, RESULT_NOTE_SAVED)
        }

        override fun onNoteLongPress(id: String) {
            showConfirmationDialog(id)
        }
    }

    private fun showConfirmationDialog(noteId: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setCancelable(true)
            setMessage(R.string.delete_note_question)
            setPositiveButton(R.string.yes,
                DialogInterface.OnClickListener { dialog, id ->
                    noteViewModel.deleteNote(noteId)
                    Log.e("CONF DIA", "id is $noteId")
                }
            )
            setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        }
        builder.create().show()
    }

}