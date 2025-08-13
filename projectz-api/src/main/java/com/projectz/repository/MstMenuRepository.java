package com.projectz.repository;

import com.projectz.entity.MstMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstMenuRepository extends JpaRepository<MstMenu, Long> {
}
