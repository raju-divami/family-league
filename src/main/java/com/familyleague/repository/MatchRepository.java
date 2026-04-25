package com.familyleague.repository;

import com.familyleague.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByIdAndDeletedFalse(Long id);

    Page<Match> findBySeasonIdAndDeletedFalse(Long seasonId, Pageable pageable);

    Page<Match> findBySeasonIdOrderByStartTimeAsc(Long seasonId, Pageable pageable);

    List<Match> findByStatusAndStartTimeBefore(String status, LocalDateTime cutoff);

    List<Match> findBySeasonIdAndStatusAndDeletedFalse(Long seasonId, String status);
}
