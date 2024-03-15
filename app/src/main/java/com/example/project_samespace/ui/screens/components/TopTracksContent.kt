package com.example.project_samespace.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.project_samespace.ui.screens.MusicViewModel

@Composable
fun TopTracksContent(navController: NavController,viewModel:MusicViewModel= hiltViewModel()){
    val songs = viewModel.songs.value.filter { it.top_track }

    LazyColumn(modifier = Modifier.background(Color.Black).fillMaxHeight()) {
        items(songs) { song ->
            SongItem(
                onClick = { navController.navigate("songScreen/${song.id}") },
                song = song
            )
        }
    }
}