package com.example.goodnote.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goodnote.R

class NotesListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list_activity)
    }
}