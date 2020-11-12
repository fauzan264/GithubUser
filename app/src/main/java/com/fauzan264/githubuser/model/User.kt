package com.fauzan264.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var photo: Int,
    var name: String?,
    var location: String?,
    var company: String?,
    var username: String?,
    var followers: String?,
    var following: String?,
    var repo: String?
): Parcelable