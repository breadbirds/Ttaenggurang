package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.data.model.dto.StockTransactionDto
import com.ladysparks.ttaenggrang.data.model.request.StockTransactionRequest
import com.ladysparks.ttaenggrang.data.model.response.ApiResponse
import com.ladysparks.ttaenggrang.data.model.response.StockOpenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StockService {

    // 주식 매도 (POST /stocks/{stockId}/sell)
    @POST("stocks/{stockId}/sell")
    suspend fun sellStock(
        @Path("stockId") stockId: Long,       // Path Parameter
        @Query("share_count") shareCount: Int, // Query Parameter
        @Query("studentId") studentId: Long   // Query Parameter
    ): ApiResponse<StockTransactionDto>

    // 주식장 열기 (교사)
    @POST("stocks/open")
    suspend fun openMarket(): ApiResponse<StockOpenResponse>

    // 주식 전체 조회 API
    @GET("stocks")
    suspend fun getAllStocks(): List<StockDto>

}