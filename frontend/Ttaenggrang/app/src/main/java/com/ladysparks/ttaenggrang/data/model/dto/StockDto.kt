package com.ladysparks.ttaenggrang.data.model.dto

data class StockDto (
    val id: Long = 0L,
    val name: String,
    val pricePer: Int = 0,
    val totalQty: Int = 0,
    val remainQty: Int = 0,
    val description: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val changeRate: Int = 0,
    val weight: Double = 0.0,
    val teacherId: Long = 0L,
    val categoryId: Long = 0L,
    val categoryName: String
)