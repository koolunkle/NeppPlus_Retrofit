package com.neppplus.retrofitlibrarypractice

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "e9740da87b9b5e177ba2d4c9ade1c02b")
    }

}