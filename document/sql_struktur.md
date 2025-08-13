# SQL Schema & Structure — ProjectZ (ERDJBE26)
*Generated:* 2025-08-12 03:29:49  
*Target DBMS:* SQL Server 2012+  
*Scope:* Master tables, mapping tables (M2M), audit logs, primary/foreign keys, and suggested indexes.

---

## 1) Entity Relationship Overview (ringkas)
- **MstAkses** (1) —< **MstUser** (N); via `MstUser.IDAkses`  
- **MstGroupMenu** (1) —< **MstMenu** (N); via `MstMenu.IDGroupMenu` *(nullable)*  
- **MstKategoriProduk** (1) —< **MstProduk** (N); via `MstProduk.IDKategoriProduk`  
- **MstProduk** (N) —< **MapProdukSupplier** >— (N) **MstSupplier** *(M2M)*  
- **MstAkses** (N) —< **MapAksesMenu** >— (N) **MstMenu** *(M2M)*  

> Catatan: Tabel **LogProduk**, **LogKategoriProduk**, **LogSupplier** menyimpan *snapshot* perubahan (audit log).

---

## 2) Konvensi Umum
- Semua `ID` bertipe `BIGINT` dengan `IDENTITY(1,1)` kecuali di tabel mapping.
- Tanggal audit menggunakan `datetime2(6)`.
- String pakai `varchar(N)` sesuai kebutuhan per entitas.
- Nama constraint mengikuti pola: `PK_<Table>`, `FK_<From>__<To>`, `IX_<Table>__<Cols>`.

---

## 3) DDL — Master & Mapping

```sql
-- SCHEMA
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'projectz')
    EXEC('CREATE SCHEMA projectz');
GO
```

### 3.1 MstAkses
```sql
CREATE TABLE projectz.MstAkses (
    ID            BIGINT IDENTITY(1,1) NOT NULL,
    Nama          VARCHAR(50)          NOT NULL,
    Deskripsi     VARCHAR(255)         NOT NULL,
    CreatedBy     BIGINT               NOT NULL,
    CreatedDate   DATETIME2(6)         NULL,
    ModifiedBy    BIGINT               NULL,
    ModifiedDate  DATETIME2(6)         NULL,
    CONSTRAINT PK_MstAkses PRIMARY KEY (ID)
);
GO
```

### 3.2 MstGroupMenu
```sql
CREATE TABLE projectz.MstGroupMenu (
    ID            BIGINT IDENTITY(1,1) NOT NULL,
    Nama          VARCHAR(50)          NOT NULL,
    Deskripsi     VARCHAR(255)         NOT NULL,
    CreatedBy     BIGINT               NOT NULL,
    CreatedDate   DATETIME2(6)         NULL,
    ModifiedBy    BIGINT               NULL,
    ModifiedDate  DATETIME2(6)         NULL,
    CONSTRAINT PK_MstGroupMenu PRIMARY KEY (ID)
);
GO
```

### 3.3 MstMenu
```sql
CREATE TABLE projectz.MstMenu (
    ID            BIGINT IDENTITY(1,1) NOT NULL,
    IDGroupMenu   BIGINT               NULL,
    Nama          VARCHAR(50)          NOT NULL,
    Path          VARCHAR(50)          NOT NULL,
    Deskripsi     VARCHAR(255)         NOT NULL,
    CreatedBy     BIGINT               NOT NULL,
    CreatedDate   DATETIME2(6)         NULL,
    ModifiedBy    BIGINT               NULL,
    ModifiedDate  DATETIME2(6)         NULL,
    CONSTRAINT PK_MstMenu PRIMARY KEY (ID),
    CONSTRAINT FK_MstMenu__MstGroupMenu
        FOREIGN KEY (IDGroupMenu) REFERENCES projectz.MstGroupMenu(ID)
);
GO

CREATE INDEX IX_MstMenu__IDGroupMenu ON projectz.MstMenu(IDGroupMenu);
GO
```

### 3.4 MstKategoriProduk
```sql
CREATE TABLE projectz.MstKategoriProduk (
    ID            BIGINT IDENTITY(1,1) NOT NULL,
    NamaProduk    VARCHAR(50)          NOT NULL,
    Deskripsi     VARCHAR(255)         NOT NULL,
    Notes         VARCHAR(255)         NOT NULL,
    CreatedAt     DATETIME2(6)         NOT NULL,
    CreatedBy     BIGINT               NOT NULL,
    ModifiedAt    DATETIME2(6)         NULL,
    ModifiedBy    BIGINT               NULL,
    CONSTRAINT PK_MstKategoriProduk PRIMARY KEY (ID)
);
GO
```

