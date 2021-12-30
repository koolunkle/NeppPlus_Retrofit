package com.neppplus.retrofitlibrarypractice.api

import android.content.Context
import com.neppplus.retrofitlibrarypractice.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
        fun getRetrofit(context: Context): Retrofit {
            if (retrofit == null) {

//                retrofit 변수에 추가 설정 진행

//                1. 토큰 자동 첨부 (retrofit 변수를 더 가공)

//                API 요청이 들어오면 -> 가로채서 헤더 추가 -> 그 뒤로 나머지 API 요청 진행

                val interceptor = Interceptor {

                    with(it) {
//                        헤더를 추가한 새 리퀘스트 생성
                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()
//                        새 리퀘스트로 나머지 동작 이어가기
                        proceed(newRequest)
                    }

                }

//                Retrofit은 OkHttp의 확장판 -> 클라이언트의 역할 : OkHttpClient 클래스 활용

//                커스텀 통신 클라이언트 제작 (API 요청에 헤더를 붙이는)

                val myClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    // 어느 서버로 접속?
                    .baseUrl(BASE_URL)
                    // 파싱을 자동 도구로 활용
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(myClient)
                    .build()
            }
            return retrofit!!
        }

    }

}