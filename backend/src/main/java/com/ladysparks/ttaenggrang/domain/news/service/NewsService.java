package com.ladysparks.ttaenggrang.domain.news.service;

import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


//@Service
//public class NewsService {
//
//    @Autowired
//    private OpenAiService openAiService;
//
//    @Autowired
//    private StockRepository stockRepository;

//
//    public void generateAndPublishNews() {
//        // 주식 리스트 가져오기
//        List<Stock> stocks = stockRepository.findAll();
//
//        // 랜덤으로 주식 선택
//        Random random = new Random();
//        Stock selectedStock = stocks.get(random.nextInt(stocks.size()));
//
//        String companyName = selectedStock.getName();
//
//        // 호재 뉴스 생성
//        String positivePrompt = "Create a positive news article about the company " + companyName + ", such as a successful product launch, positive earnings report, or new business venture.";
//        String positiveNews = openAiService.generateNews(positivePrompt);
//
//        // 악재 뉴스 생성
//        String negativePrompt = "Create a negative news article about the company " + companyName + ", such as a financial loss, legal trouble, or management scandal.";
//        String negativeNews = openAiService.generateNews(negativePrompt);
//
//        // 생성된 뉴스 출력
//        System.out.println("Negative News: " + negativeNews);
//
//        // 추가적으로, 뉴스 객체를 DB에 저장할 수 있습니다.
//        // 예: newsRepository.save(new News(positiveNews, negativeNews, selectedStock));
//    }

//}



