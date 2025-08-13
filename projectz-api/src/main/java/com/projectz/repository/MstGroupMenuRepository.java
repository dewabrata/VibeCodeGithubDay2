package com.projectz.repository;

import com.projectz.entity.MstGroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstGroupMenuRepository extends JpaRepository<MstGroupMenu, Long> {
}
