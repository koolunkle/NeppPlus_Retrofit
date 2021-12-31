package com.neppplus.retrofitlibrarypractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.retrofitlibrarypractice.R
import com.neppplus.retrofitlibrarypractice.adapters.MainRecyclerAdapter
import com.neppplus.retrofitlibrarypractice.databinding.FragmentRecyclerviewPracticeBinding
import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import com.neppplus.retrofitlibrarypractice.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewPracticeFragment : BaseFragment() {

    lateinit var binding: FragmentRecyclerviewPracticeBinding

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mMainRecyclerAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recyclerview_practice,
            container,
            false
        )
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

        mMainRecyclerAdapter = MainRecyclerAdapter(mContext, mReviewList)
        binding.mainRecyclerView.adapter = mMainRecyclerAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getReviewListFromServer() {

        apiService.getRequestReview().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                    mMainRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }

}