package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.request.TeacherSignUpRequest
import com.ladysparks.ttaenggrang.data.model.response.TeacherSignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/teachers/signup")
    suspend fun signupTeacher(@Body teacherName: TeacherSignUpRequest): TeacherSignUpResponse
}