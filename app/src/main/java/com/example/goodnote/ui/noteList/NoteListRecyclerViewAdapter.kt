package com.example.goodnote.ui.noteList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.utils.EXTRA_NOTE_TAGS
import com.example.goodnote.utils.EXTRA_NOTE_TEXT
import com.example.goodnote.utils.EXTRA_NOTE_TITLE
import kotlinx.android.synthetic.main.note_item.view.*

class NoteListRecyclerViewAdapter(private val onNoteClicked: NoteListFragment.onNoteClick) :
    RecyclerView.Adapter<NoteListRecyclerViewAdapter.ViewHolder>() {

    private val notes: MutableList<NoteModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent, false
        )

        return ViewHolder(itemView, onNoteClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) =
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bun: Bundle = payloads.first() as Bundle
            //var noteModel = notes[position]
            bun.keySet().forEach {
                when (it) {
                    EXTRA_NOTE_TITLE -> holder.setTitle(bun.getString(EXTRA_NOTE_TITLE, ""))
                        //noteModel = noteModel.copy(title = bun.getString(EXTRA_NOTE_TITLE, ""))
                    EXTRA_NOTE_TEXT -> holder.setText(bun.getString(EXTRA_NOTE_TEXT, ""))
                        //noteModel = noteModel.copy(text = bun.getString(EXTRA_NOTE_TEXT, ""))

                    EXTRA_NOTE_TAGS -> holder.setTags(bun.getString(EXTRA_NOTE_TAGS, ""))
                        //noteModel = noteModel.copy(tags = bun.getString(EXTRA_NOTE_TAGS,""))
                }
            }
            //holder.bind(noteModel)
        }

    override fun getItemCount(): Int = notes.size

    class ViewHolder(view: View, private val click: NoteListFragment.onNoteClick) :
        RecyclerView.ViewHolder(view) {

        private val title = view.item_note_title
        private val tags = view.item_tags
        private val text = view.item_note_text
        private val noteItem = view.layout_note_item

        fun bind(note: NoteModel) {
            title.text = note.title
            tags.text = note.tags
            text.text = note.text

            noteItem.setOnClickListener(View.OnClickListener {
                click.onNoteClick(note.noteId)
                Log.e("REC VIEW", "note click: $adapterPosition & ${note.title}")
            })

            noteItem.setOnLongClickListener {
                Log.e("REC VIEW LONG", "note click on pos: $adapterPosition & ${note.title}")
                click.onNoteLongPress(note.noteId)
                true
            }
        }

        fun setTitle(title: String) {
            this.title.text = title
        }

        fun setText(text: String){
            this.text.text = text
        }

        fun setTags(tags: String) {
            this.tags.text = tags
        }
    }

    internal fun setNotes(newNotes: List<NoteModel>) {
        val diffResult = DiffUtil.calculateDiff(NoteDiffCallback(this.notes, newNotes), true)
        this.notes.clear()
        this.notes.addAll(newNotes)
        diffResult.dispatchUpdatesTo(this)
    }
}