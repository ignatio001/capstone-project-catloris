package com.bangkit.catloris.helper

import com.google.gson.annotations.SerializedName

data class MetricsRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("age") val age: Int?,
    @SerializedName("height") val height: Float?,
    @SerializedName("weight") val weight: Float?,
    @SerializedName("fats") val fats: Float?
)