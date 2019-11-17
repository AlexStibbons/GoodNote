package com.example.goodnote.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodnote.database.repository.TagRepo

class TagViewModelFactory(private val repository: TagRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TagViewModel(repository) as T
    }
}