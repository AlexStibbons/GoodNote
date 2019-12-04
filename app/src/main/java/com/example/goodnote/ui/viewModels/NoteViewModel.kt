package com.example.goodnote.ui.viewModels

import androidx.lifecycle.*
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.utils.*
import kotlinx.coroutines.*

/*it is better to do thread switching only once by switching to IO thread when it is needed.
This way we are saving processor time, since there won't be 2 context switches
where one would be sufficient.*/
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    private var _repoNotes: MutableLiveData<List<NoteModel>> = MutableLiveData()
    val repoNotes: LiveData<List<NoteModel>>
        get() = _repoNotes

//    private var _addedNote: MutableLiveData<NoteModel> = MutableLiveData()
//    val addedNote: LiveData<NoteModel> = _addedNote

    init {
        getAllNotes()
    }

    fun getAllNotes() = viewModelScope.launch {
        val notes = withContext(Dispatchers.IO) { repository.getAllNotes() }
        _repoNotes.value = notes.map { it.toNoteModel() }
    }

    fun saveNote(note: NoteDetailsModel) = viewModelScope.launch {

        val noteSave = if (note.title.isNullOrEmpty()) note.copy(title = DEFAULT_TITLE) else note
        _repoNotes.addOne(noteSave.toNoteModel())

        withContext(Dispatchers.IO) {
            repository.saveNote(noteSave.toNoteDomainModel())
        }
    }

    fun deleteNote(id: String) = viewModelScope.launch {
        _repoNotes.value = _repoNotes.value?.filter { it.noteId != id }
        withContext(Dispatchers.IO) { repository.deleteNote(id) }
    }

    fun findNoteById(id: String) = viewModelScope.async {
        val foundNote = withContext(Dispatchers.IO) { repository.findNoteById(id) }
        return@async foundNote
    }

    fun deleteTagForNote(noteId: String, tagId: String) = viewModelScope.launch {
        repository.deleteTagForNote(noteId, tagId)
    }

    fun findNotesByTitle(title: String) = viewModelScope.launch {
        _repoNotes.value = _repoNotes.value?.filter { it.title.contains(title, true) }
    }

    fun clearSearch() {
        getAllNotes()
    }
}