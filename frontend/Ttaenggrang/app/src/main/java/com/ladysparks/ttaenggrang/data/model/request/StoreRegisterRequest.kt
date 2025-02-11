package com.ladysparks.ttaenggrang.data.model.request

data class StoreRegisterRequest(
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val price: Int,
    val quantity: Int
)
