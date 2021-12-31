package com.neppplus.retrofitlibrarypractice.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.neppplus.retrofitlibrarypractice.MainActivity
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.datas.BannerData
import com.neppplus.retrofitlibrarypractice.datas.ReviewData
import java.util.*
import kotlin.collections.ArrayList

class MainRecyclerAdapter(val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 화면 상단에 보여줄 배너 목록을 담고있는 ArrayList
    val mBannerList = ArrayList<BannerData>()

    // 상단 뷰페이저 어댑터 변수 -> 객체는 bind 에서 생성
    lateinit var bannerViewPagerAdapter: BannerViewPagerAdapter

//    2가지 ViewHolder 필요 -> 0번 칸 : 상단부(Header) xml / 나머지 칸(Item) : 리뷰 모양 xml

    inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgCategory1 = row.findViewById<ImageView>(R.id.imgCategory1)
        val bannerViewPager = row.findViewById<ViewPager>(R.id.bannerViewPager)

        fun bind() {

//            배너 페이지 어댑터를 생성
//            1. fm (FragmentManager) -> 화면 mContext (Context) -> MainActivity 변신 -> supportFragmentManager
//            2. bannerList -> Fragment 에서 -> 배너 목록 API 호출 -> 파싱된 것을 받아오자
            bannerViewPagerAdapter = BannerViewPagerAdapter(
                (mContext as MainActivity).supportFragmentManager,
                mBannerList
            )

            bannerViewPager.adapter = bannerViewPagerAdapter

//            완성된 배너 어댑터에 -> 2초마다 다음 그림으로 넘어가게 (ViewPager 에게 다음 페이지로) -> 할 일(코드) 생성

//            시작은 0 페이지에서
            var currentPage = 0

            val nextPage = {

//                다음쪽으로 페이지 수 증가
                currentPage++

//                증가후 검사 -> 3장짜리인데, 3번 칸으로 가게 하면? 범위 벗어나는 에러
                if (currentPage == mBannerList.size) {
//                    가야할 페이지가 3번 칸이라면 -> 처음으로 돌아가게 하자
                    currentPage = 0
                }

//                뷰페이저에 페이지 이동
                bannerViewPager.currentItem = currentPage

            } // Runnable : 할 일이 담긴 변수

//            타이머 안에서 할 일을 -> UI 스레드로 전달해주는 도구 (Handler)
            val myHandler = Handler(Looper.getMainLooper())

//            Timer 클래스 활용 -> 할 일(코드)을 2초마다 반복
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {

//                    반복 수행할 코드 -> UI 스레드가 아님 (UI 조작 / 앱 죽음)
//                    UI 스레드에다가 -> nextPage 에 적힌 할 일을 실행하도록 넘겨주자
                    myHandler.post(nextPage)

                }

            }, 2000, 2000)

            imgCategory1.setOnClickListener {
                Toast.makeText(mContext, "1번카테고리 눌림", Toast.LENGTH_SHORT).show()
            }
        }

    }

    inner class ItemViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val txtReviewProductName = row.findViewById<TextView>(R.id.txtReviewProductName)
        val txtReviewerName = row.findViewById<TextView>(R.id.txtReviewerName)

        fun bind(data: ReviewData) {
            txtReviewProductName.text = data.product.name
            txtReviewerName.text = data.user.nickname
        }

    }

    // position 별로 어떤 모양이 나가야 하는지 (viewType 어떻게 되는지) 알려줄 함수
    val HEADER_VIEW_TYPE = 1000
    val REVIEW_ITEM_TYPE = 1001

    override fun getItemViewType(position: Int): Int {

//        position 0 : 맨 윗칸 -> 상단 뷰
//        position 그 외 : 목록 표시 -> 리뷰 아이템
        return when (position) {
            0 -> HEADER_VIEW_TYPE
            else -> REVIEW_ITEM_TYPE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val row = LayoutInflater.from(mContext)
                    .inflate(R.layout.main_recycler_item_top_view, parent, false)
                HeaderViewHolder(row)
            }
            else -> {
//                리뷰 아이템
                val row = LayoutInflater.from(mContext)
                    .inflate(R.layout.main_recycler_item_review_item, parent, false)
                ItemViewHolder(row)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }
            is ItemViewHolder -> {
//                리뷰 아이템 바인딩
                holder.bind(mList[position - 1])
            }
        }

    }

    override fun getItemCount() = mList.size + 1

}