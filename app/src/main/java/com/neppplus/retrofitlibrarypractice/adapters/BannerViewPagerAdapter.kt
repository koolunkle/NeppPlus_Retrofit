package com.neppplus.retrofitlibrarypractice.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.retrofitlibrarypractice.datas.BannerData
import com.neppplus.retrofitlibrarypractice.fragments.BannerFragment

class BannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>) :
    FragmentPagerAdapter(fm) {

    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {
        return BannerFragment(mBannerList[position])
    }

}