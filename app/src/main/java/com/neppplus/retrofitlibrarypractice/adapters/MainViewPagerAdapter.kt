package com.neppplus.retrofitlibrarypractice.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.retrofitlibrarypractice.fragments.MyProfileFragment
import com.neppplus.retrofitlibrarypractice.fragments.ProductListFragment
import com.neppplus.retrofitlibrarypractice.fragments.ReviewListFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ReviewListFragment()
            1 -> ProductListFragment()
            else -> MyProfileFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "리뷰목록"
            1 -> "상품목록"
            else -> "내 프로필"
        }
    }

}