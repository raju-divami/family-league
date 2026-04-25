package com.familyleague.repository;

import com.familyleague.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

    List<PointTransaction> findBySeasonIdAndUserId(Long seasonId, Long userId);

    List<PointTransaction> findBySeasonIdAndUserIdOrderByCreatedAtDesc(Long seasonId, Long userId);

    @Query("SELECT SUM(pt.points) FROM PointTransaction pt WHERE pt.season.id = :seasonId AND pt.user.id = :userId")
    Integer sumPointsBySeasonAndUser(Long seasonId, Long userId);

    boolean existsBySourceTypeAndSourceIdAndUserId(String sourceType, Long sourceId, Long userId);
}
