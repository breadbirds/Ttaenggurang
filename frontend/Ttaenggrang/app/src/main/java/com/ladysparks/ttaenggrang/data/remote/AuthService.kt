package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.request.TeacherSignInRequest
import com.ladysparks.ttaenggrang.data.model.request.TeacherSignUpRequest
import com.ladysparks.ttaenggrang.data.model.response.ApiResponse
import com.ladysparks.ttaenggrang.data.model.response.TeacherSignInResponse
import com.ladysparks.ttaenggrang.data.model.response.TeacherSignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("teachers/signup")
    suspend fun signupTeacher(@Body teacherName: TeacherSignUpRequest): TeacherSignUpResponse

    @POST("teachers/login")
    suspend fun loginTeacher(@Body teacherInfo: TeacherSignInRequest): ApiResponse<TeacherSignInResponse>
}