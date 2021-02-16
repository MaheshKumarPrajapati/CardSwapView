package com.mahesh_prajapati.matchingapp.storage.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Login(
    val md5: String?,
    val password: String?,
    val salt: String?,
    val sha1: String?,
    val sha256: String?,
    val username: String?,
    val uuid: String?
):Parcelable