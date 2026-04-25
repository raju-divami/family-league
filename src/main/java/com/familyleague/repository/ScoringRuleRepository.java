package com.familyleague.repository;

import com.familyleague.entity.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoringRuleRepository extends JpaRepository<ScoringRule, Long> {

    Optional<ScoringRule> findByCode(String code);
}
