package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.utils.*
import com.example.goodnote.utils.addOne
import com.example.goodnote.utils.toListTagModel
import com.example.goodnote.utils.toTagDomainModel
import kotlinx.coroutines.*

class NoteDetailsViewModel(private val noteRepo: NoteRepo,
                           private val tagRepo: TagRepo) : ViewModel() {

    private var _existingTags = MutableLiveData<List<TagModel>>()
    val existingTags: LiveData<List<TagModel>> = _existingTags

    private var _onNoteSaved = MutableLiveData<Long>()
    val onNoteSaved: LiveData<Long> = _onNoteSaved

    init {
        getAllTags()
    }

    fun getAllTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) { tagRepo.getAllTags() }
        _existingTags.value = tags.toListTagModel()
    }

    fun addTag(tag: TagModel) = viewModelScope.launch {
        _existingTags.addOne(tag)
        withContext(Dispatchers.IO) { tagRepo.addTag(tag.toTagDomainModel()) }
    }

    fun getNoteById(id: String) = viewModelScope.async {
            val found = withContext(Dispatchers.IO) { noteRepo.findNoteById(id).toNoteDetailsModel() }
            return@async found
    }

    fun saveNote(note: NoteDetailsModel)  = viewModelScope.launch {

        val noteToSave = if (note.title.isNullOrEmpty()) note.copy(title = DEFAULT_TITLE) else note
        val saved = withContext(Dispatchers.IO) { noteRepo.saveNote(noteToSave.toNoteDomainModel())}

        _onNoteSaved.value = saved
    }

    fun deleteTagForNote(noteId: String, tagId: String) = viewModelScope.launch {
        noteRepo.deleteTagForNote(noteId, tagId)
    }

}