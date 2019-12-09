package com.example.goodnote.ui.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.goodnote.repository.NoteRepo
import com.example.goodnote.repository.TagRepo
import com.example.goodnote.ui.models.TagModel
import androidx.lifecycle.Observer
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.utils.toListTagModel
import com.example.goodnote.utils.toTagDomainModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteDetailsViewModelUnit4mockK {

    @get:Rule
    val rule = InstantTaskExecutorRule() // junit 4

    val testDispatcher = TestCoroutineDispatcher()

    private val noteRepo: NoteRepo = mockk(relaxed = true)

    private val tagRepo: TagRepo = mockk(relaxed = true)

    private val tagObserver: Observer<List<TagModel>> = mockk(relaxed = true)

    private val onSaveObserver: Observer<Long> = mockk(relaxed = true)

    private lateinit var viewModel: NoteDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        //clearAllMocks()
        clearMocks(tagRepo, noteRepo, tagObserver, onSaveObserver)
        viewModel = NoteDetailsViewModel(noteRepo, tagRepo)
        viewModel.existingTags.observeForever(tagObserver)
        viewModel.onNoteSaved.observeForever(onSaveObserver)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Given there are 2 tags in the list, when fetching tags is called, it should return a list of 2 tags`() {
        val tags = listOf(TagDomainModel("id1", "name1"), TagDomainModel("id2", "name2"))
        coEvery { tagRepo.getAllTags() }.returns(tags)

        viewModel.getAllTags()

        coVerify { tagRepo.getAllTags() }
        verifyAll {
            tagObserver.onChanged(tags.toListTagModel())
            // more verification
        }
        assert(viewModel.existingTags.value?.size == tags.size)
        confirmVerified(tagObserver, tagRepo)
    }

    @Test
    fun `Given there are no tags, when we add a tag,then the list of tags increases to one` (){
        //clearMocks(tagRepo, tagObserver)
        val addedTag = TagModel("2", "2")
        val tags = listOf(addedTag)

        viewModel.addTag(addedTag)

        coVerify {
            tagRepo.getAllTags()
            tagRepo.addTag(addedTag.toTagDomainModel())
        }
        verify {
            tagObserver.onChanged(tags)
        }
        assert(viewModel.existingTags.value?.size == 1)
        confirmVerified(tagRepo, tagObserver)
    }

    
}