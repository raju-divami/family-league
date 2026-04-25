package com.familyleague.mapper;

import com.familyleague.dto.response.PointTransactionResponse;
import com.familyleague.entity.PointTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointTransactionMapper {

    PointTransactionResponse toResponse(PointTransaction transaction);
}
