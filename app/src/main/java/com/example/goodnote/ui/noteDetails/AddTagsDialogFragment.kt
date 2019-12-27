package com.example.goodnote.ui.noteDetails

import android.content.Context
import android.os.Bundle
import android.support.v4.media.RatingCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.ui.viewModels.NoteDetailsViewModel
import com.example.goodnote.utils.EXTRA_NOTE_ID
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class AddTagsDialogFragment : DialogFragment() {

    private lateinit var noteViewModel: NoteDetailsViewModel
    private var noteId: String = ""
    private lateinit var parent: Context

    private lateinit var newTagName: EditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var addBtn: Button
    private lateinit var doneBtn: Button
    private lateinit var noTagsText: TextView

    private var allTags: MutableList<TagModel> = ArrayList()

    companion object {
        fun getInstance(noteVM: NoteDetailsViewModel, noteId: String) =
            AddTagsDialogFragment().apply {
                this.noteViewModel = noteVM
                arguments = Bundle().apply {
                    putString(EXTRA_NOTE_ID, noteId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { noteId = it.getString(EXTRA_NOTE_ID, "") }
        parent = requireActivity()

        noteViewModel.existingTags.observe(this, Observer {
            it ?: return@Observer
            allTags.clear()
            allTags.addAll(it)
        })

        // also observe noteToEdit and you won't need noteId as a paramenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.dialog_add_tags, container, false)

        newTagName = rootView.findViewById(R.id.add_tags_dialog_new_tag)
        chipGroup = rootView.findViewById(R.id.add_tags_dialog_chip_group)
        addBtn = rootView.findViewById(R.id.add_tags_dialog_add_btn)
        doneBtn = rootView.findViewById(R.id.add_tags_dialog_doneBtn)
        noTagsText = rootView.findViewById(R.id.add_tags_dialog_no_tags)
        noTagsText.visibility = View.GONE

        setUpTags()

        return rootView
    }

    fun setUpTags() {
        if (allTags.isNullOrEmpty()) {
            noTagsText.visibility = View.VISIBLE
        }

        allTags.forEach {addChipToGroup(it)}
    }

    fun onTagSelected(tag: TagModel) {
        noteViewModel.addTagForNote(noteId, tag)
    }

    fun onTagDiselected(tag: TagModel) {
        noteViewModel.deleteTagForNote(noteId, tag.tagId)
    }

    fun onTagAdded(name: String) {
        if (allTags.none { it.name == name } || allTags.isNullOrEmpty()) {
            val newTag = TagModel(name = name)
            addChipToGroup(newTag)
            noteViewModel.saveTag(newTag)
            noteViewModel.addTagForNote("id", newTag)
        }
    }

    fun addChipToGroup(tag: TagModel) {
        // if noteToeEdit.tags contains tag -> chip is coloured/chosed
        // else it is not
        // chips are CHOICE chips, NOT filter chips
        val chip = Chip(parent).apply {
            text = tag.name
            isClickable = true
        }
        chipGroup.addView(chip)
    }

}