package com.example.goodnote.ui.noteList

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.goodnote.ui.models.NoteModel

class NoteDiffCallback(private val oldList: List<NoteModel>,
                       private val newList: List<NoteModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].noteId == newList[newItemPosition].noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
/*
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]

        val diffBundle = Bundle().apply {
            if (newNote.noteId != oldNote.noteId) this.putString("noteId", newNote.noteId)
            if (newNote.title != oldNote.title) this.putString("title", newNote.title)
            if (newNote.text != oldNote.text) this.putString("text", newNote.text)
            if (newNote.tags != oldNote.tags) this.putString("tags", newNote.tags)
        }

        if (diffBundle.size() == 0) return null

        return diffBundle
    }*/
}