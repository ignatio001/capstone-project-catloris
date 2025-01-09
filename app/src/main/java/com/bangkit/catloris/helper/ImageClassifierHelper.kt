package com.bangkit.catloris.helper

import android.graphics.Bitmap
import android.media.Image
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.bangkit.catloris.R
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.lang.Error

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?,
    private val modelName: String = "food_image.tflite",
    private val threshold: Float = 0.1f,
    private val maxResults: Int = 3
) {

    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        // TODO: Menyiapkan Image Classifier untuk memproses gambar.
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context, modelName, optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, "Error initializing ImageClassifier: ${e.message}")
        }

    }

    fun classifyStaticImage(imageUri: Uri) {
        // TODO: mengklasifikasikan imageUri dari gambar statis.
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to convert URI to Bitmap", Toast.LENGTH_SHORT).show()
            null
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(result: Result<FloatArray>)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}