package com.bangkit.catloris.helper

import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.ProfileResponse
import com.bangkit.catloris.responses.UserProfileResponse
import retrofit2.Response

class ProfileRepository(private val apiService: ApiService) {
    suspend fun getUserProfile(token: String): Response<UserProfileResponse> {
        return apiService.getUserProfile("Bearer $token")
    }
}