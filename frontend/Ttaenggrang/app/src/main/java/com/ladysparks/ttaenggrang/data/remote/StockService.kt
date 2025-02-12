package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.data.model.response.OpenMarketResponse
import com.ladysparks.ttaenggrang.data.model.response.StockTransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StockService {

    // 주식 매도
    @POST("stocks/{stockId}/sell")  // ✅ 경로 확인
    suspend fun sellStock(
        @Path("stockId") stockId: Int,
        @Query("share_count") shareCount: Int,
        @Query("studentId") studentId: Int
    ): Response<StockTransactionResponse>

    // 주식 매수
    @POST("stocks/{stockId}/buy")  // ✅ 경로 확인
    suspend fun buyStock(
        @Path("stockId") stockId: Long,
        @Query("share_count") shareCount: Int,
        @Query("studentId") studentId: Long
    ): Response<StockTransactionResponse>

    // 주식 전체 조회
    @GET("stocks")
    suspend fun getAllStocks(): List<StockDto>

    //주식장 열림(교사)
    @POST("stocks/manage")
    suspend fun setMarketStatus(
        @Query("openMarket") openMarket: Boolean // true 또는 false 값을 보냄
    ): Response<OpenMarketResponse>

    //주식장 열림 확인(학생)
    @GET("stocks/status")
    suspend fun getMarketStatus(): Response<OpenMarketResponse>

}