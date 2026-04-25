package com.familyleague.repository;

import com.familyleague.entity.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {

    List<TeamPlayer> findBySeasonTeamId(Long seasonTeamId);

    @Query("SELECT tp FROM TeamPlayer tp WHERE tp.seasonTeam.season.id = :seasonId")
    List<TeamPlayer> findBySeasonId(Long seasonId);

    boolean existsBySeasonTeamIdAndPlayerId(Long seasonTeamId, Long playerId);
}
