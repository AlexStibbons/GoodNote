package com.example.goodnote.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.repository.TagRepo

class NoteDetailsViewModelFactory(private val noteRepo: NoteRepo,
                                  private val tagRepo: TagRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailsViewModel(noteRepo, tagRepo) as T
    }
}