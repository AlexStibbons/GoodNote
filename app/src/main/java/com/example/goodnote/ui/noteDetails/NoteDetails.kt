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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.databinding.NotesDetailsActivityBinding
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.ui.viewModels.NoteDetailsViewModel
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yalantis.filter.adapter.FilterAdapter
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.core.content.ContextCompat
import com.yalantis.filter.widget.FilterItem
import android.graphics.Color
import android.widget.Toast
import com.yalantis.filter.listener.FilterListener
import com.yalantis.filter.widget.Filter


class NoteDetails : AppCompatActivity() {

   // lateinit var title: EditText
    lateinit var text: EditText
    lateinit var chipGroup: ChipGroup
    lateinit var autocomplete: AutoCompleteTextView
    lateinit var binding: NotesDetailsActivityBinding
    lateinit var filtertag: Filter<TagModel>

    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private var allTags: MutableList<TagModel> = ArrayList()
    private var note = NoteDetailsModel("-1", "", "", mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.notes_details_activity)
        binding.setLifecycleOwner(this)

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID) ?: ""

        noteDetailsViewModel = Injectors.getNoteDetailsViewModel(this, noteId)
        binding.viewModel = noteDetailsViewModel

        filtertag = findViewById(R.id.note_details_filter)

        noteDetailsViewModel.noteToEdit.observe(this, Observer {
            it ?: return@Observer
            note = it
            getForNote(note)
            Log.e("DETAILS", "GET called")
        })

        noteDetailsViewModel.existingTags.observe(this, Observer {
            it ?: return@Observer
            allTags.clear()
            allTags.addAll(it)
        })

       // title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        chipGroup = findViewById(R.id.notes_details_tags_group)
        autocomplete = findViewById(R.id.notes_details_autocomplete)

        filtertag.apply {
            val fake = listOf(TagModel("FAKE", "one"), TagModel("fake2", "two"), TagModel("fake3", "fake"))
            adapter = TagAdapter(fake)
            listener = tagFilterListener
            noSelectedItemText = "no tags yet"
        }
        filtertag.build()

        val autoAdapter = ArrayAdapter<TagModel>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            allTags
        )

        autocomplete.setAdapter(autoAdapter)

        setupAutocomplete()
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

    private fun getForNote(note: NoteDetailsModel) {
        //title.setText(note.title)
        binding.notesDetailsTitle.setText(note.title)
        text.setText(note.text)
        chipGroup.removeAllViews()
        note.tags.forEach {
            addChip(it)
        }
    }

    private fun addTag(name: String) {
        if (name.isBlank()) return

        if (allTags.none { it.name == name }) {
            val newTag = TagModel(name = name)
            noteDetailsViewModel.saveTag(newTag)
            noteDetailsViewModel.addTagForNote(note.noteId, newTag)
        }

        if (allTags.any { it.name == name }) {
            val tag = allTags.first { it.name == name }
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
            }
        }
        chipGroup.addView(chip)
    }

    private fun setupAutocomplete() {

        autocomplete.setOnItemClickListener { adapterView, _, position, _ ->
            autocomplete.text = null
            val tag: TagModel = adapterView.getItemAtPosition(position) as TagModel
            noteDetailsViewModel.addTagForNote(note.noteId, tag)
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

    // YALANTIS VERSION 1 //

    inner class TagAdapter(items: List<TagModel>) : FilterAdapter<TagModel>(items) {

        override fun createView(position: Int, item: TagModel): FilterItem {
            val filterItem = FilterItem(this@NoteDetails)

            filterItem.strokeColor = Color.BLACK
            filterItem.textColor = Color.WHITE
            filterItem.checkedTextColor =
                ContextCompat.getColor(this@NoteDetails, android.R.color.white)
            filterItem.color = ContextCompat.getColor(this@NoteDetails, R.color.windowBackground)
            filterItem.checkedColor = ContextCompat.getColor(this@NoteDetails, R.color.colorAccent)
            filterItem.text = item.name
            filterItem.deselect()

            return filterItem
        }

    }

    val tagFilterListener = object: FilterListener<TagModel> {
        override fun onFilterDeselected(item: TagModel) {
            Toast.makeText(this@NoteDetails, "Filter deselected", Toast.LENGTH_SHORT).show()
        }

        override fun onFilterSelected(item: TagModel) {
            Toast.makeText(this@NoteDetails, "Filter selected", Toast.LENGTH_SHORT).show()
        }

        override fun onFiltersSelected(filters: java.util.ArrayList<TagModel>) {
        }

        override fun onNothingSelected() {
        }

    }
}