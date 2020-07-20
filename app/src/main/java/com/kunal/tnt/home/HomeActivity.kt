package com.kunal.tnt.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kunal.tnt.R
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var headingList: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // headingList = listOf("Home","flip","flip","flip","flip")
        //setBottomNavigation()

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        loadFragment(HomeFragment())
    }


    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

/*    private fun setBottomNavigation() {
        fluidBottomNavigation.items = listOf(
            FluidBottomNavigationItem("Home",
                ContextCompat.getDrawable(this, R.drawable.rental)),
            FluidBottomNavigationItem("flip",
                ContextCompat.getDrawable(this, R.drawable.flip)),
            FluidBottomNavigationItem("flip",
                ContextCompat.getDrawable(this, R.drawable.plus)),
            FluidBottomNavigationItem("flip",
                ContextCompat.getDrawable(this, R.drawable.search)),
            FluidBottomNavigationItem("flip",
                ContextCompat.getDrawable(this, R.drawable.more))
        )

        viewpager.adapter = HomePagerAdapter(supportFragmentManager)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                fluidBottomNavigation.selectTab(position)
                //txtHeading.text = headingList[position]
            }
        })

        fluidBottomNavigation.onTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                //txtHeading.text = headingList[position]
                *//*if (position == 2) {
                    groupFloating.visible()
                    translucentBack.visible()
                } else {
                    viewpager.currentItem = position
                    groupFloating.gone()
                    translucentBack.gone()
                }*//*
            }
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_home -> fragment = HomeFragment()
            R.id.navigation_dashboard -> fragment = HomeFragment()
            R.id.navigation_notifications -> fragment = HomeFragment()
            R.id.navigation_profile -> fragment = SettingsFragment()
        }
        return loadFragment(fragment)
    }
}