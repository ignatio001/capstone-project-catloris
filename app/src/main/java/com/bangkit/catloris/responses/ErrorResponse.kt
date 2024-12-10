package com.bangkit.catloris.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("detail")
    val detail: List<DetailItem?>? = null
)

data class DetailItem(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("loc")
    val loc: List<String?>? = null,

    @field:SerializedName("type")
    val type: String? = null
)
