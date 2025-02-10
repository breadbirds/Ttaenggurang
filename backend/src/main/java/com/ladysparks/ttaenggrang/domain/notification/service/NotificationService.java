package com.ladysparks.ttaenggrang.domain.notification.service;

import com.ladysparks.ttaenggrang.domain.notification.dto.NotificationDTO;
import com.ladysparks.ttaenggrang.domain.notification.entity.Notification;
import com.ladysparks.ttaenggrang.domain.notification.entity.NotificationStatus;
import com.ladysparks.ttaenggrang.domain.notification.mapper.NotificationMapper;
import com.ladysparks.ttaenggrang.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    /**
     * 알림 저장
     */
    @Transactional
    public NotificationDTO saveNotification(NotificationDTO dto) {
        Notification notification = notificationMapper.toEntity(dto);
        notification.setCreatedAt(Timestamp.from(Instant.now()));
        notification.setStatus(NotificationStatus.UNREAD);
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    /**
     * 특정 학생의 읽지 않은 알림 조회
     */
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotificationsForStudent(Long studentId) {
        return notificationRepository.findByReceiverStudentIdAndStatus(studentId, NotificationStatus.UNREAD)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 교사의 읽지 않은 알림 조회
     */
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotificationsForTeacher(Long teacherId) {
        return notificationRepository.findByReceiverTeacherIdAndStatus(teacherId, NotificationStatus.UNREAD)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 알림 읽음 상태로 변경
     */
    @Transactional
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 알림을 찾을 수 없습니다."));
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(Timestamp.from(Instant.now()));
        notificationRepository.save(notification);
    }

}

