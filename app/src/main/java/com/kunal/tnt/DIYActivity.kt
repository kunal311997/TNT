package com.kunal.tnt

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_d_i_y.*
import org.json.JSONObject
import java.net.URL


class DIYActivity : YouTubeBaseActivity() {

    val apiKey = "AIzaSyAixc6rvF41pS5cU_qA8t0TUCt9KkD1WCc"
    val videoode = "KmKEh_m7XNg"

    //https://www.googleapis.com/youtube/v3/videos?id=7lCDEYXw3mM&key=AIzaSyAixc6rvF41pS5cU_qA8t0TUCt9KkD1WCc&part=snippet,contentDetails,statistics,status
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_i_y)
        Log.e("werewr", getTitleQuietly(videoode).toString())
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

    fun getTitleQuietly(youtubeUrl: String?): String? {
        try {
            if (youtubeUrl != null) {
                val embededURL = URL(
                    "http://www.youtube.com/oembed?url=" +
                            youtubeUrl + "&format=json"
                )
                return JSONObject(embededURL.toString()).getString("title")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}