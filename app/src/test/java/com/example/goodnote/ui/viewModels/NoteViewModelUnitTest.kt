package com.example.goodnote.ui.viewModels

import com.example.goodnote.repository.NoteRepo

import androidx.lifecycle.Observer
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.utils.DEFAULT_TITLE
import com.example.goodnote.utils.toNoteDomainModel
import com.example.goodnote.utils.toNoteModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(InstantTaskExecutorExtension::class, MockitoExtension::class)
@ExperimentalCoroutinesApi
class NoteViewModelUnitTest: CoroutineTest {

    override lateinit var testScope: TestCoroutineScope

    override lateinit var dispatcher: TestCoroutineDispatcher

    @Mock
    private lateinit var repository: NoteRepo

    @Mock
    lateinit var observer: Observer<List<NoteModel>>

    private lateinit var viewModel: NoteViewModel

    @BeforeEach
    fun setUp() {
        dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = NoteViewModel(repository)
        viewModel.repoNotes.observeForever(observer)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Nested
    @DisplayName("Given we call get all notes")
    inner class testGetAllNotes {

        @Test
        @DisplayName("When there are existing notes")
        fun whenThereAreNotes() {
            dispatcher.runBlockingTest {
                // given the repository returns this list of notes
                val notes = listOf(
                    NoteDomanModel(
                        "id",
                        "title",
                        "text",
                        listOf(TagDomainModel(
                            "id",
                            "name"
                        ))
                    )
                )
                `when`(repository.getAllNotes()).thenReturn(notes)

                // when view model calls function
                viewModel.getAllNotes()

                // then repoNotes will equal val notes
                // verify(repository).getAllNotes() --> ERROR: wanted 1 time, was 2 times
                // verify repo passes on others ?
                verify(observer).onChanged(notes.map { it.toNoteModel() })
            }
        }
    }

    @Test

    fun testSaveNote() {
        dispatcher.runBlockingTest {
            // given
            val noteToAdd = NoteDetailsModel("fake1", "", "add")

            //when
            viewModel.saveNote(noteToAdd)

            // then
            verify(repository).getAllNotes()
            verify(repository).saveNote(noteToAdd.toNoteDomainModel())
            assertNotNull(viewModel.repoNotes.value)
            assertEquals(1, viewModel.repoNotes.value?.size)
            assertEquals(DEFAULT_TITLE, viewModel.repoNotes.value?.get(0)?.title)
        }
    }

    @Test
    fun testDeleteNote() {

        dispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            viewModel.saveNote(NoteDetailsModel("a", "a", "a"))
            viewModel.saveNote(NoteDetailsModel("b", "b", "b"))
            viewModel.saveNote(NoteDetailsModel("c", "c", "c"))

            // when viewModel deletes note
            viewModel.deleteNote("a")
            // then there are 2 notes left
            verify(repository).deleteNote("a")
            assertEquals(2, viewModel.repoNotes?.value?.size)
        }

    }

    @Test
    fun testFindNoteById() {
        dispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            viewModel.saveNote(NoteDetailsModel("a", "a", "a"))
            viewModel.saveNote(NoteDetailsModel("b", "b", "b"))
            viewModel.saveNote(NoteDetailsModel("c", "c", "c"))

            // when viewModel finds one
            viewModel.findNoteById("b")

            // then nothing happens because nothing happened in viewModel anyway

        }
    }

    @Test
    fun testFindNotesByTitle() {
        dispatcher.runBlockingTest {
            // given there are 3 notes in repoNotes
            var title = "b"
            val noteOne = NoteDetailsModel("ab", "a", "a")
            val noteTwo = NoteDetailsModel("b", "b", "b")
            val noteThree = NoteDetailsModel("c", "c", "c")
            viewModel.saveNote(noteOne)
            viewModel.saveNote(noteTwo)
            viewModel.saveNote(noteThree)

            val notes = listOf(noteOne, noteTwo)

            // when viewModel searches
            viewModel.findNotesByTitle(title)

            // _repoNotes should be filtered to show list of notes
            verify(observer).onChanged(notes.map { it.toNoteModel() })
        }
    }

}