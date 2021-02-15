package com.mahesh_prajapati.itunes.storage


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mahesh_prajapati.itunes.storage.model.*


@Database(entities = [ResultX::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongsDeo?
}