package com.ladysparks.ttaenggrang.ui.stock
//
//class StockViewModel : ViewModel() {
//    private val stockService = RetrofitUtil.stockService
//
//    private val _stockList = MutableLiveData<List<StockDto>>()
//    val stockList: LiveData<List<StockDto>> get() = _stockList
//
//    // 주식 데이터 조회
//    fun fetchAllStocks() {
//        viewModelScope.launch {
//            try {
//                val stocks = stockService.getAllStocks()
//                _stockList.postValue(stocks)
//            } catch (e: Exception) {
//                Log.e("StockViewModel", "주식 목록 불러오기 실패", e)
//            }
//        }
//    }
//}


//class StockViewModel(private val apiService: ApiService) : ViewModel() {
//
//    private val _stockList = MutableLiveData<List<StockDto>>()
//    val stockList: LiveData<List<StockDto>> get() = _stockList
//
//    private val _selectedStock = MutableLiveData<StockDto?>()
//    val selectedStock: LiveData<StockDto?> get() = _selectedStock
//
//    private val _ownedStockQty = MutableLiveData<Int>()
//    val ownedStockQty: LiveData<Int> get() = _ownedStockQty
//
//    // 주식 목록 가져오기
//    fun fetchStockList() {
//        viewModelScope.launch {
//            try {
//                val response = apiService.getStockList()
//                _stockList.postValue(response)
//            } catch (e: Exception) {
//                Log.e("StockViewModel", "주식 목록 불러오기 실패", e)
//            }
//        }
//    }
//
//    // 특정 주식 선택
//    fun selectStock(stock: StockDto) {
//        _selectedStock.value = stock
//        fetchOwnedStockQty(stock.id)
//    }
//
//    // 사용자가 보유한 주식 개수 가져오기
//    private fun fetchOwnedStockQty(stockId: Int) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.getOwnedStockQty(stockId)
//                _ownedStockQty.postValue(response)
//            } catch (e: Exception) {
//                Log.e("StockViewModel", "보유 주식 개수 불러오기 실패", e)
//                _ownedStockQty.postValue(0) // 실패 시 0으로 설정
//            }
//        }
//    }
//
//    // 주식 매수
//    fun buyStock(stockId: Int, qty: Int) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.buyStock(
//                    StockTransactionDto(
//                        stockId = stockId,
//                        shareCount = qty,
//                        transType = TransType.BUY
//                    )
//                )
//                if (response) {
//                    fetchStockList()
//                    fetchOwnedStockQty(stockId)
//                }
//            } catch (e: Exception) {
//                Log.e("StockViewModel", "매수 실패", e)
//            }
//        }
//    }
//
//    // 주식 매도
//    fun sellStock(stockId: Int, qty: Int) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.sellStock(
//                    StockTransactionDto(
//                        stockId = stockId,
//                        shareCount = qty,
//                        transType = TransType.SELL
//                    )
//                )
//                if (response) {
//                    fetchStockList()
//                    fetchOwnedStockQty(stockId)
//                }
//            } catch (e: Exception) {
//                Log.e("StockViewModel", "매도 실패", e)
//            }
//        }
//    }
//}
//
//
