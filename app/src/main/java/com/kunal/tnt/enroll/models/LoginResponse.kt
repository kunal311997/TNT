package com.kunal.tnt.enroll.models

import com.squareup.moshi.Json

data class LoginResponse(

    @Json(name = "code")
    val code: Int? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "email")
    val email: String? = null,

    @Json(name = "token")
    val token: String? = null,

    @Json(name = "username")
    val username: String? = null
)
