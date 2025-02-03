package com.ladysparks.ttaenggrang.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.data.model.dto.AlarmDTO
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.FragmentHomeTeacherBinding
import kotlinx.coroutines.launch


//class HomeFragment : Fragment() {
class HomeTeacherFragment : BaseFragment<FragmentHomeTeacherBinding>(FragmentHomeTeacherBinding::bind, R.layout.fragment_home_teacher) {

    private lateinit var alarmAdapter: AlarmAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기화 기타 기능 작성
        initAdapter()
        fetchAlarmList()
        initData()
        initEvent()
    }


    private fun initAdapter() {
        alarmAdapter = AlarmAdapter(arrayListOf())

        binding.recyclerAlarm.apply {
            layoutManager = LinearLayoutManager(context) // 세로 리스트
            adapter = alarmAdapter
        }
    }

    private fun fetchAlarmList() {
        lifecycleScope.launch {
            runCatching {
                // 서버로부터 알림 데이터 요청 (Retrofit 호출)
//                val U_ID = ApplicationClass.sharedPreferencesUtil.getUser().id
                RetrofitUtil.alarmService.saveAlarm(AlarmDTO("id", "date", "Content"))
            }.onSuccess { tf ->

                // UI 업데이트
                alarmAdapter.updateData(tf)
                binding.recyclerAlarm.visibility = View.VISIBLE
            }.onFailure { exception ->
                // Error

            }
        }

    }


    private fun initData() {
        // 화면이 변경될 때 표시할 데이터/API 를 해당 함수에서 호출합니다.
    }

    private fun initEvent() {
        binding.btnAlarmMore.setOnClickListener {
            // 다이얼로그 생성
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_alert_board)

            // 다이얼로그 내부 UI 가져오기
            val tableLayout = dialog.findViewById<TableLayout>(R.id.tableLayout)
            val btnClose = dialog.findViewById<Button>(R.id.btn_close)

            // 예제 데이터 추가
            val sampleData = listOf(
                listOf("1", "10:30", "업데이트", "새로운 기능이 추가됨"),
                listOf("2", "11:00", "공지", "서버 점검 예정"),
                listOf("3", "12:15", "이벤트", "할인 이벤트 진행 중")
            )


            // 닫기 버튼 이벤트
            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            // 다이얼로그 배경 투명 설정 및 크기 조절
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()
        }
    }


}