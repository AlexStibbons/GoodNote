package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.repository.TagRepo
import com.example.goodnote.database.models.Tag
import com.example.goodnote.utils.setId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagViewModel(private val repository: TagRepo): ViewModel() {

    private val _tags: MutableLiveData<List<Tag>> = MutableLiveData()
    private val tags: LiveData<List<Tag>>
        get() = _tags

    init {
        getAllTags() // maybe
    }

    fun getAllTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) {repository.getAllTags()}
        _tags.value = tags
    }

    fun addTag(tag: Tag) = viewModelScope.launch {
        tag.setId()
        // add to _tags
        withContext(Dispatchers.IO) {repository.addTag(tag)}
    }

    fun deleteTag(id: Int) = viewModelScope.launch {
        _tags.value = _tags.value?.filter { it.id != id }

        withContext(Dispatchers.IO) { repository.deleteTagBId(id)}
    }

    fun findTagById(id: Int) = viewModelScope.launch {
        val foundNote = withContext(Dispatchers.IO) { repository.findTagById(id)}
        // tag to LiveData<Tag> ?
    }

    fun findTagsByName(name: String) = viewModelScope.launch {
        val foundTags = withContext(Dispatchers.IO) { repository.findTagsByName(name)}
        _tags.value = foundTags
    }

    fun clearSearch() {
        getAllTags()
    }
}