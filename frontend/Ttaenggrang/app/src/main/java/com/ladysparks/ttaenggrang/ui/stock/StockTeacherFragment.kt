package com.ladysparks.ttaenggrang.ui.stock

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentStockTeacherBinding
import java.util.Calendar

class StockTeacherFragment : BaseFragment<FragmentStockTeacherBinding>(
    FragmentStockTeacherBinding::bind,
    R.layout.fragment_stock_teacher
) {

    private var startTime: String = ""
    private var endTime: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 시작 시간 선택
        binding.startTime.setOnClickListener {
            showTimePickerDialog(isStartTime = true)
        }

        // 종료 시간 선택
        binding.endTime.setOnClickListener {
            showTimePickerDialog(isStartTime = false)
        }

//        // 스위치 설정
//        setupSwitchListener()
        initData()
    }

    private fun initData() {
        // 화면이 변경될 때 표시할 데이터/API 를 해당 함수에서 호출합니다.
    }

    // TimePickerDialog를 띄우는 함수
    private fun showTimePickerDialog(isStartTime: Boolean) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

                if (isStartTime) {
                    startTime = selectedTime
                    binding.startTime.setText("시작 시간: $startTime")
                } else {
                    endTime = selectedTime
                    binding.endTime.setText("종료 시간: $endTime")
                }
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }




//    private fun setupSwitchListener() {
//        binding.btnStockOpen.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // 스위치가 ON일 때 track, thumb이 selector 파일의 ON 상태를 따라감
//                binding.btnStockOpen.trackTintList = requireContext().getColorStateList(R.color.foundation_green_500)
//                binding.btnStockOpen.thumbTintList = requireContext().getColorStateList(R.color.white)
//            } else {
//                // 스위치가 OFF일 때 track, thumb이 selector 파일의 OFF 상태를 따라감
//                binding.btnStockOpen.trackTintList = requireContext().getColorStateList(R.color.backgroundGray)
//                binding.btnStockOpen.thumbTintList = requireContext().getColorStateList(R.color.black200)
//            }
//        }
//    }
}
