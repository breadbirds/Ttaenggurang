package com.ladysparks.ttaenggrang

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ladysparks.ttaenggrang.data.model.request.TeacherSignUpRequest
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.ActivitySignupBinding
import com.ladysparks.ttaenggrang.util.showToast
import kotlinx.coroutines.launch
import java.util.Date

class SignupActivity : AppCompatActivity() {

    // binding
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    private var isPasswordVisible = false
    private var isPasswordCheckVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // btnSignup
        binding.btnSignup.setOnClickListener {
            signUp()
        }

        // 비밀번호 표시.숨김 토글
        binding.btnInvisiblePasswordSignup.setOnClickListener{
            isPasswordVisible = !isPasswordVisible

            if(isPasswordVisible) {
                binding.editPasswordSignup.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnInvisiblePasswordSignup.setImageResource(R.drawable.ic_visible)
            } else {
                binding.editPasswordSignup.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnInvisiblePasswordSignup.setImageResource(R.drawable.ic_invisible)
            }
            //커서 위치 유지
            binding.editPasswordSignup.setSelection(binding.editPasswordSignup.text.length)
        }

        // 비밀번호 2차 표시/숨김 토글
        binding.btnInvisiblePasswordcheckSignup.setOnClickListener{
            isPasswordCheckVisible = !isPasswordCheckVisible

            if(isPasswordCheckVisible) {
                binding.editPasswordcheckSignup.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnInvisiblePasswordcheckSignup.setImageResource(R.drawable.ic_visible)
            } else {
                binding.editPasswordcheckSignup.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnInvisiblePasswordcheckSignup.setImageResource(R.drawable.ic_invisible)
            }
            //커서 위치 유지
            binding.editPasswordcheckSignup.setSelection(binding.editPasswordcheckSignup.text.length)
        }

    }



    private fun signUp() {
        // Test 위한 하드코딩 데이터
        val user = TeacherSignUpRequest(
            id = 0,
            email = "test2@example.com",
            password1 = "1234",
            password2 = "1234",
            name = "test3",
            school = "SSAFY University3",
            createdAt = Date().time
        )


        // 1. input Data가져오기
        var email = binding.editEmailSignup.text
        // ...

        // 2. TeacherSignUpRequest 객체 생성
        // val user = TeacherSignUpRequest(0, email, ...)

        lifecycleScope.launch {
            runCatching {
                // 서버로부터 알림 데이터 요청
                RetrofitUtil.authService.signupTeacher(user)
            }.onSuccess { data ->
                showToast("회원 가입 완료 ! ${data}")
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            }.onFailure { exception ->
                showToast("회원가입 실패 ! ${exception}")
                Log.e("AlarmViewModel", "Error fetching alarms ${exception}")
            }
        }

    }


}
