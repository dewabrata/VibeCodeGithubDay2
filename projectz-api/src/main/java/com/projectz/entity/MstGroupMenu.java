package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_group_menu", schema = "projectz")
public class MstGroupMenu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "deskripsi", nullable = false, length = 255)
    private String deskripsi;
}
