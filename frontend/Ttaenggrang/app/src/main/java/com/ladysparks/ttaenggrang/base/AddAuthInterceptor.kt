package com.ladysparks.ttaenggrang.base

import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddAuthInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

//         로그인 요청일 경우 `Authorization` 헤더 추가하지 않음
        if (request.url.encodedPath.contains("/teachers/login") ||
            request.url.encodedPath.contains("/teachers/signup")) {
            return chain.proceed(request)
        }

        val builder = request.newBuilder()

        val token = SharedPreferencesUtil.getValue(SharedPreferencesUtil.JWT_TOKEN_KEY, "")
        if (token.isNotEmpty()) {
            builder.addHeader("Authorization", "Bearer $token")  // ✅ JWT 토큰 추가
        }
        return chain.proceed(builder.build())
    }
}