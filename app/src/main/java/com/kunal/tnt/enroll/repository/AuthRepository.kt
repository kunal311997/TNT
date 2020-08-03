package com.kunal.tnt.enroll.repository

import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.enroll.models.LoginResponse
import com.kunal.tnt.enroll.network.AuthApi
import com.kunal.tnt.enroll.util.AuthConstants
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val authApi: AuthApi
) : BaseRepository() {


    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val hashMap = hashMapOf(
            AuthConstants.EMAIL to email,
            AuthConstants.PASSWORD to password
        )
        return safeApiCall(ioDispatcher) { authApi.login(hashMap) }
    }
}