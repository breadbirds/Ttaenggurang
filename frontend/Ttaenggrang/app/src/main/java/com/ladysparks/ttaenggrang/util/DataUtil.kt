package com.ladysparks.ttaenggrang.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Date, String, Long, Calendar 형식의 데이터를 원하는 포맷(yyyy.MM.dd) 에 맞게 변환
 * - 3자리 마다 ',' 를 찍어 한국식 화폐 단위로 구분 하는 유틸
 *
 * 사용 방법 : 함수 호출 후, 괄호 안에 변환할 타입의 데이터를 넣어줍니다.
 * 함수는 크게 2가지로 구분 됩니다. 반환값이 TimeStamp 인 경우와, StringFormat 인 경우
 *
 * - 1. 반환값으로 TimeStamp 를 받고싶은 경우 (주로 서버에 Date 타입의 정보를 저장할 때 사용)
 * DateUtil.formatToTimestamp(...)
 *
 * - 2. 반환값으로 StringFormat  을 받고 싶은 경우 (주로 UI 에 표현될 때 사용)
 * DateUtil.formatDate(...)
 */
object DataUtil {

    private const val DATE_FORMAT = "yyyy.MM.dd"

    // String (yyyy.MM.dd) → Long (Timestamp) 변환
    fun formatToTimestamp(dateString: String): Long? {
        return try {
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val date = formatter.parse(dateString)
            date?.time // Date를 Timestamp(Long)으로 변환
        } catch (e: Exception) {
            null
        }
    }

    //  Date → Long (Timestamp) 변환
    fun formatToTimestamp(date: Date): Long {
        return date.time
    }

    // Calendar → Long (Timestamp) 변환
    fun formatToTimestamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }


    // String to Date
    fun formatDate(dateString: String): Date? {
        return try {
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: Exception) {
            null  // 변환 실패 시 null 반환
        }
    }

    // Long(Timestamp) to String : 주로 서버에서 받은 DateFormat 을 변환할 때 사용될 예정
    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        return formatDate(date)
    }

    // Date to String
    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    // Calendar to String
    fun formatDate(calendar: Calendar): String {
        return formatDate(calendar.time)
    }

}