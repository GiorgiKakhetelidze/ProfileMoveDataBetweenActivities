package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val lastName: String,
    val mail: String,
    val bornYear: Int,
    val gender: String
) : Parcelable
