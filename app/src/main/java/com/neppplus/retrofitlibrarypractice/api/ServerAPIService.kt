package com.neppplus.retrofitlibrarypractice.api

import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {

    // 로그인 기능 명세
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
    ): Call<BasicResponse>

    // 회원가입 기능
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
    ): Call<BasicResponse>

    // 중복 확인 기능  - GET
    @GET("/user/check")
    fun getRequestDuplicatedCheck(
        @Query("type") type: String,
        @Query("value") value: String,
    ): Call<BasicResponse>

    // 소셜 로그인 기능 - POST
    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") uid: String,
        @Field("nick_name") name: String,
    ): Call<BasicResponse>

    // 내 정보 조회 - GET / 토큰 값 (임시 방안)
    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

    // 상품 목록 받아오기 - GET / 아무 파라미터도 없음 (서버의 임시 API)
    @GET("/product")
    fun getRequestProductList(): Call<BasicResponse>

    // 소분류 전체 목록 받아오기 - GET / 아무 파라미터도 없음 (서버의 임시 API)
    @GET("/category/small")
    fun getRequestSmallCategoryList(): Call<BasicResponse>

    // 닉네임 변경
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    // 리뷰 작성
    /* @FormUrlEncoded
     @POST("/review")
     fun postRequestReview(
         @Field("product_id") productId: Int,
         @Field("title") title: String,
         @Field("content") content: String,
         @Field("score") rating: Int
     ): Call<BasicResponse> */

    // 리뷰 작성
    @Multipart
    @POST("/review")
    fun postRequestReview(
        @PartMap normalParams: HashMap<String, RequestBody>, // id, 제목, 내용 등 일반 문구 파라미터를 담을 공간
        @Part img: MultipartBody.Part, // 이미지를 별도로 첨부할 변수
    ): Call<BasicResponse>

    // 전체 리뷰 목록 (임시)
    @GET("/review")
    fun getRequestReview(): Call<BasicResponse>

    // 프로필 사진 첨부 -> Field 대신 Multipart 활용
    @Multipart
    @PUT("/user/image")
    fun putRequestProfileImg(
        @Part img: MultipartBody.Part
    ): Call<BasicResponse>

    // 메인화면 배너 목록
    @GET("/main/banner")
    fun getRequestMainBanner(): Call<BasicResponse>

    // 대분류 (1번) -> 소분류 목록 (Path Segment)
    @GET("/category/{large_category_id}")
    fun getRequestSmallCategoryInLargeCategory(
        @Path("large_category_id") id: Int,
    ): Call<BasicResponse>

}