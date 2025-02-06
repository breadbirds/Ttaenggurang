package com.ladysparks.ttaenggrang.ui.students

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.base.BaseTableAdapter
import com.ladysparks.ttaenggrang.databinding.FragmentHomeTeacherBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStudentsBinding
import com.ladysparks.ttaenggrang.ui.home.HomeViewModel
import com.ladysparks.ttaenggrang.ui.model.BaseTableRowModel
import com.ladysparks.ttaenggrang.util.showToast

class StudentsFragment  : BaseFragment<FragmentStudentsBinding>(FragmentStudentsBinding::bind, R.layout.fragment_students) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initDat()

        sampleDataStudent()
        binding.recyclerStudents.visibility = View.VISIBLE

    }


    private fun initDat() {

    }

    private fun initEvent() {
        binding.btnTabStudentInfo.setOnClickListener {
            //
            selectTab(true)
            // if 조건 추가. 데이터가 없으면 recyclerview 가리고, 텍스트만 보이도록
            binding.recyclerStudents.visibility = View.VISIBLE
            sampleDataStudent()
        }

        binding.btnTabFincialStatus.setOnClickListener {
            // if 조건 추가. 데이터가 없으면 recyclerview 가리고, 텍스트만 보이도록
            binding.recyclerStudents.visibility = View.VISIBLE
            selectTab(false)
            sampleDataFinance()
        }
    }

    private fun sampleDataStudent() {

        // 컬럼 개수가 5개 일 때 사용 방법
        val header2 = listOf("번호", "이름", "직업(월급)", "아이디", "비밀번호", "설정")
        val data2 = listOf(
            BaseTableRowModel(listOf("박지성", "user04", "축구선수", "5000만원", "2억", "관리")),
            BaseTableRowModel(listOf("손흥민", "user05", "축구선수", "7억원", "10억", "관리"))
        )

        // 컬럼 개수가 5개인 테이블
        val adapter2 = BaseTableAdapter(header2, data2)
        binding.recyclerStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerStudents.adapter = adapter2
    }

    private fun sampleDataFinance() {
        // 컬럼 개수가 5개 일 때 사용 방법
        val header2 = listOf("번호", "이름", "계좌 잔액", "은행 가입상품", "보유 주식")
        val data2 = listOf(
            BaseTableRowModel(listOf("박지성", "user04", "34,000", "3개", "1개")),
            BaseTableRowModel(listOf("손흥민", "user05", "23,999", "1개", "2개"))
        )

        // 컬럼 개수가 5개인 테이블
        val adapter2 = BaseTableAdapter(header2, data2)
        binding.recyclerStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerStudents.adapter = adapter2
    }

    // Tab 변경 이벤트
    private fun selectTab(isStudentInfo: Boolean) {
        val context = context ?: return

        if (isStudentInfo) {
            // "학생 정보" 활성화
            binding.tvStudentInfo.setTextAppearance(R.style.heading4)
            binding.tvStudentInfo.setTextColor(ContextCompat.getColor(context, R.color.mainOrange))
            binding.underlineStudentInfo.visibility = View.VISIBLE

            // "재정 상태" 비활성화
            binding.tvFinancialStatus.setTextAppearance(R.style.body2)
            binding.tvFinancialStatus.setTextColor(ContextCompat.getColor(context, R.color.lightGray))
            binding.underlineFinancialStatus.visibility = View.INVISIBLE
        } else {
            // 학생 정보 비활성화
            binding.tvStudentInfo.setTextAppearance(R.style.body2)
            binding.tvStudentInfo.setTextColor(ContextCompat.getColor(context, R.color.lightGray))
            binding.underlineStudentInfo.visibility = View.INVISIBLE

            // 재정 상태 활성화
            binding.tvFinancialStatus.setTextAppearance(R.style.heading4)
            binding.tvFinancialStatus.setTextColor(ContextCompat.getColor(context, R.color.mainOrange))
            binding.underlineFinancialStatus.visibility = View.VISIBLE
        }
    }

}