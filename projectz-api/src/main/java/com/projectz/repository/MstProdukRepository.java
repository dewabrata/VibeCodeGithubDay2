package com.projectz.repository;

import com.projectz.entity.MstProduk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstProdukRepository extends JpaRepository<MstProduk, Long> {
}
