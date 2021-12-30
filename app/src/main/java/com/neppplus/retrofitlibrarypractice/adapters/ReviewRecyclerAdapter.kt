package com.neppplus.retrofitlibrarypractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.datas.ReviewData

class ReviewRecyclerAdapter(
    val mContext: Context,
    val mList: List<ReviewData>
) : RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, parent, false)
        return ReviewViewHolder(row)

    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}