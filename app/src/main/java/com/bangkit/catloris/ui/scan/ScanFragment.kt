package com.bangkit.catloris.ui.scan

import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bangkit.catloris.R
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.FragmentScanBinding
import com.bangkit.catloris.responses.ErrorResponse
import com.bangkit.catloris.utils.getImageUri
import com.bangkit.catloris.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    companion object {
        fun newInstance() = ScanFragment()
    }

    private val viewModel: ScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)

        //INI untuk data sinkronise ke API
        val sharedPreferences = requireActivity().getSharedPreferences("email_user", MODE_PRIVATE)
        val emailUser = sharedPreferences.getString("email_user", null)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }

        binding.clearPictButton.setOnClickListener { clearPicture() }

        return binding.root
    }

    private fun analyzeImage() {
        val makanan = listOf(
            mapOf("nama" to "ayam goreng krispi", "protein" to 20, "lemak" to 15, "karbohidrat" to 10, "kalori" to 255),
            mapOf("nama" to "bakso", "protein" to 12, "lemak" to 8, "karbohidrat" to 15, "kalori" to 180),
            mapOf("nama" to "burger", "protein" to 15, "lemak" to 20, "karbohidrat" to 30, "kalori" to 360),
            mapOf("nama" to "kentang goreng", "protein" to 2, "lemak" to 15, "karbohidrat" to 35, "kalori" to 280),
            mapOf("nama" to "nasi goreng", "protein" to 8, "lemak" to 10, "karbohidrat" to 45, "kalori" to 300),
            mapOf("nama" to "nasi padang", "protein" to 20, "lemak" to 15, "karbohidrat" to 60, "kalori" to 450),
            mapOf("nama" to "nasi putih", "protein" to 3, "lemak" to 0, "karbohidrat" to 40, "kalori" to 172),
            mapOf("nama" to "nugget", "protein" to 10, "lemak" to 12, "karbohidrat" to 15, "kalori" to 208),
            mapOf("nama" to "pizza", "protein" to 12, "lemak" to 14, "karbohidrat" to 35, "kalori" to 310),
            mapOf("nama" to "rawon daging sapi", "protein" to 18, "lemak" to 12, "karbohidrat" to 20, "kalori" to 260),
            mapOf("nama" to "rendang", "protein" to 25, "lemak" to 20, "karbohidrat" to 5, "kalori" to 330),
            mapOf("nama" to "sate", "protein" to 20, "lemak" to 15, "karbohidrat" to 10, "kalori" to 250),
            mapOf("nama" to "seblak", "protein" to 8, "lemak" to 10, "karbohidrat" to 30, "kalori" to 240),
            mapOf("nama" to "sop", "protein" to 10, "lemak" to 5, "karbohidrat" to 15, "kalori" to 140),
            mapOf("nama" to "tempe goreng", "protein" to 15, "lemak" to 10, "karbohidrat" to 8, "kalori" to 182)
        )

        val inputNama = binding.analyzeResult.text.toString().lowercase().trim()
        val makananDitemukan = makanan.find { it["nama"] == inputNama }

        if (makananDitemukan != null) {
            binding.scanResultCalories.text = makananDitemukan["kalori"].toString()
            binding.scanResultCarbo.text = makananDitemukan["karbohidrat"].toString()
            binding.scanResultProtein.text = makananDitemukan["protein"].toString()
            binding.scanResultFats.text = makananDitemukan["lemak"].toString()

            showToast("Data ditemukan untuk $inputNama")
        } else {
            showToast("Makanan tidak ditemukan")
            binding.scanResultCalories.text = "-"
            binding.scanResultCarbo.text = "-"
            binding.scanResultProtein.text = "-"
            binding.scanResultFats.text = "-"
        }
    }

//    private fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, requireContext())
//            Log.d("Image Classification File", "showImageL: ${imageFile.path}")
//
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "image",
//                imageFile.name,
//                requestImageFile
//            )
//
//            lifecycleScope.launch {
//                try {
//                    val apiService = ApiConfig.getApiService()
//                    val successResponse = apiService.uploadImage(multipartBody)
//                    with(successResponse.nutrisi){
//                        binding.analyzeResult.text = this!!.nama
//                        binding.scanResultCalories.text = this.kalori.toString()
//                        binding.scanResultCarbo.text = this.karbohidrat.toString()
//                        binding.scanResultProtein.text = this.protein.toString()
//                        binding.scanResultFats.text = this.lemak.toString()
//                    }
//                } catch (e: HttpException) {
//                    val errorBody = e.response()?.errorBody()?.string()
//                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
//                    showToast(errorResponse.detail.toString())
//                }
//            }
//        } ?: showToast(getString(R.string.empty_image_warning))
//    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun clearPicture() {
        binding.photoAnalyze.setImageDrawable(null)
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
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
            binding.photoAnalyze.setImageURI(it)
        }
    }





}