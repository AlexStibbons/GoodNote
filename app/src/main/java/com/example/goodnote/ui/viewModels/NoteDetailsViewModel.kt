package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.TagModel
import com.example.goodnote.utils.*
import com.example.goodnote.utils.addOne
import com.example.goodnote.utils.toListTagModel
import com.example.goodnote.utils.toTagDomainModel
import kotlinx.coroutines.*
import java.io.File
import java.io.FileWriter
import java.io.IOException

class NoteDetailsViewModel(private val noteRepo: NoteRepo,
                           private val tagRepo: TagRepo,
                           private val noteIdFromIntent: String) : ViewModel() {

    private var _existingTags = MutableLiveData<List<TagModel>>()
    val existingTags: LiveData<List<TagModel>> = _existingTags

    private var _onNoteSaved = MutableLiveData<Long>()
    val onNoteSaved: LiveData<Long> = _onNoteSaved

    /* data binding: https://developer.android.com/topic/libraries/data-binding/two-way  */
   /* private*/ var _noteToEdit = MutableLiveData(NoteDetailsModel(title = "", text = "", tags = mutableListOf()))
    val noteToEdit: LiveData<NoteDetailsModel> = _noteToEdit

    init {
        getAllTags()
        getNoteById(noteIdFromIntent)
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

        if (id.isBlank()) return@launch

        val found = withContext(Dispatchers.IO) { noteRepo.findNoteById(id).toNoteDetailsModel() }
        _noteToEdit.value = found
    }

    fun saveNote()  = viewModelScope.launch {
        val noteToSave: NoteDetailsModel = if (noteToEdit.value?.title.isNullOrEmpty()) noteToEdit.value?.copy(title = DEFAULT_TITLE)!! else noteToEdit.value!!

        if (noteIdFromIntent.isBlank()) {
           // saveToInternal(noteToSave.toNoteDomainModel())
            val saved = withContext(Dispatchers.IO) { noteRepo.saveNote(noteToSave.toNoteDomainModel())}
            _onNoteSaved.value = saved
        } else {
            withContext(Dispatchers.IO) { noteRepo.update(noteToEdit.value?.title!!, noteToEdit.value?.text!!, noteToEdit.value?.noteId!!) }
            _onNoteSaved.value = 1 // threading issue here; update has no return value, might not work all the time
        }
    }

    fun deleteTagForNote(noteId: String, tagId: String) = viewModelScope.launch {
        val updateTags: MutableList<TagModel> = noteToEdit.value?.tags?.filter { it.tagId != tagId }?.toMutableList() ?: mutableListOf()
        _noteToEdit.value = noteToEdit.value?.copy(tags=updateTags)
        if (noteIdFromIntent.isNotBlank()) {
            withContext(Dispatchers.IO) {noteRepo.deleteTagForNote(noteId, tagId)}
        }
    }

    fun addTagForNote(noteId: String, tag: TagModel) = viewModelScope.launch {

        _noteToEdit.value?.tags?.add(tag)
        _noteToEdit.value = _noteToEdit.value?.copy()

        if (noteIdFromIntent.isNotBlank()) {
            withContext(Dispatchers.IO) {noteRepo.addTagForNote(noteId, tag.tagId)}
        }
    }

    fun saveToInternal(note: NoteDomanModel) {
        val directory: File = File(STORAGE_DIRECTORY_PATH)
        if (!directory.exists()) directory.mkdir()

        val title = note.title
        val body = StringBuilder().apply {
            append("\n")
            append(note.tags.toTagsString())
            append("\n")
            append(note.text)
        }.toString()

        try {
            val noteFile: File = File(directory, title)

            val writer: FileWriter = FileWriter(noteFile, true)
            writer.apply {
                append(body)
                flush()
                close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }
}