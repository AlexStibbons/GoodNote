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


@RunWith(JUnit4::class)
class NoteViewModelUnitTest {

    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
    fun testGetRepoNotes() {
    }

    @Test
    fun testGetAllNotes() {
    }

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
    }

    @After
    fun tearDown() {
    }
}