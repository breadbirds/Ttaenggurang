package com.ladysparks.ttaenggrang.data.model.dto

import com.google.gson.annotations.SerializedName

data class StockDto(
    @SerializedName("id")
    val id: Long,  // int64 → Long

    @SerializedName("name")
    val name: String,

    @SerializedName("price_per")
    val pricePer: Int,  // int32 → Int

    @SerializedName("total_qty")
    val totalQty: Int,  // int32 → Int

    @SerializedName("remain_qty")
    val remainQty: Int,  // int32 → Int

    @SerializedName("description")
    val description: String?,

    @SerializedName("created_at")
    val createdAt: String,  // date-time → String (ISO 8601)

    @SerializedName("updated_at")
    val updatedAt: String,  // date-time → String (ISO 8601)

    @SerializedName("changeRate")
    val changeRate: Int,  // int32 → Int

    @SerializedName("isMarketActive")
    val isMarketActive: Boolean,

    @SerializedName("priceChangeTime")
    val priceChangeTime: String,  // date-time → String (ISO 8601)

    @SerializedName("weight")
    val weight: Double,  // number → Double

    @SerializedName("teacher_id")
    val teacherId: Long,  // int64 → Long

    @SerializedName("categoryId")
    val categoryId: Long,  // int64 → Long

    @SerializedName("categoryName")
    val categoryName: String
)
