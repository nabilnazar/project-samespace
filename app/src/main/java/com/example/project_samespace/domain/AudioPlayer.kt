package com.example.project_samespace.domain

import android.media.MediaPlayer
import android.util.Log
import java.io.IOException

class AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private fun isPlaying() : Boolean = mediaPlayer?.isPlaying?:false
    fun startPlaying(audioUrl: String, onDurationReady: (Long) -> Unit) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                start()
                val duration = duration.toLong() // Get the duration
                onDurationReady(duration) // Use the callback to pass the duration
            }
            setOnErrorListener { mp, what, extra ->
                Log.e("AudioPlayer", "Error occurred: What $what, Extra $extra")
                true
            }
        }
    }

    fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun seekForward(seconds: Int){
        mediaPlayer?.let{
            val currentPosition = it.currentPosition
            val seekToPosition = currentPosition+seconds*1000
            it.seekTo(seekToPosition)
        }
    }

    fun seekBackward(seconds: Int){
        mediaPlayer?.let{
            val currentPosition = it.currentPosition
            val seekToPosition = maxOf(0,currentPosition-seconds*1000)
            it.seekTo(seekToPosition)
        }
    }

    fun getCurrentPosition(): Long = mediaPlayer?.currentPosition?.toLong()?:0L

    fun resume(){
        if (!isPlaying())
            mediaPlayer?.start()
    }
    fun seekTo(position: Long){
        mediaPlayer?.seekTo(position.toInt())
    }

    fun pause() {
        if (isPlaying()) {
            mediaPlayer?.pause()
        }
    }

}