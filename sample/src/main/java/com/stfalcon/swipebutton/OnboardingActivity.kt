package com.stfalcon.swipebutton

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.stfalcon.swipebutton.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        containerPager.adapter = mSectionsPagerAdapter
        containerPager.addOnPageChangeListener(onPageChangeListener)
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            indicatorIv0.setBackgroundResource(R.drawable.shape_page_indicator_unchecked)
            indicatorIv1.setBackgroundResource(R.drawable.shape_page_indicator_unchecked)
            indicatorIv2.setBackgroundResource(R.drawable.shape_page_indicator_unchecked)
            indicatorIv3.setBackgroundResource(R.drawable.shape_page_indicator_unchecked)
            when (position) {
                0 -> {
                    indicatorIv0.setBackgroundResource(R.drawable.shape_page_indicator_checked)
                    supportActionBar?.title = getString(R.string.default_fragment_title)
                }
                1 -> {
                    indicatorIv1.setBackgroundResource(R.drawable.shape_page_indicator_checked)
                    supportActionBar?.title = getString(R.string.attributes_fragment_title)
                }
                2 -> {
                    indicatorIv2.setBackgroundResource(R.drawable.shape_page_indicator_checked)
                    supportActionBar?.title = getString(R.string.programmatically_fragment_title)
                }
                3 -> {
                    indicatorIv3.setBackgroundResource(R.drawable.shape_page_indicator_checked)
                    supportActionBar?.title = getString(R.string.animate_fragment_title)
                }
            }
        }
    }
}
