package com.example.goodnote.ui.noteList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.database.entityModels.NoteEntity
import com.example.goodnote.ui.models.NoteListModel
import kotlinx.android.synthetic.main.note_item.view.*

class NoteListRecyclerViewAdapter(private val onNoteClicked: NoteListFragment.onNoteClick) : RecyclerView.Adapter<NoteListRecyclerViewAdapter.ViewHolder>(){

    private val notes : MutableList<NoteListModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = notes.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.item_note_title
        val tags = view.item_tags
        val text = view.item_note_text
        val noteItem = view.layout_note_item

        fun bind(position: Int) {
            title.text = notes[position].title
            tags.text = notes[position].tags
            text.text = notes[position].text

            noteItem.setOnClickListener( View.OnClickListener {
                onNoteClicked.onNoteClick(notes[position].noteId)
                Log.e("REC VIEW", "note click")
            })

            // works, but no indication to user
            noteItem.setOnLongClickListener {
                onNoteClicked.onNoteLongPress(notes[position].noteId)
                true
            }
        }
    }

    internal fun setNotes(newNotes: List<NoteListModel>) {
        this.notes.clear()
        this.notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}