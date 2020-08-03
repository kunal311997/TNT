package com.kunal.tnt.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kunal.tnt.R
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.ui.CreateFeedActivity
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.home.utils.HomeConstants
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : DaggerAppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var headingList: List<String>

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // headingList = listOf("Home","flip","flip","flip","flip")
        //setBottomNavigation()

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        loadFragment(HomeFragment())

        imgCreateFeed.setOnClickListener {
            startActivityForResult(
                Intent(this, CreateFeedActivity::class.java),
                HomeConstants.CREATE_FEED_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        HomeFragment()?.onActivityResult(requestCode, resultCode, data)

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