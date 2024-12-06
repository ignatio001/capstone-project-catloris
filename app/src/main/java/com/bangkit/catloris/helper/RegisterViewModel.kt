package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.responses.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = RegisterRepository(ApiConfig.getApiService())

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password: String, contact: String, gender: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.registerUser(name, email, password, contact, gender)
                _registerResult.value = response
            } catch (e: Exception) {
                _registerResult.value = RegisterResponse(error = true, message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}