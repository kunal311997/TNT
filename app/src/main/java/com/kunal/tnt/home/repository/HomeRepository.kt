package com.kunal.tnt.home.repository

import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.network.HomeApi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class HomeRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val homeApi: HomeApi
) : BaseRepository() {


    suspend fun getFeed(): Resource<List<Feed>> {
        return safeApiCall(ioDispatcher) { homeApi.callFeedApi() }
    }

}