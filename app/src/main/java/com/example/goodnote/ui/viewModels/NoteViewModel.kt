package com.example.goodnote.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.database.models.Note
import com.example.goodnote.utils.DUMMY_TEXT
import com.example.goodnote.utils.DUMMY_TITLE
import com.example.goodnote.utils.Injectors
import kotlinx.coroutines.launch

// AndroidViewModel(application) - use this one when you need application context
// when do you need application context? repo/network only
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {



    // dummy notes
    private var dummyNotes: MutableList<Note> = mutableListOf(
        Note(DUMMY_TITLE, DUMMY_TEXT).apply { id = 1 },
        Note("Title Two", "text").apply { id = 2 },
        Note("Title Three", DUMMY_TEXT).apply { id = 3 },
        Note("Title Four", DUMMY_TEXT).apply { id = 4 },
        Note("Title Five", "text").apply { id = 5 },
        Note("Title Six", "text").apply { id = 6 },
        Note("Title Seven", "text").apply { id = 7 },
        Note("Title Eight", DUMMY_TEXT).apply { id = 8 },
        Note("Title Nine", "text").apply { id = 9 },
        Note("Title Ten", "text").apply { id = 10 }
    )

    private val _notes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>().also {
            it.value = dummyNotes
        }
    }
    val notes: LiveData<List<Note>>
        get() = _notes

    // all repo functions here
    // scopes can be in job or not since viewModelScope is automatic
    fun saveNote(note: Note) = viewModelScope.launch {
        repository.saveNote(note)
    }

    fun addNote(note: Note) {
        dummyNotes.add(note)
        //_notes.value = dummyNotes
        _notes.postValue(dummyNotes)
    }

    fun removeNote(id: Int) {
        val note = dummyNotes.find { it.id == id }
        dummyNotes.remove(note)
        _notes.value = dummyNotes
    }

    fun filterNote(title: String) {
        val filtered =  dummyNotes.asSequence().filter {
            it.title.contains(title, true)
        }.toList()

        _notes.value = filtered
    }

    fun clearFilter() {
        _notes.value = dummyNotes
    }
}