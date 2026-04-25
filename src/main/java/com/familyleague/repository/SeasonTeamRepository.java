package com.familyleague.repository;

import com.familyleague.entity.SeasonTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long> {

    List<SeasonTeam> findBySeasonId(Long seasonId);

    boolean existsBySeasonIdAndTeamId(Long seasonId, Long teamId);
}
