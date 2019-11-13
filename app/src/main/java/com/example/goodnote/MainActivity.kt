package com.example.goodnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.models.*
import com.example.goodnote.graveyard.NoteRepoNotSnglt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var list: MutableList<Note> = arrayListOf()
    lateinit var testy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testy = findViewById(R.id.textView)

        val localDb by lazy {
            LocalDb.getInstance(this)
        }

        dummyNote(localDb)
        Log.e("main, after dummy call", "list is ${list?.size}")

    }

    private fun dummyNote(localDb: LocalDb) {

        val noteRepo = NoteRepoNotSnglt(localDb)

        CoroutineScope(Dispatchers.IO).launch {
            noteRepo.saveNote(Note("title again", "body again\nchecking update; strategy replace")
                .apply { id = 1 })
            val list2 = noteRepo.getAllNotes()
            Log.e("in scope", "list is ${list2?.size}")

            withContext(Dispatchers.Main) {
                list2?.let {
                    list.clear()
                    list.addAll(it)
                    showNote(list)
                }
                Log.e("in scope/main", "list is ${list.size}")
            }
        }
    }

    fun showNote(list: MutableList<Note>) {
        val str1 = "size is ${list.size}\nid is ${list[0].id}\ntitle is ${list[0].title}\nbody is ${list[0].text} "
        Log.e("main, NOTE", str1)
        testy.text = str1
    }
}
