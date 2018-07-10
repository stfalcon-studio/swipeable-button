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
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.button_swipe.view.*

class CustomSwipeButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var initialX: Float = 0.toFloat()
    private var active: Boolean = false
    private var onSwipedListener: (() -> Unit)? = null
    private var onSwipedOnListener: (() -> Unit)? = null
    private var onSwipedOffListener: (() -> Unit)? = null

    private var isActive = true
    private var isClickToSwipeEnable = true
    private var swipeProgressToFinish = 0.5
    private var swipeProgressToStart = 0.5
    private lateinit var activeText: String
    private lateinit var inactiveText: String
    private var activeTextColor: Int = 0
    private var inactiveTextColor: Int = 0
    private var activeIcon: Drawable? = null
    private var inactiveIcon: Drawable? = null
    private var activeBackground: Drawable? = null
    private var inactiveBackground: Drawable? = null
    private var swipeButtonPadding: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.button_swipe, this, true)
        attrs?.let {
            parseAttr(it)
        }
        implementStyle()
    }

    fun setActive(isActive: Boolean) {
        if (this.active != isActive) {
            this.active = isActive

            implementStyle()
        }
    }

    fun setOnSwipedListener(onSwiped: (() -> Unit)) {
        this.onSwipedListener = onSwiped
    }

    fun setOnSwipedOnListener(onSwiped: (() -> Unit)) {
        this.onSwipedOnListener = onSwiped
    }

    fun setOnSwipedOffListener(onSwiped: (() -> Unit)) {
        this.onSwipedOffListener = onSwiped
    }

    fun setIsClickToSwipeEnable(isEnable: Boolean) {
        this.isClickToSwipeEnable = isEnable
        implementStyle()
    }

    fun setActiveText(activeText: String) {
        this.activeText = activeText
        implementStyle()
    }

    fun setInActiveText(inactiveText: String) {
        this.inactiveText = inactiveText
        implementStyle()
    }

    fun setActiveTextColor(activeTextColor: Int) {
        this.activeTextColor = activeTextColor
        implementStyle()
    }

    fun setInActiveTextColor(inactiveTextColor: Int) {
        this.inactiveTextColor = inactiveTextColor
        implementStyle()
    }

    fun setActiveIcon(activeIcon: Drawable) {
        this.activeIcon = activeIcon
        implementStyle()
    }

    fun setInActiveIcon(inactiveIcon: Drawable) {
        this.inactiveIcon = inactiveIcon
        implementStyle()
    }

    fun setActiveBackground(activeBackground: Drawable) {
        this.activeBackground = activeBackground
        implementStyle()
    }

    fun setInActiveBackground(inactiveBackground: Drawable) {
        this.inactiveBackground = inactiveBackground
        implementStyle()
    }

    fun setEnable(isEnable: Boolean) {
        if (isEnable) {
            setOnTouchListener(getButtonTouchListener())
        } else {
            setOnTouchListener(null)
        }
        buttonSwipeView.isEnabled = isEnable
        buttonSwipeNewTv.isEnabled = isEnable
        slidingButtonIv.isEnabled = isEnable
    }

    private fun implementStyle() {
        if (this.active) {
            returnToEnd()
            implementActiveStyle()
        } else {
            returnToStart()
            implementInActiveStyle()
        }
    }

    private fun getButtonTouchListener(): View.OnTouchListener {
        return OnTouchListener { v, event ->
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
            return@OnTouchListener false
        }
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
        rootView.post {
            val animatorSet = AnimatorSet()
            val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, (width - slidingButtonIv.width).toFloat())
            positionAnimator.addUpdateListener {
                slidingButtonIv.x = positionAnimator.animatedValue as Float
            }
            animatorSet.play(positionAnimator)
            animatorSet.start()
        }
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
                implementInActiveStyle()

                onSwipedOffListener?.invoke()
                onSwipedListener?.invoke()
                active = false
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateToEnd() {
        val animatorSet = AnimatorSet()
        val positionAnimator = ValueAnimator.ofFloat(slidingButtonIv.x, (width - slidingButtonIv.width).toFloat())
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                implementActiveStyle()

                onSwipedOnListener?.invoke()
                onSwipedListener?.invoke()
                active = true
            }
        })

        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun implementActiveStyle() {
        buttonSwipeView.background = activeBackground
        slidingButtonIv.setImageDrawable(activeIcon)
        buttonSwipeNewTv.text = activeText
        buttonSwipeNewTv.setTextColor(activeTextColor)
        buttonSwipeNewTv.setPadding(0, 0, context.resources.getDimensionPixelSize(swipeButtonPadding), 0)
    }

    private fun implementInActiveStyle() {
        buttonSwipeView.background = inactiveBackground
        slidingButtonIv.setImageDrawable(inactiveIcon)
        buttonSwipeNewTv.text = inactiveText
        buttonSwipeNewTv.setTextColor(inactiveTextColor)
        buttonSwipeNewTv.setPadding(context.resources.getDimensionPixelSize(swipeButtonPadding), 0, 0, 0)
    }

    private fun onButtonMove(event: MotionEvent) {
        initialX = slidingButtonIv.x

        //set the center of the button in the position of the touch
        if (event.x > initialX + slidingButtonIv.width / 2
                && event.x + slidingButtonIv.width / 2 < width) {
            slidingButtonIv.x = event.x - slidingButtonIv.width / 2
        }
        if (event.x - slidingButtonIv.width / 2 > buttonSwipeView.x
                && slidingButtonIv.x > 0
                && event.x + slidingButtonIv.width / 2 < width) {
            slidingButtonIv.x = event.x - slidingButtonIv.width / 2
        }

        //set the moving part position to the limits
        if (event.x + slidingButtonIv.width / 2 > width
                && slidingButtonIv.x + slidingButtonIv.width / 2 < width) {
            slidingButtonIv.x = (width - slidingButtonIv.width).toFloat()
        }

        //set the moving part position to the left border
        if (event.x < slidingButtonIv.width / 2
                && slidingButtonIv.x > 0) {
            slidingButtonIv.x = 0.toFloat()
        }
    }

    private fun onButtonMoved() {
        if (active) {
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

        isActive = typedArray.getBoolean(R.styleable.CustomSwipeButton_isActive, false)
        isClickToSwipeEnable = typedArray.getBoolean(R.styleable.CustomSwipeButton_isClickToSwipeEnable, true)
        swipeProgressToFinish = typedArray.getFloat(R.styleable.CustomSwipeButton_swipeProgressToFinish, swipeProgressToFinish.toFloat()).toDouble()
        swipeProgressToStart = 1 - typedArray.getFloat(R.styleable.CustomSwipeButton_swipeProgressToStart, swipeProgressToStart.toFloat()).toDouble()

        activeText = typedArray.getString(R.styleable.CustomSwipeButton_activeText)
                ?: context.getString(typedArray.getResourceId(R.styleable.CustomSwipeButton_activeText, R.string.activate_text))

        inactiveText = typedArray.getString(R.styleable.CustomSwipeButton_inactiveText)
                ?: context.getString(typedArray.getResourceId(R.styleable.CustomSwipeButton_inactiveText, R.string.inactivate_text))

        activeTextColor = if (typedArray.getInt(R.styleable.CustomSwipeButton_activeTextColor, 0) != 0) {
            typedArray.getInt(R.styleable.CustomSwipeButton_activeTextColor, 0)
        } else {
            ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CustomSwipeButton_activeTextColor, android.R.color.white))
        }

        inactiveTextColor = if (typedArray.getInt(R.styleable.CustomSwipeButton_inactiveTextColor, 0) != 0) {
            typedArray.getInt(R.styleable.CustomSwipeButton_inactiveTextColor, 0)
        } else {
            ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CustomSwipeButton_inactiveTextColor, android.R.color.black))
        }

        activeIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_activeIcon)
                ?: ContextCompat.getDrawable(context,typedArray.getResourceId(R.styleable.CustomSwipeButton_activeIcon, R.drawable.ic_stop))
        inactiveIcon = typedArray.getDrawable(R.styleable.CustomSwipeButton_inactiveIcon)
                ?: ContextCompat.getDrawable(context,typedArray.getResourceId(R.styleable.CustomSwipeButton_inactiveIcon, R.drawable.ic_play))

        activeBackground = ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.CustomSwipeButton_activeBackground, R.drawable.shape_scrolling_view_active))
        inactiveBackground = ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.CustomSwipeButton_inactiveBackground, R.drawable.shape_scrolling_view_inactive))

        swipeButtonPadding = if (typedArray.getInt(R.styleable.CustomSwipeButton_swipeButtonPadding, 0) != 0) {
            typedArray.getInt(R.styleable.CustomSwipeButton_swipeButtonPadding,
                    typedArray.getInt(R.styleable.CustomSwipeButton_swipeButtonPadding, 0))
        } else {
            R.dimen.default_padding
        }

        typedArray.recycle()
    }
}