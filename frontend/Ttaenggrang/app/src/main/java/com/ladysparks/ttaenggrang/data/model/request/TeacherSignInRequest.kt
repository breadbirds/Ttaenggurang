package com.ladysparks.ttaenggrang.data.model.request

data class TeacherSignInRequest(
    val email: String? = null,
    val password: String? = null,
    val token: String? = null
) {
    // 유효성 검사 함수
    /**
     * 유효성 검사 함수
     * (email + pw) 로그인 방식인지, Token 로그인 방식인지 판단
     */
    fun isValidRequest(): Boolean {
        return (email != null && password != null) || (token != null)
    }
}