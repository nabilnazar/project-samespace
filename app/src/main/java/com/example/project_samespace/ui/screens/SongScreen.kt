package com.example.project_samespace.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.project_samespace.R
import com.example.project_samespace.data.model.Song
import kotlin.math.max
import kotlin.math.min


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongScreen(
    viewModel: MusicViewModel = hiltViewModel(),
    songId: Int
) {


    lateinit var songPlaying: Song
    val songs = viewModel.songs.value



    val pagerState =
        rememberPagerState(pageCount = { viewModel.songs.value.count() }, initialPage = songId-1)
    // This will be called whenever a swipe is detected

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
            pageSpacing = 2.dp,
            contentPadding = PaddingValues(28.dp)
        ) { page ->
            val song = songs[page]
            songPlaying = song

            Column() {
                Image(
                    painter = rememberAsyncImagePainter("https://cms.samespace.com/assets/${song.cover}"),
                    contentDescription = "Cover image for ${song.id}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .clip(RoundedCornerShape(8))
                    //.border(5.dp,Color.Red, RoundedCornerShape(10))
                    ,
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = song.name,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = song.artist,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                }
            }
        }

        if (viewModel.totalDuration.value > 0) {
            val sliderPosition by remember {
                derivedStateOf {
                    val position = viewModel.currentPlaybackPosition.value.toFloat()
                    val total = viewModel.totalDuration.value.toFloat()
                    if (total > 0f) {
                        position / total
                    } else {
                        0f
                    }
                }
            }

            Slider(
                value = sliderPosition,
                onValueChange = { newPosition ->
                    val newTime = (newPosition * viewModel.totalDuration.value).toLong()
                    viewModel.seekTo(newTime)
                },

                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..1f,
            )
            Log.e("songScreen sliderPosition:", sliderPosition.toString())
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val currentText by remember {
                derivedStateOf { viewModel.currentPlaybackPosition.value.convertToText() }
            }
            val remainTime by remember {
                derivedStateOf {
                    val remain =
                        viewModel.totalDuration.value - viewModel.currentPlaybackPosition.value
                    if (remain >= 0) remain.convertToText() else "00:00"
                }
            }

            Text(
                text = currentText,
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Text(
                text = remainTime,
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.backward_btn),
                contentDescription = "Replay 10 seconds",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = { viewModel.seekBackward(10) })
                    .padding(12.dp)
                    .size(32.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            ControlButton(
                icon = if (viewModel.isPlaying) R.drawable.pause_btn else R.drawable.play_btn,
                size = 64.dp,
                onClick = {
                    if (viewModel.isPlaying) {
                        viewModel.pause()
                    } else {
                        if (songPlaying.id!=9)
                         viewModel.playSong(removeWhitespace(songPlaying.url))
                    }
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painterResource(R.drawable.forward_btn),
                contentDescription = "Replay 10 seconds",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = { viewModel.seekForward(10) })
                    .padding(12.dp)
                    .size(32.dp)
            )
        }

    }
}

private fun Long.convertToText(): String {
    val sec = this / 1000
    val minutes = sec / 60
    val seconds = sec % 60

    val minutesString = if (minutes < 10) {
        "0$minutes"
    } else {
        minutes.toString()
    }
    val secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        seconds.toString()
    }
    return "$minutesString:$secondsString"
}

@Composable
fun ControlButton(icon: Int, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size / 1.5f),
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}


fun removeWhitespace(string: String): String {
    return string.replace("\\s".toRegex(), "")
}