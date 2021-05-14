package com.mahesh_prajapati.matchingapp.data.api

import com.mahesh_prajapati.matchingapp.storage.model.CandidateResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=30")
    suspend fun getCandidates(): CandidateResponse
}