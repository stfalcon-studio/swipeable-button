package com.stfalcon.swipebutton.samples

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stfalcon.swipebutton.R
import kotlinx.android.synthetic.main.fragment_animate.*

class AnimateFragment : Fragment() {

    companion object {
        fun newInstance(): AnimateFragment {
            return AnimateFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customSwipeButton4.isChecked = true
        customSwipeButton4.isEnabled = true
        customSwipeButton4.checkedText = "Swipe to unchecked"
        customSwipeButton4.uncheckedText = "Swipe to checked"
        customSwipeButton4.textSize =
                resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        customSwipeButton4.swipeProgressToFinish = 0.1
        customSwipeButton4.swipeProgressToStart = 0.3
        customSwipeButton4.checkedTextColor = ContextCompat.getColor(
            requireContext(),
            R.color.checkedTextColor3
        )
        customSwipeButton4.uncheckedTextColor = ContextCompat.getColor(
            requireContext(),
            R.color.uncheckedTextColor3
        )
        customSwipeButton4.checkedBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_scrolling_view_checked3)
        customSwipeButton4.uncheckedBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_scrolling_view_unchecked3)
        customSwipeButton4.checkedToggleBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_checked_toggle3)
        customSwipeButton4.uncheckedToggleBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_unchecked_toggle3)
        customSwipeButton4.uncheckedIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
        customSwipeButton4.checkedIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_unchecked)

        animateBtn.setOnClickListener {
            customSwipeButton4.setCheckedAnimated(!customSwipeButton4.isChecked)
        }
    }
}
