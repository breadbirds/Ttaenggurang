package com.ladysparks.ttaenggrang.domain.notification.dto;

import com.ladysparks.ttaenggrang.domain.notification.entity.NotificationStatus;
import com.ladysparks.ttaenggrang.domain.notification.entity.NotificationType;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private Long senderStudentId;
    private Long senderTeacherId;
    private Long receiverStudentId;
    private Long receiverTeacherId;
    private NotificationType notificationType;
    private String title;
    private String message;
    private NotificationStatus status;
    private Timestamp createdAt;
    private Timestamp readAt;

}
