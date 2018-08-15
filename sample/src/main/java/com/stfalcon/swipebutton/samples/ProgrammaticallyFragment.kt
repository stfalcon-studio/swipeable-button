package com.stfalcon.swipebutton.samples

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stfalcon.swipebutton.R
import kotlinx.android.synthetic.main.fragment_programmatically.*

class ProgrammaticallyFragment : Fragment() {

    companion object {
        fun newInstance(): ProgrammaticallyFragment {
            return ProgrammaticallyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programmatically, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customSwipeButton3.isChecked = true
        customSwipeButton3.isEnable = true
        customSwipeButton3.checkedText = "Swipe to unlock"
        customSwipeButton3.uncheckedText = "Swipe to lock"
        customSwipeButton3.textSize =
                resources.getDimensionPixelSize(R.dimen.program_text_size).toFloat()
        customSwipeButton3.swipeProgressToFinish = 0.1
        customSwipeButton3.swipeProgressToStart = 0.3
        customSwipeButton3.checkedTextColor = ContextCompat.getColor(
            requireContext(),
            R.color.checkedTextColor2
        )
        customSwipeButton3.uncheckedTextColor = ContextCompat.getColor(
            requireContext(),
            R.color.uncheckedTextColor2
        )
        customSwipeButton3.checkedBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_scrolling_view_checked2)
        customSwipeButton3.uncheckedBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_scrolling_view_unchecked2)
        customSwipeButton3.checkedToggleBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_checked_toggle2)
        customSwipeButton3.uncheckedToggleBackground =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_sample_unchecked_toggle2)
        customSwipeButton3.checkedIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock)
        customSwipeButton3.uncheckedIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_unlock)
    }


}
