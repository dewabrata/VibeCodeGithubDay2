package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "map_produk_supplier", schema = "projectz")
public class MapProdukSupplier {
    @EmbeddedId
    private MapProdukSupplierId id;

    @MapsId("idProduk")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_produk", nullable = false)
    private MstProduk produk;

    @MapsId("idSupplier")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_supplier", nullable = false)
    private MstSupplier supplier;
}
