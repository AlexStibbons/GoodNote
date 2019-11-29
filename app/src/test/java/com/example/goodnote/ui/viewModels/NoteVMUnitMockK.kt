package com.example.goodnote.ui.viewModels
/*


import com.example.goodnote.repository.NoteRepo

import androidx.lifecycle.Observer
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.utils.toNoteModel
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExperimentalCoroutinesApi
class NoteVMUnitMockK : CoroutineTest {

    override lateinit var testScope: TestCoroutineScope

    override lateinit var dispatcher: TestCoroutineDispatcher

    private val repository: NoteRepo = mockk(relaxed = true)

    private val observer: Observer<List<NoteModel>> = mockk(relaxed = true)

    private lateinit var viewModel: NoteViewModel

    @BeforeEach
    fun setUp() {
        clearMocks(repository, observer)
        viewModel = NoteViewModel(repository)
        viewModel.repoNotes.observeForever(observer)
    }

    @AfterEach
    fun tearDown() {

    }

    @Nested
    @DisplayName("All notes")
    inner class testGetAllNotes {

        // given the repository returns this list of notes
        val notes = listOf(
            NoteDomanModel(
                "id",
                "title",
                "text",
                listOf(
                    TagDomainModel(
                        "id",
                        "name"
                    )
                )
            )
        )

        @Test
        @DisplayName("When there is 1 note, return list of 1 note")
        fun whenThereAreNotes() {
            coEvery { repository.getAllNotes() } returns notes
            viewModel.getAllNotes()
            val retVal = notes.map { it.toNoteModel() }
            // then repoNotes will equal val notes
            coVerify(atLeast = 1) { repository.getAllNotes() }
            verify { observer.onChanged(retVal) }
            assert(retVal == viewModel.repoNotes.value)
            assert(viewModel.repoNotes.value?.size == 1)
            confirmVerified(observer, repository)
        }

        @Test
        @DisplayName("When there are no notes, return empty list")
        fun whenNoNotes() {
            coEvery { repository.getAllNotes() } returns emptyList()
            viewModel.getAllNotes()
            coVerify(atLeast = 1) { repository.getAllNotes() }
            verifyAll {
                observer.onChanged(emptyList())
            }
            confirmVerified(observer)
        }
    }

}
*/