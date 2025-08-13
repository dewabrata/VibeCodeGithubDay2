package com.projectz.repository;

import com.projectz.entity.LogSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogSupplierRepository extends JpaRepository<LogSupplier, Long> {
}
