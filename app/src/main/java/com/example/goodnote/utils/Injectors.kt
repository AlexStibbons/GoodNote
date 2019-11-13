package com.example.goodnote.utils

import android.content.Context
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.repository.INoteRepo
import com.example.goodnote.database.repository.NoteRepo2

// singleton
// why not internal fun?
// why not extension fun?
object Injectors {

    // this fun used in factory
    fun getNoteRepository(context: Context): INoteRepo { // would using `=` be too unreadable?
        return NoteRepo2.getInstance(
            LocalDb.getInstance(context.applicationContext).noteDao()) // why application context?
    }
}