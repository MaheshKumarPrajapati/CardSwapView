package com.mahesh_prajapati.matchingapp.data.repository

import com.mahesh_prajapati.matchingapp.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getItunesSongs() = apiHelper.getItunesSongs()
}