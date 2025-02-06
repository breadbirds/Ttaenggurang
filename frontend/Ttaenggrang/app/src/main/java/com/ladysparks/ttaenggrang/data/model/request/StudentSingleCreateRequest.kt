package com.ladysparks.ttaenggrang.data.model.request

data class StudentSingleCreateRequest(
    val username: String,
    val password: String,
    val profileImage: String
)