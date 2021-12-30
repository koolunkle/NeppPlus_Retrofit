package com.neppplus.retrofitlibrarypractice.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    // Retrofit 타입 객체 하나만 생성 -> 모두가 공유
    companion object {

        // 기본 접속 서버
        private var BASE_URL = "https://api.gudoc.in"

        // 앱이 처음 켜질 때는 없다 -> 한번만 만들고 함수를 통해서 공유
        private var retrofit: Retrofit? = null

        // 통신 담당 객체를 만들지 않았다면 -> 없을 때만 새로 한번 만들자
        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    // 어느 서버로 접속?
                    .baseUrl(BASE_URL)
                    // 파싱을 자동 도구로 활용
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    }

}