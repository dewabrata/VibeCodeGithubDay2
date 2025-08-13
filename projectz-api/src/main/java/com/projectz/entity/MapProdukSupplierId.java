package com.projectz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MapProdukSupplierId implements Serializable {
    private static final long serialVersionUID = 453378169436321732L;
    @Column(name = "id_produk", nullable = false)
    private Long idProduk;

    @Column(name = "id_supplier", nullable = false)
    private Long idSupplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapProdukSupplierId that = (MapProdukSupplierId) o;
        return Objects.equals(idProduk, that.idProduk) && Objects.equals(idSupplier, that.idSupplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduk, idSupplier);
    }
}
