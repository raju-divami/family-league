package com.familyleague.repository;

import com.familyleague.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    Optional<Leaderboard> findBySeasonIdAndUserId(Long seasonId, Long userId);

    List<Leaderboard> findBySeasonId(Long seasonId);

    List<Leaderboard> findBySeasonIdOrderByTotalPointsDesc(Long seasonId);

    List<Leaderboard> findBySeasonIdOrderByRankNoAsc(Long seasonId);
}
