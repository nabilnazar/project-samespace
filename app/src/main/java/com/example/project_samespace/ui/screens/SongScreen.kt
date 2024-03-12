package com.example.project_samespace.ui.screens

import android.content.ContentValues
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongScreen(
    viewModel: MusicViewModel = hiltViewModel()
) {
    val songs = viewModel.songs.value
    val pagerState = rememberPagerState(pageCount = {viewModel.songs.value.count()})

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
            pageSpacing = 2.dp,
            contentPadding = PaddingValues(28.dp)
        ) { page ->

            val song = songs[page]
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
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = song.name,
                        fontSize = 32.sp ,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = song.artist,
                        modifier = Modifier.padding(top = 8.dp)
                    )


                }
            }
        }

        val currentTime:Long = 2
        val totalTime: Long = 4
        val sliderColors = if (isSystemInDarkTheme()) {
            SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onBackground,
                activeTrackColor = MaterialTheme.colorScheme.onBackground,
                inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.24f
                ),
            )
        } else SliderDefaults.colors(
            thumbColor = Color.Red,
            activeTrackColor = Color.Yellow ,
            inactiveTrackColor = Color.Yellow.copy(
                alpha = 0.24f
            ),
        )
        var sliderPosition by remember{ mutableStateOf(0f) }
        Slider(
            value = currentTime.toFloat(),
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..totalTime.toFloat(),
            colors = sliderColors,
            onValueChange = {sliderPosition = it},
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = currentTime.convertToText(),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            val remainTime = totalTime - currentTime
            Text(
                text = if (remainTime >= 0) remainTime.convertToText() else "",
                modifier = Modifier
                    .padding(8.dp),
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "Replay 10 seconds",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = {})
                    .padding(12.dp)
                    .size(32.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))
            val isPlaying = listOf(true, false)
            ControlButton(

                icon = if (isPlaying.shuffled().first()) R.drawable.ic_round_pause else R.drawable.ic_round_play_arrow,
                size = 100.dp,
                onClick = {
                    if (isPlaying.shuffled().first()) {

                    } else {

                    }

                })
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "Replay 10 seconds",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = {})
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
            tint = Color.White,
            contentDescription = null
        )
    }
}