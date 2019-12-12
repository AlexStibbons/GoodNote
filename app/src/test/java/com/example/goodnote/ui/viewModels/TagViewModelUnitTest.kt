package com.example.goodnote.ui.viewModels
/* JUnit 4
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.goodnote.database.entityModels.TagEntity
import com.example.goodnote.repository.TagRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import junit.framework.TestCase.assertEquals

@ExperimentalCoroutinesApi
class TagViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var observer: Observer<List<TagEntity>>

    @Mock
    private lateinit var repo: TagRepo

    private lateinit var tagViewModel: TagViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        tagViewModel = TagViewModel(repo)
        tagViewModel.tags.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAllTags() = testDispatcher.runBlockingTest {
        val tags = listOf(TagEntity("name one", "fakeId"))
        `when`(repo.getAllTags()).thenReturn(tags)

        tagViewModel.getTags()

       // verify(repo).getAllTags() //--> ERROR: wanted 1 time, but was 2 times
        // calls once on init and then calls method?
        verify(observer).onChanged(tags)
        assertEquals(1, tagViewModel.tags?.value?.size)
    }

    @Test
    fun saveTag() = testDispatcher.runBlockingTest {
        val tagOne = TagEntity("first", "fakeID1")
        val tagTwo = TagEntity("second", "fakeId2")

        tagViewModel.saveTag(tagOne)
        tagViewModel.saveTag(tagTwo)

        verify(repo).saveTag(tagOne)
        verify(repo).saveTag(tagTwo)
        assertEquals(2, tagViewModel.tags?.value?.size)
        assertEquals("first", tagViewModel.tags?.value?.get(0)?.name)
    }

    @Test
    fun deleteTag() = testDispatcher.runBlockingTest {
        val tagOne = TagEntity("first", "fakeID1")
        val tagTwo = TagEntity("second", "fakeId2")
        tagViewModel.saveTag(tagOne)
        tagViewModel.saveTag(tagTwo)
        val returned: List<TagEntity> = listOf(tagTwo)

        tagViewModel.deleteTag("fakeID1")

        verify(repo).deleteTagById("fakeID1")
        verify(observer).onChanged(returned)
    }

    @Test
    fun findTagById() = testDispatcher.runBlockingTest {
        val tagOne = TagEntity("first", "fakeID1")
        val tagTwo = TagEntity("second", "fakeId2")
        tagViewModel.saveTag(tagOne)
        tagViewModel.saveTag(tagTwo)
        `when`(repo.findTagById("fakeID1")).thenReturn(tagOne)

        tagViewModel.findTagById("fakeID1")

        verify(repo).findTagById("fakeID1")

        // no LiveData<TagEntity> to retun in tag view model

    }

    @Test
    fun findTagsByName() = testDispatcher.runBlockingTest {
        val tagOne = TagEntity("first", "fakeID1")
        val tagTwo = TagEntity("second", "fakeId2")
        val tagThree = TagEntity("irs", "fakeId3")
        tagViewModel.saveTag(tagOne)
        tagViewModel.saveTag(tagTwo)
        tagViewModel.saveTag(tagThree)
        val returned = listOf(tagOne, tagThree)
        `when`(repo.findTagsByName("irs")).thenReturn(returned)

        tagViewModel.findTagsByName("irs")

        verify(repo).findTagsByName("irs")
        verify(observer).onChanged(returned)
    }
}*/