package com.familyleague.mapper;

import com.familyleague.dto.response.LeaderboardResponse;
import com.familyleague.dto.response.PointTransactionResponse;
import com.familyleague.entity.Leaderboard;
import com.familyleague.entity.PointTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaderboardMapper {

    @Mapping(target = "seasonId", source = "season.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userDisplayName", ignore = true)
    LeaderboardResponse toResponse(Leaderboard leaderboard);

    PointTransactionResponse toTransactionResponse(PointTransaction transaction);
}
