package com.familyleague.repository;

import com.familyleague.entity.MatchPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchPredictionRepository extends JpaRepository<MatchPrediction, Long> {

    Optional<MatchPrediction> findByMatchIdAndUserId(Long matchId, Long userId);

    List<MatchPrediction> findByMatchIdAndStatus(Long matchId, String status);

    List<MatchPrediction> findByMatchId(Long matchId);

    List<MatchPrediction> findBySeasonIdAndUserId(Long seasonId, Long userId);

    boolean existsByMatchIdAndUserId(Long matchId, Long userId);
}
