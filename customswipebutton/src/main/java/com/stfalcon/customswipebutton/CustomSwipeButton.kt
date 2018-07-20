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
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.button_swipe.view.*

class CustomSwipeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var isActive: Boolean = false
    private var onSwipedListener: (() -> Unit)? = null
    private var onSwipedOnListener: (() -> Unit)? = null
    private var onSwipedOffListener: (() -> Unit)? = null

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
    private var textPadding: Int = 0
    private var textSize: Float = 0.0F

    init {
        LayoutInflater.from(context).inflate(R.layout.button_swipe, this, true)
        attrs?.let {
            parseAttr(it)
        } ?: kotlin.run {
            initVariables()
        }
        implementStyle()
    }

    fun setActive(isActive: Boolean) {
        if (this.isActive != isActive) {
            this.isActive = isActive

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

    fun setActiveIcon(activeIcon: Drawable?) {
        this.activeIcon = activeIcon
        implementStyle()
    }

    fun setInActiveIcon(inactiveIcon: Drawable?) {
        this.inactiveIcon = inactiveIcon
        implementStyle()
    }

    fun setActiveBackground(activeBackground: Drawable?) {
        this.activeBackground = activeBackground
        implementStyle()
    }

    fun setInActiveBackground(inactiveBackground: Drawable?) {
        this.inactiveBackground = inactiveBackground
        implementStyle()
    }

    fun setEnable(isEnable: Boolean) {
        if (isEnable) {
            slidingButtonIv.setOnClickListener(getButtonClickListener())
            slidingButtonIv.setOnTouchListener(getButtonTouchListener())
            buttonSwipeView.setOnClickListener(getButtonClickListener())
        } else {
            slidingButtonIv.setOnClickListener(null)
            slidingButtonIv.setOnTouchListener(null)
            buttonSwipeView.setOnClickListener(null)
        }
        buttonSwipeView.isEnabled = isEnable
        buttonSwipeNewTv.isEnabled = isEnable
        slidingButtonIv.isEnabled = isEnable
    }

    fun setSwipeProgressToFinish(swipeProgressToFinish: Double) {
        this.swipeProgressToFinish = swipeProgressToFinish
    }

    fun setSwipeProgressToStart(swipeProgressToStart: Double) {
        this.swipeProgressToStart = swipeProgressToStart
    }

    fun setTextPadding(textPadding: Int) {
        this.textPadding = textPadding
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
    }

    private fun initVariables() {
        isActive = false
        isClickToSwipeEnable = true

        activeText = context.getString(R.string.activate_text)
        inactiveText = context.getString(R.string.inactivate_text)
        activeTextColor = ContextCompat.getColor(
            context,
            android.R.color.white
        )

        inactiveTextColor = ContextCompat.getColor(
            context,
            android.R.color.black
        )

        activeIcon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_stop
        )

        inactiveIcon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_play
        )

        activeBackground = ContextCompat.getDrawable(
            context,
            R.drawable.shape_scrolling_view_active
        )

        inactiveBackground = ContextCompat.getDrawable(
            context,
            R.drawable.shape_scrolling_view_inactive
        )
        textPadding = context.resources.getDimensionPixelSize(R.dimen.default_padding)

        textSize = context.resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
    }

    private fun implementStyle() {
        if (this.isActive) {
            returnToEnd()
            implementActiveStyle()
        } else {
            returnToStart()
            implementInActiveStyle()
        }
    }

    private fun getButtonTouchListener(): View.OnTouchListener {
        return OnTouchListener { _, event ->
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

    private fun getButtonClickListener(): View.OnClickListener {
        return OnClickListener {
            animateClick()
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
            ValueAnimator.ofFloat(slidingButtonIv.x, slidingButtonIv.x - 40, slidingButtonIv.x)
        positionAnimator.addUpdateListener {
            slidingButtonIv.x = positionAnimator.animatedValue as Float
        }
        animatorSet.play(positionAnimator)
        animatorSet.start()
    }

    private fun animateInactiveClick() {
        val animatorSet = AnimatorSet()
        val positionAnimator =
            ValueAnimator.ofFloat(slidingButtonIv.x, slidingButtonIv.x + 40, slidingButtonIv.x)
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
        rootView.post {
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
                implementActiveStyle()

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
        if (isActive) {
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

    private fun implementActiveStyle() {
        buttonSwipeView.background = activeBackground
        slidingButtonIv.setImageDrawable(activeIcon)
        buttonSwipeNewTv.text = activeText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(activeTextColor)
        buttonSwipeNewTv.setPadding(0, 0, textPadding, 0)
    }

    private fun implementInActiveStyle() {
        buttonSwipeView.background = inactiveBackground
        slidingButtonIv.setImageDrawable(inactiveIcon)
        buttonSwipeNewTv.text = inactiveText
        buttonSwipeNewTv.textSize = textSize
        buttonSwipeNewTv.setTextColor(inactiveTextColor)
        buttonSwipeNewTv.setPadding(textPadding, 0, 0, 0)
    }

    class Builder constructor(private val context: Context) {
        private var onSwipedListener: (() -> Unit)? = null
        private var onSwipedOnListener: (() -> Unit)? = null
        private var onSwipedOffListener: (() -> Unit)? = null

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
        private var textPadding: Int = 0
        private var textSize: Float = 0.0F

        fun setOnSwipedListener(onSwiped: (() -> Unit)): Builder {
            onSwipedListener = onSwiped
            return this
        }

        fun setOnSwipedOnListener(onSwiped: (() -> Unit)): Builder {
            onSwipedOnListener = onSwiped
            return this
        }

        fun setOnSwipedOffListener(onSwiped: (() -> Unit)): Builder {
            onSwipedOffListener = onSwiped
            return this
        }

        fun setIsClickToSwipeEnable(isEnable: Boolean): Builder {
            isClickToSwipeEnable = isEnable
            return this
        }

        fun setActiveText(activeTextMsg: String): Builder {
            activeText = activeTextMsg
            return this
        }

        fun setInActiveText(inactiveTextMsg: String): Builder {
            inactiveText = inactiveTextMsg
            return this
        }

        fun setActiveTextColor(activeTextColorRes: Int): Builder {
            activeTextColor = activeTextColorRes
            return this
        }

        fun setInActiveTextColor(inactiveTextColorRes: Int): Builder {
            inactiveTextColor = inactiveTextColorRes
            return this
        }

        fun setActiveIcon(activeIconDrawable: Drawable?): Builder {
            activeIcon = activeIconDrawable
            return this
        }

        fun setInActiveIcon(inactiveIconDrawable: Drawable?): Builder {
            inactiveIcon = inactiveIconDrawable
            return this
        }

        fun setActiveBackground(activeBackgroundDrawable: Drawable?): Builder {
            activeBackground = activeBackgroundDrawable
            return this
        }

        fun setInActiveBackground(inactiveBackgroundDrawable: Drawable?): Builder {
            inactiveBackground = inactiveBackgroundDrawable
            return this
        }

        fun setTextPadding(textPaddingData: Int): Builder {
            textPadding = textPaddingData
            return this
        }

        fun setTextSize(textSizeData: Float): Builder {
            textSize = textSizeData
            return this
        }

        fun setSwipeProgressToFinish(swipeProgressToFinishData: Double): Builder {
            swipeProgressToFinish = swipeProgressToFinishData
            return this
        }

        fun setSwipeProgressToStart(swipeProgressToFinishStart: Double): Builder {
            swipeProgressToStart = swipeProgressToFinishStart
            return this
        }

        fun build(): CustomSwipeButton {
            val swipeButton = CustomSwipeButton(context = context)
            swipeButton.setActiveText(activeText)
            swipeButton.setInActiveText(inactiveText)
            swipeButton.setActiveTextColor(activeTextColor)
            swipeButton.setInActiveTextColor(inactiveTextColor)
            swipeButton.setActiveIcon(activeIcon)
            swipeButton.setInActiveIcon(inactiveIcon)
            swipeButton.setTextPadding(textPadding)
            swipeButton.setTextSize(textSize)
            swipeButton.setSwipeProgressToFinish(swipeProgressToFinish)
            swipeButton.setSwipeProgressToStart(swipeProgressToStart)
            swipeButton.setActiveBackground(activeBackground)
            swipeButton.setInActiveBackground(inactiveBackground)
            swipeButton.setOnSwipedListener { onSwipedListener?.invoke() }
            swipeButton.setOnSwipedOnListener { onSwipedOnListener?.invoke() }
            swipeButton.setOnSwipedOffListener { onSwipedOffListener?.invoke() }

            swipeButton.setIsClickToSwipeEnable(true)
            return swipeButton
        }
    }
}