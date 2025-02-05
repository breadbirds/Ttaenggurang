package com.ladysparks.ttaenggrang.util

import android.content.Context
import android.content.SharedPreferences

/**
 * [사용 방법]
 * - 다양한 데이터 유형 저장/관리를 지원하기 위하 '오버로딩' 방식 사용하여 구현
 * - String, Int, boolean, float, long 지원
 *
 * 저장
 * SharedPreferencesUtil.putValue(context, "user_name", "Alice")
 * SharedPreferencesUtil.putValue(context, "user_age", 25)
 *
 * 데이터 확인
 * - getValue(context, 이름, 값이 없을 경우 보여질 기본 값) 구조
 * val name = SharedPreferencesUtil.getValue(context, "user_name", "Unknown")
 * val age = SharedPreferencesUtil.getValue(context, "user_age", 0)
 *
 * 삭제
 * - 삭제는 반환 값 true/false 제공
 * - 만약 삭제할 데이터(key) 존재하지 않을 경우 false 를 반환한다.gi
 * val isRemoved = SharedPreferencesUtil.removeValue(context, "user_age")
 *
 * 초기화
 * SharedPreferencesUtil.clearAll(context)
 */
object SharedPreferencesUtil {
    private const val PREFS_NAME = "app_prefs"
    val COOKIES_KEY_NAME = "cookies"

    private lateinit var preferences: SharedPreferences

    // 🚀 초기화 함수 추가 (ApplicationClass에서 초기화할 예정)
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Cookies 관련 함수 추가 (이전 프로젝트와 동일)
     */
    fun addUserCookie(cookies: HashSet<String>) {
        preferences.edit().putStringSet(COOKIES_KEY_NAME, cookies).apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }

    fun deleteUserCookie() {
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }


    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 값 저장 (오버로딩 적용)
     */
    fun putValue(context: Context, key: String, value: String) {
        getPreferences(context).edit().putString(key, value).apply()
    }

    fun putValue(context: Context, key: String, value: Int) {
        getPreferences(context).edit().putInt(key, value).apply()
    }

    fun putValue(context: Context, key: String, value: Boolean) {
        getPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun putValue(context: Context, key: String, value: Float) {
        getPreferences(context).edit().putFloat(key, value).apply()
    }

    fun putValue(context: Context, key: String, value: Long) {
        getPreferences(context).edit().putLong(key, value).apply()
    }

    /**
     * 값 가져오기 (오버로딩 적용, 기본값 설정)
     */
    fun getValue(context: Context, key: String, defaultValue: String = ""): String {
        return getPreferences(context).getString(key, defaultValue) ?: defaultValue
    }

    fun getValue(context: Context, key: String, defaultValue: Int = 0): Int {
        return getPreferences(context).getInt(key, defaultValue)
    }

    fun getValue(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getPreferences(context).getBoolean(key, defaultValue)
    }

    fun getValue(context: Context, key: String, defaultValue: Float = 0f): Float {
        return getPreferences(context).getFloat(key, defaultValue)
    }

    fun getValue(context: Context, key: String, defaultValue: Long = 0L): Long {
        return getPreferences(context).getLong(key, defaultValue)
    }

    /**
     * 특정 값 삭제
     */
    fun removeValue(context: Context, key: String): Boolean {
        val prefs = getPreferences(context)
        return if (prefs.contains(key)) {  // 키가 존재하는지 확인
            prefs.edit().remove(key).apply()
            true  // 성공적으로 삭제됨
        } else {
            false // 해당 키가 존재하지 않음
        }
    }

    /**
     * SharedPreferences 전체 초기화
     */
    fun clearAll(context: Context) {
        getPreferences(context).edit().clear().apply()
    }


}