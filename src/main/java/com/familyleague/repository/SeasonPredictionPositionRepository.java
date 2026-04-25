package com.familyleague.repository;

import com.familyleague.entity.SeasonPredictionPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonPredictionPositionRepository extends JpaRepository<SeasonPredictionPosition, Long> {

    List<SeasonPredictionPosition> findByPredictionIdOrderByRankPosition(Long predictionId);

    void deleteByPredictionId(Long predictionId);
}
