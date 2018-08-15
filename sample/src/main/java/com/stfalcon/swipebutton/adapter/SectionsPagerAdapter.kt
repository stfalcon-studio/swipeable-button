package com.stfalcon.swipebutton.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.stfalcon.swipebutton.samples.AnimateFragment
import com.stfalcon.swipebutton.samples.AttributesFragment
import com.stfalcon.swipebutton.samples.DefaultViewFragment
import com.stfalcon.swipebutton.samples.ProgrammaticallyFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DefaultViewFragment.newInstance()
            1 -> return AttributesFragment.newInstance()
            2 -> return ProgrammaticallyFragment.newInstance()
            3 -> return AnimateFragment.newInstance()
        }
        return DefaultViewFragment.newInstance()
    }

    override fun getCount() = 4
}