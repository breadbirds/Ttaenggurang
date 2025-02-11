package com.ladysparks.ttaenggrang.data.model.dto

data class StockDto(
    val categoryId: Int,
    val categoryName: String,
    val changeRate: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val isMarketActive: Boolean,
    val name: String,
    val priceChangeTime: String,
    val price_per: Int,
    val remain_qty: Int,
    val teacher_id: Int,
    val total_qty: Int,
    val updated_at: String,
    val weight: Int
)