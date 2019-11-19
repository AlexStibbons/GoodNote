package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.models.Note
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.utils.DEFAULT_TITLE
import com.example.goodnote.utils.addOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*it is better to do thread switching only once by switching to IO thread when it is needed.
This way we are saving processor time, since there won't be 2 context switches
where one would be sufficient.*/
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    private var _repoNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val repoNotes: LiveData<List<Note>>
        get() = _repoNotes

    init {
        getAllNotes()
    }

    fun getAllNotes() = viewModelScope.launch {
        val notes = withContext(Dispatchers.IO) { repository.getAllNotes() }
        _repoNotes.value = notes
    }

    fun saveNote(note: Note) = viewModelScope.launch {

        if (note.title.isNullOrEmpty()) note.title = DEFAULT_TITLE

        _repoNotes.addOne(note) // --> do not make a new list, but just add a new note (DiffUtil)

        withContext(Dispatchers.IO) {
            repository.saveNote(note)
        }
    }

    fun deleteNote(id: String) = viewModelScope.launch {
        // remove from list
        _repoNotes.value = _repoNotes.value?.filter { it.noteId != id }
        // remove from DB
        withContext(Dispatchers.IO) { repository.deleteNote(id) }
    }

    fun findNoteById(id: String) = viewModelScope.launch {
        val foundNote = withContext(Dispatchers.IO) { repository.findNoteById(id) }

        // should a single note by a LiveData<Note>?
        /* _note.value = foundNote */
    }

    fun findNotesByTitle(title: String) = viewModelScope.launch {
        val foundNotes = withContext(Dispatchers.IO) { repository.findNoteByTitle(title) }
        _repoNotes.value = foundNotes
    }

    fun clearSearch() {
        getAllNotes()
    }
}