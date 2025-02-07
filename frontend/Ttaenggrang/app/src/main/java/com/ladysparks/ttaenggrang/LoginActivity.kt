package com.ladysparks.ttaenggrang

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ladysparks.ttaenggrang.base.ApplicationClass
import com.ladysparks.ttaenggrang.data.model.request.TeacherSignInRequest
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.ActivityLoginBinding
import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import com.ladysparks.ttaenggrang.util.showToast
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        autoLoginCheck()
        initEvent()

        // 비밀번호 표시.숨김 토글
        binding.btnInvisiblePassword.setOnClickListener{
            isPasswordVisible = !isPasswordVisible

            if(isPasswordVisible) {
                binding.editPasswordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnInvisiblePassword.setImageResource(R.drawable.ic_visible)
            } else {
                binding.editPasswordLogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnInvisiblePassword.setImageResource(R.drawable.ic_invisible)
            }
            //커서 위치 유지
            binding.editPasswordLogin.setSelection(binding.editPasswordLogin.text.length)
        }
    }

    private fun autoLoginCheck() {
        // 사용 자가 이전에 자동 로그인 항목을 선택한 경우, token 이 있을 때 한하여 자동로그인을 진행
        // 1. 자동 로그인 여부 확인 (SharedPreferencesUtil.AUTO_LOGIN)
        // 2. Token 확인 후, 값이 있을 겨우 로그인 진행

        if(true){
            // 자동 로그인으 선택한 경우
            // ..
        }
    }

    private fun initEvent() {
        binding.btnLogin.setOnClickListener {

            // 수정필요 : 현재 로그인 정보 하드코딩 상태
//            var user = TeacherSignInRequest(email = "test2@example.com", password = "1234")
            val email = binding.editIdLogin.text.toString().trim()
            val password = binding.editPasswordLogin.text.toString().trim()

            if(email.isEmpty()||password.isEmpty()) {
                showToast("모든 정보를 작성해주세요")
                return@setOnClickListener
            }

            val user = TeacherSignInRequest(email, password)


            lifecycleScope.launch {
                runCatching {
                    RetrofitUtil.authService.loginTeacher(user)
                }.onSuccess {
                    showToast("로그인 성공")

                    // Token 저장
                    val token = it.data!!.token
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.JWT_TOKEN_KEY, token)

                    // MainActivity 이동
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                }.onFailure {
                    showToast("로그인 실패")
                }
            }
        }
    }
}