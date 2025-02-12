package com.ladysparks.ttaenggrang.data.model.response

data class NationInfoResponse(
    val treasuryIncome : Int,
    val averageStudentBalance : Double,
    val activeItemCount : Int,
    val classSavingsGoal : Int,
    val isPossible: Boolean? = null // 국가 정보가 있는지 여부를 보기 위한 확인용 변수
)