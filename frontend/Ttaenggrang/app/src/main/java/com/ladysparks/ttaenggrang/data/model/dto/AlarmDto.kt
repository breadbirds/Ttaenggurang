package com.ladysparks.ttaenggrang.data.model.dto

import java.util.Date


//data class AlarmDTO (
//    val idx: Int, // 알람 번호
//    val title : String,
//    val content: String, // 알람시간
//    val date: Long // 알람 내용
//){
//    constructor(idx: Int, title: String, content: String, publisher: String, date: Long): this(0, title, content, publish)
//}

data class AlarmDto (
    val idx: Int=0,
    val title : String,
    val content: String,
    val publisher: String,
    val date: Long = Date().time
)