# Database Schema Documentation - ProjectZ

## Overview

ProjectZ implements a comprehensive database schema for managing access control, product catalogs, suppliers, and audit trails. The system uses JPA/Hibernate with Spring Data for ORM mapping.

## Technical Specifications

- **Database**: H2 (development), MySQL (production)
- **Schema**: `projectz`
- **ORM**: JPA/Hibernate with Spring Data
- **Naming Convention**: snake_case for database tables/columns, camelCase for Java entities
- **Primary Keys**: BIGINT with auto-increment (IDENTITY strategy)
- **Spring Boot Version**: 3.2.0
- **Java Version**: 17

## Database Structure

### Core Master Tables

#### 1. MstAkses (Access/Role Management)
Manages system access roles and permissions.

**Table**: `projectz.mst_akses`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| nama | VARCHAR(50) | NOT NULL | Access role name |
| deskripsi | VARCHAR(255) | NOT NULL | Role description |
| created_by | BIGINT | NOT NULL | User who created the record |
| created_date | DATETIME2(6) | NULL | Record creation timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |
| modified_date | DATETIME2(6) | NULL | Last modification timestamp |

**Java Entity**: `MstAkses` extends `Auditable`

#### 2. MstUser (User Management)
Stores user information with role relationships.

**Table**: `projectz.mst_user`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_akses | BIGINT | FOREIGN KEY → mst_akses(id) | Reference to user's access role |
| username | VARCHAR(16) | NOT NULL, UNIQUE | Unique username |
| password | VARCHAR(64) | NOT NULL | Hashed password |
| nama_lengkap | VARCHAR(70) | NOT NULL | User's full name |
| email | VARCHAR(256) | NOT NULL, UNIQUE | Unique email address |
| no_hp | VARCHAR(18) | NOT NULL | Phone number |
| alamat | VARCHAR(255) | NOT NULL | User address |
| tanggal_lahir | DATE | NOT NULL | Birth date |
| is_registered | BIT | NULL | Registration status flag |
| otp | VARCHAR(64) | NULL | One-time password |
| token_estafet | VARCHAR(64) | NULL | Authentication token |
| link_image | VARCHAR(256) | NULL | Profile image URL |
| path_image | VARCHAR(256) | NULL | Profile image file path |
| created_by | BIGINT | NOT NULL | User who created the record |
| created_date | DATETIME2(6) | NULL | Record creation timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |
| modified_date | DATETIME2(6) | NULL | Last modification timestamp |

**Unique Constraints**:
- `UX_MstUser__Username` on username
- `UX_MstUser__Email` on email

**Java Entity**: `MstUser` extends `Auditable`

#### 3. MstGroupMenu (Menu Grouping)
Organizes menu items into logical groups.

**Table**: `projectz.mst_group_menu`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| nama | VARCHAR(50) | NOT NULL | Group name |
| deskripsi | VARCHAR(255) | NOT NULL | Group description |
| created_by | BIGINT | NOT NULL | User who created the record |
| created_date | DATETIME2(6) | NULL | Record creation timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |
| modified_date | DATETIME2(6) | NULL | Last modification timestamp |

**Java Entity**: `MstGroupMenu` extends `Auditable`

#### 4. MstMenu (Menu Items)
Defines system menu items with optional group relationships.

**Table**: `projectz.mst_menu`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_group_menu | BIGINT | FOREIGN KEY → mst_group_menu(id), NULL | Optional reference to menu group |
| nama | VARCHAR(50) | NOT NULL | Menu item name |
| path | VARCHAR(50) | NOT NULL | Menu navigation path |
| deskripsi | VARCHAR(255) | NOT NULL | Menu description |
| created_by | BIGINT | NOT NULL | User who created the record |
| created_date | DATETIME2(6) | NULL | Record creation timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |
| modified_date | DATETIME2(6) | NULL | Last modification timestamp |

**Java Entity**: `MstMenu` extends `Auditable`

#### 5. MstKategoriProduk (Product Categories)
Manages product category classifications.

