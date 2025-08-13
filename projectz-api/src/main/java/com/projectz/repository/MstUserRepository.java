package com.projectz.repository;

import com.projectz.entity.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MstUserRepository extends JpaRepository<MstUser, Long> {
    Optional<MstUser> findByUsername(String username);
    Optional<MstUser> findByEmail(String email);
}
