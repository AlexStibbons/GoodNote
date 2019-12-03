package com.example.goodnote.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodnote.repository.NoteRepo

class NoteViewModelFactory(private val repository: NoteRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}