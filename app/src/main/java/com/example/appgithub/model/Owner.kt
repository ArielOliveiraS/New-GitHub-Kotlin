package com.example.appgithub.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class Owner(
    val avatar_url: String,
    val login: String
): Parcelable