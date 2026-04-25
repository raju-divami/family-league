package com.familyleague.mapper;

import com.familyleague.dto.request.CreatePlayerRequest;
import com.familyleague.dto.response.PlayerResponse;
import com.familyleague.entity.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    Player toEntity(CreatePlayerRequest request);

    PlayerResponse toResponse(Player player);
}
