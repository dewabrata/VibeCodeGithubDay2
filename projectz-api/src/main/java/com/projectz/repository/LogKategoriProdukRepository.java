package com.projectz.repository;

import com.projectz.entity.LogKategoriProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogKategoriProdukRepository extends JpaRepository<LogKategoriProduk, Long> {
}
