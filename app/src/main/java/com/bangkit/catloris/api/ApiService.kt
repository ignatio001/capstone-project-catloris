package com.bangkit.catloris.api

import com.bangkit.catloris.responses.MetricsResponse
import com.bangkit.catloris.responses.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("user_id") userId: Int,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("contact") contact : String,
        @Field("gender") gender: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/metrics/user")
    suspend fun sendMetrics(
        @Field("user_id") userId: Int,
        @Field("age") age: Int,
        @Field("height") height: Float,
        @Field("weight") weight: Float,
        @Field("fats") fats: Float
    ): MetricsResponse
}