package com.neppplus.retrofitlibrarypractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.datas.ProductData

class ProductRecyclerAdapter(val mContext: Context, val mList: List<ProductData>) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(row)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}