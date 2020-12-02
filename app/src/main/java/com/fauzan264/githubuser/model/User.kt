package com.fauzan264.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var photo: String? = null,
    var name: String? = null,
    var location: String? = null,
    var username: String? = null,
    var company: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var repo: String? = null
): Parcelable