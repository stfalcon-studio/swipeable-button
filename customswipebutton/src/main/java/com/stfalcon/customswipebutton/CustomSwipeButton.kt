package com.stfalcon.customswipebutton

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.button_swipe.view.*

open class CustomSwipeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var onSwipedListener: (() -> Unit)? = null
    var onSwipedOnListener: (() -> Unit)? = null
    var onSwipedOffListener: (() -> Unit)? = null

    var isChecked: Boolean = false
        set(isChecked) {
            field = isChecked
            rootView.post { updateState() }
        }
    var isClickToSwipeEnable = true
        set(isClickToSwipeEnable) {
            field = isClickToSwipeEnable
            updateState()
        }
    var swipeProgressToFinish = 0.5
        set(swipeProgressToFinish) {
            field = swipeProgressToFinish
            updateState()
        }
    var swipeProgressToStart = 0.5
        set(swipeProgressToStart) {
            field = swipeProgressToStart
            updateState()
        }
    var checkedText: String = context.getString(R.string.checked_text)
        set(checkedText) {
            field = checkedText
            updateState()
        }
    var uncheckedText: String = context.getString(R.string.unchecked_text)
        set(uncheckedText) {
            field = uncheckedText
            updateState()
        }
    var checkedTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        set(checkedTextColor) {
            field = checkedTextColor
            updateState()
        }
    var uncheckedTextColor: Int = ContextCompat.getColor(context, android.R.color.black)
        set(uncheckedTextColor) {
            field = uncheckedTextColor
            updateState()
        }
    var checkedIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_stop)
        set(checkedIcon) {
            field = checkedIcon
            updateState()
        }
    var uncheckedIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_play)
        set(uncheckedIcon) {
            field = uncheckedIcon
            updateState()
        }
    var uncheckedBackground: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.shape_scrolling_view_unchecked)
        set(uncheckedBackground) {
            field = uncheckedBackground
            updateState()
        }
    var checkedBackground: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.shape_scrolling_view_checked)
        set(checkedBackground) {
            field = checkedBackground
            updateState()
        }
    var textSize: Float =
        context.resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        set(textSize) {
            field = textSize
            updateState()
        }
    var isEnable: Boolean = true
        set(isEnable) {
            field = isEnable

            updateEnableState()
        }

    private val onTouchListener = OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return@OnTouchListener true
            MotionEvent.ACTION_MOVE -> {
                onButtonMove(event)
                return@OnTouchListener true
            }
            MotionEvent.ACTION_UP -> {
                onButtonMoved()
                return@OnTouchListener true
            }
        }
        v?.onTouchEvent(event) ?: true
    }

    private val onClickListener = OnClickListener {
        animateClick()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.button_swipe, this, true)
        attrs?.let {
            parseAttr(it)
        }

        updateState()
        updateEnableState()
    }

    fun setSwipeButtonState(isChecked: Boolean){
        if (isChecked) {
            animateToggleToEnd()
        } else {
            animateToggleToStart()
        }
    }

    private fun updateEnableState() {
        if (this.isEnable) {
            slidingButtonIv.setOnTouchListener(onTouchListener)
            slidingButtonIv.setOnClickListener(onClickListener)
            buttonSwipeView.setOnClickListener(onClickListener)
        } else {
            slidingButtonIv.setOnClickListener(null)
            slidingButtonIv.setOnTouchListener(null)
            buttonSwipeView.setOnClickListener(null)
        }
        buttonSwipeView.isEnabled = this.isEnable
        buttonSwipeNewTv.isEnabled = this.isEnable
        slidingButtonIv.isEnabled = this.isEnable
    }

    private fun updateState() {
        if (this.isChecked) {
            setActivatedStyle()
            setToggleToEnd()
        } else {
            setDeactivatedStyle()
            setToggleToStart()
        }
    }

    private fun setToggleToEnd() {
        slidingButtonIv.x = (buttonSwipeView.width - slidingButtonIv.width).toFloat()
    }

    private fun setToggleToStart() {
        slidingButtonIv.x = 0F
    }

    private fun returnToggleToStart() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, 0F)
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun returnToggleToEnd() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(
            slidingButtonIv.x,
            (buttonSwipeView.width - slidingButtonIv.width).toFloat()
        )
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateToggleToStart() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, 0F)
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                setDeactivatedStyle()

                onSwipedOffListener?.invoke()
                onSwipedListener?.invoke()
                isChecked = false
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateToggleToEnd() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(
            slidingButtonIv.x,
            (buttonSwipeView.width - slidingButtonIv.width).toFloat()
        )
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                setActivatedStyle()

                onSwipedOnListener?.invoke()
                onSwipedListener?.invoke()
                isChecked = true
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateClick() {
        if (isClickToSwipeEnable) {
            if (this.isChecked) {
                animateClickToActivate()
            } else {
                animateClickToDeactivate()
            }
        }
    }

    private fun animateClickToActivate() {
        val animatorSet = AnimatorSet()
        val positionAnimator =
            ValueAnimator.ofFloat(
                (buttonSwipeView.width - slidingButtonIv.width).toFloat(),
                ((buttonSwipeView.width - slidingButtonIv.width) - (slidingButtonIv.width / 2)).toFloat(),
                (buttonSwipeView.width - slidingButtonIv.width).toFloat()
            )
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateClickToDeactivate() {
        val animatorSet = AnimatorSet()
        val positionAnimator =
            ValueAnimator.ofFloat(
                0F,
                (slidingButtonIv.width / 2).toFloat(),
                0F
            )
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun onButtonMove(event: MotionEvent) {
        if (slidingButtonIv.x >= 0
            && event.rawX + slidingButtonIv.width / 2 < width
        ) {
            if (slidingButtonIv.x + slidingButtonIv.width / 2 < event.x
                || event.rawX - slidingButtonIv.width / 2 > buttonSwipeView.x
            ) {
                slidingButtonIv.x = event.rawX - slidingButtonIv.width / 2
            }
        }
    }

    private fun onButtonMoved() {
        if (this.isChecked) {
            if (slidingButtonIv.x < buttonSwipeView.width * swipeProgressToStart) {
                animateToggleToStart()
            } else {
                returnToggleToEnd()
            }
        } else {
            if (slidingButtonIv.x > buttonSwipeView.width * swipeProgressToFinish) {
                animateToggleToEnd()
            } else {
                returnToggleToStart()
            }
        }
    }

    private fun parseAttr(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSwipeButton)

        isChecked = typedArray.getBoolean(R.styleable.CustomSwipeButton_isChecked, false)
        isClickToSwipeEnable =
                typedArray.getBoolean(R.styleable.CustomSwipeButton_isClickToSwipeEnable, true)
        swipeProgressToFinish = typedArray.getFloat(
            R.styleable.CustomSwipeButton_swipeProgressToFinish,
            swipeProgressToFinish.toFloat()
        ).toDouble()
        swipeProgressToStart = 1 - typedArray.getFloat(
            R.styleable.CustomSwipeButton_swipeProgressToStart,
            swipeProgressToStart.toFloat()
        ).toDouble()

        checkedText = typedArray.getString(R.styleable.CustomSwipeButton_checkedText)
                ?: context.getString(
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_checkedText,
                R.string.checked_text
            )
        )

        uncheckedText = typedArray.getString(R.styleable.CustomSwipeButton_uncheckedText)
                ?: context.getString(
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_uncheckedText,
                R.string.unchecked_text
            )
        )

        checkedTextColor =
                if (typedArray.getInt(R.styleable.CustomSwipeButton_checkedTextColor, 0) != 0) {
                    typedArray.getInt(R.styleable.CustomSwipeButton_checkedTextColor, 0)
                } else {
                    ContextCompat.getColor(
                        context,
                        typedArray.getResourceId(
                            R.styleable.CustomSwipeButton_checkedTextColor,
                            android.R.color.white
                        )
                    )
                }

        uncheckedTextColor =
                if (typedArray.getInt(R.styleable.CustomSwipeButton_uncheckedTextColor, 0) != 0) {
                    typedArray.getInt(R.styleable.CustomSwipeButton_uncheckedTextColor, 0)
                } else {
                    ContextCompat.getColor(
                        context,
                        typedArray.getResourceId(
                            R.styleable.CustomSwipeButton_uncheckedTextColor,
                            android.R.color.black
                        )
                    )
                }

        checkedIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_checkedIcon)
                ?: ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(R.styleable.CustomSwipeButton_checkedIcon, R.drawable.ic_stop)
        )
        uncheckedIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_uncheckedIcon)
                ?: ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(R.styleable.CustomSwipeButton_uncheckedIcon, R.drawable.ic_play)
        )

        checkedBackground = ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_checkedBackground,
                R.drawable.shape_scrolling_view_checked
            )
        )
        uncheckedBackground = ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_uncheckedBackground,
                R.drawable.shape_scrolling_view_unchecked
            )
        )

        textSize = if (typedArray.getDimensionPixelSize(
                R.styleable.CustomSwipeButton_textSize,
                0
            ) != 0
        ) {
            typedArray.getDimensionPixelSize(R.styleable.CustomSwipeButton_textSize, 0).toFloat()
        } else {
            context.resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        }

        typedArray.recycle()
    }

    private fun setActivatedStyle() {
        buttonSwipeView.background = checkedBackground
        slidingButtonIv.setImageDrawable(checkedIcon)
        buttonSwipeNewTv.text = checkedText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(checkedTextColor)
    }

    private fun setDeactivatedStyle() {
        buttonSwipeView.background = uncheckedBackground
        slidingButtonIv.setImageDrawable(uncheckedIcon)
        buttonSwipeNewTv.text = uncheckedText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(uncheckedTextColor)
    }
}