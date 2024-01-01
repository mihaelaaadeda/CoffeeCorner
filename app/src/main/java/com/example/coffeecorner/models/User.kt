package com.example.coffeecorner.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//data model class for User with required fields.
@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profileCompleted: Int = 0,
    var isAdmin: Boolean = false) : Parcelable
