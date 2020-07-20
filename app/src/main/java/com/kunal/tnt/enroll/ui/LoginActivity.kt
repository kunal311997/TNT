package com.kunal.tnt.enroll.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kunal.tnt.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txtNoAccount

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtRegister.setOnClickListener(this)
        txtNoAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtRegister, txtNoAccount -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}