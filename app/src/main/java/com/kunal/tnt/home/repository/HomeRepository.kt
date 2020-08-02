package com.kunal.tnt.home.repository

import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.createfeed.network.CreateFeedApi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class HomeRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val createFeedApi: CreateFeedApi
) : BaseRepository() {


//    suspend fun getFeed(): Resource<FeedResponse> {
//        return safeApiCall(ioDispatcher) { homeApi.callFeedApi() }
//    }

    suspend fun createFeed(
        title: String,
        keywords: String,
        description: String,
        file: File?
    ): Resource<CreateFeedResponse> {

        val hashMap = HashMap<String, RequestBody>()
        hashMap["title"] = Utilities.getTextPart(title)
        hashMap["keywords"] = Utilities.getTextPart(keywords)
        hashMap["description"] = Utilities.getTextPart(description)

        val imageFile: MultipartBody.Part? = file?.let { Utilities.getImagePart(it, "image") }

        return safeApiCall(ioDispatcher) {
            createFeedApi.createFeed(hashMap)
        }
    }
}