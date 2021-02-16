package com.mahesh_prajapati.matchingapp.storage.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Dob(
    val age: Int?,
    val date: String?
): Parcelable