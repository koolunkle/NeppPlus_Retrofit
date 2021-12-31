package com.neppplus.retrofitlibrarypractice.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    2가지 ViewHolder 필요 -> 0번 칸 : 상단부(Header) xml / 나머지 칸(Item) : 리뷰 모양 xml

    inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }

    inner class ItemViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }

}