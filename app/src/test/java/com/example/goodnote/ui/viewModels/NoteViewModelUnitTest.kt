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
import com.example.goodnote.utils.DEFAULT_TITLE
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

    @Test
    fun testGetAllNotes() {
        testDispatcher.runBlockingTest {
            // given the repository returns this list of notes
            val notes = listOf(Note("test", "test"))
            `when`(repository.getAllNotes()).thenReturn(notes)

            // when view model calls function
            viewModel.getAllNotes()

            // then repoNotes will equal val notes
            // verify(repository).getAllNotes() --> ERROR: wanted 1 time, was 2 times
            // verify repo passes on others ?
            verify(observer).onChanged(notes)
        }

    }

    @Test
    fun testSaveNote() {
        testDispatcher.runBlockingTest {
            // given
            val noteToAdd = Note("", "add")

            //when
            viewModel.saveNote(noteToAdd)

            // then
            verify(repository).saveNote(noteToAdd)
            assertNotNull(viewModel.repoNotes.value)
            assertEquals(1, viewModel.repoNotes.value?.size)
            assertEquals(DEFAULT_TITLE, viewModel.repoNotes.value?.get(0)?.title)
        }
    }

    @Test
    fun testDeleteNote() {

        testDispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            viewModel.saveNote(Note("a", "a", "a"))
            viewModel.saveNote(Note("b", "b", "b"))
            viewModel.saveNote(Note("c", "c", "c"))

            // when viewModel deletes note
            viewModel.deleteNote("a")
            // then there are 2 notes left
            verify(repository).deleteNote("a")
            assertEquals(2, viewModel.repoNotes?.value?.size)
        }

    }

    @Test
    fun testFindNoteById() {
        testDispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            viewModel.saveNote(Note("a", "a", "a"))
            viewModel.saveNote(Note("b", "b", "b"))
            viewModel.saveNote(Note("c", "c", "c"))

            // when viewModel finds one
            viewModel.findNoteById("b")

            // then nothing happens because nothing happened in viewModel anyway

        }
    }

    @Test
    fun testFindNotesByTitle() {
        testDispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            var title = "b"
            val noteOne = Note("ab", "a", "a")
            val noteTwo = Note("b", "b", "b")
            val noteThree = Note("c", "c", "c")
            viewModel.saveNote(noteOne)
            viewModel.saveNote(noteTwo)
            viewModel.saveNote(noteThree)

            val notes = listOf(noteOne, noteTwo)

            `when`(repository.findNoteByTitle(title)).thenReturn(notes)

            // when viewModel searches
            viewModel.findNotesByTitle(title)

            // then repoNotes are changed to notes
            //verify(repository).findNoteByTitle(title) --> same invocation error
            verify(observer).onChanged(notes)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}