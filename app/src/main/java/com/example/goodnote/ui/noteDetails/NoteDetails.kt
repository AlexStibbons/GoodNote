package com.example.goodnote.ui.noteDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.databinding.NotesDetailsActivityBinding
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.ui.viewModels.NoteDetailsViewModel
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.example.goodnote.utils.toTagsString
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class NoteDetails : AppCompatActivity() {

   // lateinit var title: EditText
    lateinit var text: EditText
    lateinit var addTagsBtn: Button
    lateinit var tagsText: TextView
    lateinit var binding: NotesDetailsActivityBinding

    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private var note = NoteDetailsModel("-1", "", "", mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.notes_details_activity)
        binding.setLifecycleOwner(this)

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID) ?: ""

        noteDetailsViewModel = Injectors.getNoteDetailsViewModel(this, noteId)
        binding.viewModel = noteDetailsViewModel

        noteDetailsViewModel.noteToEdit.observe(this, Observer {
            it ?: return@Observer
            note = it
            populateNoteDetails(note)
            Log.e("DETAILS", "GET called")
        })

       // title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        addTagsBtn = findViewById(R.id.add_tags_button)
        tagsText = findViewById(R.id.tags_text)

        addTagsBtn.setOnClickListener {
            val dialog: AddTagsDialogFragment = AddTagsDialogFragment.getInstance(noteId)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun onBackPressed() {
        if (text.text.isNotBlank() || binding.notesDetailsTitle.text.isNotBlank() || note.tags.isNotEmpty()) {
            noteDetailsViewModel.saveNote()
        } else {
            super.onBackPressed()
        }

        noteDetailsViewModel.onNoteSaved.observe(this, Observer {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        })
    }

    private fun populateNoteDetails(note: NoteDetailsModel) {
        //title.setText(note.title)
        binding.notesDetailsTitle.setText(note.title)
        tagsText.text =  note.tags.toTagsString().ifBlank { "No tags yet!" }
        text.setText(note.text)
    }
}