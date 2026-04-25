package com.familyleague.repository;

import com.familyleague.entity.LeagueSeason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason, Long> {

    Page<LeagueSeason> findByLeagueIdAndDeletedFalse(Long leagueId, Pageable pageable);

    Optional<LeagueSeason> findByIdAndDeletedFalse(Long id);

    Optional<LeagueSeason> findByLeagueIdAndSeasonCodeAndDeletedFalse(Long leagueId, String seasonCode);

    List<LeagueSeason> findByStatusAndDeletedFalse(String status);

    Page<LeagueSeason> findAllByDeletedFalse(Pageable pageable);
}
