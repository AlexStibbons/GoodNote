package com.example.goodnote.database.repository

import androidx.lifecycle.LiveData
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepo2 private constructor(private val noteDao: NoteDao) : INoteRepo {

    /*If the repository is a singleton or otherwise scoped to the application, the repository will
    not be destroyed until the process is killed. This will only happen when the system needs
    resources or the user manually kills the app. If the repository is holding a reference to a
    callback in the ViewModel, the ViewModel will be temporarily leaked*/
    companion object{
        @Volatile
        private var instance: NoteRepo2? = null

        fun getInstance(noteDao: NoteDao) = // `operator fun`
            instance ?: synchronized(this) {
                instance ?: NoteRepo2(noteDao).also { instance = it }
            }
    }

    override val allNotes: LiveData<List<Note>>
        get() = noteDao.liveData()

    override suspend fun getAllNotes(): List<Note> {
        TODO("not implemented")
    }

    override suspend fun deleteNote(id: Int) {
        TODO("not implemented")
    }

    override suspend fun saveNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun findNoteById(id: Int): Note {
        TODO("not implemented")
    }

    override suspend fun findNoteByTitle(title: String): List<Note> {
        TODO("not implemented")
    }
}