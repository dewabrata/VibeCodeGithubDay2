package com.projectz.repository;

import com.projectz.entity.LogProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogProdukRepository extends JpaRepository<LogProduk, Long> {
    
    Page<LogProduk> findByIdProduk(Long idProduk, Pageable pageable);
    
    Page<LogProduk> findByFlag(String flag, Pageable pageable);
    
    Page<LogProduk> findByCreatedAtBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
    
    @Query("SELECT lp FROM LogProduk lp WHERE " +
           "(:idProduk IS NULL OR lp.idProduk = :idProduk) AND " +
           "(:flag IS NULL OR lp.flag = :flag) AND " +
           "(:dateFrom IS NULL OR lp.createdAt >= :dateFrom) AND " +
           "(:dateTo IS NULL OR lp.createdAt <= :dateTo)")
    Page<LogProduk> findByFilters(@Param("idProduk") Long idProduk,
                                  @Param("flag") String flag,
                                  @Param("dateFrom") LocalDateTime dateFrom,
                                  @Param("dateTo") LocalDateTime dateTo,
                                  Pageable pageable);
}