### 3.5 MstProduk
```sql
CREATE TABLE projectz.MstProduk (
    ID               BIGINT IDENTITY(1,1) NOT NULL,
    IDKategoriProduk BIGINT               NOT NULL,
    NamaProduk       VARCHAR(50)          NOT NULL,
    Merk             VARCHAR(50)          NOT NULL,
    Model            VARCHAR(50)          NOT NULL,
    Warna            VARCHAR(30)          NOT NULL,
    DeskripsiProduk  VARCHAR(255)         NOT NULL,
    Stok             INT                  NOT NULL,
    CreatedAt        DATETIME2(6)         NOT NULL,
    CreatedBy        BIGINT               NOT NULL,
    ModifiedAt       DATETIME2(6)         NULL,
    ModifiedBy       BIGINT               NULL,
    CONSTRAINT PK_MstProduk PRIMARY KEY (ID),
    CONSTRAINT FK_MstProduk__MstKategoriProduk
        FOREIGN KEY (IDKategoriProduk) REFERENCES projectz.MstKategoriProduk(ID)
);
GO

CREATE INDEX IX_MstProduk__IDKategoriProduk ON projectz.MstProduk(IDKategoriProduk);
GO
```

### 3.6 MstSupplier
```sql
CREATE TABLE projectz.MstSupplier (
    ID              BIGINT IDENTITY(1,1) NOT NULL,
    NamaSupplier    VARCHAR(50)          NOT NULL,
    AlamatSupplier  VARCHAR(255)         NOT NULL,
    CreatedAt       DATETIME2(6)         NOT NULL,
    CreatedBy       BIGINT               NOT NULL,
    ModifiedAt      DATETIME2(6)         NULL,
    ModifiedBy      BIGINT               NULL,
    CONSTRAINT PK_MstSupplier PRIMARY KEY (ID)
);
GO
```

### 3.7 MstUser
```sql
CREATE TABLE projectz.MstUser (
    ID            BIGINT IDENTITY(1,1) NOT NULL,
    IDAkses       BIGINT               NULL,
    Username      VARCHAR(16)          NOT NULL,
    Password      VARCHAR(64)          NOT NULL,
    NamaLengkap   VARCHAR(70)          NOT NULL,
    Email         VARCHAR(256)         NOT NULL,
    NoHp          VARCHAR(18)          NOT NULL,
    Alamat        VARCHAR(255)         NOT NULL,
    TanggalLahir  DATE                 NOT NULL,
    IsRegistered  BIT                  NULL,
    OTP           VARCHAR(64)          NULL,
    TokenEstafet  VARCHAR(64)          NULL,
    LinkImage     VARCHAR(256)         NULL,
    PathImage     VARCHAR(256)         NULL,
    CreatedBy     BIGINT               NOT NULL,
    CreatedDate   DATETIME2(6)         NULL,
    ModifiedBy    BIGINT               NULL,
    ModifiedDate  DATETIME2(6)         NULL,
    CONSTRAINT PK_MstUser PRIMARY KEY (ID),
    CONSTRAINT FK_MstUser__MstAkses
        FOREIGN KEY (IDAkses) REFERENCES projectz.MstAkses(ID)
);
GO

CREATE INDEX IX_MstUser__IDAkses ON projectz.MstUser(IDAkses);
CREATE UNIQUE INDEX UX_MstUser__Username ON projectz.MstUser(Username);
CREATE UNIQUE INDEX UX_MstUser__Email ON projectz.MstUser(Email);
GO
```

### 3.8 MapAksesMenu (M2M)
```sql
CREATE TABLE projectz.MapAksesMenu (
    IDAkses BIGINT NOT NULL,
    IDMenu  BIGINT NOT NULL,
    CONSTRAINT PK_MapAksesMenu PRIMARY KEY (IDAkses, IDMenu),
    CONSTRAINT FK_MapAksesMenu__MstAkses
        FOREIGN KEY (IDAkses) REFERENCES projectz.MstAkses(ID),
    CONSTRAINT FK_MapAksesMenu__MstMenu
        FOREIGN KEY (IDMenu)  REFERENCES projectz.MstMenu(ID)
);
GO
```

