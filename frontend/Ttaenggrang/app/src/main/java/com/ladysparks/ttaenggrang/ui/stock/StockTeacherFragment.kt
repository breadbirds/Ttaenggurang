package com.ladysparks.ttaenggrang.ui.stock

import android.os.Bundle
import android.view.View
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentStockTeacherBinding

class StockTeacherFragment : BaseFragment<FragmentStockTeacherBinding>(FragmentStockTeacherBinding::bind, R.layout.fragment_stock_teacher) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData() {
        // 화면이 변경될 때 표시할 데이터/API 를 해당 함수에서 호출합니다.
    }


}