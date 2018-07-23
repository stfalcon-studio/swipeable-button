package com.stfalcon.customswipebutton

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
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

    var isActive: Boolean = false
        set(isActive) {
            field = isActive
            activateStyle()
        }
    var isClickToSwipeEnable = true
        set(isClickToSwipeEnable) {
            field = isClickToSwipeEnable
            activateStyle()
        }
    var swipeProgressToFinish = 0.5
        set(swipeProgressToFinish) {
            field = swipeProgressToFinish
            activateStyle()
        }
    var swipeProgressToStart = 0.5
        set(swipeProgressToStart) {
            field = swipeProgressToStart
            activateStyle()
        }
    var activeText: String = context.getString(R.string.activate_text)
        set(activeText) {
            field = activeText
            activateStyle()
        }
    var inactiveText: String = context.getString(R.string.inactivate_text)
        set(inactiveText) {
            field = inactiveText
            activateStyle()
        }
    var activeTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        set(activeTextColor) {
            field = activeTextColor
            activateStyle()
        }
    var inactiveTextColor: Int = ContextCompat.getColor(context, android.R.color.black)
        set(inactiveTextColor) {
            field = inactiveTextColor
            activateStyle()
        }
    var activeIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_stop)
        set(activeIcon) {
            field = activeIcon
            activateStyle()
        }
    var inactiveIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_play)
        set(inactiveIcon) {
            field = inactiveIcon
            activateStyle()
        }
    var inactiveBackground: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.shape_scrolling_view_inactive)
        set(inactiveBackground) {
            field = inactiveBackground
            activateStyle()
        }
    var activeBackground: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.shape_scrolling_view_active)
        set(activeBackground) {
            field = activeBackground
            activateStyle()
        }
    var textPadding: Int = context.resources.getDimensionPixelSize(R.dimen.default_padding)
        set(textPadding) {
            field = textPadding
            activateStyle()
        }
    var textSize: Float =
        context.resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        set(textSize) {
            field = textSize
            activateStyle()
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
        activateStyle()
        updateEnableState()
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

    private fun activateStyle() {
        if (this.isActive) {
            makeActive()
            returnToEnd()
        } else {
            makeInActive()
            returnToStart()
        }
    }

    private fun animateClick() {
        if (isClickToSwipeEnable) {
            if (this.isActive) {
                animateActiveClick()
            } else {
                animateInactiveClick()
            }
        }
    }

    private fun animateActiveClick() {
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

    private fun animateInactiveClick() {
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

    private fun returnToStart() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, 0F)
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()

    }

    private fun returnToEnd() {
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

    private fun animateToStart() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, 0F)
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                makeInActive()

                onSwipedOffListener?.invoke()
                onSwipedListener?.invoke()
                isActive = false
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateToEnd() {
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
                makeActive()

                onSwipedOnListener?.invoke()
                onSwipedListener?.invoke()
                isActive = true
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
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
        if (this.isActive) {
            if (slidingButtonIv.x < buttonSwipeView.width * swipeProgressToStart) {
                animateToStart()
            } else {
                returnToEnd()
            }
        } else {
            if (slidingButtonIv.x > buttonSwipeView.width * swipeProgressToFinish) {
                animateToEnd()
            } else {
                returnToStart()
            }
        }
    }

    private fun parseAttr(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSwipeButton)

        isActivated = typedArray.getBoolean(R.styleable.CustomSwipeButton_isActive, false)
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

        activeText = typedArray.getString(R.styleable.CustomSwipeButton_activeText)
                ?: context.getString(
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_activeText,
                R.string.activate_text
            )
        )

        inactiveText = typedArray.getString(R.styleable.CustomSwipeButton_inactiveText)
                ?: context.getString(
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_inactiveText,
                R.string.inactivate_text
            )
        )

        activeTextColor =
                if (typedArray.getInt(R.styleable.CustomSwipeButton_activeTextColor, 0) != 0) {
                    typedArray.getInt(R.styleable.CustomSwipeButton_activeTextColor, 0)
                } else {
                    ContextCompat.getColor(
                        context,
                        typedArray.getResourceId(
                            R.styleable.CustomSwipeButton_activeTextColor,
                            android.R.color.white
                        )
                    )
                }

        inactiveTextColor =
                if (typedArray.getInt(R.styleable.CustomSwipeButton_inactiveTextColor, 0) != 0) {
                    typedArray.getInt(R.styleable.CustomSwipeButton_inactiveTextColor, 0)
                } else {
                    ContextCompat.getColor(
                        context,
                        typedArray.getResourceId(
                            R.styleable.CustomSwipeButton_inactiveTextColor,
                            android.R.color.black
                        )
                    )
                }

        activeIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_activeIcon)
                ?: ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(R.styleable.CustomSwipeButton_activeIcon, R.drawable.ic_stop)
        )
        inactiveIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_inactiveIcon)
                ?: ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(R.styleable.CustomSwipeButton_inactiveIcon, R.drawable.ic_play)
        )

        activeBackground = ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_activeBackground,
                R.drawable.shape_scrolling_view_active
            )
        )
        inactiveBackground = ContextCompat.getDrawable(
            context,
            typedArray.getResourceId(
                R.styleable.CustomSwipeButton_inactiveBackground,
                R.drawable.shape_scrolling_view_inactive
            )
        )

        textPadding = if (typedArray.getDimensionPixelSize(
                R.styleable.CustomSwipeButton_textPadding,
                0
            ) != 0
        ) {
            typedArray.getDimensionPixelSize(R.styleable.CustomSwipeButton_textPadding, 0)
        } else {
            context.resources.getDimensionPixelSize(R.dimen.default_padding)
        }

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

    private fun moveToEnd() {
        val layoutParams = slidingButtonIv.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.UNSET
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        slidingButtonIv.layoutParams = layoutParams
    }

    private fun moveToStart() {
        val layoutParams = slidingButtonIv.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.UNSET
        slidingButtonIv.layoutParams = layoutParams
    }

    private fun makeActive() {
        buttonSwipeView.background = activeBackground
        slidingButtonIv.setImageDrawable(activeIcon)
        buttonSwipeNewTv.text = activeText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(activeTextColor)
    }

    private fun makeInActive() {
        buttonSwipeView.background = inactiveBackground
        slidingButtonIv.setImageDrawable(inactiveIcon)
        buttonSwipeNewTv.text = inactiveText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(inactiveTextColor)
    }
}