package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.database.entityModels.TagEntity
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.utils.addOne
import com.example.goodnote.utils.toListTagModel
import com.example.goodnote.utils.toTagDomainModel
import com.example.goodnote.utils.toTagModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagViewModel(private val repository: TagRepo): ViewModel() {

    private val _tags: MutableLiveData<List<TagModel>> = MutableLiveData()
    val tags: LiveData<List<TagModel>>
        get() = _tags

     init {
        getTags()
    }

    fun getTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) {repository.getAllTags()}
        _tags.value = tags.toListTagModel()
    }

    fun addTag(tag: TagModel) = viewModelScope.launch {
        _tags.addOne(tag)
        withContext(Dispatchers.IO) {repository.addTag(tag.toTagDomainModel())}
    }

    fun deleteTag(id: String) = viewModelScope.launch {
        _tags.value = _tags.value?.filter { it.tagId != id }

        withContext(Dispatchers.IO) { repository.deleteTagById(id)}
    }

    fun findTagById(id: String) = viewModelScope.launch {
        val foundTag: TagModel = withContext(Dispatchers.IO) { repository.findTagById(id).toTagModel()}
        // tag to LiveData<TagEntity> ?
    }

    fun findTagsByName(name: String) {
        _tags.value = _tags.value?.filter { it.name.contains(name, true) }
    }

    fun clearSearch() {
        getTags()
    }
}