**Table**: `projectz.mst_kategori_produk`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| nama_produk | VARCHAR(50) | NOT NULL | Category name |
| deskripsi | VARCHAR(255) | NOT NULL | Category description |
| notes | VARCHAR(255) | NOT NULL | Additional notes |
| created_at | DATETIME2(6) | NOT NULL | Record creation timestamp |
| created_by | BIGINT | NOT NULL | User who created the record |
| modified_at | DATETIME2(6) | NULL | Last modification timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |

**Java Entity**: `MstKategoriProduk` (individual audit fields)

#### 6. MstProduk (Products)
Stores product information with category relationships.

**Table**: `projectz.mst_produk`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_kategori_produk | BIGINT | FOREIGN KEY → mst_kategori_produk(id), NOT NULL | Product category reference |
| nama_produk | VARCHAR(50) | NOT NULL | Product name |
| merk | VARCHAR(50) | NOT NULL | Product brand |
| model | VARCHAR(50) | NOT NULL | Product model |
| warna | VARCHAR(30) | NOT NULL | Product color |
| deskripsi_produk | VARCHAR(255) | NOT NULL | Product description |
| stok | INT | NOT NULL | Stock quantity |
| created_at | DATETIME2(6) | NOT NULL | Record creation timestamp |
| created_by | BIGINT | NOT NULL | User who created the record |
| modified_at | DATETIME2(6) | NULL | Last modification timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |

**Java Entity**: `MstProduk` (individual audit fields)

#### 7. MstSupplier (Suppliers)
Manages supplier information.

**Table**: `projectz.mst_supplier`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| nama_supplier | VARCHAR(50) | NOT NULL | Supplier name |
| alamat_supplier | VARCHAR(255) | NOT NULL | Supplier address |
| created_at | DATETIME2(6) | NOT NULL | Record creation timestamp |
| created_by | BIGINT | NOT NULL | User who created the record |
| modified_at | DATETIME2(6) | NULL | Last modification timestamp |
| modified_by | BIGINT | NULL | User who last modified the record |

**Java Entity**: `MstSupplier` (individual audit fields)

### Mapping Tables (Many-to-Many Relationships)

#### 1. MapAksesMenu (Access-Menu Permissions)
Maps access roles to menu permissions.

**Table**: `projectz.map_akses_menu`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id_akses | BIGINT | PRIMARY KEY, FOREIGN KEY → mst_akses(id) | Access role reference |
| id_menu | BIGINT | PRIMARY KEY, FOREIGN KEY → mst_menu(id) | Menu item reference |

**Composite Primary Key**: (id_akses, id_menu)
**Java Entity**: `MapAksesMenu` with `MapAksesMenuId` embedded key

#### 2. MapProdukSupplier (Product-Supplier Relationships)
Maps products to their suppliers.

**Table**: `projectz.map_produk_supplier`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id_produk | BIGINT | PRIMARY KEY, FOREIGN KEY → mst_produk(id) | Product reference |
| id_supplier | BIGINT | PRIMARY KEY, FOREIGN KEY → mst_supplier(id) | Supplier reference |

**Composite Primary Key**: (id_produk, id_supplier)
**Java Entity**: `MapProdukSupplier` with `MapProdukSupplierId` embedded key

### Audit Log Tables

#### 1. LogKategoriProduk (Category Audit Trail)
Tracks changes to product categories.

**Table**: `projectz.log_kategori_produk`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_kategori_produk | BIGINT | NULL | Original category ID |
| nama_produk | VARCHAR(50) | NOT NULL | Category name snapshot |
| deskripsi | VARCHAR(255) | NOT NULL | Description snapshot |
| notes | VARCHAR(255) | NOT NULL | Notes snapshot |
| flag | CHAR(1) | NOT NULL | Operation flag (C/U/D) |
| created_at | DATETIME2(6) | NOT NULL | Log entry timestamp |
| created_by | BIGINT | NOT NULL | User who made the change |

**Java Entity**: `LogKategoriProduk`

#### 2. LogProduk (Product Audit Trail)
Tracks changes to products.

