package com.example.goodnote.repository

import android.util.Log
import com.example.goodnote.database.daos.JoinNoteTagDao
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.entityModels.JoinNoteTagEntity
import com.example.goodnote.database.entityModels.NoteEntity
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.utils.toListTagDomainModel
import com.example.goodnote.utils.toNoteDomainModel
import com.example.goodnote.utils.toNoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepoImpl private constructor(private val noteDao: NoteDao, private val joinDao: JoinNoteTagDao) : NoteRepo {

    /*If the repository is a singleton or otherwise scoped to the application, the repository will
    not be destroyed until the process is killed. This will only happen when the system needs
    resources or the user manually kills the app. If the repository is holding a reference to a
    callback in the ViewModel, the ViewModel will be temporarily leaked*/
    companion object{
        @Volatile
        private var instance: NoteRepoImpl? = null

        fun getInstance(noteDao: NoteDao, joinDao: JoinNoteTagDao) = // `operator fun`
            instance ?: synchronized(this) {
                instance ?: NoteRepoImpl(noteDao, joinDao).also { instance = it }
            }
    }

    override suspend fun getAllNotes(): List<NoteDomanModel> {
        val noteEntities: List<NoteEntity> = noteDao.getAllNotes()

        val noteDomainModels = mutableListOf<NoteDomanModel>()
        noteEntities.forEach{
            val tags = joinDao.getTagsForNote(it.noteId)
            noteDomainModels.add(it.toNoteDomainModel(tags.toListTagDomainModel()))
        }
        return noteDomainModels
    }

    override suspend fun deleteNote(id: String) {
        joinDao.deleteJoinByNoteId(id)
        noteDao.deleteNote(id)
    }

    override suspend fun saveNote(note: NoteDomanModel): Long {

        val saved = noteDao.addNote(note.toNoteEntity())

        note.tags.forEach {
            joinDao.addNoteTag(JoinNoteTagEntity(it.tagId, note.noteId))
        }
        return saved
    }

    override suspend fun findNoteById(id: String): NoteDomanModel {
        val foundNote = noteDao.findNoteById(id)
        val foundTags = joinDao.getTagsForNote(id).toListTagDomainModel()

        return foundNote.toNoteDomainModel(foundTags)
    }

    override suspend fun findNoteByTitle(title: String): List<NoteDomanModel> {
        val foundNotes = noteDao.findNotesByTitle(title)
        val notesWithTags = mutableListOf<NoteDomanModel>()
        foundNotes.forEach {
            val tags = joinDao.getTagsForNote(it.noteId).toListTagDomainModel()
            notesWithTags.add(it.toNoteDomainModel(tags))
        }

        return notesWithTags
    }

    override suspend fun deleteTagForNote(noteId: String, tagId: String) {
        joinDao.deleteTagForNote(noteId, tagId)
    }

    override suspend fun addTagForNote(noteId: String, tagId: String) {
        joinDao.addNoteTag(JoinNoteTagEntity(tagId, noteId))
    }

    override suspend fun updateNote(note: NoteDomanModel): Int {
        TODO()
       // return noteDao.update(note.toNoteEntity())
    }

    override suspend fun update(title: String, text: String, noteId: String): Long {
        noteDao.update(title, text, noteId)
        return 1
    }
}