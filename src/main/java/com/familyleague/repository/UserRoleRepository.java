package com.familyleague.repository;

import com.familyleague.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur.role.code FROM UserRole ur WHERE ur.user.id = :userId")
    Set<String> findRoleCodesByUserId(Long userId);
}
