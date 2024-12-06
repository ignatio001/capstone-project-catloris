package com.bangkit.catloris.api

import com.bangkit.catloris.responses.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("fullname") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("contact") contact : String,
        @Field("gender") gender: String
    ): RegisterResponse
}