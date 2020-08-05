package com.kunal.tnt.enroll.network

import com.kunal.tnt.enroll.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body hashMap: HashMap<String, String>): LoginResponse

    @POST("auth/signUp")
    suspend fun signUp(@Body hashMap: HashMap<String, String>): LoginResponse

}