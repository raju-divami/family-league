package com.familyleague.mapper;

import com.familyleague.dto.response.AuditLogResponse;
import com.familyleague.entity.AuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditLogResponse toResponse(AuditLog log);
}
