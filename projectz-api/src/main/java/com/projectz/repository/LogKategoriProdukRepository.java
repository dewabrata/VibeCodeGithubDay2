package com.projectz.repository;

import com.projectz.entity.LogKategoriProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogKategoriProdukRepository extends JpaRepository<LogKategoriProduk, Long> {
    
    Page<LogKategoriProduk> findByIdKategoriProduk(Long idKategoriProduk, Pageable pageable);
    
    Page<LogKategoriProduk> findByFlag(String flag, Pageable pageable);
    
    Page<LogKategoriProduk> findByCreatedAtBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
    
    @Query("SELECT lkp FROM LogKategoriProduk lkp WHERE " +
           "(:idKategoriProduk IS NULL OR lkp.idKategoriProduk = :idKategoriProduk) AND " +
           "(:flag IS NULL OR lkp.flag = :flag) AND " +
           "(:dateFrom IS NULL OR lkp.createdAt >= :dateFrom) AND " +
           "(:dateTo IS NULL OR lkp.createdAt <= :dateTo)")
    Page<LogKategoriProduk> findByFilters(@Param("idKategoriProduk") Long idKategoriProduk,
                                          @Param("flag") String flag,
                                          @Param("dateFrom") LocalDateTime dateFrom,
                                          @Param("dateTo") LocalDateTime dateTo,
                                          Pageable pageable);
}
