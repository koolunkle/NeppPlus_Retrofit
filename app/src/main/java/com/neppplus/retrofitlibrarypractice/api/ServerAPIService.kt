package com.neppplus.retrofitlibrarypractice.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServerAPIService {

    // 로그인 기능 명세
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
    )

}