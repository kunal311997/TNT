package com.kunal.tnt.home.repository

import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.createfeed.data.CreateFeedRequest
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.createfeed.network.CreateFeedApi
import com.kunal.tnt.feed.data.FeedResponse
import com.kunal.tnt.home.network.HomeApi
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Named

class HomeRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val homeApi: HomeApi,
    private val createFeedApi: CreateFeedApi
) : BaseRepository() {


    suspend fun getFeed(): Resource<FeedResponse> {
        return safeApiCall(ioDispatcher) { homeApi.callFeedApi() }
    }

    suspend fun createFeed(@Body request: CreateFeedRequest): Resource<CreateFeedResponse> {
        return safeApiCall(ioDispatcher) { createFeedApi.createFeed(request) }
    }
}