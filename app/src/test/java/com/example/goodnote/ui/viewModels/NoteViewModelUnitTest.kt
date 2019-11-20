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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.goodnote.database.models.Note
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

// naming convention:
// subjectUnderTest_actionOrInput_resultState
// example: getAllNotes_manyNotes_returnsManyNotes
// example: getAllNotes_noNotes_returnsEmptyList

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class) // or AndroidJUnit4?
class NoteViewModelUnitTest {

    /*rule allow us to run LiveData synchronously --> why? */
    @get:Rule
    var rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var repository: NoteRepo

    @Mock
    lateinit var observer: Observer<List<Note>>

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = NoteViewModel(repository)
        viewModel.repoNotes.observeForever(observer)
    }

    // everything breaks because of coroutines
   /* @Test
    fun testGetRepoNotes() {
        // passes when init(read: 2 context changes) is commented in NotesViewModel

        // WHEN
        val returns: LiveData<List<Note>> = viewModel.repoNotes
        // THEN
        assertNotNull(returns)
    }*/

    @Test
    fun testGetAllNotes() {

        testDispatcher.runBlockingTest{
            // given
            val notes = listOf(Note("test", "test"))
            `when`(repository.getAllNotes()).thenReturn(notes)
            // when
            viewModel.getAllNotes()

            // then
            verify(observer).onChanged(notes)
        }

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
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}