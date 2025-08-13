package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "mst_user", schema = "projectz",
       uniqueConstraints = {
           @UniqueConstraint(name = "UX_MstUser__Username", columnNames = "Username"),
           @UniqueConstraint(name = "UX_MstUser__Email", columnNames = "Email")
       })
public class MstUser extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_akses")
    private MstAkses akses;

    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "nama_lengkap", nullable = false, length = 70)
    private String namaLengkap;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "no_hp", nullable = false, length = 18)
    private String noHp;

    @Column(name = "alamat", nullable = false, length = 255)
    private String alamat;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(name = "is_registered")
    private Boolean isRegistered;

    @Column(name = "otp", length = 64)
    private String otp;

    @Column(name = "token_estafet", length = 64)
    private String tokenEstafet;

    @Column(name = "link_image", length = 256)
    private String linkImage;

    @Column(name = "path_image", length = 256)
    private String pathImage;
}
