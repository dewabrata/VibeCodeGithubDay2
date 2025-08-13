package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log_kategori_produk", schema = "projectz")
public class LogKategoriProduk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_kategori_produk")
    private Long idKategoriProduk;

    @Column(name = "nama_produk", nullable = false, length = 50)
    private String namaProduk;

    @Column(name = "deskripsi", nullable = false, length = 255)
    private String deskripsi;

    @Column(name = "notes", nullable = false, length = 255)
    private String notes;

    @Column(name = "flag", nullable = false, length = 1)
    private String flag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;
}
