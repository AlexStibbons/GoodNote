package com.example.goodnote.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.daos.JoinNoteTagDao
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.daos.TagDao
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.repository.NoteRepoImpl
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.repository.TagRepoImpl
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.viewModels.*

object Injectors {

    private var noteDao: NoteDao? = null
    private var tagDao: TagDao? = null
    private var joinDao: JoinNoteTagDao? = null

    private fun getNoteDao(context: Context): NoteDao {
        return noteDao ?: LocalDb.getInstance(context.applicationContext).noteDao().also { noteDao = it }
    }

    private fun getTagDao(context: Context): TagDao {
        return tagDao ?: LocalDb.getInstance(context.applicationContext).tagDao().also { tagDao = it }
    }

    private fun getJoinDao(context: Context): JoinNoteTagDao {
        return joinDao ?: LocalDb.getInstance(context.applicationContext).joinNoteTagDao().also { joinDao = it }
    }

    private fun getNoteRepository(context: Context): NoteRepo {
        return NoteRepoImpl.getInstance(getNoteDao(context), getJoinDao(context))
    }

     private fun getTagRepository(context: Context): TagRepo {
         return TagRepoImpl.getInstance(getTagDao(context), getJoinDao(context))
     }

    fun getNoteViewModel(parent: FragmentActivity): NoteViewModel {
        val viewModelFactory = NoteViewModelFactory(getNoteRepository(parent))
        return ViewModelProviders.of(parent, viewModelFactory).get(NoteViewModel::class.java)
    }

    fun getTagViewModel(parent: FragmentActivity): TagViewModel {
        val tagViewModelFactory = TagViewModelFactory(getTagRepository(parent))
        return ViewModelProviders.of(parent, tagViewModelFactory).get(TagViewModel::class.java)
    }

    fun getNoteDetailsViewModel(activity: AppCompatActivity, noteId: String): NoteDetailsViewModel {
        val noteDetailsViewModelFactory = NoteDetailsViewModelFactory(getNoteRepository(activity),
                                                                        getTagRepository(activity),
                                                                        noteId)

        return ViewModelProviders.of(activity, noteDetailsViewModelFactory).get(NoteDetailsViewModel::class.java)
    }

}