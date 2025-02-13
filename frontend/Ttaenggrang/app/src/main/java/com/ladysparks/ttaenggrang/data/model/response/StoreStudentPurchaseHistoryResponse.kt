package com.ladysparks.ttaenggrang.data.model.response

data class StoreStudentPurchaseHistoryResponse(
    val id: Int,
    val itemId: Int,
    val quantity: Int,
    val itemName: String,
    val itemPrice: Int,
    val itemDescription: String
)
