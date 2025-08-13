package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_menu", schema = "projectz")
public class MstMenu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group_menu")
    private MstGroupMenu groupMenu;

    @Column(name = "nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "path", nullable = false, length = 50)
    private String path;

    @Column(name = "deskripsi", nullable = false, length = 255)
    private String deskripsi;
}
