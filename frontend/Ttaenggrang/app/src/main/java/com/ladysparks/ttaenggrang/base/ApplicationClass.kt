package com.ladysparks.ttaenggrang.base

import android.app.Application
import android.util.Log
//import com.github.mikephil.charting.BuildConfig
import com.ladysparks.ttaenggrang.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        // SERVER_URL 참조 오류 발생할 경우, import 패키지 경로 확인해 볼 것.
        const val SERVER_URL = BuildConfig.SERVER_URL
        const val IMGS_URL = ""

        lateinit var retrofit: Retrofit

        // 🔥 SharedPreferencesUtil 싱글톤을 감싸서 전역으로 제공!
        val sharedPreferencesUtil: SharedPreferencesUtil
            get() = SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesUtil.init(this)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


        // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
        // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)  // ✅ 추가된 부분 (로그 확인용)
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor()).build()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        Log.d("TAG", "onCreate: 서버 주소 ${SERVER_URL}")
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        Log.d("TAG: Retrofit !", "onCreate: Retrofit 초기화 완료")
    }

    val gson : Gson = GsonBuilder()
        .setLenient()
        .setPrettyPrinting()  // JSON을 보기 좋게 출력
        .create()

}