package com.ladysparks.ttaenggrang.data.model.dto

data class AlarmDTO (
    val idx: Int, // 알람 번호
    val userId : String,
    val date: String, // 알람시간
    val content: String // 알람 내용
){
    constructor(userId: String, date: String, content: String): this(0, userId, date, content)
}
