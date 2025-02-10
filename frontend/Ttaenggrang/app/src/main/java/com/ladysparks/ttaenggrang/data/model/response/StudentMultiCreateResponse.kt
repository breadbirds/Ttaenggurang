package com.ladysparks.ttaenggrang.data.model.response

data class StudentMultiCreateResponse<T>(
    val id: Int,
    val username: String,
    val profileImage: Any,
    val teacher: Teacher,
    val bankAccount: BankAccountResponse,
    val token: String,
)