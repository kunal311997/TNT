package com.kunal.tnt.enroll.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunal.tnt.R
import com.kunal.tnt.enroll.adapters.SelectInterestsAdapter
import com.kunal.tnt.home.HomeActivity
import kotlinx.android.synthetic.main.activity_select_interests.*

class SelectInterestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interests)

        rvInterests.adapter = SelectInterestsAdapter(
            this,
            arrayListOf("Tech", "Food", "Science", "Fitness", "Party")
        )

        btnContinue.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}