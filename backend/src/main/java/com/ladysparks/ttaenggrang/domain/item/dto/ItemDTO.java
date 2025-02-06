package com.ladysparks.ttaenggrang.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"id", })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data // @Getter + @Setter
public class ItemDTO {

    private Long id;
    private Long sellerId;
    private Long teacherId;
    private String name;
    private String description;
    private String image;
    private int price;
    private int quantity;
    private boolean isApproved;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
