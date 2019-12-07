package com.example.goodnote.ui.noteDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
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
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.ui.viewModels.TagViewModel
import com.example.goodnote.utils.*
import com.example.goodnote.utils.toNoteDetailsModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.*

class NoteDetails : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var text: EditText
    lateinit var chipGroup: ChipGroup
    lateinit var autocomplete: AutoCompleteTextView

    private var existingTags: MutableList<TagModel> = ArrayList()
    private lateinit var tagViewModel: TagViewModel
    private lateinit var noteViewModel: NoteViewModel

    private var noteToEdit = NoteDetailsModel(title = "", text = "", tags = mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("DETAILS", "on create")
        Log.e("onCreate", "created note: ${noteToEdit.noteId}, ${noteToEdit.title}")

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID) ?: ""
        Log.e("DETAILS", "on create: $noteId")

        tagViewModel = Injectors.getTagViewModel(this)
        noteViewModel = Injectors.getNoteViewModel(this)

        tagViewModel.tags.observe(this, Observer {
            it ?: return@Observer
            existingTags.clear()
            existingTags.addAll(it)
            Log.e("observer", "tags: ${it.size}, existing: ${existingTags.size}")
        })

        title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        chipGroup = findViewById(R.id.notes_details_tags_group)
        autocomplete = findViewById(R.id.notes_details_autocomplete)

        if (noteId.isNotBlank()) {
           CoroutineScope(Dispatchers.Main).launch {
               noteToEdit = noteViewModel.findNoteById(noteId).await().toNoteDetailsModel()
               getForNote(noteToEdit)
           }
        }

        // for autocomplete

        // get all tags in adapter
        val autoAdapter = ArrayAdapter<TagModel>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            existingTags
        )
        // set adapter
        autocomplete.setAdapter(autoAdapter)

        // select from auto complete; selecting existing tags
        autocomplete.setOnItemClickListener {adapterView, _, position, _  ->
            autocomplete.text = null
            val tag: TagModel = adapterView.getItemAtPosition(position) as TagModel
            noteToEdit.tags.add(tag)
            addChip(tag)
        }

        // space or comma is detected
        autocomplete.addTextChangedListener {
            if (it != null && it.isEmpty()) return@addTextChangedListener

            if (it?.last() == ',') {
                val name = it.substring(0, it.length - 1)
                addTag(name)
                autocomplete.text = null
            }
        }

        // done button is pressed
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

    override fun onBackPressed() {

        Log.e("on back", "called")
        noteToEdit = noteToEdit.copy(noteId = noteToEdit.noteId,
            title = title.text.toString(),
            text = text.text.toString(),
            tags = noteToEdit.tags)
        Log.e("on back", "tags: ${noteToEdit.tags.size}")

        // updating list issue
        if (text.text.isNotBlank() || title.text.isNotBlank() || noteToEdit.tags.isNotEmpty()) {
            noteViewModel.saveNote(noteToEdit)
        }
        noteViewModel.onNoteSaved.observe(this, Observer {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        // open for edit?
    }

    private fun getForNote(note: NoteDetailsModel) {
        title.setText(note.title)
        text.setText(note.text)
            note.tags.forEach {
                val tagM = it
                val chip = Chip(this).apply {
                    text = it.name
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        note.tags.remove(tagM)
                        noteViewModel.deleteTagForNote(note.noteId, tagM.tagId)
                        chipGroup.removeView(this)
                    }
                }
                chipGroup.addView(chip)
            }
    }

    private fun addTag(name: String) {
        if (name.isBlank()) return

        if (existingTags.none { it.name == name }) {
            val newTag = TagModel(name = name)
            noteToEdit.tags.add(newTag)
            addChip(newTag)
            tagViewModel.addTag(newTag)
        }

        if (existingTags.any { it.name == name }){
            val tag = existingTags.first { it.name == name }
            noteToEdit.tags.add(tag)
            addChip(tag)
        }
    }

    private fun addChip(tag: TagModel) {
        val chip = Chip(this).apply {
            text = tag.name
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                noteToEdit.tags.remove(tag)
                noteViewModel.deleteTagForNote(noteToEdit.noteId, tag.tagId)
                chipGroup.removeView(this)
            }
        }
        chipGroup.addView(chip)
    }
}