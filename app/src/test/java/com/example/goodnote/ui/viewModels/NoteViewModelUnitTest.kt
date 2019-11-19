package com.example.goodnote.ui.viewModels

import com.example.goodnote.database.repository.NoteRepo
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.goodnote.database.models.Note
import org.junit.Rule
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`



@RunWith(JUnit4::class)
class NoteViewModelUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NoteRepo

    @Mock
    lateinit var observer: Observer<List<Note>>

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NoteViewModel(repository)
        viewModel.repoNotes.observeForever(observer)
    }


    @Test
    fun testGetAllNotes() {

        val notes = listOf(Note("test", "test"))

        //`when`(repository.getAllNotes()).thenReturn(notes)

        viewModel.getAllNotes()
        verify(observer).onChanged(notes)
    }
/*
    @Test
    fun testSaveNote() {
    }

    @Test
    fun testDeleteNote() {
    }

    @Test
    fun testFindNoteById() {
    }

    @Test
    fun testFindNotesByTitle() {
    }

    @Test
    fun testClearSearch() {
    }*/

    @After
    fun tearDown() {
    }
}