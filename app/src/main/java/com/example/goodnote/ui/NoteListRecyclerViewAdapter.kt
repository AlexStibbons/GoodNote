package com.example.goodnote.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.database.models.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteListRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<NoteListRecyclerViewAdapter.ViewHolder>(){

    private var notes = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(context).inflate(
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
                // openNoteDetails(notes[position].id)
                Toast.makeText(context, "Clicked ${notes[position].title}", Toast.LENGTH_LONG).show()
            })
        }
    }

    internal fun setNotes(newNotes: List<Note>) {
        this.notes = newNotes
        notifyDataSetChanged()
    }
}