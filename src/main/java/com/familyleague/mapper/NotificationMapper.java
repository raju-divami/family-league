package com.familyleague.mapper;

import com.familyleague.dto.response.NotificationResponse;
import com.familyleague.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);
}
