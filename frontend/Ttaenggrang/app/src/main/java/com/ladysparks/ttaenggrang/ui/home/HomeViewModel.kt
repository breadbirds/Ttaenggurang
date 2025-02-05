package com.ladysparks.ttaenggrang.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladysparks.ttaenggrang.data.model.dto.AlarmDto
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val _alarmList = MutableLiveData<List<AlarmDto>>() // LiveData를 활용
    val alarmList: LiveData<List<AlarmDto>> get() = _alarmList

    fun fetchAlarmList() {
        viewModelScope.launch {
            runCatching {
                // 서버로부터 알림 데이터 요청
                RetrofitUtil.alarmService.saveAlarm("김선생")
            }.onSuccess { data ->
//                _alarmList.value = data  // LiveData 업데이트 (UI 자동 반영)
            }.onFailure { exception ->
                Log.e("AlarmViewModel", "Error fetching alarms", exception)
            }
        }
    }
}