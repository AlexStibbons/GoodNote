package com.example.goodnote.ui.noteDetails

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.ui.viewModels.TagViewModel
import com.example.goodnote.utils.EMPTY_NONTE_ID
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.example.goodnote.utils.Injectors
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.*

class NoteDetails : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var text: EditText
    lateinit var chipGroup: ChipGroup

    private var existingTags: MutableList<TagModel> = ArrayList()// for choosing or adding a new tag
    private lateinit var tagViewModel: TagViewModel // for getting/removing tag for note
    private lateinit var noteViewModel: NoteViewModel // for getting/saving/editing/creating note

    private var noteTags: MutableList<TagModel> = ArrayList()

    var foundNote: NoteDetailsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("DETAILS", "on create")

        tagViewModel = Injectors.getTagViewModel(this)
        noteViewModel = Injectors.getNoteViewModel(this)

        tagViewModel.tags.observe(this, Observer {
            it ?: return@Observer
            existingTags.clear()
            existingTags.addAll(it)
            Log.e("observer", "tags: ${it.size}, existing: ${existingTags.size}")
        })

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID)
        Log.e("DETAILS", "on create: $noteId")

        title = findViewById(R.id.notes_details_title)
        text = findViewById(R.id.notes_details_text)
        chipGroup = findViewById(R.id.notes_details_tags_group)

        val testChip = Chip(this)
        testChip.text = "test chippy"
        testChip.isCloseIconVisible = true
        chipGroup.addView(testChip)

        val testList: List<TagModel> = listOf(TagModel("temp1", "temp"), TagModel("temp2", "temp2"))
        testList.forEach {
            Log.e("for each", "called")
            val chip = Chip(this).apply {
                this.text = it.name
                this.isCloseIconVisible = true
                this.setOnCloseIconClickListener {
                    // remove tag from this note
                }
            }
            chipGroup.addView(chip)
        }

        Toast.makeText(this, "NoteEntity ID is $noteId", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("on back", "called")
        // list does not update
        if (text.text.isNotBlank() || title.text.isNotBlank()) {
            noteViewModel.saveNote(
                NoteDetailsModel(
                    title = title.text.toString(),
                    text = text.text.toString(),
                    tags = noteTags
                )
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        // open for edit?
    }
}