package id.hwaryun.story.data.network.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.hwaryun.story.data.model.viewparam.StoryViewParam

class PagedTestDataSource private constructor() :
    PagingSource<Int, LiveData<List<StoryViewParam>>>() {
    companion object {
        fun snapshot(items: List<StoryViewParam>): PagingData<StoryViewParam> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryViewParam>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryViewParam>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}