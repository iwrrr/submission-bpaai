package id.hwaryun.story.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.story.data.network.datasource.PagedTestDataSource
import id.hwaryun.story.domain.StoryLocationResult
import id.hwaryun.story.domain.StoryResult
import id.hwaryun.story.presentation.adapter.StoryAdapter
import id.hwaryun.story.utils.StoryDummy
import id.hwaryun.story.utils.TestDispatcherRule
import id.hwaryun.story.utils.getOrAwaitValue
import id.hwaryun.story.utils.noopListUpdateCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var storyViewModel: StoryViewModel

    private val stories = StoryDummy.storyResponse().map { it.toViewParam() }

    @Test
    fun `when getToken should return string`() = runTest {
        val expected = MutableLiveData<ViewResource<String>>()
        expected.value = ViewResource.Success("token")

        `when`(storyViewModel.tokenResult).thenReturn(expected)

        val actual = storyViewModel.tokenResult.getOrAwaitValue()
        verify(storyViewModel).tokenResult
        assertNotNull(actual)
        assertEquals(expected.value?.payload, actual.payload)
    }

    @Test
    fun `when getStoriesWithLocation should return stories`() = runTest {
        val expected = MutableLiveData<ViewResource<StoryLocationResult>>()
        expected.value = ViewResource.Success(stories)

        `when`(storyViewModel.storyLocationResult).thenReturn(expected)

        val actual = storyViewModel.storyLocationResult.getOrAwaitValue()
        verify(storyViewModel).storyLocationResult
        assertNotNull(actual)
        assertEquals(expected.value?.payload?.size, actual.payload?.size)
    }

    @Test
    fun `when getStories should return stories`() = runTest {
        val snapshot = PagedTestDataSource.snapshot(stories)
        val expected = MutableLiveData<ViewResource<StoryResult>>()
        expected.value = ViewResource.Success(snapshot)

        `when`(storyViewModel.storyResult).thenReturn(expected)

        val actual = storyViewModel.storyResult.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = dispatcherRule.dispatcher,
            workerDispatcher = dispatcherRule.dispatcher
        )

        actual.payload?.let { differ.submitData(it) }

        verify(storyViewModel).storyResult
        assertNotNull(differ.snapshot())
        assertEquals(stories.size, differ.snapshot().size)
    }
}