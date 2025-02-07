package com.ladysparks.ttaenggrang.domain.tax.entity;

import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaxPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tax_id", nullable = false)
    private Tax tax;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxPaymentStatus status;

    @CreationTimestamp
    private Timestamp createdAt;

}
