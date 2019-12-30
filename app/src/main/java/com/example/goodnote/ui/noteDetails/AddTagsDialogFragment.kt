package com.example.goodnote.ui.noteDetails

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.media.RatingCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.goodnote.R
import com.example.goodnote.ui.models.NoteDetailsModel
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
    private lateinit var cancelBtn: Button
    private lateinit var noTagsText: TextView

    private var allTags: MutableList<TagModel> = ArrayList()
    private var noteToEdit: NoteDetailsModel = NoteDetailsModel("", "", "")

    // https://developer.android.com/topic/libraries/architecture/viewmodel#implement
    // see section on fragments sharing a view model
    companion object {
        fun getInstance(noteVM: NoteDetailsViewModel, noteId: String) =
            AddTagsDialogFragment().apply {
                this.noteViewModel = noteVM
                arguments = Bundle().apply {
                    putString(EXTRA_NOTE_ID, noteId)
                }
            }
    }

    // leakage?
    // when dialog is dismissed, does it go to onDestroy?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { noteId = it.getString(EXTRA_NOTE_ID, "") }
        parent = requireActivity()

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
        cancelBtn = rootView.findViewById(R.id.add_tags_dialog_cancelBtn)
        noTagsText = rootView.findViewById(R.id.add_tags_dialog_no_tags)
        noTagsText.visibility = View.GONE

        noteViewModel.existingTags.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            allTags.clear()
            allTags.addAll(it)
        })

        noteViewModel.noteToEdit.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            noteToEdit = it
            setUpTags() // but will all existing tags be loaded too? no?
        })

        addBtn.setOnClickListener {
            if (newTagName.text.isNotBlank()) {
                onTagAdded(newTagName.text.toString())
                newTagName.text.clear()
            }
        }
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setUpTags() {
        if (allTags.isNullOrEmpty()) {
            noTagsText.visibility = View.VISIBLE
        }

        allTags.forEach {addChipToGroup(it)}
    }

    private fun onTagSelected(tag: TagModel) {
        noteViewModel.addTagForNote(noteId, tag)
    }

    private fun onTagDiselected(tag: TagModel) {
        noteViewModel.deleteTagForNote(noteId, tag.tagId)
    }

    private fun onTagAdded(name: String) {
        if (allTags.none { it.name == name } || allTags.isNullOrEmpty()) {
            val newTag = TagModel(name = name)
            addChipToGroup(newTag)
            noteViewModel.saveTag(newTag)
            noteViewModel.addTagForNote(noteId, newTag)
        }
    }

    private fun addChipToGroup(tag: TagModel) {
        // if noteToeEdit.tags contains tag -> chip is coloured/chosen
        // else it is not
        // chips are CHOICE chips, NOT filter chips
        val chip = Chip(parent).apply {
            text = tag.name
            isClickable = true
            setOnClickListener {
                Toast.makeText(requireActivity(), "clicked $text!", Toast.LENGTH_SHORT).show()
            }
        }
        chipGroup.addView(chip)
    }

}