package com.example.goodnote.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodnote.R
import com.google.android.material.snackbar.Snackbar

class NoteDetails: AppCompatActivity() {

    // declare all stuffs
    lateinit var title: EditText
    lateinit var tags: EditText
    lateinit var text: EditText

    // needs viewModel to communicate with repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_details_activity)
        Log.e("DETAILS", "on create")

        // bind all stuffs
        title = findViewById(R.id.notes_details_title)
        tags = findViewById(R.id.notes_details_tags)
        text = findViewById(R.id.notes_details_text)

        val noteId = intent.getIntExtra("noteId", -1)
        Log.e("DETAILS", "on create: $noteId")
        Toast.makeText(this, "Note ID is $noteId", Toast.LENGTH_SHORT).show()
        //tags.text = "Note Id is $noteId" as Editable
    }
}