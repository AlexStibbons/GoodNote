package com.example.goodnote.ui.noteList

import androidx.recyclerview.widget.DiffUtil
import com.example.goodnote.ui.models.NoteModel

class NoteDiffCallback(private val oldList: MutableList<NoteModel>,
                       private val newList: List<NoteModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].noteId == newList[newItemPosition].noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return TODO("Create bundle with changes for onBindViewHolder in the adapter")
        //return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}