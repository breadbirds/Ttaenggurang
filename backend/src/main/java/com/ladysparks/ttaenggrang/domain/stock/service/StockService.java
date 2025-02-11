package com.ladysparks.ttaenggrang.domain.stock.service;

import com.ladysparks.ttaenggrang.domain.stock.category.Category;
import com.ladysparks.ttaenggrang.domain.stock.category.CategoryRepository;
import com.ladysparks.ttaenggrang.domain.bank.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
import com.ladysparks.ttaenggrang.domain.bank.service.BankTransactionService;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import com.ladysparks.ttaenggrang.domain.stock.entity.StockHistory;
import com.ladysparks.ttaenggrang.domain.stock.entity.StockTransaction;
import com.ladysparks.ttaenggrang.domain.stock.entity.TransType;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockHistoryRepository;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockRepository;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockTransactionRepository;
import com.ladysparks.ttaenggrang.domain.student.entity.Student;
import com.ladysparks.ttaenggrang.domain.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository; //의존성 주입

    private final StockHistoryRepository stockHistoryRepository;

    private final StockTransactionRepository stockTransactionRepository;
    //학생
    private final StudentRepository studentRepository;

    private final BankTransactionService bankTransactionService;

    private final CategoryRepository categoryRepository;
    private final HolidayService holidayService;


    @Transactional
    public StockDTO registerStock(StockDTO stockDTO) {
        // 카테고리 이름을 가져오거나 새로 생성
        if (stockDTO.getCategoryName() == null || stockDTO.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 필수로 선택해야 합니다.");
        }

        // 카테고리 찾기 또는 새로 생성
        Category category = categoryRepository.findByName(stockDTO.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(stockDTO.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        // 주식 이름 중복 확인
        if (stockRepository.existsByName(stockDTO.getName())) {
            throw new IllegalArgumentException("이미 존재하는 주식 이름입니다.");
        }

        // DTO -> Entity 변환 후 저장
        Stock stock = Stock.builder()
                .name(stockDTO.getName())
                .price_per(stockDTO.getPrice_per())
                .total_qty(stockDTO.getTotal_qty())
                .remain_qty(stockDTO.getRemain_qty())
                .description(stockDTO.getDescription())
                .created_at(Timestamp.valueOf(LocalDateTime.now()))
                .updated_at(Timestamp.valueOf(LocalDateTime.now()))
                .category(category)  // 카테고리 설정
                .build();

        // 주식 정보 저장
        Stock savedStock = stockRepository.save(stock);

        // Entity -> DTO 변환 후 반환
        return StockDTO.fromEntity(savedStock);
    }


    //목록 조회
    public Long saveStock(StockDTO stockDto) {
        // StockDTO를 Stock 엔티티로 변환
        Stock stock = StockDTO.toEntity(stockDto);
        // 변환된 엔티티를 DB에 저장
        stockRepository.save(stock);
        // 저장된 엔티티의 ID 반환
        return stock.getId();
    }

    public List<StockDTO> findStocks() {
        //모든 주식 데이터 조회
        List<Stock> stocks = stockRepository.findAll();
        // 조회된 Stock 엔티티 리스트를 StockDTO 리스트로 변환
        return stocks.stream()
                .map(StockDTO::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 변환된 DTO를 리스트로 반환
    }

    public Optional<StockDTO> findStock(Long stockId) {
        // ID로 주식 조회 후, StockDTO로 변환하여 반환
        return stockRepository.findById(stockId)
                .map(StockDTO::fromEntity); // 엔티티를 DTO로 변환
    }

    public List<StockDTO> getFilteredStocks(List<Long> stockId) {
        // 주식 ID 리스트를 사용하여 해당 주식들을 조회
        List<Stock> stocks = stockRepository.findAllById(stockId);

        // 조회된 주식들을 StockDTO로 변환하여 반환
        return stocks.stream()
                .map(StockDTO::fromEntity) // Stock 엔티티를 StockDTO로 변환
                .collect(Collectors.toList()); // DTO 리스트 반환


    }


    // 주식 매수 로직
    @Transactional
    public StockTransactionDTO buyStock(Long stockId, int shareCount, Long studentId) {
        // 주식 정보 가져오기
        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        if (stockOptional.isEmpty()) {
            throw new IllegalArgumentException("주식이 존재하지 않습니다.");
        }
        Stock stock = stockOptional.get();


        // 학생 정보 가져오기
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new IllegalArgumentException("학생이 존재하지 않습니다.");
        }
        Student student = studentOptional.get();


        // 구매 가능한 수량 확인
        if (shareCount <= 0) {
            throw new IllegalArgumentException("0 이하 수량은 매수할 수 없습니다.");
        }

        // 남은 수량 확인
        if (stock.getRemain_qty() < shareCount) {
            throw new IllegalArgumentException("남은 수량이 부족합니다.");
        }


        // 주식 현재 가격 가져오기
        int price_per = stock.getPrice_per();
        if (price_per <= 0) {
            throw new IllegalStateException("주식 가격이 설정되지 않았습니다.");
        }

        // 총 금액 계산
        int totalAmount = price_per * shareCount;

        // 로그로 값 확인
        System.out.println("현재 주식 가격: " + price_per);
        System.out.println("총 구매 금액: " + totalAmount);

        //은행 계좌에서 금액 차감 (API 호출)
        Long bankAccountId = student.getBankAccount().getId();
        BankTransactionDTO transactionRequest = new BankTransactionDTO();
        transactionRequest.setBankAccountId(bankAccountId);
        transactionRequest.setType(BankTransactionType.STOCK_BUY);
        transactionRequest.setAmount(totalAmount);
        transactionRequest.setDescription("주식 매수: " + stock.getName());

        BankTransactionDTO bankTransactionDTO = bankTransactionService.addBankTransaction(transactionRequest);

        // 은행 서비스에서 받은 최종 잔액 확인
        int balanceAfter = bankTransactionDTO.getBalanceAfter();
        System.out.println("주식 매수 완료, 남은 잔액: " + balanceAfter);


        // 주식의 재고 수량 차감
        stock.setRemain_qty(stock.getRemain_qty() - shareCount);
        stockRepository.save(stock);


        // 학생이 현재 보유한 해당 주식 수량 조회
        Integer owned_qty = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId.intValue(), TransType.BUY);
        if (owned_qty == null) {
            owned_qty = 0; // 처음 구매라면 0으로 설정
        }

        // 기존 보유량 + 새로 매수한 수량
        int updatedOwnedQty = owned_qty + shareCount;


        // 새로운 매수 거래 생성
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stock);
        transaction.setStudent(student);
        transaction.setShare_count(shareCount);
        transaction.setTransType(TransType.BUY);
        transaction.setTrans_date(new Timestamp(System.currentTimeMillis()));  //날짜
        transaction.setOwned_qty(updatedOwnedQty); // 기존 보유량 + 새로 매수한 수량
        transaction.setTotal_amt(totalAmount);
        transaction.setPurchase_prc(price_per); // 현재 가격을 그대로 저장

        stockTransactionRepository.save(transaction);

        // 주식의 현재 가격을 업데이트
        stock.setPrice_per(price_per);
        stockRepository.save(stock);

        return StockTransactionDTO.fromEntity(transaction, updatedOwnedQty);
    }

    // 주식 매도 로직
    @Transactional
    public StockTransactionDTO sellStock(Long stockId, int shareCount, Long studentId) {
        // 주식 정보 가져오기
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("주식이 존재하지 않습니다."));

        // 학생 정보 가져오기
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));


        // 매도 가능한 수량 확인
        if (shareCount <= 0) {
            throw new IllegalArgumentException("0 이하 수량은 매도할 수 없습니다.");
        }

        //학생의 총 매수량(BUY)과 총 매도량(SELL) 조회
        Integer totalBought = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId.intValue(), TransType.BUY);
        Integer totalSold = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId.intValue(), TransType.SELL);

        // NULL 방지 처리
        totalBought = (totalBought == null) ? 0 : totalBought;
        totalSold = (totalSold == null) ? 0 : totalSold;

        // 현재 보유량 = 총 매수량 - 총 매도량
        int owned_qty = totalBought - totalSold;

        // 보유량보다 더 많이 매도하려는 경우 예외 발생
        if (owned_qty < shareCount) {
            throw new IllegalArgumentException("보유한 주식 수량이 부족합니다.");
        }

        // 주식 현재 가격 가져오기
        int price_per = stock.getPrice_per();
        if (price_per <= 0) {
            throw new IllegalStateException("주식 가격이 설정되지 않았습니다.");
        }

        // 총 매도 금액 계산
        int totalAmount = price_per * shareCount;

        // 로그 확인
        System.out.println("현재 주식 가격: " + price_per);
        System.out.println("총 매도 금액: " + totalAmount);

        //은행 계좌에서 금액 차감 (API 호출)
        Long bankAccountId = student.getBankAccount().getId();
        BankTransactionDTO transactionRequest = new BankTransactionDTO();
        transactionRequest.setBankAccountId(bankAccountId);
        transactionRequest.setType(BankTransactionType.STOCK_SELL);
        transactionRequest.setAmount(totalAmount);
        transactionRequest.setDescription("주식 매도: " + stock.getName());

        BankTransactionDTO bankTransactionDTO = bankTransactionService.addBankTransaction(transactionRequest);

        // 은행 서비스에서 받은 최종 잔액 확인
        int balanceAfter = bankTransactionDTO.getBalanceAfter();
        System.out.println("주식 매도 완료, 남은 잔액: " + balanceAfter);


        stock.setRemain_qty(stock.getRemain_qty() + shareCount);
        stockRepository.save(stock);


        int updatedOwnedQty = owned_qty - shareCount;


        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stock);
        transaction.setStudent(student);
        transaction.setShare_count(shareCount);
        transaction.setTransType(TransType.SELL); // 매도 타입
        transaction.setTrans_date(new Timestamp(System.currentTimeMillis())); // 거래 날짜
        transaction.setOwned_qty(updatedOwnedQty); // 매도 후 남은 보유량 저장
        transaction.setTotal_amt(totalAmount);
        transaction.setPurchase_prc(price_per); // 현재 가격 저장

        // 매도 거래 저장
        stockTransactionRepository.save(transaction);

        // DTO 변환 후 반환
        return StockTransactionDTO.fromEntity(transaction, updatedOwnedQty);
    }

