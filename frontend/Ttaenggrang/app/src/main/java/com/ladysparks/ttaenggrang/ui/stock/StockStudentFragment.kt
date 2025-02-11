package com.ladysparks.ttaenggrang.ui.stock

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.databinding.DialogStockTradingBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStockStudentBinding

class StockStudentFragment : BaseFragment<FragmentStockStudentBinding>(
    FragmentStockStudentBinding::bind,
    R.layout.fragment_stock_student
), OnStockClickListener {

    private val viewModel: StockViewModel by viewModels()
    private lateinit var stockAdapter: StockAdapter
    private var selectedStock: StockDto? = null // 선택한 주식 저장

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
            selectedStock?.let { stock ->
                showDialog(stock)  // ✅ 선택한 주식 정보 다이얼로그에 전달
            } ?: Toast.makeText(requireContext(), "먼저 주식을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog(stock: StockDto) {
        val dialogBinding = DialogStockTradingBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        // ✅ 주식명 & 현재 주가 설정
        dialogBinding.textDialogStockTitle.setText(stock.name.substringBefore(" "))
        dialogBinding.textDialogStockPrice.setText(stock.price_per)


        dialogBinding.btnSell.setOnClickListener {

            Toast.makeText(requireContext(), "매도 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        dialogBinding.btnBuy.setOnClickListener {

            Toast.makeText(requireContext(), "매수 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }

    override fun onStockClick(stock: StockDto) {
        Toast.makeText(requireContext(), "선택한 주식: ${stock.name}", Toast.LENGTH_SHORT).show()
        selectedStock = stock
        binding.textHeadStockName.text = stock.name.substringBefore(" ")
        binding.textHeadStockPrice.text = stock.price_per.toString()
        binding.textHeadStockChange.text = "${stock.changeRate}%"
    }



    private fun initAdapter() {
        stockAdapter = StockAdapter(arrayListOf(), this)
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