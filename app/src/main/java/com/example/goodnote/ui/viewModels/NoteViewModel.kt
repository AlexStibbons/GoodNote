package com.example.goodnote.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.models.Note
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.utils.dummyNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// AndroidViewModel(application) - use this one when you need application context
// when do you need application context? repo/network only
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    private val _notes: MutableLiveData<List<Note>> = MutableLiveData(dummyNotes)
    val notes: LiveData<List<Note>>
        get() = _notes


    private var _repoNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val repoNotes: LiveData<List<Note>>
        get() = _repoNotes

    init {
        getAllNotes()
    }

    // all repo functions here

    fun getAllNotes(): Unit {
         viewModelScope.launch(Dispatchers.IO) {
            val listIO = repository.getAllNotes()
            withContext(Dispatchers.Main) {
                 _repoNotes.postValue(listIO)
            }
        }
    }

    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {

        // if title.isEmpty || title == null --> title = DEFAULT_TITLE

        repository.saveNote(note)
        getAllNotes()
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