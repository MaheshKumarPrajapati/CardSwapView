package com.mahesh_prajapati.matchingapp.storage.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Location(
    val city: String?,
    val coordinates: Coordinates,
    val country: String?,
    val postcode: String?,
    val state: String?,
    val street: Street,
    val timezone: Timezone
): Parcelable