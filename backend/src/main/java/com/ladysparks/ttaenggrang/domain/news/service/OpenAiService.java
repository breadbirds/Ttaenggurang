package com.ladysparks.ttaenggrang.domain.news.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

//@Service
//public class OpenAiService {
//

//    @Value("${openai.api.key}")
//    private String apiKey;
//
//    private static final String API_URL = "https://api.openai.com/v1/completions";
//
//    public String generateNews(String prompt) {
//        // API 요청을 위한 RestTemplate 초기화
//        RestTemplate restTemplate = new RestTemplate();
//
//        // API 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("Content-Type", "application/json");
//
//        // API 요청 바디 설정
//        String body = "{ \"model\": \"text-davinci-003\", \"prompt\": \"" + prompt + "\", \"max_tokens\": 150 }";
//
//        // API 요청
//        HttpEntity<String> entity = new HttpEntity<>(body, headers);
//        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
//
//        // 응답에서 생성된 뉴스 기사 추출
//        return response.getBody(); // 실제 구현 시, 필요한 데이터만 추출
//    }
//}


