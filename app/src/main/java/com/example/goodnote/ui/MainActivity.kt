package com.example.goodnote.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodnote.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_fragments, NoteListFragment.getInstance())
        ft.commit()
    }
}
