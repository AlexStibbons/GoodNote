package com.example.goodnote.ui.noteList

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

        val (noteId, title, text, tags) = oldList[oldItemPosition]
        val (newnoteId, newtitle, newtext, newtags) = newList[newItemPosition]
        val bool = (noteId == newnoteId && title == newtitle && text == newtext && tags == newtags)

        return bool
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}