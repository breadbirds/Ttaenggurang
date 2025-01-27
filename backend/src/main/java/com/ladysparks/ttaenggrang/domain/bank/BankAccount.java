package com.ladysparks.ttaenggrang.domain.bank;

import com.ladysparks.ttaenggrang.domain.user.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private int balance;

    @CreationTimestamp
    private Timestamp createdAt;

}
