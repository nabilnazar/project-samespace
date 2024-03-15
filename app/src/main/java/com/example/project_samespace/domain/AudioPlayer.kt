package com.example.project_samespace.domain

import android.media.MediaPlayer
import android.os.Handler
import android.util.Log

class AudioPlayer {

    var playbackPositionListener: PlaybackPositionListener? = null


    private var mediaPlayer: MediaPlayer? = null
    private var length = 0
    private val handler = Handler()
    private var progressLogger: Runnable? = null

    private fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun startPlaying(audioUrl: String, onDurationReady: (Long) -> Unit) {
        stopLoggingProgress() // Ensure any previous logging is stopped
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                start()
                startLoggingProgress() // Start logging the progress once playback starts
                val duration = duration.toLong() // Get the duration
                onDurationReady(duration) // Use the callback to pass the duration
            }
            setOnErrorListener { _, what, extra ->
                Log.e("AudioPlayer", "Error occurred: What $what, Extra $extra")
                true
            }
        }
    }


    fun stopPlaying() {
        stopLoggingProgress() // Stop logging when playback is stopped
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

    fun resume() {
        mediaPlayer?.seekTo(length)
        mediaPlayer?.start()
        startLoggingProgress() // Resume logging when playback is resumed
    }
    fun seekTo(position: Long){
        mediaPlayer?.seekTo(position.toInt())
    }


    fun pause() {
        if (isPlaying()) {
            mediaPlayer?.pause()
            length = mediaPlayer?.currentPosition!!
            stopLoggingProgress() // Pause logging when playback is paused
        }
    }

    private fun startLoggingProgress() {
        progressLogger = Runnable {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    //Log.e("AudioPlayer", "Current playback position: ${it.currentPosition}")
                    playbackPositionListener?.onPositionUpdate(it.currentPosition.toLong())
                    handler.postDelayed(progressLogger!!, 1000) // Log every second
                }
            }
        }
        handler.post(progressLogger!!)
    }

    private fun stopLoggingProgress() {
        progressLogger?.let {
            handler.removeCallbacks(it)
        }
    }

    interface PlaybackPositionListener {
        fun onPositionUpdate(position: Long)
    }

}

