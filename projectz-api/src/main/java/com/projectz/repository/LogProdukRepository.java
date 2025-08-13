package com.projectz.repository;

import com.projectz.entity.LogProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogProdukRepository extends JpaRepository<LogProduk, Long> {
}
