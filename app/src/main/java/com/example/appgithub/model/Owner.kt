package com.example.appgithub.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class Owner(
    val avatar_url: String,
    val login: String,
    val followers_url: String,
    val following_url: String
): Parcelable