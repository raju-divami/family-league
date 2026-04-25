package com.familyleague.service;

import com.familyleague.dto.response.LeaderboardResponse;
import com.familyleague.dto.response.PointTransactionResponse;

import java.util.List;

public interface LeaderboardService {

    void recalculateLeaderboard(Long seasonId);

    List<LeaderboardResponse> getLeaderboard(Long seasonId);

    List<PointTransactionResponse> getPointHistory(Long seasonId, Long userId);
}
