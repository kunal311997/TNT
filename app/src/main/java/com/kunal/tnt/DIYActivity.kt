package com.kunal.tnt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_d_i_y.*

class DIYActivity : YouTubeBaseActivity() {

    val apiKey = "AIzaSyAixc6rvF41pS5cU_qA8t0TUCt9KkD1WCc"
    val videoode = "KmKEh_m7XNg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_i_y)


        yPlayer.initialize(apiKey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if (!p2)
                    p1?.loadVideo(videoode)
                p1?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

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