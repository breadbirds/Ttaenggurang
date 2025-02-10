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
import com.ladysparks.ttaenggrang.base.BaseActivity
import com.ladysparks.ttaenggrang.data.model.request.StudentSignInRequest
import com.ladysparks.ttaenggrang.data.model.request.TeacherSignInRequest
import com.ladysparks.ttaenggrang.data.model.response.StudentSignInResponse
import com.ladysparks.ttaenggrang.data.model.response.TeacherSignInResponse
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.ActivityLoginBinding
import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import com.ladysparks.ttaenggrang.util.showToast
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tempEvent()
        initEvent()

        // 비밀번호 표시.숨김 토글
        binding.btnInvisiblePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                binding.editPasswordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnInvisiblePassword.setImageResource(R.drawable.ic_visible)
            } else {
                binding.editPasswordLogin.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnInvisiblePassword.setImageResource(R.drawable.ic_invisible)
            }
            //커서 위치 유지
            binding.editPasswordLogin.setSelection(binding.editPasswordLogin.text.length)
        }
    }

    /**
     * 로그인안될 경우
     * - 인터넷 확인
     * - aa@aa.com 이라는 이멜을 스웨거에서 로그인 테스트
     * - DB 초기화되었는지 체크
     */
    private fun tempEvent() {
        binding.tempBtnTeacher.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    RetrofitUtil.authService.loginTeacher(TeacherSignInRequest(email = "aa@aa.com", password = "1234"))
                }.onSuccess {
                    showToast("교사 로그인 성공")

                    // Token 저장
                    val token = when(val userData = it.data){
                        is TeacherSignInResponse -> userData.token
                        else -> ""
                    }
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.JWT_TOKEN_KEY, token)
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.IS_TEACHER, true)

                    // MainActivity 이동
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }.onFailure { error ->
                    showToast("로그인 실패 ${error}")
                }
            }
        }

        binding.tempBtnStudent.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    RetrofitUtil.authService.loginStudent(StudentSignInRequest(username = "서미지", password = "1234"))
                }.onSuccess {
                    showToast("학생 로그인 성공")

                    // Token 저장
                    val token = when(val userData = it.data){
                        is StudentSignInResponse -> userData.token
                        else -> ""
                    }
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.JWT_TOKEN_KEY, token)

                    // 교사, 학생 여부 저장
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.IS_TEACHER, false)

                    // MainActivity 이동
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }.onFailure { error ->
                    showToast("로그인 실패 ${error}")
                }
            }
        }
    }


    private fun initEvent() {
        // 회원 가입 페이지 이동
        binding.textLoginSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        // 로그인
        binding.btnLogin.setOnClickListener {
            // 이메일 형식 로그인일 경우 교사 : 상단 체크박스를 안누른 경우 대비 (시스템에서 대신 활성화 시켜줌)
            if(binding.editIdLogin.text.toString().contains("@")){
                binding.checkBoxAgree.isChecked = true
            }

            // 수정 필요 : 현재 하드코딩으로 체크박스 유무에 따라 학생, 선생님 로그인 다름
            lifecycleScope.launch {
                runCatching {
                    if (binding.checkBoxAgree.isChecked) {
                        showToast("교사 로그인 시도")
                        var user = TeacherSignInRequest(email = binding.editIdLogin.text.toString(), password = binding.editPasswordLogin.text.toString())
                        RetrofitUtil.authService.loginTeacher(user)
                    } else {
                        showToast("학생 로그인 시도")
                        var user = StudentSignInRequest(username = binding.editIdLogin.text.toString(), password = binding.editPasswordLogin.text.toString())
                        RetrofitUtil.authService.loginStudent(user)
                    }
                }.onSuccess {
                    showToast("로그인 성공")

//                     Token 저장
                    val token = when(val userData = it.data){
                        is StudentSignInResponse -> userData.token
                        is TeacherSignInResponse -> userData.token
                        else -> ""
                    }
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.JWT_TOKEN_KEY, token)

                    // 교사, 학생 여부 저장
                    SharedPreferencesUtil.putValue(SharedPreferencesUtil.IS_TEACHER, binding.checkBoxAgree.isChecked)

                    // MainActivity 이동
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }.onFailure { error ->
                    showToast("로그인 실패 ${error}")
                }
            }


        }
    }
}