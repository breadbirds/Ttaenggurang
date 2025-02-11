package com.ladysparks.ttaenggrang.data.model.dto

data class StockTransactionDto(
    val id: Long = 0L,
    val shareCount: Int = 0,
    val transDate: String,
    val purchasePrc: Int = 0,
    val totalAmt: Int = 0,
    val returnAmt: Int = 0,
    val transType: TransType,
    val ownedQty: Int = 0,
    val studentId: Long = 0L,
    val stockId: Long = 0L
)

enum class TransType {
    BUY, SELL
}