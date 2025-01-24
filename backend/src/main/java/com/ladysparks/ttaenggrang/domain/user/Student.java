package com.ladysparks.ttaenggrang.domain.user;

import jakarta.persistence.*;

import java.sql.Timestamp;

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 학생 ID

    @Column(length = 20)
    private String account_id;

    @Column
    private byte[] password;

    @Column(length = 100)
    private String name;

    @Column(length = 2083)
    private byte[] profile_image;  // 이미지 파일 경로

    @Column
    private Timestamp created_at;
}
