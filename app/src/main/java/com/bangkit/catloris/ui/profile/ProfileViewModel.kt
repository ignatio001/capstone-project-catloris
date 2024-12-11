package com.bangkit.catloris.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.catloris.helper.ProfileRepository
import com.bangkit.catloris.responses.Data
import com.bangkit.catloris.responses.UserProfileResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfileResponse>()
    val userProfile: LiveData<UserProfileResponse> get() = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val response: Response<UserProfileResponse> = repository.getUserProfile(token)
                if (response.isSuccessful) {
                    _userProfile.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}