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
        customSwipeButton3.isChecked = true
        customSwipeButton3.isEnable = true
        customSwipeButton3.checkedText = "Checked text"
        customSwipeButton3.uncheckedText = "Unchecked text"
        customSwipeButton3.textSize =
                resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        customSwipeButton3.swipeProgressToFinish = 0.1
        customSwipeButton3.swipeProgressToStart = 0.8
        customSwipeButton3.checkedTextColor = ContextCompat.getColor(
            this,
            R.color.checkedTextColor
        )
        customSwipeButton3.uncheckedTextColor = ContextCompat.getColor(
            this,
            R.color.uncheckedTextColor
        )
        customSwipeButton3.checkedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_checked)
        customSwipeButton3.uncheckedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_unchecked)
        customSwipeButton3.checkedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_checked_toggle)
        customSwipeButton3.uncheckedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_unchecked_toggle)
        customSwipeButton3.checkedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_done_black)
        customSwipeButton3.uncheckedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_pause_black)

        animateBtn.setOnClickListener {
            customSwipeButton4.setSwipeButtonState(!customSwipeButton4.isChecked)
        }
    }
}
