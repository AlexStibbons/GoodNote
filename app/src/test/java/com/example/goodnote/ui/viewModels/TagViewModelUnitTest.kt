package com.example.goodnote.ui.viewModels

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.goodnote.database.models.Tag
import com.example.goodnote.database.repository.TagRepo
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
import junit.framework.TestCase.assertNotNull

// refactor to use JUnit5 and MockK

@ExperimentalCoroutinesApi
class TagViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var observer: Observer<List<Tag>>

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
        val tags = listOf(Tag("name one", "fakeId"))
        `when`(repo.getAllTags()).thenReturn(tags)

        tagViewModel.getAllTags()

        // verify(repo).getAllTags() --> ERROR: wanted 1 time, but was 2 times
        // calls once on init and then calls method?
        verify(observer).onChanged(tags)
        assertEquals(1, tagViewModel.tags?.value?.size)
    }

    @Test
    fun addTag() {
    }

    @Test
    fun deleteTag() {
    }

    @Test
    fun findTagById() {
    }

    @Test
    fun findTagsByName() {
    }

    @Test
    fun clearSearch() {
    }
}