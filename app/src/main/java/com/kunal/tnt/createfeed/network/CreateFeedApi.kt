package com.kunal.tnt.createfeed.network

import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.createfeed.data.ImageUploadResponse
import com.kunal.tnt.createfeed.utils.FeedConstants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CreateFeedApi {

    @Multipart
    @POST(FeedConstants.FEED_BASE_URL)
    suspend fun createFeed(
        @PartMap map: HashMap<String, RequestBody>
        //@Part file: MultipartBody.Part?
    ): CreateFeedResponse


    @FormUrlEncoded
    @POST(FeedConstants.IMAGE_BASE_URL)
    suspend fun uploadImage(@Field("image") file: String): ImageUploadResponse
}