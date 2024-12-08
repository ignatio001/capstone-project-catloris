package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.responses.MetricsResponse
import kotlinx.coroutines.launch

class MetricsViewModel(private val apiService: MetricsRepository) : ViewModel() {

    private val _metricsState = MutableLiveData<MetricsResponse>()
    val metricsState: LiveData<MetricsResponse> get() = _metricsState

    fun sendMetrics(userId: Int, age: Int, height: Float, weight: Float, fats: Float) {
        viewModelScope.launch {
            try {
                val response = apiService.sendMetrics(userId, age, height, weight, fats)
                _metricsState.postValue(response)
            } catch (e: Exception) {
                // Handle exceptions (e.g., network issues)
                _metricsState.postValue(MetricsResponse(error = true, message = e.message))
            }
        }
    }

}