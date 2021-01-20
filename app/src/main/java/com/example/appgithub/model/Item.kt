package com.example.appgithub.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: Owner,
    val stargazers_count: Int,
    val forks: Int
): Parcelable