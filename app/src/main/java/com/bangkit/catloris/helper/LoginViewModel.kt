package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.responses.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginState = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginState.value = response
                _errorState.value = null
            } catch (e: Exception) {
                _loginState.value = null
                _errorState.value = e.message
            }
        }
    }
}