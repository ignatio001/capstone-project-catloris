package com.bangkit.catloris.helper

import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.RegisterResponse

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerUser(name: String, email: String, password: String, contact: String, gender: String): RegisterResponse {
        return apiService.register(name, email, password, contact, gender)
    }
}