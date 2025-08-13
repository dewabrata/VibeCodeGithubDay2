package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log_produk", schema = "projectz")
public class LogProduk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_produk")
    private Long idProduk;

    @Column(name = "id_kategori_produk", nullable = false)
    private Long idKategoriProduk;

    @Column(name = "nama_produk", nullable = false, length = 50)
    private String namaProduk;

    @Column(name = "merk", nullable = false, length = 50)
    private String merk;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "warna", nullable = false, length = 30)
    private String warna;

    @Column(name = "deskripsi_produk", nullable = false, length = 255)
    private String deskripsiProduk;

    @Column(name = "stok", nullable = false)
    private Integer stok;

    @Column(name = "flag", nullable = false, length = 1)
    private String flag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;
}
