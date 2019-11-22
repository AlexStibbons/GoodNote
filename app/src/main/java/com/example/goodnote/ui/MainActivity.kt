package com.example.goodnote.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodnote.R
import com.example.goodnote.database.LocalDb
import com.example.goodnote.ui.noteList.NoteListFragment
import com.example.goodnote.ui.tagList.TagListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var db: LocalDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        db = LocalDb.getInstance(this) // to get default notes/tags

        /* REAL START: see a list of notes
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_fragments, NoteListFragment.getInstance())
        ft.commit()*/

        //TEST START: see a list of tags
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_fragments, TagListFragment.getInstance())
        ft.commit()
    }
}
