package com.bangkit.catloris.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import com.bangkit.catloris.api.ApiService
import com.bangkit.catloris.responses.UserProfileResponse
import retrofit2.Response

class ProfileRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_profile", MODE_PRIVATE)

    // Fungsi untuk mengambil emailUser dari SharedPreferences
    fun getEmailUser(): String? {
        val sharedPreferences = context.getSharedPreferences("email_user", MODE_PRIVATE)
        return sharedPreferences.getString("email_user", "Guest")
    }
    fun getUserFullName(): String? {
        return sharedPreferences.getString("full_name", "")
    }

    fun getUserPhoneNumber(): String? {
        return sharedPreferences.getString("phone_number", "")
    }

    fun getUserImageUri(): Uri? {
        val imageUriString = sharedPreferences.getString("profile_image_uri", null)
        return if (imageUriString != null) Uri.parse(imageUriString) else null
    }

    fun saveUserProfile(fullName: String, phoneNumber: String, email: String, imageUri: Uri?) {
        val editor = sharedPreferences.edit()
        editor.putString("full_name", fullName)
        editor.putString("phone_number", phoneNumber)
        editor.putString("email_user", email)
        imageUri?.let {
            editor.putString("profile_image_uri", it.toString())
        }
        editor.apply()
    }
}