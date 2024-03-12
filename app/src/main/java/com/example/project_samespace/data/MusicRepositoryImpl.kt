package com.example.project_samespace.data

import com.example.project_samespace.data.model.Song
import com.example.project_samespace.data.model.Songs
import com.example.project_samespace.domain.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val apiService: ApiService
):MusicRepository{
    override suspend fun getSongs():Songs{
        return apiService.getSongs()
    }
}