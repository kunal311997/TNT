package com.kunal.tnt.enroll.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.enroll.adapters.WalkThroughPagerAdapter
import com.kunal.tnt.home.ui.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_walk_through.*
import javax.inject.Inject


class WalkThroughActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var prefClient: SharedPrefClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_walk_through)

        viewpager.adapter = WalkThroughPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (viewpager.currentItem == 1) {
                    txtNext.text = resources.getString(R.string.done)
                } else txtNext.text = resources.getString(R.string.next)
            }
        })

        txtNext.setOnClickListener {
            if (viewpager.currentItem < 1) {
                txtNext.text = resources.getString(R.string.done)
                viewpager.currentItem = viewpager.currentItem + 1
            } else {
                prefClient.updateWalkThroughDone(true)

                val i = Intent(this@WalkThroughActivity, HomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            }
        }
    }
}
