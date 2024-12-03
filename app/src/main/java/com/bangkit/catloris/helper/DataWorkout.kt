package com.bangkit.catloris.helper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataWorkout(
    val title: String,
    val image: Int
) : Parcelable
