package com.example.goodnote.utils

import android.content.Context
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.database.repository.NoteRepoImpl

object Injectors {

    /*private?*/fun getNoteRepository(context: Context): NoteRepo {
        return NoteRepoImpl.getInstance(
            LocalDb.getInstance(context.applicationContext).noteDao())
    }
}