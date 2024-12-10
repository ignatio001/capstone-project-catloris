package com.bangkit.catloris.helper

import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.MetricsResponse

class MetricsRepository(private val apiService: ApiService) {
    suspend fun metricsUser(request: MetricsRequest): MetricsResponse {
        return apiService.metrics(request)
    }


}