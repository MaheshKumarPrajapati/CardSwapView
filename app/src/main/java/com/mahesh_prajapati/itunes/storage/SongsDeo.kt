package com.mahesh_prajapati.itunes.storage

import androidx.annotation.Nullable
import androidx.room.*
import com.mahesh_prajapati.itunes.storage.model.Id
import com.mahesh_prajapati.itunes.storage.model.Result
import com.mahesh_prajapati.itunes.storage.model.ResultX
import org.jetbrains.annotations.NotNull

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