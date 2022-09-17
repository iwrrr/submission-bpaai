package id.hwaryun.story.data.network.service

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.story.data.model.response.StoryResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StoryService {

    @POST("stories")
    suspend fun addNewStory(
        @Body body: RequestBody
    ): BaseResponse<Unit>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("location") location: Int = 0,
    ): BaseResponse<StoryResponse>
}