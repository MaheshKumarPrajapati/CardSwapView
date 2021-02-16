package com.mahesh_prajapati.matchingapp.storage


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahesh_prajapati.matchingapp.storage.model.*


@Database(entities = [ResultX::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDeo?
}