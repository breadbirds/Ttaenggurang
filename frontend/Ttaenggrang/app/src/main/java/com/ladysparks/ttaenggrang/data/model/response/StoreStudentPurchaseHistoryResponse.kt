package com.ladysparks.ttaenggrang.data.model.response

data class StoreStudentPurchaseHistoryResponse(
    val buyerId: Int,
    val buyerName: String,
    val createdAt: String,
    val id: Int,
    val itemId: Int,
    val quantity: Int
)
