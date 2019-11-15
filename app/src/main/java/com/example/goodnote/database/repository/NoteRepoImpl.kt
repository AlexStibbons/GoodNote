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

    override suspend fun getAllNotes(): List<Note> = noteDao.getAllNotes()

    override suspend fun deleteNote(id: Int) = noteDao.deleteNote(id)

    override suspend fun saveNote(note: Note) = noteDao.addNote(note)

    override suspend fun findNoteById(id: Int): Note = noteDao.findNoteById(id)
    // this would have to return a list of tags related to the note
    // so note repo must use JoinDao too

    override suspend fun findNoteByTitle(title: String): List<Note> = noteDao.findNotesByTitle(title)
    // for a list of notes to be shown on screen, each one needs to have a
    // list of its related tags

    // so, noteRepo and tagRepo must have a joinDao
    // while joinRepo is useless and not needed
}