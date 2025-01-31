package com.ladysparks.ttaenggrang.domain.item;
import com.ladysparks.ttaenggrang.domain.user.Student;
import com.ladysparks.ttaenggrang.domain.user.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_transaction")
public class ItemTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Student buyer;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    private Timestamp createdAt;

}
