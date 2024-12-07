package com.bangkit.catloris.helper

import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.MetricsResponse

class MetricsRepository(private val apiService: ApiService) {
    suspend fun metricsUser(age: Int, height: Float, weight: Float, fats: Float): MetricsResponse {
        return apiService.metrics(age, height, weight, fats)
    }
}