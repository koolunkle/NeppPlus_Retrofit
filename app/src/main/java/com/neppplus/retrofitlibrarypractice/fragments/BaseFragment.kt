package com.neppplus.retrofitlibrarypractice.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()
    }

    abstract fun setupEvents()
    abstract fun setValues()

}