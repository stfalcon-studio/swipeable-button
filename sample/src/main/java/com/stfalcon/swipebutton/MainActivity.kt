package com.stfalcon.swipebutton

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.stfalcon.customswipebutton.CustomSwipeButton
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
        buildComponent()
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
        customSwipeButton4.setTextPadding(16)
        customSwipeButton4.setTextSize(8f)
        customSwipeButton4.setSwipeProgressToFinish(0.1)
        customSwipeButton4.setSwipeProgressToStart(0.9)
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

    private fun buildComponent() {
        val customSwipeButtonBuild = CustomSwipeButton.Builder(this)
            .setActiveText("Active Builder")
            .setInActiveText("Inactive builder")
            .setActiveTextColor(android.R.color.holo_green_light)
            .setInActiveTextColor(android.R.color.holo_blue_bright)
            .setActiveIcon(ContextCompat.getDrawable(this, R.drawable.ic_android_black))
            .setInActiveIcon(ContextCompat.getDrawable(this, R.drawable.ic_cloud_black))
            .setIsClickToSwipeEnable(true)
            .setTextPadding(resources.getDimensionPixelSize(R.dimen.default_padding))
            .setTextSize(resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat())
            .setSwipeProgressToFinish(0.5)
            .setSwipeProgressToStart(0.5)
            .setActiveBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.shape_sample_scrolling_view_active
                )
            )
            .setInActiveBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.shape_sample_scrolling_view_inactive
                )
            )
            .setOnSwipedListener { Log.d(TAG, "onSwiped") }
            .setOnSwipedOnListener { Log.d(TAG, "onSwipedOn") }
            .setOnSwipedOffListener { Log.d(TAG, "onSwipedOff") }
            .build()

        customSwipeButtonBuild.setEnable(true)

        mainContainer.addView(customSwipeButtonBuild)
    }
}
