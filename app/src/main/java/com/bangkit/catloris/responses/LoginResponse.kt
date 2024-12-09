package com.bangkit.catloris.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: LoginData? = null
)

data class LoginData(

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null
)