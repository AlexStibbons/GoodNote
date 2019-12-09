package com.example.goodnote.ui.noteDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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
import com.example.goodnote.utils.toNoteDetailsModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteDetails : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var text: EditText
    lateinit var chipGroup: ChipGroup
    lateinit var autocomplete: AutoCompleteTextView

    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private var allTags: MutableList<TagModel> = ArrayList()
    private var note  = NoteDetailsModel(title = "", text = "", tags = mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("DETAILS", "on create")
        Log.e("onCreate", "created note: ${note.noteId}, ${note.title}")

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID) ?: ""
        Log.e("DETAILS", "on create: $noteId")

        noteDetailsViewModel = Injectors.getNoteDetailsViewModel(this)

        noteDetailsViewModel.existingTags.observe(this, Observer {
            it ?: return@Observer
            allTags.clear()
            allTags.addAll(it)
            Log.e("observer", "tags: ${it.size}, existing: ${allTags.size}")
        })

        title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        chipGroup = findViewById(R.id.notes_details_tags_group)
        autocomplete = findViewById(R.id.notes_details_autocomplete)

        if (noteId.isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                note = noteDetailsViewModel.getNoteById(noteId).await()
                getForNote(note)
            }
        }

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
            note.tags.add(newTag)
            addChip(newTag)
            noteDetailsViewModel.addTag(newTag)
        }

        if (allTags.any { it.name == name }){
            val tag = allTags.first { it.name == name }
            note.tags.add(tag)
            addChip(tag)
        }
    }

    private fun addChip(tag: TagModel) {
        val chip = Chip(this).apply {
            text = tag.name
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                note.tags.remove(tag)
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
            note.tags.add(tag)
            addChip(tag)
        }

        autocomplete.addTextChangedListener {
            if (it != null && it.isEmpty()) return@addTextChangedListener

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