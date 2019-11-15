package com.example.goodnote.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.database.models.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteListRecyclerViewAdapter(private val onNoteClicked: NoteListFragment.onNoteClick) : RecyclerView.Adapter<NoteListRecyclerViewAdapter.ViewHolder>(){

    private val notes : MutableList<Note> = ArrayList()

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
            tags.text = "from join table somehow"
            text.text = notes[position].text

            noteItem.setOnClickListener( View.OnClickListener {
                onNoteClicked.onNoteClick(notes[position].id)
                Log.e("REC VIEW", "note click")
            })
        }
    }

    internal fun setNotes(newNotes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}