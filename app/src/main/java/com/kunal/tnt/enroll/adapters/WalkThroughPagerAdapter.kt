package com.kunal.tnt.enroll.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kunal.tnt.enroll.ui.WTFirstFragment
import com.kunal.tnt.enroll.ui.WTSecondFragment


class WalkThroughPagerAdapter(
    private val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = WTFirstFragment()
            1 -> fragment = WTSecondFragment()
            //2 -> fragment = Fragment()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

}