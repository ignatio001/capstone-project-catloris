package com.bangkit.catloris.responses

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
