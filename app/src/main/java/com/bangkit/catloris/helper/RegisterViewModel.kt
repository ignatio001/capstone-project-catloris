package com.bangkit.catloris.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.responses.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _registerState = MutableLiveData<RegisterResponse?>()
    val registerState: LiveData<RegisterResponse?> get() = _registerState

    fun registerUser(
        userId: Int,
        fullname: String,
        email: String,
        password: String,
        contact: String,
        gender: String
    ) {

        viewModelScope.launch {
            try {
                val response = registerRepository.registerUser(userId, fullname, email, password, contact, gender)
                _registerState.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _registerState.postValue(null)
            }
        }
    }

}