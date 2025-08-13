package com.projectz.repository;

import com.projectz.entity.MapProdukSupplier;
import com.projectz.entity.MapProdukSupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapProdukSupplierRepository extends JpaRepository<MapProdukSupplier, MapProdukSupplierId> {
}
