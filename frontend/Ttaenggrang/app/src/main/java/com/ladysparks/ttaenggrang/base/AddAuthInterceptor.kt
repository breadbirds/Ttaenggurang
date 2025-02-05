package com.ladysparks.ttaenggrang.base

import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddAuthInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val token = SharedPreferencesUtil.getValue(SharedPreferencesUtil.JWT_TOKEN_KEY, "")
        if (token.isNotEmpty()) {
            builder.addHeader("Authorization", "Bearer $token")  // ✅ JWT 토큰 추가
        }
        return chain.proceed(builder.build())
    }
}