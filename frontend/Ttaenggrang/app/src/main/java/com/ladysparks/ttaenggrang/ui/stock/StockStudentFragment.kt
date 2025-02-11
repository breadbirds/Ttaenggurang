package com.ladysparks.ttaenggrang.ui.stock

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.DialogStockTradingBinding
import com.ladysparks.ttaenggrang.databinding.DialogueBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStockStudentBinding
import com.ladysparks.ttaenggrang.ui.home.AlarmAdapter
import com.ladysparks.ttaenggrang.ui.home.StockAdapter

class StockStudentFragment : BaseFragment<FragmentStockStudentBinding>(FragmentStockStudentBinding::bind, R.layout.fragment_stock_student) {

    private val viewModel: StockViewModel by viewModels()
    private lateinit var stockAdapter: StockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //초기화
        initAdapter()

        // LiveData 관찰하여 데이터 변경 시 UI 업데이트
        observeViewModel()

        // 서버에서 주식 데이터 가져오기
        viewModel.fetchAllStocks()

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




    private fun initAdapter() {
        stockAdapter = StockAdapter(arrayListOf())
        binding.recyclerStockList.adapter = stockAdapter
    }

    private fun observeViewModel() {
        viewModel.stockList.observe(viewLifecycleOwner, Observer { stockList ->
            stockList?.let {
                stockAdapter.updateData(it)  // 어댑터 데이터 업데이트
            }
        })
    }

}