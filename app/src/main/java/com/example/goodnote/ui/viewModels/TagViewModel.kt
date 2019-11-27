package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.database.entityModels.TagEntity
import com.example.goodnote.utils.addOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagViewModel(private val repository: TagRepo): ViewModel() {

    private val _tags: MutableLiveData<List<TagEntity>> = MutableLiveData()
    val tags: LiveData<List<TagEntity>>
        get() = _tags

     init {
        getTags()
    }

    fun getTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) {repository.getAllTags()}
        _tags.value = tags
    }

    fun addTag(tag: TagEntity) = viewModelScope.launch {
        _tags.addOne(tag)
        withContext(Dispatchers.IO) {repository.addTag(tag)}
    }

    fun deleteTag(id: String) = viewModelScope.launch {
        _tags.value = _tags.value?.filter { it.tagId != id }

        withContext(Dispatchers.IO) { repository.deleteTagById(id)}
    }

    fun findTagById(id: String) = viewModelScope.launch {
        val foundTag: TagEntity = withContext(Dispatchers.IO) { repository.findTagById(id)}
        // tag to LiveData<TagEntity> ?
    }

    fun findTagsByName(name: String) = viewModelScope.launch {

        _tags.value = _tags.value?.filter { it.name.contains(name, true) }

        // is fetching any kind of filtered list needed at all?
        val foundTags = withContext(Dispatchers.IO) { repository.findTagsByName(name)}
    }

    fun clearSearch() {
        getTags()
    }
}