package com.projectz.repository;

import com.projectz.entity.MstKategoriProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstKategoriProdukRepository extends JpaRepository<MstKategoriProduk, Long> {
}
