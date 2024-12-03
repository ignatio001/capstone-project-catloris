package com.bangkit.catloris.helper

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val title: String,
    val image: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun describeContents(): Int = 0

    companion object : Parceler<Workout> {
        override fun Workout.write(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeInt(image)
        }

        override fun create(parcel: Parcel): Workout = Workout(parcel)
    }
}
