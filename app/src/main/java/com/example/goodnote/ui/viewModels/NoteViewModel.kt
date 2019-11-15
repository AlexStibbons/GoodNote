package com.example.goodnote.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.models.Note
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.utils.dummyNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// AndroidViewModel(application) - use this one when you need application context
// when do you need application context? repo/network only
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    private val _notes: MutableLiveData<List<Note>> = MutableLiveData(dummyNotes)
    val notes: LiveData<List<Note>>
        get() = _notes

    fun getNotes2(): LiveData<List<Note>> = _notes

    // all repo functions here

    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllNotes()
    }

    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(id)
    }

    fun findNoteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.findNoteById(id)
    }

    fun findNotesByTitle(title: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.findNoteByTitle(title)
    }

    // for dummy list
    fun addNote(note: Note) {
        dummyNotes.add(note)
        _notes.postValue(dummyNotes)
    }

    fun removeNote(id: Int) {
        val note = dummyNotes.find { it.id == id }
        dummyNotes.remove(note)
        _notes.value = dummyNotes
    }

    fun filterNote(title: String) {
        val filtered = dummyNotes.filter {
            it.title.contains(title, true)
        }.toList()

        _notes.value = filtered
    }

    fun clearFilter() {
        _notes.value = dummyNotes
    }
}