package com.kunal.tnt.enroll.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kunal.tnt.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txtLogin.setOnClickListener(this)
        txtNoAccount.setOnClickListener(this)
        btSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtLogin, txtNoAccount -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            btSubmit -> {
                val intent = Intent(this, SelectInterestsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}