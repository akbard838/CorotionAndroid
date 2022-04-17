package com.example.corotion.data.model

import android.os.Parcelable
import com.example.corotion.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Information(
    val image: Int = 0,
    val description: String,
    val title: String = emptyString()
): Parcelable