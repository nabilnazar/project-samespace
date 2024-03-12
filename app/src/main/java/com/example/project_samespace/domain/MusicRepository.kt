package com.example.project_samespace.domain

import com.example.project_samespace.data.model.Songs
interface MusicRepository {

  suspend fun getSongs(): Songs
}