package com.stfalcon.swipebutton

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CustomSwipeButton"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customSwipeButton.setEnable(true)
        customSwipeButton2.setEnable(true)
        customSwipeButton3.setEnable(true)

        initComponent()
        initListeners()
    }

    private fun initListeners() {
        customSwipeButton.setOnSwipedListener {
            Log.d(TAG, "onSwiped")
        }
        customSwipeButton.setOnSwipedOnListener {
            Log.d(TAG, "onSwipedOn")
        }
        customSwipeButton.setOnSwipedOffListener {
            Log.d(TAG, "onSwipedOff")
        }
    }

    private fun initComponent() {
        customSwipeButton4.setActive(true)
        customSwipeButton4.setEnable(true)
        customSwipeButton4.setActiveText("Active text")
        customSwipeButton4.setInActiveText("InActive text")
        customSwipeButton4.setActiveTextColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_blue_bright
            )
        )
        customSwipeButton4.setInActiveTextColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_purple
            )
        )
        ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_active)?.let {
            customSwipeButton4.setActiveBackground(it)
        }
        ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_inactive)?.let {
            customSwipeButton4.setInActiveBackground(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_android_black)?.let {
            customSwipeButton4.setActiveIcon(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_cloud_black)?.let {
            customSwipeButton4.setInActiveIcon(it)
        }
    }
}
