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
        customSwipeButton2.isEnable = false
        customSwipeButton3.isEnable = true

        initComponent()
        initListeners()
    }

    private fun initListeners() {
        customSwipeButton.onSwipedListener = {
            Log.d(TAG, "onSwiped")
        }
        customSwipeButton.onSwipedOnListener = {
            Log.d(TAG, "onSwipedOn")
        }
        customSwipeButton.onSwipedOffListener = {
            Log.d(TAG, "onSwipedOff")
        }
    }

    private fun initComponent() {
        customSwipeButton4.isChecked = true
        customSwipeButton4.isEnable = true
        customSwipeButton4.checkedText = "Checked text"
        customSwipeButton4.uncheckedText = "Unchecked text"
        customSwipeButton4.textSize =
                resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        customSwipeButton4.swipeProgressToFinish = 0.1
        customSwipeButton4.swipeProgressToStart = 0.9
        customSwipeButton4.checkedTextColor = ContextCompat.getColor(
            this,
            android.R.color.holo_blue_bright
        )
        customSwipeButton4.uncheckedTextColor = ContextCompat.getColor(
            this,
            android.R.color.holo_purple
        )
        customSwipeButton4.checkedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_active)
        customSwipeButton4.uncheckedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_inactive)
        customSwipeButton4.checkedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_android_black)
        customSwipeButton4.uncheckedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_cloud_black)

        animateBtn.setOnClickListener {
            customSwipeButton5.setSwipeButtonState(!customSwipeButton5.isChecked)
        }
    }
}
