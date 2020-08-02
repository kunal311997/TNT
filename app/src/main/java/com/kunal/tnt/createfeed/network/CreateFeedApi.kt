package com.kunal.tnt.createfeed.network

import com.kunal.tnt.createfeed.data.CreateFeedResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface CreateFeedApi {

    @Multipart
    @POST("feed/createFeed")
    suspend fun createFeed(
        @PartMap map: HashMap<String, RequestBody>
    ): CreateFeedResponse
}