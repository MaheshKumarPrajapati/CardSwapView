package com.mahesh_prajapati.itunes.storage

import androidx.annotation.Nullable
import androidx.room.*
import com.mahesh_prajapati.itunes.storage.model.ResultX

@Dao
interface SongsDeo {
    @Nullable
    @Query("SELECT * FROM ResultX")
    suspend fun getAll(): List<ResultX>

    @Insert
    suspend fun insert(song: ResultX)

    @Delete
    suspend fun delete(song: ResultX)

    @Query(
        "UPDATE ResultX SET selectVal=:selectVal WHERE dbId= :dbId"
    )
    suspend fun updateCandidate(
      selectVal: Int,
      dbId: Int
    )

}