package com.familyleague.repository;

import com.familyleague.entity.SeasonMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonMemberRepository extends JpaRepository<SeasonMember, Long> {

    Optional<SeasonMember> findBySeasonIdAndUserId(Long seasonId, Long userId);

    boolean existsBySeasonIdAndUserId(Long seasonId, Long userId);

    List<SeasonMember> findBySeasonId(Long seasonId);

    List<SeasonMember> findBySeasonIdAndStatus(Long seasonId, String status);
}
