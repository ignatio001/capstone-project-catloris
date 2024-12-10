package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.responses.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.registerUser(request)
                _registerResult.value = response
            } catch (e: Exception) {
                _registerResult.value = RegisterResponse(
                    error = true,
                    message = "Registration failed: ${e.localizedMessage}",
                    status = "error"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}