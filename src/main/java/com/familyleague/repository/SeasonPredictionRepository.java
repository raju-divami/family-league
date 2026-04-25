package com.familyleague.repository;

import com.familyleague.entity.SeasonPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonPredictionRepository extends JpaRepository<SeasonPrediction, Long> {

    Optional<SeasonPrediction> findBySeasonIdAndUserId(Long seasonId, Long userId);

    List<SeasonPrediction> findBySeasonId(Long seasonId);

    boolean existsBySeasonIdAndUserId(Long seasonId, Long userId);
}
