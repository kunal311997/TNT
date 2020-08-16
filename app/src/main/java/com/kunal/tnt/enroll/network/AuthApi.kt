package com.kunal.tnt.enroll.network

import com.kunal.tnt.enroll.models.LoginResponse
import com.kunal.tnt.enroll.util.AuthConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST(AuthConstants.LOGIN)
    suspend fun login(@Body hashMap: HashMap<String, String>): LoginResponse

    @POST(AuthConstants.SIGN_UP)
    suspend fun signUp(@Body hashMap: HashMap<String, String>): LoginResponse

}