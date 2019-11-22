package com.example.goodnote.utils

import android.content.Context
import android.provider.ContactsContract
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.daos.JoinNoteTagDao
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.daos.TagDao
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.database.repository.NoteRepoImpl
import com.example.goodnote.database.repository.TagRepo
import com.example.goodnote.database.repository.TagRepoImpl
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.ui.viewModels.NoteViewModelFactory
import com.example.goodnote.ui.viewModels.TagViewModel
import com.example.goodnote.ui.viewModels.TagViewModelFactory

object Injectors {

    private var noteDao: NoteDao? = null
    private var tagDao: TagDao? = null

    private fun getNoteDao(context: Context): NoteDao {
        return noteDao ?: LocalDb.getInstance(context.applicationContext).noteDao().also { noteDao = it }
    }

    private fun getTagDao(context: Context): TagDao {
        return tagDao ?: LocalDb.getInstance(context.applicationContext).tagDao().also { tagDao = it }
    }

    private fun getNoteRepository(context: Context): NoteRepo {
        return NoteRepoImpl.getInstance(getNoteDao(context))
    }

     private fun getTagRepository(context: Context): TagRepo {
         return TagRepoImpl.getInstance(getTagDao(context))
     }

     /*private fun getJoinRepository(context: Context): JoinRepo {
         return
     }*/

    fun getNoteViewModel(parent: FragmentActivity): NoteViewModel {
        val viewModelFactory = NoteViewModelFactory(getNoteRepository(parent))
        return ViewModelProviders.of(parent, viewModelFactory).get(NoteViewModel::class.java)
    }

    fun getTagViewModel(parent: FragmentActivity): TagViewModel {
        val tagViewModelFactory = TagViewModelFactory(getTagRepository(parent))
        return ViewModelProviders.of(parent, tagViewModelFactory).get(TagViewModel::class.java)
    }

}