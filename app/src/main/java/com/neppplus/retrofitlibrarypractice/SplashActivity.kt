package com.neppplus.retrofitlibrarypractice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.messaging.FirebaseMessaging
import com.neppplus.retrofitlibrarypractice.databinding.ActivitySplashBinding
import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import com.neppplus.retrofitlibrarypractice.datas.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        푸시용 기기 토큰 발급 요청
        FirebaseMessaging.getInstance().token.addOnCompleteListener {

//            토큰을 잘 받아왔다면
            if (it.isSuccessful) {
                val deviceToken = it.result
                Log.d("FCM토큰", deviceToken!!)
            }

        }

        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                // 올바른 토큰일 때 -> loginUser 파싱해서 객체 대입
                if (response.isSuccessful) {
                    GlobalData.loginUser = response.body()!!.data.user
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent: Intent

            if (GlobalData.loginUser != null) {
                myIntent = Intent(mContext, MainActivity::class.java)
            } else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)

            finish()

        }, 1500)

    }

}