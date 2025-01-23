package com.ladysparks.ttaenggrang.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EtfDTO {
    private Long id;
    private Long teacher_id;
    private String name;
    private int price_per_share;
    private int total_qty;
    private int remain_qty;
    private String description;
    private Timestamp created_at;
    private Timestamp update_at;

}
