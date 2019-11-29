package com.example.goodnote.ui.noteList

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import com.example.goodnote.database.entityModels.NoteEntity
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.noteDetails.NoteDetails
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.utils.DUMMY_TEXT
import com.example.goodnote.utils.EMPTY_NONTE_ID
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListFragment : Fragment() {

    private val TAG = NoteListFragment::class.java.simpleName

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NoteListRecyclerViewAdapter
    private var confirmationDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // IF it receives TAG ID, THEN show notes that contain that tag

        // IF it received default/empty tag id, THEN show all notes

        val parent = requireActivity()
        noteViewModel = Injectors.getNoteViewModel(parent)
        notesAdapter = NoteListRecyclerViewAdapter(clickedNote)


        noteViewModel.repoNotes.observe(this, Observer { notes ->
            notes ?: return@Observer
            Log.e("FRGM OBSERVER", "Notes are ${notes.size}")
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
            Log.e("FRAGMENT", "SAVING NOTE?")
            noteViewModel.saveNote(NoteDetailsModel(
                title = "",
                text = "TEST TXT"
            ))

            // change to start activity for result
            startActivity(Intent(activity, NoteDetails::class.java).apply { putExtra(EXTRA_NOTE_ID, EMPTY_NONTE_ID) })
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
            startActivity(intent)
        }

        override fun onNoteLongPress(id: String) {
            showConfirmationDialog(id)
            //noteViewModel.deleteNote(id)
        }
    }

    private fun showConfirmationDialog(noteId: String){
        confirmationDialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage("Are you sure you want to delete this note?")
                setTitle("Deletion")
                setPositiveButton("Yes",
                    DialogInterface.OnClickListener{dialog, id ->
                        Toast.makeText(it, "clicked to delete", Toast.LENGTH_SHORT).show()
                        noteViewModel.deleteNote(noteId)
                    }
                )
                setNegativeButton("No", null)
            }

            // should the build be in the beginning somewhere, in onCreate maybe, and only
            // the builder.create() in the long press?
            // or just confirmationDialog.show()?
            builder.create()
        }
    }
}
