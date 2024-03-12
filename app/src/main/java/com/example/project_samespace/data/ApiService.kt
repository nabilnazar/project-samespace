package com.example.project_samespace.data

import com.example.project_samespace.data.model.Songs
import retrofit2.http.GET

interface ApiService {

    @GET("items/songs")
    suspend fun getSongs(): Songs

}