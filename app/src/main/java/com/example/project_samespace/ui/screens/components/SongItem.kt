package com.example.project_samespace.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_samespace.data.model.Song

@Composable
fun SongItem(
    song: Song,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Row(modifier.padding(16.dp).clickable { onClick.invoke()}){
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current).data("https://cms.samespace.com/assets/${song.cover}").build(),
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

