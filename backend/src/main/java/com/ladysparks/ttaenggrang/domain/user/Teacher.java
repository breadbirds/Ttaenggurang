package com.ladysparks.ttaenggrang.domain.user;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 선생님 ID

    @Column(length = 50)
    private String name;

    @Column
    private byte[] password;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String school;

    @Column
    private Timestamp created_at;
}
