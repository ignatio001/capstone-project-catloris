package com.bangkit.catloris.ui.challenge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit.catloris.MainActivity
import com.bangkit.catloris.R
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.ActivityChallengeScanBinding
import com.bangkit.catloris.responses.ErrorResponse
import com.bangkit.catloris.utils.getImageUri
import com.bangkit.catloris.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ChallengeScan : AppCompatActivity() {

    private lateinit var binding: ActivityChallengeScanBinding
    private var currentImageUri: Uri? = null

    private var targetCalories: Float = 0f
    private var analyzedCalories: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityChallengeScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        targetCalories = intent.getFloatExtra("CH_ONE_VALUES", 0f)
        binding.chGalleryButton.setOnClickListener { startGallery() }
        binding.chCameraButton.setOnClickListener { startCamera() }
        binding.chAnalyzeButton.setOnClickListener { uploadImage() }
        binding.chClearPictButton.setOnClickListener { clearPicture() }
        binding.chResultSaveButton.setOnClickListener { saveResult() }
    }

    private fun saveResult() {
        if (analyzedCalories == 0f) {
            showToast(getString(R.string.empty_calories_warning))
            return
        }
        if (analyzedCalories in (targetCalories - 100)..(targetCalories + 100)) {
            showToast("Food according to the calorie target around $targetCalories")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            showToast("Food is not suitable! The calorie target is approx $targetCalories")
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image Classification File", "showImageL: ${imageFile.path}")

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.uploadImage(multipartBody)
                    with(successResponse.nutrisi) {
                        binding.chAnalyzeResult.text = this!!.nama
                        binding.chScanResultCalories.text = this.kalori.toString()
                        binding.chScanResultCarbo.text = this.karbohidrat.toString()
                        binding.chScanResultProtein.text = this.protein.toString()
                        binding.chScanResultFats.text = this.lemak.toString()
                        analyzedCalories = this.kalori?.toFloat() ?: 0f
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    showToast(errorResponse.detail.toString())
                }
            }
        }?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearPicture() {
        binding.chPhotoAnalyze.setImageDrawable(null)
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
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
            binding.chPhotoAnalyze.setImageURI(it)
        }
    }
}