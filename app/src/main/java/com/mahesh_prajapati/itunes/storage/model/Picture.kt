package com.mahesh_prajapati.itunes.storage.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
):Parcelable