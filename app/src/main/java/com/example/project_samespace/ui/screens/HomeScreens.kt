package com.example.project_samespace.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.project_samespace.data.model.Song
import com.example.project_samespace.ui.screens.components.HomeScreenBottomNavigation
import com.example.project_samespace.ui.screens.components.SongItem
import com.example.project_samespace.ui.theme.ProjectsamespaceTheme


@Composable
fun HomeScreens() {
     ProjectsamespaceTheme{
        Scaffold(
            bottomBar = { HomeScreenBottomNavigation() }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(10) {
                    SongItem(
                        onClick = { /*TODO*/ },
                        song = Song(
                            name = "Song Title",
                            artist = "Artist Name",
                            accent = "",
                            id = 1,
                            top_track = true,
                            sort = "",
                            cover = "https://cms.samespace.com/assets/4f718272-6b0e-42ee-92d0-805b783cb471"
                        )
                    )
                }
            }
        }
    }
}
