package com.projectz.repository;

import com.projectz.entity.LogSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogSupplierRepository extends JpaRepository<LogSupplier, Long> {
    
    Page<LogSupplier> findByIdSupplier(Long idSupplier, Pageable pageable);
    
    Page<LogSupplier> findByFlag(String flag, Pageable pageable);
    
    Page<LogSupplier> findByCreatedAtBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
    
    @Query("SELECT ls FROM LogSupplier ls WHERE " +
           "(:idSupplier IS NULL OR ls.idSupplier = :idSupplier) AND " +
           "(:flag IS NULL OR ls.flag = :flag) AND " +
           "(:dateFrom IS NULL OR ls.createdAt >= :dateFrom) AND " +
           "(:dateTo IS NULL OR ls.createdAt <= :dateTo)")
    Page<LogSupplier> findByFilters(@Param("idSupplier") Long idSupplier,
                                    @Param("flag") String flag,
                                    @Param("dateFrom") LocalDateTime dateFrom,
                                    @Param("dateTo") LocalDateTime dateTo,
                                    Pageable pageable);
}
