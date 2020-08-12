package com.kunal.tnt.createfeed.repository

import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.createfeed.network.CreateFeedApi
import com.kunal.tnt.createfeed.utils.FeedConstants
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class CreateFeedRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val createFeedApi: CreateFeedApi
) : BaseRepository() {


    suspend fun createFeed(
        title: String,
        category: String,
        description: String,
        source: String,
        createdBy: String,
        file: File?
    ): Resource<CreateFeedResponse> {

        val hashMap = HashMap<String, RequestBody>()
        hashMap[FeedConstants.KEY_TITLE] = Utilities.getTextPart(title)
        hashMap[FeedConstants.KEY_CATEGORY] = Utilities.getTextPart(category)
        hashMap[FeedConstants.KEY_DESCRIPTION] = Utilities.getTextPart(description)
        hashMap[FeedConstants.KEY_SOURCE] = Utilities.getTextPart(source)
        hashMap[FeedConstants.KEY_CREATED_BY] = Utilities.getTextPart(createdBy)

        val imageFile: MultipartBody.Part? =
            file?.let { Utilities.getImagePart(it, FeedConstants.KEY_IMAGE) }

        return safeApiCall(ioDispatcher) {
            createFeedApi.createFeed(hashMap,imageFile)
        }
    }
}