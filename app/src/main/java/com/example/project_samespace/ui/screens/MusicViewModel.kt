package com.example.project_samespace.ui.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_samespace.MainActivity
import com.example.project_samespace.data.model.Song
import com.example.project_samespace.domain.AudioPlayer
import com.example.project_samespace.domain.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Url
import javax.inject.Inject


@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: MusicRepository
): ViewModel(), AudioPlayer.PlaybackPositionListener {

    private val audioPlayer = AudioPlayer().also {
        it.playbackPositionListener = this
    }

    var isPlaying by mutableStateOf(false)

    private var _totalDuration = mutableStateOf<Long>(0L)
    val totalDuration: State<Long> = _totalDuration

    private var _currentPlaybackPosition  = mutableStateOf<Long>(0L)
    val currentPlaybackPosition: State<Long> = _currentPlaybackPosition

    private var _currentSliderPosition = mutableStateOf(0f)


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

    fun playSong(audioUrl: String) {
        if (!isPlaying) {
            audioPlayer.startPlaying(audioUrl) { duration ->
                _totalDuration.value = duration
                isPlaying = true
            }

        }
    }

    fun pause() {
        if (isPlaying) {
            audioPlayer.pause()
            isPlaying = false
        }
    }

    fun resume(){
        audioPlayer.resume()
    }


    fun seekForward(seconds: Int){
        audioPlayer.seekForward(seconds)
    }

    fun seekBackward(seconds: Int){
        audioPlayer.seekBackward(seconds)
    }

    fun seekTo(newTime: Long) {
        audioPlayer.seekTo(newTime)
        _currentPlaybackPosition.value = newTime
    }

    override fun onPositionUpdate(position: Long) {
        // This will be called with the playback position updates
        _currentPlaybackPosition.value = position
        Log.e("AudioPlayer", "Current playback position: ${position}")

        updateSliderPosition()
    }


    fun updateSliderPosition() {
        // Calculate the current slider position based on the current playback position and the total duration
        val currentPosition = audioPlayer.getCurrentPosition()
        val totalDuration = this.totalDuration.value

        // Avoid division by zero
        if (totalDuration > 0) {
            val sliderPosition = currentPosition.toFloat() / totalDuration.toFloat()
            // Update the state that holds the slider position
            _currentSliderPosition.value = sliderPosition
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stopPlaying()

    }



}