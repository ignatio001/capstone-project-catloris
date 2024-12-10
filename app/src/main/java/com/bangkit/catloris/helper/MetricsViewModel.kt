package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.responses.MetricsResponse
import kotlinx.coroutines.launch

class MetricsViewModel(private val repository: MetricsRepository) : ViewModel() {

    private val _metricsResult = MutableLiveData<MetricsResponse>()
    val metricsResult: LiveData<MetricsResponse> = _metricsResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun metricsUser(request: MetricsRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.metricsUser(request)
                _metricsResult.value = response
//                val response = apiService.sendMetrics(userId, age, height, weight, fats)
//                _metricsState.postValue(response)
            } catch (e: Exception) {
//                _metricsState.postValue(MetricsResponse(error = true, message = e.message))
                _metricsResult.value = MetricsResponse(
                    error = true,
                    message = "Metrics failed: ${e.localizedMessage}",
                    status = "error"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

}