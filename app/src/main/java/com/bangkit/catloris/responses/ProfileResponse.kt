package com.bangkit.catloris.responses

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
