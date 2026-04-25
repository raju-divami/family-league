package com.familyleague.repository;

import com.familyleague.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByIdAndDeletedFalse(Long id);

    Page<Team> findByDeletedFalse(Pageable pageable);

    boolean existsByCode(String code);
}
