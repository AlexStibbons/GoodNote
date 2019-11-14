package com.example.goodnote.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.database.repository.NoteRepoImpl
import com.example.goodnote.ui.viewModels.NoteViewModel
import com.example.goodnote.ui.viewModels.NoteViewModelFactory

object Injectors {

    private fun getNoteRepository(context: Context): NoteRepo {
        return NoteRepoImpl.getInstance(
            LocalDb.getInstance(context.applicationContext).noteDao()
        )
    }

    /* private fun getTagRepository(context: Context): TagRepo {
         return
     }

     private fun getJoinRepository(context: Context): JoinRepo {
         return
     }*/

    fun getNoteViewModel(context: Context): NoteViewModelFactory {
        val repo = getNoteRepository(context)
        return NoteViewModelFactory(repo)
    }

    fun getNoteViewModel1(parent: FragmentActivity): NoteViewModel {
        val viewModelFactory = NoteViewModelFactory(getNoteRepository(parent))
        return ViewModelProviders.of(parent, viewModelFactory).get(NoteViewModel::class.java)
    }
}