package com.neppplus.retrofitlibrarypractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.databinding.FragmentReviewListBinding
import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import com.neppplus.retrofitlibrarypractice.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListFragment : BaseFragment() {

    lateinit var binding: FragmentReviewListBinding

    val mReviewList = ArrayList<ReviewData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        getReviewListFromServer()

    }

    fun getReviewListFromServer() {

        apiService.getRequestReview().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }

}