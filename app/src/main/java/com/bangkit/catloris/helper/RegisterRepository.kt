package com.bangkit.catloris.helper

import android.util.Log
import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.RegisterResponse

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        return apiService.register(request)
    }
}