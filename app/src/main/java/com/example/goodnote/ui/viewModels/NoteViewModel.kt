package com.example.goodnote.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodnote.database.repository.NoteRepo
import com.example.goodnote.database.models.Note
import com.example.goodnote.utils.Injectors
import kotlinx.coroutines.launch
// AndroidViewModel(application) - use this one when you need application context
// when do you need application context? repo/network only? or?
class NoteViewModel(private val context: Context) : ViewModel() {

    // inject repository here via viewmodelfactory, so the views are completely decoupled from repos

    private val repository: NoteRepo = Injectors.getNoteRepository(context)

    // dummy lists
   private var _notes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>().also{
            it.postValue(dummyNotes)
        }
    }
    val notes: LiveData<List<Note>>
        get() = _notes

    // all repo functions here
    fun saveNote(note: Note) = viewModelScope.launch {
        repository.saveNote(note)
    }

    private val dummyNotes: List<Note> = listOf(
        Note("title", "text")
    )
}