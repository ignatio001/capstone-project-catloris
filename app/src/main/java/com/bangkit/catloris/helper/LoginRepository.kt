package com.bangkit.catloris.helper

import android.annotation.SuppressLint
import android.net.http.HttpException
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.responses.LoginResponse
import java.io.IOException

class LoginRepository {
    private val apiService = ApiConfig.getApiService()

    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            apiService.login(email, password)
        } catch (@SuppressLint("NewApi") e: HttpException) {
            throw Exception("Server error: ${e.message}")
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Unexpected error: ${e.message}")
        }
    }
}