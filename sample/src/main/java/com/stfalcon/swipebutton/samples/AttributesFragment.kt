package com.stfalcon.swipebutton.samples


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stfalcon.swipebutton.R

class AttributesFragment : Fragment() {

    companion object {
        fun newInstance(): AttributesFragment {
            return AttributesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attributes, container, false)
    }
}
