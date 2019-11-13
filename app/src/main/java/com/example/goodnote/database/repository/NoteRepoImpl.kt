package com.example.goodnote.database.repository

import androidx.lifecycle.LiveData
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.models.Note

class NoteRepoImpl private constructor(private val noteDao: NoteDao) : NoteRepo {

    /*If the repository is a singleton or otherwise scoped to the application, the repository will
    not be destroyed until the process is killed. This will only happen when the system needs
    resources or the user manually kills the app. If the repository is holding a reference to a
    callback in the ViewModel, the ViewModel will be temporarily leaked*/
    companion object{
        @Volatile
        private var instance: NoteRepoImpl? = null

        fun getInstance(noteDao: NoteDao) = // `operator fun`
            instance ?: synchronized(this) {
                instance ?: NoteRepoImpl(noteDao).also { instance = it }
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