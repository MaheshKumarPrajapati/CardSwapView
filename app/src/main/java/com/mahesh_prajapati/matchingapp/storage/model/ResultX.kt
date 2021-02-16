package com.mahesh_prajapati.matchingapp.storage.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ResultX(
    @PrimaryKey(autoGenerate = true) val dbId:Int?,
    val viewId: Long = counter++,
    val cell: String?,
    val dob: Dob?,
    val email: String?,
    val gender: String?,
    val id: Id?,
    val location: Location?,
    val login: Login?,
    val name: Name?,
    val nat: String?,
    val phone: String?,
    val picture: Picture?,
    val registered: Registered?,
    var selectVal:Int=0  // 0 for nutral 1 for selected -1 for unselected

): Parcelable{
    companion object {
        private var counter = 0L
    }
}