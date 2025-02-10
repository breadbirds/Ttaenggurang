package com.ladysparks.ttaenggrang.domain.notification.entity;

import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_student_id")
    private Student senderStudent;

    @ManyToOne
    @JoinColumn(name = "sender_teacher_id")
    private Teacher senderTeacher;

    @ManyToOne
    @JoinColumn(name = "receiver_student_id")
    private Student receiverStudent;

    @ManyToOne
    @JoinColumn(name = "receiver_teacher_id")
    private Teacher receiverTeacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "read_at")
    private Timestamp readAt;

}
