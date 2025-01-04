package com.bangkit.catloris.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
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

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> get() = _fullName

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _profileImageUri = MutableLiveData<Uri>()
    val profileImageUri: LiveData<Uri> get() = _profileImageUri


    fun loadUserProfile() {
        _fullName.postValue(repository.getUserFullName() ?: "")
        _phoneNumber.postValue(repository.getUserPhoneNumber() ?: "")
        _email.postValue(repository.getEmailUser() ?: "Guest")
        _profileImageUri.postValue(repository.getUserImageUri())
    }


    @SuppressLint("NullSafeMutableLiveData")
    fun saveUserProfile(fullName: String, phoneNumber: String, email: String, imageUri: Uri?) {
        repository.saveUserProfile(fullName, phoneNumber, email, imageUri)
        _fullName.postValue(fullName)
        _phoneNumber.postValue(phoneNumber)
        _email.postValue(email)
        _profileImageUri.postValue(imageUri)
    }
}