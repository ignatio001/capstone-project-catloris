package com.bangkit.catloris.ui.profile

import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bangkit.catloris.R
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.FragmentProfileBinding
import com.bangkit.catloris.helper.ProfileRepository
import com.bangkit.catloris.helper.ProfileViewModelFactory


class ProfileFragment  : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var viewModel: ProfileViewModel

    // ViewModel dan LiveData
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(ProfileRepository(requireActivity().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Mengambil emailUser dari SharedPreferences secara langsung
        val sharedPreferences = requireActivity().getSharedPreferences("email_user", MODE_PRIVATE)
        val emailUser = sharedPreferences.getString("email_user", "Guest") // Default: "Guest"
        binding.profileName.text = emailUser

        // Memuat emailUser menggunakan LiveData
        profileViewModel.loadUserProfile()

        // Mengamati perubahan pada LiveData emailUser
        profileViewModel.email.observe(viewLifecycleOwner, Observer { email ->
            binding.profileName.text = email
        })

        binding.editPictureUser.setOnClickListener { startGallery() }
        setupEditProfileButtons()

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

    private fun setupEditProfileButtons() {
        binding.editProfileButton.setOnClickListener {
            binding.editPictureUser.visibility = View.VISIBLE
            binding.saveProfileButton.visibility = View.VISIBLE
            binding.editProfileButton.visibility = View.GONE
            setEditTextFocusable(true)
        }
    }

    private fun setEditTextFocusable(isFocusable: Boolean) {
        binding.fullName.isFocusableInTouchMode = isFocusable
        binding.phoneNumber.isFocusableInTouchMode = isFocusable
        binding.email.isFocusableInTouchMode = isFocusable
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}