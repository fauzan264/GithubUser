package com.fauzan264.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var photo: String? = null,
    var username: String? = null
): Parcelable