package com.kunal.tnt.videos.ui

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.kunal.tnt.R
import kotlinx.android.synthetic.main.activity_video_view.*


class VideoViewActivity : YouTubeBaseActivity() {

    val apiKey = "AIzaSyAixc6rvF41pS5cU_qA8t0TUCt9KkD1WCc"
    var videoCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        videoCode = intent.getStringExtra("videoCode") ?: ""
        initYoutube()

    }

    private fun initYoutube() {
        yPlayer.initialize(apiKey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if (!p2) {
                    p1?.setFullscreen(true)
                    p1?.loadVideo(videoCode)
                    p1?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.e("fail", p1.toString())
            }

        })
    }

}