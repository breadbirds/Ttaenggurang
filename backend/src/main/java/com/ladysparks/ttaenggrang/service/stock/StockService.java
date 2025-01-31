package com.ladysparks.ttaenggrang.service.stock;

import com.ladysparks.ttaenggrang.domain.stock.Stock;
import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import com.ladysparks.ttaenggrang.domain.stock.TransType;
import com.ladysparks.ttaenggrang.domain.user.Student;
import com.ladysparks.ttaenggrang.dto.stock.StockDTO;
import com.ladysparks.ttaenggrang.repository.stock.StockRepository;
import com.ladysparks.ttaenggrang.repository.stock.StockTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository; //의존성 주입

    private final StockTransactionRepository stockTransactionRepository;

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
    public boolean buyStock(int stockId, int share_count) {
        // 주식 정보 가져오기
        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        if (stockOptional.isEmpty()) {
            return false; // 주식이 존재하지 않으면 실패
        }

        Stock stock = stockOptional.get();

        // 구매 가능한 수량이 있는지 확인
        if (share_count <= 0) {
            return false; // 0 이하 수량은 매수할 수 없음
        }

        // 남은 수량 확인
        if (stock.getRemain_qty() < share_count) {
            return false; // 남은 수량이 부족하면 매수 불가능
        }

        // 주식의 재고 수량을 차감
        stock.setRemain_qty(stock.getRemain_qty() - share_count);

        // 재고 업데이트 저장
        stockRepository.save(stock);

        // 주식 거래 내역 저장 (매수)
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stock);
        transaction.setShare_count(share_count);
        transaction.setTransType(TransType.BUY); // BUY 거래로 설정
        stockTransactionRepository.save(transaction);

        return true; // 매수 성공
    }





}