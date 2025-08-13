package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log_supplier", schema = "projectz")
public class LogSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_supplier")
    private Long idSupplier;

    @Column(name = "nama_supplier", nullable = false, length = 50)
    private String namaSupplier;

    @Column(name = "alamat_supplier", nullable = false, length = 255)
    private String alamatSupplier;

    @Column(name = "flag", nullable = false, length = 1)
    private String flag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;
}