**Table**: `projectz.log_produk`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_produk | BIGINT | NULL | Original product ID |
| id_kategori_produk | BIGINT | NOT NULL | Category ID snapshot |
| nama_produk | VARCHAR(50) | NOT NULL | Product name snapshot |
| merk | VARCHAR(50) | NOT NULL | Brand snapshot |
| model | VARCHAR(50) | NOT NULL | Model snapshot |
| warna | VARCHAR(30) | NOT NULL | Color snapshot |
| deskripsi_produk | VARCHAR(255) | NOT NULL | Description snapshot |
| stok | INT | NOT NULL | Stock snapshot |
| flag | CHAR(1) | NOT NULL | Operation flag (C/U/D) |
| created_at | DATETIME2(6) | NOT NULL | Log entry timestamp |
| created_by | BIGINT | NOT NULL | User who made the change |

**Java Entity**: `LogProduk`

#### 3. LogSupplier (Supplier Audit Trail)
Tracks changes to suppliers.

**Table**: `projectz.log_supplier`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, IDENTITY(1,1) | Auto-increment primary key |
| id_supplier | BIGINT | NULL | Original supplier ID |
| nama_supplier | VARCHAR(50) | NOT NULL | Supplier name snapshot |
| alamat_supplier | VARCHAR(255) | NOT NULL | Address snapshot |
| flag | CHAR(1) | NOT NULL | Operation flag (C/U/D) |
| created_at | DATETIME2(6) | NOT NULL | Log entry timestamp |
| created_by | BIGINT | NOT NULL | User who made the change |

**Java Entity**: `LogSupplier`

## Audit Patterns

### Auditable Base Class
Entities extending `Auditable` include:
- `created_by` (BIGINT, NOT NULL)
- `created_date` (DATETIME2(6), NULL)
- `modified_by` (BIGINT, NULL)
- `modified_date` (DATETIME2(6), NULL)

**Entities using Auditable**: MstAkses, MstUser, MstGroupMenu, MstMenu

### Individual Audit Fields
Some entities implement their own audit pattern:
- `created_at` (DATETIME2(6), NOT NULL)
- `created_by` (BIGINT, NOT NULL)
- `modified_at` (DATETIME2(6), NULL)
- `modified_by` (BIGINT, NULL)

**Entities with individual audit**: MstKategoriProduk, MstProduk, MstSupplier

### Log Table Pattern
Audit log tables capture:
- Complete data snapshot at time of change
- Operation flag (C=Create, U=Update, D=Delete)
- Timestamp and user who made the change
- Reference to original entity (may be NULL for deleted records)

## Indexes and Performance

### Recommended Indexes
```sql
-- Primary relationship indexes
CREATE INDEX IX_MstUser__IDAkses ON projectz.MstUser(IDAkses);
CREATE INDEX IX_MstMenu__IDGroupMenu ON projectz.MstMenu(IDGroupMenu);
CREATE INDEX IX_MstProduk__IDKategoriProduk ON projectz.MstProduk(IDKategoriProduk);

-- Search optimization indexes
CREATE INDEX IX_MstProduk__NamaProduk ON projectz.MstProduk(NamaProduk);
CREATE INDEX IX_MstSupplier__NamaSupplier ON projectz.MstSupplier(NamaSupplier);
CREATE INDEX IX_MstUser__NoHp ON projectz.MstUser(NoHp);

-- Mapping table indexes
CREATE INDEX IX_MapProdukSupplier__IDSupplier ON projectz.MapProdukSupplier(IDSupplier);
```

### Unique Constraints
- `UX_MstUser__Username` on mst_user.username
- `UX_MstUser__Email` on mst_user.email

## Business Rules and Constraints

### Data Validation
- Stock quantities should be non-negative: `ALTER TABLE projectz.MstProduk ADD CONSTRAINT CK_MstProduk__Stok_NonNeg CHECK (Stok >= 0);`
- Email format validation handled at application layer
- Username length restrictions: 16 characters maximum
- Password should be hashed before storage

### Referential Integrity
- User access roles are enforced via foreign key to MstAkses
- Product categories are required for all products
- Menu items may optionally belong to a group
- Mapping tables prevent duplicate relationships via composite primary keys

## Migration Considerations

### From Development to Production
- H2 database schema can be exported and converted to MySQL
- Data types are compatible between H2 and MySQL
- Consider adding connection pooling configuration for production
- Review and optimize indexes based on query patterns

### Backup and Recovery
- Regular backups of master tables
- Log tables can be archived periodically to manage size
- Consider partitioning large log tables by date