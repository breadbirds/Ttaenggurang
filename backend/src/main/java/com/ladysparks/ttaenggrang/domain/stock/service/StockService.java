package com.ladysparks.ttaenggrang.domain.stock.service;

import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import com.ladysparks.ttaenggrang.domain.stock.entity.StockTransaction;
import com.ladysparks.ttaenggrang.domain.stock.entity.TransType;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockRepository;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockTransactionRepository;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.global.exception.GlobalExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository; //의존성 주입

    private final StockTransactionRepository stockTransactionRepository;
    //학생
    private final StudentRepository studentRepository;



    //목록 조회
    public int saveStock(StockDTO stockDto) {
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
    public Optional<StockDTO> findStock(int stockId) {
        // ID로 주식 조회 후, StockDTO로 변환하여 반환
        return stockRepository.findById(stockId)
                .map(StockDTO::fromEntity); // 엔티티를 DTO로 변환
    }


    // 주식 매수 로직
    @Transactional
    public StockTransactionDTO buyStock(int stockId, int shareCount, Long studentId) {
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


        // 주식의 재고 수량 차감
        stock.setRemain_qty(stock.getRemain_qty() - shareCount);
        stockRepository.save(stock);


        // 학생이 현재 보유한 해당 주식 수량 조회
        Integer owned_qty = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId, TransType.BUY);
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

    // 🟢 주식의 현재 가격을 업데이트
        stock.setPrice_per(price_per);
        stockRepository.save(stock);

        return StockTransactionDTO.fromEntity(transaction, updatedOwnedQty);
    }

    // 주식 매도 로직
    @Transactional
    public StockTransactionDTO sellStock(int stockId, int shareCount, Long studentId) {
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

        // 🟢 (수정) 학생의 총 매수량(BUY)과 총 매도량(SELL) 조회
        Integer totalBought = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId, TransType.BUY);
        Integer totalSold = stockTransactionRepository.findTotalSharesByStudentAndStock(studentId, stockId, TransType.SELL);

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

}