//    //가격 변동
//    @Transactional
//    public ResponseEntity<String> manageMarket(boolean openMarket) {
//        try {
//            // 주식장 열기 또는 닫기 처리
//            List<Stock> stocks = stockRepository.findAll();
//
//            if (stocks.isEmpty()) {
//                return ResponseEntity.badRequest().body("주식 목록이 비어 있습니다.");
//            }
//
//            List<OpenResponseDTO> responseList = new ArrayList<>();
//            StringBuilder marketStatus = new StringBuilder();
//
//            System.out.println("openMarket 값: " + openMarket);  // openMarket 값 확인
//
//            for (Stock stock : stocks) {
//                if (openMarket) {
//                    // 주식장 열기 - 상태 초기화
//                    stock.setIsMarketActive(true);  // 주식장 열기
//                    stock.setRemain_qty(stock.getTotal_qty()); // 남은 개수 초기화
//
//                    // 거래량 초기화 (StockHistory에서만)
//                    List<StockHistory> stockHistories = stockHistoryRepository.findByStockId(stock.getId());
//                    for (StockHistory history : stockHistories) {
//                        // 거래량을 0으로 초기화
//                        history.setBuyVolume(0);  // 매수량 초기화
//                        history.setSellVolume(0); // 매도량 초기화
//                        stockHistoryRepository.save(history);  // 저장
//                    }
//
//                    // 주식장이 열릴 때 가격 변동을 계산 (거래량에 따라)
//                    int totalBuyVolume = stockHistoryRepository.getTotalBuyVolume(stock.getId()); // 최근 매수량
//                    int totalSellVolume = stockHistoryRepository.getTotalSellVolume(stock.getId()); // 최근 매도량
//
//                    // 거래량이 있으면 가격 변동 처리
//                    if (totalBuyVolume > 0 || totalSellVolume > 0) {
//                        double newPrice = calculatePriceChange(totalBuyVolume, totalSellVolume, stock.getPrice_per());
//                        stock.setPrice_per((int) Math.round(newPrice));  // 새로운 가격 적용
//                        stock.setPriceChangeTime(LocalDateTime.now());  // 가격 변동 시간 갱신
//                    }
//
//                    // 콘솔에 주식 가격 출력 (열렸을 때)
//                    System.out.println("📈 주식장 열기 - " + stock.getName() + "의 초기 가격: " + stock.getPrice_per() + "원");
//                    System.out.println("총 개수: " + stock.getTotal_qty() + "개");
//                    System.out.println("남은 개수: " + stock.getRemain_qty() + "개");
//
//                } else {
//                    // 주식장 닫기 -> 거래량에 따른 가격 변동 처리
//                    int totalBuyVolume = stockHistoryRepository.getTotalBuyVolume(stock.getId()); // 최근 매수량
//                    int totalSellVolume = stockHistoryRepository.getTotalSellVolume(stock.getId()); // 최근 매도량
//
//                    // 거래량이 없으면 가격을 유지하고, 거래량이 있으면 가격 변동 처리
//                    if (totalBuyVolume == 0 && totalSellVolume == 0) {
//                        marketStatus.append("거래량 없음: ").append(stock.getName()).append(" 가격 유지\n");
//                        continue; // 거래량 없으면 가격 유지
//                    }
//
//                    // 주식장이 닫힐 때 가격 변동 계산
//                    double newPrice = calculatePriceChange(totalBuyVolume, totalSellVolume, stock.getPrice_per());
//                    stock.setPrice_per((int) Math.round(newPrice));  // 새로운 가격 적용
//                    stock.setPriceChangeTime(LocalDateTime.now());  // 가격 변동 시간 갱신
//                    stock.setIsMarketActive(false);  // 주식장 닫기
//
//                    // 거래 내역을 StockHistory에 저장
//                    StockHistory stockHistory = new StockHistory();
//                    stockHistory.setStock(stock);
//                    stockHistory.setBuyVolume(totalBuyVolume);  // 매수량
//                    stockHistory.setSellVolume(totalSellVolume);  // 매도량
//                    stockHistory.setDate(Timestamp.valueOf(LocalDateTime.now()));  // 현재 날짜/시간 기록
//                    stockHistory.setPrice(stock.getPrice_per()); // 현재 가격을 거래 내역에 기록
//
//                    stockHistoryRepository.save(stockHistory);
//
//                    // 콘솔에 매수/매도량 및 변동 가격 출력 (닫힐 때)
//                    System.out.println("📊 " + stock.getName() + " 매수량: " + totalBuyVolume + ", 매도량: " + totalSellVolume);
//                    System.out.println("📉 주식 가격 변동 적용: " + stock.getName() + " -> " + stock.getPrice_per() + "원");
//                }
//            }
//
//            // 변환된 Stock 리스트를 DB에 저장
//            stockRepository.saveAll(stocks);
//
//            return ResponseEntity.ok(marketStatus.toString());
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("주식장 처리 실패: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("알 수 없는 오류 발생: " + e.getMessage());
//        }
//    }
//
//
//
//    /**
//     * 주식 가격 변동 계산 (매수량과 매도량 기반)
//     */
//    private double calculatePriceChange(int buyVolume, int sellVolume, double oldPrice) {
//        if (buyVolume == 0 && sellVolume == 0) {
//            return oldPrice; // 거래가 없으면 가격 유지
//        }
//
//        try {
//            double changeRate;
//
//            if (buyVolume > sellVolume) {
//                changeRate = (double) (buyVolume - sellVolume) / (buyVolume + sellVolume); // 매수량이 더 많으면 가격 상승
//            } else {
//                changeRate = (double) (sellVolume - buyVolume) / (buyVolume + sellVolume); // 매도량이 더 많으면 가격 하락
//                changeRate = -changeRate; // 매도량이 더 많을 때는 하락
//            }
//
//            // 변동률을 ±10%로 제한 (0.10 = 10%)
//            changeRate = Math.max(-0.10, Math.min(0.10, changeRate));
//
//            return oldPrice * (1 + changeRate);  // 새로운 가격 계산
//        } catch (ArithmeticException e) {
//            throw new IllegalArgumentException("매수량과 매도량의 합이 0입니다. 가격 계산이 불가능합니다.");
//        } catch (Exception e) {
//            throw new RuntimeException("알 수 없는 오류 발생: " + e.getMessage());
//        }
//    }


    // 주식 시장 관리 (개장 or 폐장) 및 가격 변동 처리
    public boolean manageMarket(boolean openMarket) {
        LocalDateTime today = LocalDateTime.now();
        LocalTime currentTime = today.toLocalTime();

        // 주말 및 공휴일 확인
        if (today.getDayOfWeek().getValue() >= 6) { // 토요일 또는 일요일
            throw new IllegalArgumentException("주말에는 주식시장이 열리지 않습니다.");
        }
        if (holidayService.isHoliday(today.toLocalDate())) {
            throw new IllegalArgumentException("오늘은 공휴일 또는 예약된 휴장일입니다.");
        }

        List<Stock> stocks = stockRepository.findAll();  // Stock 객체들을 DB에서 조회
        if (stocks.isEmpty()) {
            throw new IllegalArgumentException("주식 목록이 비어 있습니다.");
        }

        // 주식 시장 열렸다고 출력
        if (openMarket) {
            System.out.println("주식 시장이 열렸습니다.");
            for (Stock stock : stocks) {
                stock.setIsMarketActive(true);
                stock.setRemain_qty(stock.getRemain_qty());
                System.out.println(stock.getName() + " " + stock.getPrice_per() + "원");  // 각 주식 가격 출력
            }
        } else {
            // 개장 시간 ~ 폐장 시간 체크
            for (Stock stock : stocks) {
                if (currentTime.isBefore(stock.getOpenTime()) || currentTime.isAfter(stock.getCloseTime())) {
                    stock.setIsMarketActive(false);  // 폐장 처리

                    // 거래 기록 처리
                    int totalBuyVolumeInRange = stockHistoryRepository.getTotalBuyVolumeInRange(stock.getId(), stock.getOpenTime(), stock.getCloseTime());
                    int totalSellVolumeInRange = stockHistoryRepository.getTotalSellVolumeInRange(stock.getId(), stock.getOpenTime(), stock.getCloseTime());

                    // 거래량에 따라 가격을 업데이트하고 거래 기록 저장
                    Stock updatedStock = updateStockPrice(stock, totalBuyVolumeInRange, totalSellVolumeInRange);  // 가격 변동
                    saveStockHistory(stock, totalBuyVolumeInRange, totalSellVolumeInRange);  // 거래 기록 저장

                    System.out.println(stock.getName() + " 주식 시장이 폐장되었습니다.");  // 주식장 폐장 출력
                    System.out.println("최종 가격: " + updatedStock.getPrice_per() + "원");  // 변동된 가격 출력
                } else {
                    System.out.println(stock.getName() + " 주식 시장이 개장되었습니다.");  // 주식장 개장 출력
                    stock.setIsMarketActive(true);  // 개장 처리
                }
            }
        }
        stockRepository.saveAll(stocks);  // DB에 업데이트된 주식 정보 저장

        return openMarket;
    }

    // 가격 변동 처리 (폐장 시 적용)
    private Stock updateStockPrice(Stock stock, int totalBuyVolumeInRange, int totalSellVolumeInRange) {
        if (totalBuyVolumeInRange > 0 || totalSellVolumeInRange > 0) {
            double priceChangeRate = calculatePriceChange(totalBuyVolumeInRange, totalSellVolumeInRange);
            double newPrice = stock.getPrice_per() * (1 + priceChangeRate);
            stock.setPrice_per((int) Math.round(newPrice));
        }

        // 거래 기록 저장을 위해 가격 변동 처리 후 saveStockHistory 호출
        saveStockHistory(stock, totalBuyVolumeInRange, totalSellVolumeInRange);  // 거래 기록 저장
        System.out.println(stock.getName() + " 주식 가격이 변동되었습니다.");
        System.out.println("새 가격: " + stock.getPrice_per() + "원");

        return stock;
    }

    // 주식 거래 기록 저장
    private void saveStockHistory(Stock stock, int totalBuyVolumeInRange, int totalSellVolumeInRange) {
        StockHistory stockHistory = new StockHistory();

        stockHistory.setStock(stock);
        stockHistory.setBuyVolume(totalBuyVolumeInRange);  // 매수량
        stockHistory.setSellVolume(totalSellVolumeInRange);  // 매도량
        stockHistory.setDate(Timestamp.valueOf(LocalDateTime.now()));  // 거래 시간 설정
        stockHistory.setPrice(stock.getPrice_per());  // 변동된 가격 반영

        // 거래 기록을 저장합니다
        stockHistoryRepository.save(stockHistory);
        System.out.println("주식 거래 기록이 저장되었습니다.");
    }


    // 가격 변동 계산
    private double calculatePriceChange(int buyVolume, int sellVolume) {
        // 거래량에 따른 가격 변동률 계산
        double changeRate = (double) (buyVolume - sellVolume) / (buyVolume + sellVolume);
        changeRate = Math.max(-0.10, Math.min(0.10, changeRate)); // 가격 변동률을 -10% ~ +10%로 제한
        return changeRate;
    }

    // 주식의 개장 시간과 폐장 시간을 변경
    @Transactional
    public void updateMarketTimeForAllStocks(LocalTime newOpenTime, LocalTime newCloseTime) {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            stock.setOpenTime(newOpenTime);
            stock.setCloseTime(newCloseTime);
        }
        stockRepository.saveAll(stocks);
        stockRepository.flush();
    }


    // 주식의 폐장 시간만 변경하는 메서드
    public void updateCloseTime(Long stockId, LocalTime newCloseTime) {
        // 주식 정보 가져오기
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("주식 정보가 없습니다."));

        // 기존의 개장 시간은 그대로 두고, 폐장 시간만 변경
        stock.setCloseTime(newCloseTime);

        // DB에 변경된 주식 시간 저장
        stockRepository.save(stock);
        System.out.println(stock.getName() + "의 폐장 시간이 " + newCloseTime + "로 변경되었습니다.");
    }
}

