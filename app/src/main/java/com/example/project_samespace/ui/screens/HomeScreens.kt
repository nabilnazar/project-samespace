package com.example.project_samespace.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.project_samespace.ui.screens.components.ForYouContent
import com.example.project_samespace.ui.screens.components.TopTracksContent


@Composable
fun HomeScreens(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(bottomBar = {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {


            NavigationBar(containerColor = Color.Transparent){
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("For You") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                    label = { Text("Top Tracks") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
        }
    }) { paddingValues ->
        // Switch between tabs
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> ForYouContent(navController)
                1 -> TopTracksContent(navController)
            }
        }
    }
}