### 3.9 MapProdukSupplier (M2M)
```sql
CREATE TABLE projectz.MapProdukSupplier (
    IDProduk   BIGINT NOT NULL,
    IDSupplier BIGINT NOT NULL,
    CONSTRAINT PK_MapProdukSupplier PRIMARY KEY (IDProduk, IDSupplier),
    CONSTRAINT FK_MapProdukSupplier__MstProduk
        FOREIGN KEY (IDProduk)   REFERENCES projectz.MstProduk(ID),
    CONSTRAINT FK_MapProdukSupplier__MstSupplier
        FOREIGN KEY (IDSupplier) REFERENCES projectz.MstSupplier(ID)
);
GO

CREATE INDEX IX_MapProdukSupplier__IDSupplier ON projectz.MapProdukSupplier(IDSupplier);
GO
```

---

## 4) DDL — Audit / Log Tables

### 4.1 LogKategoriProduk
```sql
CREATE TABLE projectz.LogKategoriProduk (
    ID               BIGINT IDENTITY(1,1) NOT NULL,
    IDKategoriProduk BIGINT               NULL,
    NamaProduk       VARCHAR(50)          NOT NULL,
    Deskripsi        VARCHAR(255)         NOT NULL,
    Notes            VARCHAR(255)         NOT NULL,
    Flag             CHAR(1)              NOT NULL,
    CreatedAt        DATETIME2(6)         NOT NULL,
    CreatedBy        BIGINT               NOT NULL,
    CONSTRAINT PK_LogKategoriProduk PRIMARY KEY (ID)
);
GO
```

### 4.2 LogProduk
```sql
CREATE TABLE projectz.LogProduk (
    ID               BIGINT IDENTITY(1,1) NOT NULL,
    IDProduk         BIGINT               NULL,
    IDKategoriProduk BIGINT               NOT NULL,
    NamaProduk       VARCHAR(50)          NOT NULL,
    Merk             VARCHAR(50)          NOT NULL,
    Model            VARCHAR(50)          NOT NULL,
    Warna            VARCHAR(30)          NOT NULL,
    DeskripsiProduk  VARCHAR(255)         NOT NULL,
    Stok             INT                  NOT NULL,
    Flag             CHAR(1)              NOT NULL,
    CreatedAt        DATETIME2(6)         NOT NULL,
    CreatedBy        BIGINT               NOT NULL,
    CONSTRAINT PK_LogProduk PRIMARY KEY (ID)
);
GO
```

### 4.3 LogSupplier
```sql
CREATE TABLE projectz.LogSupplier (
    ID             BIGINT IDENTITY(1,1) NOT NULL,
    IDSupplier     BIGINT               NULL,
    NamaSupplier   VARCHAR(50)          NOT NULL,
    AlamatSupplier VARCHAR(255)         NOT NULL,
    Flag           CHAR(1)              NOT NULL,
    CreatedAt      DATETIME2(6)         NOT NULL,
    CreatedBy      BIGINT               NOT NULL,
    CONSTRAINT PK_LogSupplier PRIMARY KEY (ID)
);
GO
```

---

## 5) Saran Indeks Tambahan
- `IX_MstProduk__NamaProduk` pada `MstProduk(NamaProduk)` untuk pencarian.
- `IX_MstSupplier__NamaSupplier` pada `MstSupplier(NamaSupplier)`.
- `IX_MstUser__NoHp` pada `MstUser(NoHp)` jika sering dipakai untuk login/identifikasi.

```sql
CREATE INDEX IX_MstProduk__NamaProduk ON projectz.MstProduk(NamaProduk);
CREATE INDEX IX_MstSupplier__NamaSupplier ON projectz.MstSupplier(NamaSupplier);
CREATE INDEX IX_MstUser__NoHp ON projectz.MstUser(NoHp);
GO
```

---

## 6) Catatan Implementasi
- Tabel mapping didefinisikan dengan **PK komposit** untuk mencegah duplikasi relasi.
- Kolom tanggal bertipe `datetime2(6)` untuk presisi cukup tanpa `datetimeoffset`.
- Pertimbangkan penambahan kolom `DeletedAt/DeletedBy` bila diperlukan *soft delete*.
- Validasi *business rule* (contoh: `Stok >= 0`) melalui CHECK constraint atau layer aplikasi.

```sql
ALTER TABLE projectz.MstProduk
ADD CONSTRAINT CK_MstProduk__Stok_NonNeg CHECK (Stok >= 0);
GO
```