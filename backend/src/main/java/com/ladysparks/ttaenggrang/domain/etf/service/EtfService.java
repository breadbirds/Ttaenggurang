package com.ladysparks.ttaenggrang.domain.etf.service;

import com.ladysparks.ttaenggrang.domain.etf.dto.EtfTransactionDTO;
import com.ladysparks.ttaenggrang.domain.etf.entity.Etf;
import com.ladysparks.ttaenggrang.domain.etf.dto.EtfDTO;
import com.ladysparks.ttaenggrang.domain.etf.entity.EtfTransaction;
import com.ladysparks.ttaenggrang.domain.etf.repository.EtfRepository;
import com.ladysparks.ttaenggrang.domain.etf.repository.EtfTransactionRepository;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import com.ladysparks.ttaenggrang.domain.stock.entity.StockTransaction;
import com.ladysparks.ttaenggrang.domain.stock.entity.TransType;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockTransactionRepository;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class EtfService {
    private final EtfRepository etfRepository; //의존성 주입

    private final EtfTransactionRepository etfTransactionRepository;
    //학생
    private final StudentRepository studentRepository;

    //목록 조회
    public int saveEtf(EtfDTO etfDto) {
        // EtfDTO를 Etf 엔티티로 변환
        Etf etf = EtfDTO.toEntity(etfDto);
        // 변환된 엔티티를 DB에 저장
        etfRepository.save(etf);
        // 저장된 엔티티의 ID 반환
        return etf.getId();
    }

    public List<EtfDTO> findEtfs() {
        //모든 주식 데이터 조회
        List<Etf> etfs = etfRepository.findAll();
        // 조회된 ETF 엔티티 리스트를 ETFDTO 리스트로 변환
        return etfs.stream()
                .map(EtfDTO::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 변환된 DTO를 리스트로 반환
    }
    public Optional<EtfDTO> findEtf(int etfId) {
        // ID로 주식 조회 후, ETFDTO로 변환하여 반환
        return etfRepository.findById(etfId)
                .map(EtfDTO::fromEntity); // 엔티티를 DTO로 변환
    }
    // ETF 매수 로직
    @Transactional
    public EtfTransactionDTO buyEtf(int etfId, int shareCount, Long studentId) {
        // 주식 정보 가져오기
        Optional<Etf> etfOptional = etfRepository.findById(etfId);
        if (etfOptional.isEmpty()) {
            throw new IllegalArgumentException("주식이 존재하지 않습니다.");
        }
        Etf etf = etfOptional.get();

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
        if (etf.getRemain_qty() < shareCount) {
            throw new IllegalArgumentException("남은 수량이 부족합니다.");
        }


        // 주식 현재 가격 가져오기
        int price_per = etf.getPrice_per();
        if (price_per <= 0) {
            throw new IllegalStateException("주식 가격이 설정되지 않았습니다.");
        }

        // 총 금액 계산
        int totalAmount = price_per * shareCount;

        // 로그로 값 확인
        System.out.println("현재 주식 가격: " + price_per);
        System.out.println("총 구매 금액: " + totalAmount);


        // 주식의 재고 수량 차감
        etf.setRemain_qty(etf.getRemain_qty() - shareCount);
        etfRepository.save(etf);


        // 학생이 현재 보유한 해당 주식 수량 조회
        Integer owned_qty = etfTransactionRepository.findTotalSharesByStudentAndEtf(studentId, etfId, TransType.BUY);
        if (owned_qty == null) {
            owned_qty = 0; // 처음 구매라면 0으로 설정
        }

        // 기존 보유량 + 새로 매수한 수량
        int updatedOwnedQty = owned_qty + shareCount;



        // 새로운 매수 거래 생성
        EtfTransaction transaction = new EtfTransaction();
        transaction.setEtf(etf);
        transaction.setStudent(student);
        transaction.setShare_count(shareCount);
        transaction.setTransType(TransType.BUY);
        transaction.setTrans_date(new Timestamp(System.currentTimeMillis()));  //날짜
        transaction.setOwned_qty(updatedOwnedQty); // 기존 보유량 + 새로 매수한 수량
        transaction.setTotal_amt(totalAmount);
        transaction.setPurchase_prc(price_per); // 현재 가격을 그대로 저장

        etfTransactionRepository.save(transaction);

        // 🟢 주식의 현재 가격을 업데이트
        etf.setPrice_per(price_per);
        etfRepository.save(etf);

        return EtfTransactionDTO.fromEntity(transaction, updatedOwnedQty);
    }
}
