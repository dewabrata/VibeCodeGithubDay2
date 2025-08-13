package com.projectz.repository;

import com.projectz.entity.MstAkses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstAksesRepository extends JpaRepository<MstAkses, Long> {
}
