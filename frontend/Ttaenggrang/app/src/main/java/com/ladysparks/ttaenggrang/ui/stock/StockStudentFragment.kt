package com.ladysparks.ttaenggrang.ui.stock

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.databinding.DialogStockTradingBinding
import com.ladysparks.ttaenggrang.databinding.DialogueBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStockStudentBinding
import com.ladysparks.ttaenggrang.ui.home.AlarmAdapter
import com.ladysparks.ttaenggrang.ui.home.StockAdapter

class StockStudentFragment : BaseFragment<FragmentStockStudentBinding>(FragmentStockStudentBinding::bind, R.layout.fragment_stock_student) {

    private lateinit var stockAdapter: StockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //초기화
        initAdapter()

        // 샘플데이터
        sampleDataStockList()

        //거래 버튼
        binding.btnTrade.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_stock_trading)

        val btnSell = dialog.findViewById<Button>(R.id.btnSell)
        btnSell.setOnClickListener {
            //매도 기능 구현
        }
        val btnBuy = dialog.findViewById<Button>(R.id.btnBuy)
        btnBuy.setOnClickListener {
            //매수 기능 구현
        }

        dialog.show()
    }


    // 샘플 데이터 (StockDto 리스트)
    private fun sampleDataStockList() {
//        val tempData = listOf(
//            StockDto(1, "Apple", 150, 1000, 500, "Tech", "2024-02-01", "2024-02-02", 2, 1.2, 10, 5, "IT"),
//            StockDto(2, "Google", 2800, 500, 250, "Search Engine", "2024-02-01", "2024-02-02", -1, 2.5, 11, 6, "IT"),
//            StockDto(3, "Tesla", 850, 1200, 600, "EV", "2024-02-01", "2024-02-02", 3, 2.8, 12, 7, "Automobile")
//        )

        // 어뎁터 초기화
        //stockAdapter = StockAdapter(tempData)
        //binding.recyclerStockList.adapter = stockAdapter

        // 어댑터 데이터 갱신
        //stockAdapter.updateData(tempData)
    }

    private fun initAdapter() {
        stockAdapter = StockAdapter(arrayListOf())
        binding.recyclerStockList.adapter = stockAdapter
    }

}