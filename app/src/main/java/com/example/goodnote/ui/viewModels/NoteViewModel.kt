package com.example.goodnote.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.models.Note
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.utils.DEFAULT_TITLE
import com.example.goodnote.utils.addOne
import com.example.goodnote.utils.dummyNotes
import com.example.goodnote.utils.setId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*it is better to do thread switching only once by switching to IO thread when it is needed.
This way we are saving processor time, since there won't be 2 context switches
where one would be sufficient.*/
class NoteViewModel(private val repository: NoteRepo) : ViewModel() {

    // for dummies -- can be removed
    private val _notes: MutableLiveData<List<Note>> = MutableLiveData(dummyNotes)
    val notes: LiveData<List<Note>>
        get() = _notes

    // for DB notes
    private var _repoNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val repoNotes: LiveData<List<Note>>
        get() = _repoNotes

    init {
        getAllNotes()
    }

    // all repo functions here

    fun getAllNotes() = viewModelScope.launch {
        val notes = withContext(Dispatchers.IO) { repository.getAllNotes() }
        _repoNotes.value = notes
    }

    fun saveNote(note: Note) = viewModelScope.launch {

        if (note.title.isNullOrEmpty()) note.title = DEFAULT_TITLE

        val newNote = note.setId()

        // doesn't add?
        /*_repoNotes.value  = _repoNotes.value?.run { // might not add if list is 0, which it can be
            toMutableList().add(newNote)
            Log.e("NOTE VM mutable list", "size is ${this.size}")
            toList()
        }*/

        // why does this work and above does not? a fully new list is added to
        // _repoNotes in both cases
       //val value: List<Note> = _repoNotes.value ?: emptyList()
        //_repoNotes.value = value + listOf(newNote)

        _repoNotes.addOne(newNote)

        withContext(Dispatchers.IO) {
            repository.saveNote(newNote)
            //getAllNotes()
        }
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        // remove from list
        _repoNotes.value = _repoNotes.value?.filter { it.id != id }
        // remove from DB
        withContext(Dispatchers.IO) { repository.deleteNote(id) }
    }

    fun findNoteById(id: Int) = viewModelScope.launch {
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

    // for dummy list -- can be removed
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