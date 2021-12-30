package com.neppplus.retrofitlibrarypractice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.retrofitlibrarypractice.databinding.ActivitySignUpBinding
import com.neppplus.retrofitlibrarypractice.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    var isDuplicateOk = false
    var isPasswordLengthOk = false
    var isPasswordSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        1. 비밀번호 타이핑 이벤트 -> 8글자 이상인지 아닌지 검사
//          => 검사 결과 txtPasswordCheckResult1 반영

        binding.edtPassword.addTextChangedListener {

            if (it.toString().length >= 8) {
                binding.txtPasswordCheckResult1.text = "사용해도 좋은 비밀번호입니다."
                isPasswordLengthOk = true
            } else {
                binding.txtPasswordCheckResult1.text = "8글자 이상으로 해주세요."
                isPasswordLengthOk = false
            }

            isPasswordSame = compareTwoPasswords()

        }

//        2. 비밀번호 확인 타이핑 이벤트 -> 첫 비밀번호 입력과 같은지?
//         => 검사 결과 txtPasswordCheckResult2 반영

        binding.edtPasswordRepeat.addTextChangedListener {
            isPasswordSame = compareTwoPasswords()
        }

//        3. 회원가입 API 호출 전, '비밀번호 8글자 이상' 및 '두 개 비밀번호 동일' 여부 모두 통과 해야, 서버에 호출
//         => 통과 못하면 이메일 중복검사처럼 토스트 안내 및 함수 강제 종료

        binding.edtEmail.addTextChangedListener {
//            Log.d("입력된내용", it.toString())

//            재검사 요청
            binding.txtEmailCheckResult.text = "이메일 중복검사를 해주세요."
            isDuplicateOk = false
        }

        binding.btnEmailCheck.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            apiService.getRequestDuplicatedCheck("EMAIL", email)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
//                        code : 200 -> 사용해도 좋은 이메일
                        if (response.isSuccessful) {
                            binding.txtEmailCheckResult.text = "사용해도 좋은 이메일 입니다."
                            isDuplicateOk = false
                        }
//                        사용하면 안되는 이메일
                        else {
                            binding.txtEmailCheckResult.text = "다른 이메일로 다시 검사해주세요."
                            isDuplicateOk = false
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })

        }

        binding.btnSignUp.setOnClickListener {

            if (!isDuplicateOk) {
                Toast.makeText(mContext, "이메일 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isPasswordSame) {
                Toast.makeText(mContext, "두개의 비밀번호는 서로 같아야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val nickname = binding.edtNickname.text.toString()

            apiService.putRequestSignUp(email, password, nickname).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Log.d("가입한사람토큰", br.data.token)

                        val signUpUserNickname = br.data.user.nickname
                        Toast.makeText(
                            mContext,
                            "${signUpUserNickname}님 가입을 축하합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    fun compareTwoPasswords(): Boolean {

        val originalPassword = binding.edtPassword.text.toString()
        val repeatPassword = binding.edtPasswordRepeat.text.toString()

        if (originalPassword == repeatPassword) {
            binding.txtPasswordCheckResult2.text = "사용해도 좋습니다."
            return true
        } else {
            binding.txtPasswordCheckResult2.text = "위의 비밀번호와 일치해야 합니다."
            return false
        }

    }

    override fun setValues() {

    }

}