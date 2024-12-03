package com.bangkit.catloris.helper

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val title: String,
    val imageResource: Int
) : Parcelable
