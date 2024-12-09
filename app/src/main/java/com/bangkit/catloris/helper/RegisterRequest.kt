package com.bangkit.catloris.helper

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("contact") val contact: String,
    @SerializedName("gender") val gender: String
)
