package com.ladysparks.ttaenggrang.data.remote

import com.ladysparks.ttaenggrang.data.model.dto.AlarmDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AlarmService {
    // POST 요청으로 Like 객체를 저장하고 성공 여부를 반환한다.
    @POST("rest/alarm")
    suspend fun saveAlarm(@Body alarm: AlarmDTO): Boolean
}

