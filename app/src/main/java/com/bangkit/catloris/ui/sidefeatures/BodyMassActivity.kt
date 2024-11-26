package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityBodyMassBinding

class BodyMassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBodyMassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyMassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etWeight = binding.weight
        val etHeight = binding.height
        val etAge = binding.age
        val btnCalculate = binding.button
        val tvResult = binding.bmiResult

        btnCalculate.setOnClickListener {
            // Input dari pengguna
            val weight = etWeight.text.toString().toDoubleOrNull()
            val height = etHeight.text.toString().toDoubleOrNull()
            val age = etAge.text.toString().toIntOrNull()

            if (weight == null || height == null || age == null) {
                Toast.makeText(this, "Mohon masukkan data dengan benar.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Menghitung BMI
            val bmi = weight / ((height / 100) * (height / 100))

            // Kategori BMI WHO
            val whoCategory = when {
                bmi < 18.5 -> "Kurus"
                bmi in 18.5..24.9 -> "Normal"
                bmi in 25.0..29.9 -> "Gendut"
                bmi in 30.0..39.9 -> "Obesitas Tipe 1"
                bmi in 40.0..49.9 -> "Obesitas Tipe 2"
                bmi >= 50.0 -> "Obesitas Tipe 3"
                else -> "Undefined"
            }

            // Kategori BMI Asia
            val asiaCategory = when {
                bmi < 18.5 -> "Kurus"
                bmi in 18.5..22.9 -> "Normal"
                bmi in 23.0..24.9 -> "Gendut"
                bmi in 25.0..29.9 -> "Pra Obesitas"
                bmi >= 30.0 -> "Obesitas"
                else -> "Undefined"
            }

            // Menampilkan hasil melalui Toast
            val toastMessage = """
                Usia: $age
                Berat Badan: $weight kg
                Tinggi Badan: $height cm
                BMI: %.2f
                Kategori WHO: $whoCategory
                Kategori Asia: $asiaCategory
            """.trimIndent().format(bmi)

            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

            tvResult.text = """
                Usia: $age
                Berat Badan: $weight kg
                Tinggi Badan: $height cm
                BMI: %.2f
                Kategori WHO: $whoCategory
                Kategori Asia: $asiaCategory
            """.trimIndent().format(bmi)
        }
    }
}