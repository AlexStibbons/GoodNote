package com.example.goodnote.ui

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodnote.R
import com.example.goodnote.utils.EXTRA_NOTE_ID

class NoteDetails : AppCompatActivity() {

    // declare all stuffs
    lateinit var title: EditText
    lateinit var tags: EditText
    lateinit var text: EditText

    // needs viewModel to communicate with repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("DETAILS", "on create")
        // notesViewModel = Injectors.getNoteViewModel(this)

        title = findViewById(R.id.notes_details_title)
        tags = findViewById(R.id.notes_details_tags)
        text = findViewById(R.id.notes_details_text)

        // if string extra exists, this is an existing note and
        // should be viewed and/or edited [add to repo with id]

        // if string extra does not exist, this activity is used for creating
        // a new note [add to repo w/o id]

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID)
        Log.e("DETAILS", "on create: $noteId")
        Toast.makeText(this, "Note ID is $noteId", Toast.LENGTH_SHORT).show()
    }


}