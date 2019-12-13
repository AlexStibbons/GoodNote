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

    private var _noteToEdit = MutableLiveData(NoteDetailsModel(title = "", text = "", tags = mutableListOf()))
    val noteToEdit: LiveData<NoteDetailsModel> = _noteToEdit

    private var iddd = ""

    init {
        getAllTags()
    }

    fun getAllTags() = viewModelScope.launch {
        val tags = withContext(Dispatchers.IO) { tagRepo.getAllTags() }
        _existingTags.value = tags.toListTagModel()
    }

    fun saveTag(tag: TagModel) = viewModelScope.launch {
        _existingTags.addOne(tag)

        withContext(Dispatchers.IO) { tagRepo.addTag(tag.toTagDomainModel()) }
    }

    fun getNoteById(id: String) = viewModelScope.launch {
        iddd = id
        if (id.isBlank()) return@launch

        val found = withContext(Dispatchers.IO) { noteRepo.findNoteById(id).toNoteDetailsModel() }
        _noteToEdit.value = found
    }

    fun saveNote(note: NoteDetailsModel)  = viewModelScope.launch {
        val noteToSave = if (note.title.isNullOrEmpty()) note.copy(title = DEFAULT_TITLE) else note
        if (iddd.isBlank()) {
            val saved = withContext(Dispatchers.IO) { noteRepo.saveNote(noteToSave.toNoteDomainModel())}
            _onNoteSaved.value = saved
        } else {
            //val updated = withContext(Dispatchers.IO) { noteRepo.updateNote(noteToSave.toNoteDomainModel()) }
            withContext(Dispatchers.IO) { noteRepo.update(note.title, note.text, note.noteId) }
            _onNoteSaved.value = 1 // threading issue here; update has no return value, might not work all the time
        }
    }

    fun deleteTagForNote(noteId: String, tagId: String) = viewModelScope.launch {
        val updateTags: MutableList<TagModel> = noteToEdit.value?.tags?.filter { it.tagId != tagId }?.toMutableList() ?: mutableListOf()
        _noteToEdit.value = noteToEdit.value?.copy(tags=updateTags)
        if (iddd.isNotBlank()) {
            withContext(Dispatchers.IO) {noteRepo.deleteTagForNote(noteId, tagId)}
        }
    }

    fun addTagForNote(noteId: String, tag: TagModel) = viewModelScope.launch {

        _noteToEdit.value?.tags?.add(tag)
        _noteToEdit.value = _noteToEdit.value?.copy()

        if (iddd.isNotBlank()) {
            withContext(Dispatchers.IO) {noteRepo.addTagForNote(noteId, tag.tagId)}
        }
    }

}