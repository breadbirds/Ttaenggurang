package com.ladysparks.ttaenggrang.domain.notification.mapper;

import com.ladysparks.ttaenggrang.domain.notification.dto.NotificationDTO;
import com.ladysparks.ttaenggrang.domain.notification.entity.Notification;
import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "senderStudent.id", target = "senderStudentId")
    @Mapping(source = "senderTeacher.id", target = "senderTeacherId")
    @Mapping(source = "receiverStudent.id", target = "receiverStudentId")
    @Mapping(source = "receiverTeacher.id", target = "receiverTeacherId")
    NotificationDTO toDto(Notification notification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senderStudent", expression = "java(notificationDTO.getSenderStudentId() != null ? new Student(notificationDTO.getSenderStudentId()) : null)")
    @Mapping(target = "senderTeacher", expression = "java(notificationDTO.getSenderTeacherId() != null ? new Teacher(notificationDTO.getSenderTeacherId()) : null)")
    @Mapping(target = "receiverStudent", expression = "java(notificationDTO.getReceiverStudentId() != null ? new Student(notificationDTO.getReceiverStudentId()) : null)")
    @Mapping(target = "receiverTeacher", expression = "java(notificationDTO.getReceiverTeacherId() != null ? new Teacher(notificationDTO.getReceiverTeacherId()) : null)")
    Notification toEntity(NotificationDTO notificationDTO);

}
