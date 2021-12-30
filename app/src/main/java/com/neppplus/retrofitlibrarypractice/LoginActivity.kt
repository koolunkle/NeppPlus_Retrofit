package com.neppplus.retrofitlibrarypractice

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.kakao.sdk.user.UserApiClient
import com.neppplus.retrofitlibrarypractice.databinding.ActivityLoginBinding
import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.util.*

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnKakaoLogin.setOnClickListener {

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
//                카톡 앱이 깔려있는 상황
                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->
                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                    } else if (token != null) {
                        Log.e("카톡로그인", "로그인 성공")
                        Log.e("카톡로그인", token.accessToken)

                        getMyInfoFromKakao()
                    }
                }
            } else {
//                앱은 안깔려있는 상황
                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                    } else if (token != null) {
                        Log.e("카톡로그인", "로그인 성공")
                        Log.e("카톡로그인", token.accessToken)

                        getMyInfoFromKakao()
                    }
                }
            }

        }

        binding.btnFacebookLogin.setOnClickListener {

//            실제 페이스북 로그인 실행
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"))

        }

        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            apiService.postRequestLogin(inputEmail, inputPw).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
//                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()

//                        추가 파싱 -> 로그인한 사람의 닉네임 활용 "~님 환영합니다!" 토스트
                        val userNickname = basicResponse.data.user.nickname
                        Toast.makeText(mContext, "${userNickname}님, 환영합니다!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val errorJson = JSONObject(response.errorBody()!!.string())
                        Log.d("에러경우", errorJson.toString())

                        val message = errorJson.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    override fun setValues() {

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    Log.d("페북로그인", result!!.accessToken.token)

//                1차 로그인 결과로 받은 accessToken 활용
//                내 정보를 받아오는데 활용
//                페이스북 (graph) API 중 내 정보 가져오기 기능 요청

                    val graphApiRequest = GraphRequest.newMeRequest(
                        result.accessToken,
                        object : GraphRequest.GraphJSONObjectCallback {
                            override fun onCompleted(
                                jsonObj: JSONObject?,
                                response: GraphResponse?
                            ) {
                                Log.d("내정보요청", jsonObj.toString())

//                                서버(api.gudoc.in)에 소셜 로그인 성공 요청 호출
                                val name = jsonObj!!.getString("name")
                                val id = jsonObj.getString("id")

                                apiService.postRequestSocialLogin("facebook", id, name)
                                    .enqueue(object : Callback<BasicResponse> {
                                        override fun onResponse(
                                            call: Call<BasicResponse>,
                                            response: Response<BasicResponse>
                                        ) {
                                            if (response.isSuccessful) {
                                                val br = response.body()!!
                                                Toast.makeText(
                                                    mContext,
                                                    "${br.data.user.nickname}님, 환영합니다!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<BasicResponse>,
                                            t: Throwable
                                        ) {

                                        }
                                    })
                            }
                        })
                    graphApiRequest.executeAsync()
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }
            })

        getKeyHash()

    }

    fun getKeyHash() {

        val info = packageManager.getPackageInfo(
            "com.neppplus.retrofitlibrarytest_20211122",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getMyInfoFromKakao() {

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("카톡로그인", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "카톡로그인", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                )
            }
        }

    }

}