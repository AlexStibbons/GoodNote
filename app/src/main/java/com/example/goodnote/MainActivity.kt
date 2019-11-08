package com.example.goodnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.models.*
import com.example.goodnote.database.repository.NoteRepoImpl
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
        val noteRepo = NoteRepoImpl(localDb)
        CoroutineScope(Dispatchers.IO).launch {
            noteRepo.saveNote(Note("title", "body"))
            val list2 = noteRepo.getAllNotes()
            Log.e("in scope", "list is ${list2.size}")

            withContext(Dispatchers.Main) {
                list.clear()
                list.addAll(list2)
                Log.e("in scope/main", "list is ${list.size}")
                showNote(list) // ??????
            }


        }
    }

    fun showNote(list: MutableList<Note>) {
        val note1 = list?.let {
            val idn = it[0].id
            Note(it[0].title, it[0].text).apply {
                id = idn
            }
        }

        val str = "id is ${note1?.id}\n title is ${note1?.title}\n text is ${note1?.text}"
        Log.e("main, NOTE", str)
        testy.text = str
    }
}
