package com.neppplus.retrofitlibrarypractice.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.retrofitlibrarypractice.EditReviewActivity
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.datas.ProductData

class ProductRecyclerAdapter(val mContext: Context, val mList: List<ProductData>) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)
        val imgStoreLogo = view.findViewById<ImageView>(R.id.imgStoreLogo)
        val imgProductImg = view.findViewById<ImageView>(R.id.imgProductImg)
        val txtStoreName = view.findViewById<TextView>(R.id.txtStoreName)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtProductPrice = view.findViewById<TextView>(R.id.txtProductPrice)
        val btnWriteReview = view.findViewById<Button>(R.id.btnWriteReview)

        fun bind(data: ProductData) {

            Glide.with(mContext).load(data.store.logoURL).into(imgStoreLogo)
            Glide.with(mContext).load(data.imageURL).into(imgProductImg)
            txtStoreName.text = data.store.name
            txtProductName.text = data.name
            // 39800 -> 39,800 형태로 가공 (상품 데이터에 가격을 가공해주는 함수 추가)
            txtProductPrice.text = data.getFormattedPrice()

            rootLayout.setOnClickListener {
                Toast.makeText(mContext, "${data.name} 상품 클릭됨", Toast.LENGTH_SHORT).show()
            }

            rootLayout.setOnLongClickListener {

                val alert = AlertDialog.Builder(mContext)
                alert.setTitle("상품 삭제")
                alert.setMessage("정말 해당 상품을 삭제하겠습니까?")
                alert.setPositiveButton("확인", null)
                alert.setNegativeButton("취소", null)
                alert.show()

                return@setOnLongClickListener true

            }

            btnWriteReview.setOnClickListener {

//                리뷰 작성 화면 이동
                val myIntent = Intent(mContext, EditReviewActivity::class.java)
                myIntent.putExtra("product", data)
                mContext.startActivity(myIntent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(row)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}