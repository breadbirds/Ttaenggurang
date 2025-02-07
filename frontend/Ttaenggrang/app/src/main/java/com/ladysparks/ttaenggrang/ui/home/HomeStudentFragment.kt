package com.ladysparks.ttaenggrang.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentHomeStudentBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStudentsBinding
import com.ladysparks.ttaenggrang.ui.component.PieChartComponent


class HomeStudentFragment : BaseFragment<FragmentHomeStudentBinding>(FragmentHomeStudentBinding::bind, R.layout.fragment_home_student) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupGoalAchievementChart()
        setupTotalAssetsChart()

    }

    private fun setupGoalAchievementChart(){
        val pieChart = binding.pieChartGoal
        val entries = listOf(
            PieEntry(65f), // ✅ 실제 값
            PieEntry(35f)  // ✅ 나머지 회색 부분
        )

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.chartYellow), // ✅ 노란색
                ContextCompat.getColor(requireContext(), R.color.chartGray)    // ✅ 회색
            )
            valueTextSize = 0f  // ✅ 내부 값 숨기기
            setDrawValues(false) // ✅ 값 텍스트 안 보이게 설정
        }

        val pieData = PieData(dataSet)

        pieChart.apply {
            data = pieData
            description.isEnabled = false
            setUsePercentValues(true) // ✅ 퍼센트 값 사용
            setDrawEntryLabels(false) // ✅ 내부 Label 숨기기
            isDrawHoleEnabled = true // ✅ 도넛 모양 만들기
            holeRadius = 60f // ✅ 도넛 크기 조절
//            transparentCircleRadius = 80f // 투명 원 크기 조절

            setHoleColor(Color.WHITE) // ✅ 중앙 배경색 (회색으로 변경 가능)

            // ✅ 중앙 텍스트 추가
            centerText = "65%"
            setCenterTextSize(16f) // ✅ 중앙 텍스트 크기
            setCenterTextColor(Color.BLACK) // ✅ 중앙 텍스트 색상

            // 바깥 기본 여백을 없애기 위해 음수값 사용
            setExtraOffsets(-10f, -10f, -10f, -10f)

            legend.isEnabled = false

            animateY(1000)
        }
    }

    private fun setupTotalAssetsChart() {
        val pieChartComponent = PieChartComponent(requireContext(), binding.pieChart)

        // 데이터 설정
        val dataList = listOf(
            35f to "급여",
            50f to "투자",
            15f to "저축"
        )

        // 색상 설정
        val colorList = listOf(
            R.color.chartBlue,
            R.color.chartPink,
            R.color.chartPurple
        )

        // 동적 데이터 전달하여 차트 그리기
        pieChartComponent.setupPieChart(dataList, colorList)
    }


}