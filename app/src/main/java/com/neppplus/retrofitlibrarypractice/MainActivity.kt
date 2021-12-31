package com.neppplus.retrofitlibrarypractice

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.neppplus.retrofitlibrarypractice.adapters.MainViewPagerAdapter
import com.neppplus.retrofitlibrarypractice.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var mvpa: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        1. 뷰페이저를 옮기면 (이벤트) -> 바텀네비게이션 클릭

        binding.mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

//                손으로 옮기는 모든 순간 -> 얼만큼 옮기는지도 확인 가능

            }

            override fun onPageSelected(position: Int) {

//                페이지 선택이 완료 되었을 때

//                바텀네비게이션바의 position 에 맞는 메뉴 클릭
                binding.mainBottomNavView.menu.getItem(position).isChecked = true

            }

            override fun onPageScrollStateChanged(state: Int) {

//                가만히 있다가 움직이기 시작하는 등의 상태 변경 이벤트

            }
        })

//        2. 바텀네비게이션 클릭 -> 뷰페이저 페이지 이동

        binding.mainBottomNavView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> binding.mainViewPager.currentItem = 0
                R.id.ranking -> binding.mainViewPager.currentItem = 1
                R.id.myProfile -> binding.mainViewPager.currentItem = 2
            }

            return@setOnItemSelectedListener true

        }

    }

    override fun setValues() {

        mvpa = MainViewPagerAdapter(supportFragmentManager)
        binding.mainViewPager.adapter = mvpa

//        뷰페이저가 3장의 프래그먼트를 계속 메모리에 유지시키게 하자
        binding.mainViewPager.offscreenPageLimit = 3

        binding.mainTabLayout.setupWithViewPager(binding.mainViewPager)

    }

}