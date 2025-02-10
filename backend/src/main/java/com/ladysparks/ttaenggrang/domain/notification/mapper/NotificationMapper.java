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
    @Mapping(source = "senderStudentId", target = "senderStudent.id")
    @Mapping(source = "senderTeacherId", target = "senderTeacher.id")
    @Mapping(source = "receiverStudentId", target = "receiverStudent.id")
    @Mapping(source = "receiverTeacherId", target = "receiverTeacher.id")
    Notification toEntity(NotificationDTO notificationDTO);

}
