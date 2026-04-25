package com.familyleague.repository;

import com.familyleague.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByIdAndDeletedFalse(Long id);

    Page<Player> findByDeletedFalse(Pageable pageable);

    boolean existsByCode(String code);
}
