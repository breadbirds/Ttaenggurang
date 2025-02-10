package com.ladysparks.ttaenggrang.data.model.response

data class TeacherSignInResponse(
    val id: Int?,
    val email: String,
    val name: String,
    val school: String,
    val token: String
)