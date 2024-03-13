package com.example.project_samespace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_samespace.data.model.Song
import com.example.project_samespace.ui.screens.HomeScreens
import com.example.project_samespace.ui.screens.SongScreen
import com.example.project_samespace.ui.screens.components.HomeScreenBottomNavigation
import com.example.project_samespace.ui.screens.components.SongItem
import com.example.project_samespace.ui.theme.ProjectsamespaceTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectsamespaceTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomeScreens(navController) }
                    composable("songScreen/{songId}") { backStackEntry ->
                        SongScreen(
                            songId = backStackEntry.arguments?.getString("songId"),
                            navController = navController
                        )
                    }

                }
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjectsamespaceTheme {

    }
}