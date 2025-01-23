package com.ladysparks.ttaenggrang.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder // 객체 생성(@Builder를 이용하기 위해 @AllArgsConstructor와 @NoArgsConstructor를 같이 처리해야 컴파일 에러가 발생하지 않음)
@AllArgsConstructor
@NoArgsConstructor
@Entity // DB의 테이블을 뜻함(Spring Data JPA 에서는 반드시 @Entity 어노테이션을 추가해야 함)
@Table(name = "item") // DB 테이블의 이름을 명시(테이블 명과 클래스 명이 동일한 경우 생략 가능)
public class Item {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 전략: MySQL의 AUTO_INCREMENT를 사용
    private int id;

//    @ManyToOne // Many: Item, One: Student
//    @JoinColumn(name = "sellerId") // FK
//    private Student student;

    @Column(nullable = false, length = 100) // Column과 반대로 테이블에 컬럼으로 생성되지 않는 필드의 경우엔 @Transient 어노테이션을 적용
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 2083)
    private String image;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = true)
    private boolean isApproved;

    @CreationTimestamp // 값이 입력될때 혹은 업데이트될때 자동으로 시간이 들어간다.
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

}
