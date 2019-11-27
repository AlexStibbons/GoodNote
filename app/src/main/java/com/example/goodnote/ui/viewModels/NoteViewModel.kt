package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*it is better to do thread switching only once by switching to IO thread when it is needed.
This way we are saving processor time, since there won't be 2 context switches
where one would be sufficient.*/
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    private var _repoNotes: MutableLiveData<List<NoteModel>> = MutableLiveData()
    val repoNotes: LiveData<List<NoteModel>>
        get() = _repoNotes

    init {
        getAllNotes()
    }

    fun getAllNotes() = viewModelScope.launch {

        val notes = withContext(Dispatchers.IO) { repository.getAllNotes() }

        _repoNotes.value = notes.map { it.toNoteModel() }

    }

    fun saveNote(note: NoteDetailsModel) = viewModelScope.launch {

        val noteSave = if (note.title.isNullOrEmpty()) note.copy(title = DEFAULT_TITLE) else note

        _repoNotes.addOne(noteSave.toNoteModel()) // --> do not make a new list, but just add a new note (DiffUtil)

        withContext(Dispatchers.IO) {
            repository.saveNote(noteSave.toNoteDomainModel())
        }
    }

    fun deleteNote(id: String) = viewModelScope.launch {
        _repoNotes.value = _repoNotes.value?.filter { it.noteId != id }
        withContext(Dispatchers.IO) { repository.deleteNote(id) }
    }

    fun findNoteById(id: String) = viewModelScope.launch {
        val foundNote = withContext(Dispatchers.IO) { repository.findNoteById(id) }
        val noteDetails = foundNote.toNoteDetailsModel()
    }

    fun findNotesByTitle(title: String) = viewModelScope.launch {
        // is the trip to databse warranted? no need to get notes from db, right?
       // val foundNotes = withContext(Dispatchers.IO) { repository.findNoteByTitle(title) }
        _repoNotes.value = _repoNotes.value?.filter { it.title.contains(title, true) }
    }

    fun clearSearch() {
        getAllNotes()
    }
}