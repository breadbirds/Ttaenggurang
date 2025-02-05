package com.ladysparks.ttaenggrang

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // btnSignup
        binding.btnSignup.setOnClickListener {
            signUp()
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
