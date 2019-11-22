package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.repository.TagRepo
import com.example.goodnote.database.models.Tag
import com.example.goodnote.utils.addOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagViewModel(private val repository: TagRepo): ViewModel() {

    // is this here [by lazy] a better option?
    // because of init, it seems like there's no difference
    private val tags2: MutableLiveData<List<Tag>> by lazy {
        MutableLiveData<List<Tag>>().also {
            getTags()
        }
    }

    private val _tags: MutableLiveData<List<Tag>> = tags2
    val tags: LiveData<List<Tag>>
        get() = _tags

    // inspect need for init:
    // since ViewModel is singleton, init is called only once, isn't it?
     init {
        getTags()
    }

    fun getTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) {repository.getAllTags()}
        _tags.value = tags
    }

    fun addTag(tag: Tag) = viewModelScope.launch {
        _tags.addOne(tag)
        withContext(Dispatchers.IO) {repository.addTag(tag)}
    }

    fun deleteTag(id: String) = viewModelScope.launch {
        _tags.value = _tags.value?.filter { it.tagId != id }

        withContext(Dispatchers.IO) { repository.deleteTagById(id)}
    }

    fun findTagById(id: String) = viewModelScope.launch {
        val foundTag: Tag = withContext(Dispatchers.IO) { repository.findTagById(id)}
        // tag to LiveData<Tag> ?
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