package id.hwaryun.story.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import id.hwaryun.core.base.BaseResponse
import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.presentation.adapter.StoryAdapter
import id.hwaryun.story.utils.StoryDummy
import id.hwaryun.story.utils.TestDispatcherRule
import id.hwaryun.story.utils.noopListUpdateCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    @Test
    fun `when getStories not return empty PagingData`() = runTest {
        val stories = StoryDummy.storyResponse().map { it.toEntity() }
        val pagingData = PagingData.from(stories)
        val flow = flowOf(pagingData)

        `when`(repository.getStories()).thenReturn(flow)
        val actuallyPagingData = repository.getStories()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = dispatcherRule.dispatcher,
            workerDispatcher = dispatcherRule.dispatcher
        )
        differ.submitData(actuallyPagingData.first().map { it.toViewParam() })
        advanceUntilIdle()
        assertNotNull(differ.snapshot())
        assertEquals(stories.size, differ.snapshot().size)
    }

    @Test
    fun `when getStoryWithLocation return stories`() = runTest {
        val expected = DataResource.Success(StoryDummy.getStories())
        val flow = flowOf(expected)
        `when`(repository.getStoriesWithLocation()).thenReturn(flow)
        val actual = repository.getStoriesWithLocation().first()
        assertTrue(actual.payload?.stories?.isNotEmpty() == true)
        assertEquals(expected, actual)
    }

    @Test
    fun `when getStoryWithLocation return empty`() = runTest {
        val expected = DataResource.Success(
            BaseResponse<StoryResponse>(
                false,
                "success",
                null,
                listOf()
            )
        )

        val flow = flowOf(expected)
        `when`(repository.getStoriesWithLocation()).thenReturn(flow)
        val actual = repository.getStoriesWithLocation().first()
        assertTrue(actual.payload?.stories?.isEmpty() == true)
        assertEquals(expected, actual)
    }

    @Test
    fun `when getStoryWithLocation return error`() = runTest {
        val expected = DataResource.Error<BaseResponse<StoryResponse>>(RuntimeException())

        val flow = flowOf(expected)
        `when`(repository.getStoriesWithLocation()).thenReturn(flow)
        val actual = repository.getStoriesWithLocation().first()
        assertTrue(actual is DataResource.Error)
        assertEquals(expected, actual)
    }
}