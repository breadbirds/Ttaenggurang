package com.ladysparks.ttaenggrang.ui.nation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentNationBinding
import com.ladysparks.ttaenggrang.util.showToast

class NationFragment : BaseFragment<FragmentNationBinding>(FragmentNationBinding::bind, R.layout.fragment_nation){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.btnGoalSavings.setOnClickListener { createGoalDialog() }
        binding.btnNationInfo.setOnClickListener{ createNationIfoDialog() }

    }

    private fun createGoalDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_goal_saving, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val etTargetAmount = dialogView.findViewById<EditText>(R.id.editGoalSaving)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val targetAmount = etTargetAmount.text.toString()

            if (targetAmount.isEmpty()) {
                showToast("목표 금액을 입력해주세요.")
                return@setOnClickListener
            }

            // 수정 : 목표 금액 설정 API 추가

            dialog.dismiss()  // 다이얼로그 닫기
        }

        dialog.show()

    }

    private fun createNationIfoDialog(){
        // 수정 : 국가 설립일, 단위, 학급인원 추가 되어야 함
        // 학급이원이 변경된 경우 수정 요청 api 날려야함. 후순위

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_nation_info, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val textNationStartDate = dialogView.findViewById<TextView>(R.id.textNationStartDate)
        val textNationMoneyLabel = dialogView.findViewById<TextView>(R.id.textNationMoneyLabel)
        val editStudentCount = dialogView.findViewById<EditText>(R.id.editStudentCount)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val targetAmount = editStudentCount.text.toString()

            if (targetAmount.isEmpty()) {
                showToast("목표 금액을 입력해주세요.")
                return@setOnClickListener
            }

            // 수정 : 목표 금액 설정 API 추가

            dialog.dismiss()  // 다이얼로그 닫기
        }

        dialog.show()

    }
}