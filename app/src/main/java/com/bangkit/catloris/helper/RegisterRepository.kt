package com.bangkit.catloris.helper

import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.RegisterResponse

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerUser(
        userId: Int,
        fullname: String,
        email: String,
        password: String,
        contact: String,
        gender: String
    ): RegisterResponse {
        return apiService.register(userId, fullname, email, password, contact, gender)
    }
}