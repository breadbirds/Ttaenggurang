package com.ladysparks.ttaenggrang.ui.stock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.data.model.dto.StockTransactionDto
import com.ladysparks.ttaenggrang.data.model.dto.StudentStockDto
import com.ladysparks.ttaenggrang.data.model.dto.TransType
import com.ladysparks.ttaenggrang.data.model.request.StudentSignInRequest
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil.Companion.authService
import com.ladysparks.ttaenggrang.data.remote.StockService
import com.ladysparks.ttaenggrang.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {
    private val stockService: StockService = RetrofitUtil.stockService

    //주식 전체조회
    private val _stockList = MutableLiveData<List<StockDto>>()
    val stockList: LiveData<List<StockDto>> get() = _stockList

    //주식 매도
    private val _sellTransaction = MutableLiveData<StockTransactionDto?>()
    val sellTransaction: LiveData<StockTransactionDto?> get() = _sellTransaction

    //주식 매수
    private val _buyTransaction = MutableLiveData<StockTransactionDto?>()
    val buyTransaction: LiveData<StockTransactionDto?> get() = _buyTransaction

    // 에러메세지
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    //  내 보유 주식 수 LiveData
    private val _ownedStocks = MutableLiveData<List<StudentStockDto>>()
    val ownedStocks: LiveData<List<StudentStockDto>> = _ownedStocks

    // 첫번째 아이템 불러오기
    private val _selectedStock = MutableLiveData<StockDto?>()
    val selectedStock: LiveData<StockDto?> get() = _selectedStock

    // 주식 열림 확인
    private val _isMarketActive = MutableLiveData<Boolean>()
    val isMarketActive: LiveData<Boolean> get() = _isMarketActive

    // 사용자가 입력한 거래 주식 수
    private val _tradeAmount = MutableLiveData<Int>()
    val tradeAmount: LiveData<Int> get() = _tradeAmount

    // 예상 결제 금액
    private val _expectedPayment = MutableLiveData<Int>()
    val expectedPayment: LiveData<Int> get() = _expectedPayment

    // 결제 후 내 자산 (임시 하드코딩된 자산 값 사용)
    private val _myAsset = MutableLiveData<Int>(100000) // ✅ 현재 내 자산 (하드코딩된 값)
    val myAsset: LiveData<Int> get() = _myAsset

    // 거래 후 내 보유 현금 계산
    private val _updatedMyAsset = MutableLiveData<Int>()
    val updatedMyAsset: LiveData<Int> get() = _updatedMyAsset

    // 거래 후 내 보유 주식 수
    private val _updatedOwnedStock = MutableLiveData<Int>()
    val updatedOwnedStock: LiveData<Int> get() = _updatedOwnedStock


    // 주식 데이터 조회
    fun fetchAllStocks() {
        viewModelScope.launch {
            try {
                val stocks = stockService.getAllStocks()
                _stockList.postValue(stocks)

                //  주식 목록을 불러온 후 첫 번째 주식을 선택
                if (stocks.isNotEmpty()) {
                    _selectedStock.postValue(stocks[0])
                }

            } catch (e: Exception) {
                Log.e("StockViewModel", "주식 목록 불러오기 실패", e)
            }
        }
    }

    // 학생이 보유한 주식 목록 조회
    fun fetchOwnedStocks(studentId: Int) {
        viewModelScope.launch {
            try {
                val response = stockService.getStudentStocks(studentId) // ✅ 서비스 수정 반영
                _ownedStocks.postValue(response) // ✅ LiveData 업데이트
            } catch (e: Exception) {
                Log.e("StockViewModel", "보유 주식 조회 실패", e)
                _ownedStocks.postValue(emptyList())
            }
        }
    }

    // 학생 ID 조회 후 주식 매도 요청
    fun sellStock(stockId: Int, shareCount: Int, studentId: Int) {
        viewModelScope.launch {
            try {
                // ✅ `sellStock()` 요청 실행
                val sellResponse = stockService.sellStock(stockId, shareCount, studentId)

                if (sellResponse.isSuccessful) {
                    val transactionData = sellResponse.body()?.data
                    // ✅ 매도 성공 후 보유 주식 수 업데이트
                    _sellTransaction.postValue(transactionData)
                    Log.d("StockViewModel", "매도 성공: ${transactionData?.shareCount}주")
                } else {
                    _errorMessage.postValue("매도 요청 실패 (HTTP ${sellResponse.code()})")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류 발생: ${e.message}")
                Log.e("StockViewModel", "네트워크 오류", e)
            }
        }
    }


    // 매수 기능
    fun buyStock(stockId: Int, shareCount: Int, studentId: Int) {
        viewModelScope.launch {
            try {
                val buyResponse = stockService.buyStock(stockId, shareCount, studentId)

                if (buyResponse.isSuccessful) {
                    val transactionData = buyResponse.body()?.data
                    _buyTransaction.postValue(transactionData)
                    Log.d("StockViewModel", "매수 성공: ${transactionData?.shareCount}주")
                } else {
                    _errorMessage.postValue("매수 요청 실패 (HTTP ${buyResponse.code()})")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류 발생: ${e.message}")
                Log.e("StockViewModel", "네트워크 오류", e)
            }
        }
    }


    // ✅ 특정 주식 선택 (리사이클러뷰에서 클릭 시 호출됨)
    fun selectStock(stock: StockDto) {
        _selectedStock.value = stock
    }

    //주식장 열기(교사)
    fun updateMarketStatus(openMarket: Boolean) {
        Log.d("TAG", "updateMarketStatus: 1단계!!!!")
        viewModelScope.launch {
            try {
                val response = stockService.setMarketStatus(openMarket) // API 호출
                if (response.isSuccessful) {
                    Log.d("TAG", "updateMarketStatus: 성공!!!!")
                    _isMarketActive.value = response.body()?.data // 응답 값 반영
                } else {
                    Log.d("TAG", "updateMarketStatus: 실패!!!!")
                    _isMarketActive.value = false // 실패 시 기본값 설정
                }
            } catch (e: Exception) {
                Log.d("TAG", "updateMarketStatus: 에러!!!!")
                _isMarketActive.value = false // 오류 발생 시 기본값 설정
            }
        }
    }

    // 주식장 열림 확인(학생). 변경사항이 있을때만 ui 업데이트
    fun fetchMarketStatus() {
        viewModelScope.launch {
            try {
                val response = stockService.getMarketStatus()
                if (response.isSuccessful) {
                    val newStatus = response.body()?.data ?: false
                    if (_isMarketActive.value != newStatus) {
                        _isMarketActive.postValue(newStatus)
                    }
                } else {
                    _isMarketActive.postValue(false)
                }
            } catch (e: Exception) {
                _isMarketActive.postValue(false)
            }
        }
    }

    fun updateTradeAmount(amount: Int, stockPrice: Int, ownedStock: Int, transactionType: TransType) {
        _tradeAmount.postValue(amount)

        // ✅ 예상 결제 금액 계산
        val calculatedPayment = stockPrice * amount
        _expectedPayment.postValue(calculatedPayment)

        // ✅ 매도 후 보유 현금 증가, 매수 후 보유 현금 감소
        _updatedMyAsset.postValue(if (transactionType == TransType.SELL) {
            (_myAsset.value ?: 0) + calculatedPayment
        } else {
            (_myAsset.value ?: 0) - calculatedPayment
        })

        // ✅ 매도 후 보유 주식 감소, 매수 후 보유 주식 증가
        _updatedOwnedStock.postValue(if (transactionType == TransType.SELL) {
            ownedStock - amount
        } else {
            ownedStock + amount
        })
    }
}



