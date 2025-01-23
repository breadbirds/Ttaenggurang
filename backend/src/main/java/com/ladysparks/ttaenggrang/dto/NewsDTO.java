package com.ladysparks.ttaenggrang.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class NewsDTO {
    private Long id;            // 뉴스 ID
    private Long stock_id;      // 주식 ID
    private String title;       // 제목 ID
    private String content;
    private float impact;
    private String created_by;
    private Timestamp created_at;
   //호재,악재 넣어야함


}
