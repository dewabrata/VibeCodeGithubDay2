-- H2 Database Migration V1 - Initial Schema

-- Create schema
CREATE SCHEMA IF NOT EXISTS projectz;

-- 3.1 MstAkses
CREATE TABLE projectz.mst_akses (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    nama          VARCHAR(50)           NOT NULL,
    deskripsi     VARCHAR(255)          NOT NULL,
    created_by     BIGINT                NOT NULL,
    created_date   TIMESTAMP             NULL,
    modified_by    BIGINT                NULL,
    modified_date  TIMESTAMP             NULL,
    CONSTRAINT PK_MstAkses PRIMARY KEY (id)
);

-- 3.2 MstGroupMenu
CREATE TABLE projectz.mst_group_menu (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    nama          VARCHAR(50)           NOT NULL,
    deskripsi     VARCHAR(255)          NOT NULL,
    created_by     BIGINT                NOT NULL,
    created_date   TIMESTAMP             NULL,
    modified_by    BIGINT                NULL,
    modified_date  TIMESTAMP             NULL,
    CONSTRAINT PK_MstGroupMenu PRIMARY KEY (id)
);

-- 3.3 MstMenu
CREATE TABLE projectz.mst_menu (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    id_group_menu   BIGINT                NULL,
    nama          VARCHAR(50)           NOT NULL,
    path          VARCHAR(50)           NOT NULL,
    deskripsi     VARCHAR(255)          NOT NULL,
    created_by     BIGINT                NOT NULL,
    created_date   TIMESTAMP             NULL,
    modified_by    BIGINT                NULL,
    modified_date  TIMESTAMP             NULL,
    CONSTRAINT PK_MstMenu PRIMARY KEY (id),
    CONSTRAINT FK_MstMenu__MstGroupMenu
        FOREIGN KEY (id_group_menu) REFERENCES projectz.mst_group_menu(id)
);

CREATE INDEX IX_MstMenu__IDGroupMenu ON projectz.mst_menu(id_group_menu);

-- 3.4 MstKategoriProduk
CREATE TABLE projectz.mst_kategori_produk (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    nama_produk    VARCHAR(50)           NOT NULL,
    deskripsi     VARCHAR(255)          NOT NULL,
    notes         VARCHAR(255)          NOT NULL,
    created_at     TIMESTAMP             NOT NULL,
    created_by     BIGINT                NOT NULL,
    modified_at    TIMESTAMP             NULL,
    modified_by    BIGINT                NULL,
    CONSTRAINT PK_MstKategoriProduk PRIMARY KEY (id)
);

-- 3.5 MstProduk
CREATE TABLE projectz.mst_produk (
    id               BIGINT AUTO_INCREMENT NOT NULL,
    id_kategori_produk BIGINT                NOT NULL,
    nama_produk       VARCHAR(50)           NOT NULL,
    merk             VARCHAR(50)           NOT NULL,
    model            VARCHAR(50)           NOT NULL,
    warna            VARCHAR(30)           NOT NULL,
    deskripsi_produk  VARCHAR(255)          NOT NULL,
    stok             INT                   NOT NULL,
    created_at        TIMESTAMP             NOT NULL,
    created_by        BIGINT                NOT NULL,
    modified_at       TIMESTAMP             NULL,
    modified_by       BIGINT                NULL,
    CONSTRAINT PK_MstProduk PRIMARY KEY (id),
    CONSTRAINT FK_MstProduk__MstKategoriProduk
        FOREIGN KEY (id_kategori_produk) REFERENCES projectz.mst_kategori_produk(id)
);

CREATE INDEX IX_MstProduk__IDKategoriProduk ON projectz.mst_produk(id_kategori_produk);

-- 3.6 MstSupplier
CREATE TABLE projectz.mst_supplier (
    id              BIGINT AUTO_INCREMENT NOT NULL,
    nama_supplier    VARCHAR(50)           NOT NULL,
    alamat_supplier  VARCHAR(255)          NOT NULL,
    created_at       TIMESTAMP             NOT NULL,
    created_by       BIGINT                NOT NULL,
    modified_at      TIMESTAMP             NULL,
    modified_by      BIGINT                NULL,
    CONSTRAINT PK_MstSupplier PRIMARY KEY (id)
);

-- 3.7 MstUser
CREATE TABLE projectz.mst_user (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    id_akses       BIGINT                NULL,
    username      VARCHAR(16)           NOT NULL,
    password      VARCHAR(64)           NOT NULL,
    nama_lengkap   VARCHAR(70)           NOT NULL,
    email         VARCHAR(256)          NOT NULL,
    no_hp          VARCHAR(18)           NOT NULL,
    alamat        VARCHAR(255)          NOT NULL,
    tanggal_lahir  DATE                  NOT NULL,
    is_registered  BOOLEAN               NULL,
    otp           VARCHAR(64)           NULL,
    token_estafet  VARCHAR(64)           NULL,
    link_image     VARCHAR(256)          NULL,
    path_image     VARCHAR(256)          NULL,
    created_by     BIGINT                NOT NULL,
    created_date   TIMESTAMP             NULL,
    modified_by    BIGINT                NULL,
    modified_date  TIMESTAMP             NULL,
    CONSTRAINT PK_MstUser PRIMARY KEY (id),
    CONSTRAINT FK_MstUser__MstAkses
        FOREIGN KEY (id_akses) REFERENCES projectz.mst_akses(id)
);

