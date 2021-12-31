package com.neppplus.retrofitlibrarypractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.datas.ReviewData

class MainRecyclerAdapter(val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    2가지 ViewHolder 필요 -> 0번 칸 : 상단부(Header) xml / 나머지 칸(Item) : 리뷰 모양 xml

    inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {

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
            }
            is ItemViewHolder -> {
//                리뷰 아이템 바인딩
                holder.bind(mList[position - 1])
            }
        }

    }

    override fun getItemCount() = mList.size + 1

}