package com.bangkit.catloris.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.FragmentProfileBinding
import com.bangkit.catloris.ui.Alarm.AlarmActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

//        binding.dashprofileEdit.setOnClickListener { startGallery() }
        binding.editPictureUser.setOnClickListener { startGallery() }

        binding.editPictureUser.visibility = View.GONE
        binding.saveProfileButton.visibility = View.GONE
        binding.editProfileButton.visibility = View.VISIBLE
        binding.fullName.isFocusableInTouchMode = false
        binding.phoneNumber.isFocusableInTouchMode = false
        binding.email.isFocusableInTouchMode = false


        binding.editProfileButton.setOnClickListener {
            binding.editPictureUser.visibility = View.VISIBLE
            binding.saveProfileButton.visibility = View.VISIBLE
            binding.editProfileButton.visibility = View.GONE
            binding.fullName.isFocusableInTouchMode = true
            binding.fullName.isFocusable = true
            binding.phoneNumber.isFocusableInTouchMode = true
            binding.phoneNumber.isFocusable = true
            binding.email.isFocusableInTouchMode = true
            binding.email.isFocusable = true
        }
        binding.saveProfileButton.setOnClickListener {
            binding.editPictureUser.visibility = View.GONE
            binding.saveProfileButton.visibility = View.GONE
            binding.editProfileButton.visibility = View.VISIBLE
            binding.fullName.isFocusableInTouchMode = false
            binding.fullName.isFocusable = false
            binding.phoneNumber.isFocusableInTouchMode = false
            binding.phoneNumber.isFocusable = false
            binding.email.isFocusableInTouchMode = false
            binding.email.isFocusable = false
        }



        return binding.root
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.dashProfilePict.setImageURI(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}