package com.kunal.tnt.common.repository

import com.kunal.tnt.common.data.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


open class BaseRepository {

    suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.error(throwable, null)
                    is HttpException -> {
                        Resource.error(throwable, null)
                    }
                    is Exception -> Resource.error(throwable, null)
                    else -> Resource.error(throwable, null)
                }
            }
        }
    }
}