package com.mahesh_prajapati.itunes.storage.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Name(
    val first: String?,
    val last: String?,
    val title: String?
):Parcelable