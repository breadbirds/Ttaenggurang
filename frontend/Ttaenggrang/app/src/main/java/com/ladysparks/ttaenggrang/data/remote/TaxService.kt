package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.response.ApiResponse
import com.ladysparks.ttaenggrang.data.model.response.StudentTaxPaymentResponse
import com.ladysparks.ttaenggrang.data.model.response.TeacherTaxInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface TaxService {
    @GET("tax-payments/student")
    suspend fun getStudentTaxPayments(): ApiResponse<List<StudentTaxPaymentResponse>>

    @GET("taxes/")
    suspend fun getTeacherTaxInfo(): ApiResponse<List<TeacherTaxInfoResponse>>

    @GET("tax-payments/teacher")
    suspend fun getStudentTaxPaymentsTeacher(): ApiResponse<List<StudentTaxPaymentResponse>>
}