CREATE INDEX IX_MstUser__IDAkses ON projectz.mst_user(id_akses);
CREATE UNIQUE INDEX UX_MstUser__Username ON projectz.mst_user(username);
CREATE UNIQUE INDEX UX_MstUser__Email ON projectz.mst_user(email);

-- 3.8 MapAksesMenu (M2M)
CREATE TABLE projectz.map_akses_menu (
    id_akses BIGINT NOT NULL,
    id_menu  BIGINT NOT NULL,
    CONSTRAINT PK_MapAksesMenu PRIMARY KEY (id_akses, id_menu),
    CONSTRAINT FK_MapAksesMenu__MstAkses
        FOREIGN KEY (id_akses) REFERENCES projectz.mst_akses(id),
    CONSTRAINT FK_MapAksesMenu__MstMenu
        FOREIGN KEY (id_menu)  REFERENCES projectz.mst_menu(id)
);

-- 3.9 MapProdukSupplier (M2M)
CREATE TABLE projectz.map_produk_supplier (
    id_produk   BIGINT NOT NULL,
    id_supplier BIGINT NOT NULL,
    CONSTRAINT PK_MapProdukSupplier PRIMARY KEY (id_produk, id_supplier),
    CONSTRAINT FK_MapProdukSupplier__MstProduk
        FOREIGN KEY (id_produk)   REFERENCES projectz.mst_produk(id),
    CONSTRAINT FK_MapProdukSupplier__MstSupplier
        FOREIGN KEY (id_supplier) REFERENCES projectz.mst_supplier(id)
);

-- 3.10 LogKategoriProduk (Audit)
CREATE TABLE projectz.log_kategori_produk (
    id               BIGINT AUTO_INCREMENT NOT NULL,
    id_kategori_produk BIGINT                NOT NULL,
    nama_produk       VARCHAR(50)           NOT NULL,
    deskripsi        VARCHAR(255)          NOT NULL,
    notes            VARCHAR(255)          NOT NULL,
    flag             VARCHAR(10)           NOT NULL,
    created_at        TIMESTAMP             NOT NULL,
    created_by        BIGINT                NOT NULL,
    CONSTRAINT PK_LogKategoriProduk PRIMARY KEY (id)
);

CREATE INDEX IX_LogKategoriProduk__IDKategoriProduk ON projectz.log_kategori_produk(id_kategori_produk);
CREATE INDEX IX_LogKategoriProduk__Flag ON projectz.log_kategori_produk(flag);

-- 3.11 LogProduk (Audit)
CREATE TABLE projectz.log_produk (
    id               BIGINT AUTO_INCREMENT NOT NULL,
    id_produk         BIGINT                NOT NULL,
    id_kategori_produk BIGINT                NOT NULL,
    nama_produk       VARCHAR(50)           NOT NULL,
    merk             VARCHAR(50)           NOT NULL,
    model            VARCHAR(50)           NOT NULL,
    warna            VARCHAR(30)           NOT NULL,
    deskripsi_produk  VARCHAR(255)          NOT NULL,
    stok             INT                   NOT NULL,
    flag             VARCHAR(10)           NOT NULL,
    created_at        TIMESTAMP             NOT NULL,
    created_by        BIGINT                NOT NULL,
    CONSTRAINT PK_LogProduk PRIMARY KEY (id)
);

CREATE INDEX IX_LogProduk__IDProduk ON projectz.log_produk(id_produk);
CREATE INDEX IX_LogProduk__Flag ON projectz.log_produk(flag);

-- 3.12 LogSupplier (Audit)
CREATE TABLE projectz.log_supplier (
    id              BIGINT AUTO_INCREMENT NOT NULL,
    id_supplier      BIGINT                NOT NULL,
    nama_supplier    VARCHAR(50)           NOT NULL,
    alamat_supplier  VARCHAR(255)          NOT NULL,
    flag            VARCHAR(10)           NOT NULL,
    created_at       TIMESTAMP             NOT NULL,
    created_by       BIGINT                NOT NULL,
    CONSTRAINT PK_LogSupplier PRIMARY KEY (id)
);

CREATE INDEX IX_LogSupplier__IDSupplier ON projectz.log_supplier(id_supplier);
CREATE INDEX IX_LogSupplier__Flag ON projectz.log_supplier(flag);
