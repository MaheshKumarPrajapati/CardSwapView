package com.mahesh_prajapati.matchingapp.storage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Street(
    val name: String?,
    val number: Int?
):Parcelable