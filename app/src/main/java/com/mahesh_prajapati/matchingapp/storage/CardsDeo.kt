package com.mahesh_prajapati.matchingapp.storage

import androidx.annotation.Nullable
import androidx.room.*
import com.mahesh_prajapati.matchingapp.storage.model.ResultX

@Dao
interface CardsDeo {
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
    suspend fun updateCards(
      selectVal: Int,
      dbId: Int
    )

}