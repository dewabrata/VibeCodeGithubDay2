package com.projectz.repository;

import com.projectz.entity.MstSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstSupplierRepository extends JpaRepository<MstSupplier, Long> {
}
