package com.example.goodnote.ui.noteDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.ui.viewModels.NoteDetailsViewModel
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class NoteDetails : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var text: EditText
    lateinit var chipGroup: ChipGroup
    lateinit var autocomplete: AutoCompleteTextView

    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private var allTags: MutableList<TagModel> = ArrayList()
    private var note = NoteDetailsModel("-1", "", "", mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("onCreate", "DEFAULT NOTE: ${note.noteId}, ${note.title}")

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID) ?: ""
        Log.e("DETAILS EXTRA", "on create: $noteId")

        noteDetailsViewModel = Injectors.getNoteDetailsViewModel(this)

        noteDetailsViewModel.noteToEdit.observe(this, Observer {
            it ?: return@Observer
            note = it
            getForNote(note)
            Log.e("OBS", "NOTE IS: in note ${note.title}; in it: ${it.title}")
        })

        noteDetailsViewModel.existingTags.observe(this, Observer {
            it ?: return@Observer
            allTags.clear()
            allTags.addAll(it)
        })

        title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        chipGroup = findViewById(R.id.notes_details_tags_group)
        autocomplete = findViewById(R.id.notes_details_autocomplete)

        noteDetailsViewModel.getNoteById(noteId)

        val autoAdapter = ArrayAdapter<TagModel>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            allTags
        )

        autocomplete.setAdapter(autoAdapter)

        setupAutocomplete()
    }

    override fun onBackPressed() {
        note = note.copy(
            noteId = note.noteId,
            title = title.text.toString(),
            text = text.text.toString(),
            tags = note.tags
        )

        if (text.text.isNotBlank() || title.text.isNotBlank() || note.tags.isNotEmpty()) {
            noteDetailsViewModel.saveNote(note)
        } else { super.onBackPressed() }

        noteDetailsViewModel.onNoteSaved.observe(this, Observer {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        })
    }

    private fun getForNote(note: NoteDetailsModel) {
        title.setText(note.title)
        text.setText(note.text)
        note.tags.forEach {
            addChip(it)
        }
    }

    private fun addTag(name: String) {
        if (name.isBlank()) return

        if (allTags.none { it.name == name }) {
            val newTag = TagModel(name = name)
            addChip(newTag)
            noteDetailsViewModel.saveTag(newTag)
            noteDetailsViewModel.addTagForNote(note.noteId, newTag)
        }

        if (allTags.any { it.name == name }){
            val tag = allTags.first { it.name == name }
            addChip(tag)
            noteDetailsViewModel.addTagForNote(note.noteId, tag)
            Log.e("ADD TAG", "${note.title} and id ${note.noteId}, ${tag.name}")
        }
    }

    private fun addChip(tag: TagModel) {
        val chip = Chip(this).apply {
            text = tag.name
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                noteDetailsViewModel.deleteTagForNote(note.noteId, tag.tagId)
                chipGroup.removeView(this)
            }
        }
        chipGroup.addView(chip)
    }

    private fun setupAutocomplete() {

        autocomplete.setOnItemClickListener {adapterView, _, position, _  ->
            autocomplete.text = null
            val tag: TagModel = adapterView.getItemAtPosition(position) as TagModel
            noteDetailsViewModel.addTagForNote(note.noteId, tag)
            addChip(tag)
        }

        autocomplete.addTextChangedListener {
            if (it?.isEmpty() == true) return@addTextChangedListener

            if (it?.last() == ',') {
                val name = it.substring(0, it.length - 1)
                addTag(name)
                autocomplete.text = null
            }
        }

        autocomplete.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = v.text.toString()
                v.text = null
                addTag(name)
                return@setOnEditorActionListener true
            }
            false
        }
    }
}