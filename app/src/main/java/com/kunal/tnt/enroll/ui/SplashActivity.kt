package com.kunal.tnt.enroll.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Constant
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.home.ui.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var prefClient: SharedPrefClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (prefClient.isUserLoggedIn()) {
                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, Constant.SPLASH_SCREEN_TIMEOUT)
    }
}