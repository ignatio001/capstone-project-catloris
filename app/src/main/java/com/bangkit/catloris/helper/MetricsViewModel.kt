package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.responses.MetricsResponse
import kotlinx.coroutines.launch

class MetricsViewModel : ViewModel() {
    private val metricsRepository = MetricsRepository(ApiConfig.getApiService())

    private val _metricsResult = MutableLiveData<MetricsResponse>()
    val metricsResult: LiveData<MetricsResponse> = _metricsResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun metricsUser(age: Int, height: Float, weight: Float, fats: Float) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = metricsRepository.metricsUser(age, height, weight, fats)
                _metricsResult.value = response
            } catch (e: Exception) {
                _metricsResult.value = MetricsResponse(error = true, message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

}