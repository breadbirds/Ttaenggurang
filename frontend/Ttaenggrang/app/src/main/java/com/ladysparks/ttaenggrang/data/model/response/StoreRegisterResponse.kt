package com.ladysparks.ttaenggrang.data.model.response

data class StoreRegisterResponse(
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val price: Int,
    val quantity: Int
)
