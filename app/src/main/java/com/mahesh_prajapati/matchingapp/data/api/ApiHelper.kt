package com.mahesh_prajapati.matchingapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getItunesSongs() = apiService.getItunesSongs()
}