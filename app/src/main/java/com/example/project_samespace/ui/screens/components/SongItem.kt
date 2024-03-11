package com.example.project_samespace.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.OnPlacedModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.project_samespace.data.model.Song

@Composable
fun SongItem(
    onClick : ()-> Unit,
    song: Song,
    modifier: Modifier = Modifier
){

    Row(modifier.padding(16.dp)){
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current).data(song.cover).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop

        )
            Column(Modifier.padding(16.dp,8.dp,0.dp,0.dp).weight(1f)
            ){
                Text(text = song.name,style = MaterialTheme.typography.bodyLarge)
                Text(text = song.artist,style = MaterialTheme.typography.bodyMedium)

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSongItem() {
    // Mock Song object
    val mockSong = Song(
        name = "Song Title",
        artist = "Artist Name",
        accent = "",
        id = 1,
        top_track = true,
        sort = "",
        cover = "https://images.unsplash.com/photo-1565372195457-c04c11b1ad1a"
    )

    // Wrapper Box to align the content, adjust as needed

    Box {
        SongItem(
            onClick = {},
            song = mockSong
        )
    }

}