package com.stfalcon.swipebutton

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CustomSwipeableButton"
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
        customSwipeButton3.checkedText = "Swipe to unlock"
        customSwipeButton3.uncheckedText = "Swipe to lock"
        customSwipeButton3.textSize =
                resources.getDimensionPixelSize(R.dimen.program_text_size).toFloat()
        customSwipeButton3.swipeProgressToFinish = 0.1
        customSwipeButton3.swipeProgressToStart = 0.3
        customSwipeButton3.checkedTextColor = ContextCompat.getColor(
            this,
            R.color.checkedTextColor2
        )
        customSwipeButton3.uncheckedTextColor = ContextCompat.getColor(
            this,
            R.color.uncheckedTextColor2
        )
        customSwipeButton3.checkedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_checked2)
        customSwipeButton3.uncheckedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_unchecked2)
        customSwipeButton3.checkedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_checked_toggle2)
        customSwipeButton3.uncheckedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_unchecked_toggle2)
        customSwipeButton3.checkedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_lock)
        customSwipeButton3.uncheckedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_unlock)

        customSwipeButton4.isChecked = true
        customSwipeButton4.isEnable = true
        customSwipeButton4.checkedText = "Swipe to unchecked"
        customSwipeButton4.uncheckedText = "Swipe to checked"
        customSwipeButton4.textSize =
                resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        customSwipeButton4.swipeProgressToFinish = 0.1
        customSwipeButton4.swipeProgressToStart = 0.3
        customSwipeButton4.checkedTextColor = ContextCompat.getColor(
            this,
            R.color.checkedTextColor3
        )
        customSwipeButton4.uncheckedTextColor = ContextCompat.getColor(
            this,
            R.color.uncheckedTextColor3
        )
        customSwipeButton4.checkedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_checked3)
        customSwipeButton4.uncheckedBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_unchecked3)
        customSwipeButton4.checkedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_checked_toggle3)
        customSwipeButton4.uncheckedToggleBackground =
                ContextCompat.getDrawable(this, R.drawable.shape_sample_unchecked_toggle3)
        customSwipeButton4.uncheckedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_check)
        customSwipeButton4.checkedIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_unchecked)

        animateBtn.setOnClickListener {
            customSwipeButton4.setCheckedAnimated(!customSwipeButton4.isChecked)
        }
    }
}
