package com.example.project_samespace.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_samespace.data.model.Song
import com.example.project_samespace.domain.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: MusicRepository
): ViewModel() {



    private val _songs = mutableStateOf<List<Song>>(emptyList())
    val songs: State<List<Song>> = _songs
    init{
        fetchSongs()
    }

    private fun fetchSongs() {
       viewModelScope.launch {
           _songs.value = repository.getSongs().data
       }
    }


}