package com.kunal.tnt.home.paging

import androidx.paging.PagingSource
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.data.FeedResponse
import com.kunal.tnt.home.network.HomeApi

class FeedPagingSource(
    private val homeApi: HomeApi
) : PagingSource<Int, Feed>() {

    var totalPages = 1

    companion object {
        var totalList = ArrayList<Feed>()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feed> {
        try {

            val currentLoadingPageKey = params.key ?: 1

            var response: FeedResponse? = null
            if (currentLoadingPageKey <= totalPages) {
                response = homeApi.callFeedApi(currentLoadingPageKey)
                response.feedsList?.forEach {
                    totalList.add(it)
                }
            }
            totalPages = response?.totalPages!!
            val responseData = mutableListOf<Feed>()
            val data = response.feedsList ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}