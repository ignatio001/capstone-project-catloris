package com.bangkit.catloris.responses

import com.google.gson.annotations.SerializedName

data class PredictResponse(

    @field:SerializedName("prediksi makanan")
    val prediksiMakanan: String? = null,

    @field:SerializedName("nutrisi")
    val nutrisi: Nutrisi? = null,

    @field:SerializedName("confidence")
    val confidence: Any? = null
)

data class Nutrisi(

    @field:SerializedName("kalori")
    val kalori: Int? = null,

    @field:SerializedName("karbohidrat")
    val karbohidrat: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("protein")
    val protein: Int? = null,

    @field:SerializedName("lemak")
    val lemak: Int? = null
)
