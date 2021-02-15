package com.mahesh_prajapati.itunes.data.api

import com.mahesh_prajapati.itunes.storage.model.CandidateResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=30")
    suspend fun getItunesSongs(): CandidateResponse
}