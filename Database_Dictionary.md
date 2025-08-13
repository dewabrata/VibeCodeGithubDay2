# Database Dictionary - ProjectZ

## Overview

This document provides comprehensive field-by-field documentation for all tables in the ProjectZ database schema, including data types, constraints, business rules, and usage examples.

## Master Tables

### MstAkses (Access/Role Management)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| nama | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Access role name | Unique role identifier, descriptive | "Administrator", "User", "Manager" |
| deskripsi | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Role description | Detailed explanation of role purpose | "Full system access", "Read-only user" |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| created_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |
| modified_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |

**Table**: `projectz.mst_akses`  
**Java Entity**: `MstAkses extends Auditable`  
**Business Purpose**: Defines system access roles for user authorization

---

### MstUser (User Management)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_akses | BIGINT | MstAkses | @ManyToOne @JoinColumn | FOREIGN KEY → mst_akses(id) | User's access role | Must reference valid access role | FK to MstAkses |
| username | VARCHAR(16) | String | @Column | NOT NULL, UNIQUE, MAX 16 chars | Unique login username | Alphanumeric, no spaces, unique | "admin", "john_doe", "user123" |
| password | VARCHAR(64) | String | @Column | NOT NULL, MAX 64 chars | Hashed password | BCrypt/SHA256 hash, never plaintext | "$2a$10$hash..." |
| nama_lengkap | VARCHAR(70) | String | @Column | NOT NULL, MAX 70 chars | User's full name | Display name for UI | "John Doe", "Jane Smith" |
| email | VARCHAR(256) | String | @Column | NOT NULL, UNIQUE, MAX 256 chars | User's email address | Valid email format, unique | "john@example.com" |
| no_hp | VARCHAR(18) | String | @Column | NOT NULL, MAX 18 chars | Phone number | International format supported | "+62812345678", "0812345678" |
| alamat | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | User's address | Complete address string | "Jl. Sudirman No. 123, Jakarta" |
| tanggal_lahir | DATE | LocalDate | @Column | NOT NULL | Birth date | Must be past date, age validation | 1990-05-15, 1985-12-25 |
| is_registered | BIT | Boolean | @Column | NULL | Registration status flag | TRUE=registered, FALSE/NULL=pending | true, false, null |
| otp | VARCHAR(64) | String | @Column | NULL, MAX 64 chars | One-time password | Temporary verification code | "123456", "ABC123" |
| token_estafet | VARCHAR(64) | String | @Column | NULL, MAX 64 chars | Authentication token | JWT or session token | "eyJhbGciOiJIUzI1..." |
| link_image | VARCHAR(256) | String | @Column | NULL, MAX 256 chars | Profile image URL | External image URL | "https://cdn.example.com/user1.jpg" |
| path_image | VARCHAR(256) | String | @Column | NULL, MAX 256 chars | Local image file path | Server file path | "/uploads/profiles/user1.jpg" |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| created_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |
| modified_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |

**Table**: `projectz.mst_user`  
**Java Entity**: `MstUser extends Auditable`  
**Unique Constraints**: UX_MstUser__Username (username), UX_MstUser__Email (email)  
**Business Purpose**: Stores user account information and profile data

---

### MstGroupMenu (Menu Grouping)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| nama | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Menu group name | Descriptive group identifier | "System Management", "Reports", "Product Management" |
| deskripsi | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Group description | Detailed explanation of group purpose | "System administration functions", "Business reports and analytics" |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| created_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |
| modified_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |

**Table**: `projectz.mst_group_menu`  
**Java Entity**: `MstGroupMenu extends Auditable`  
**Business Purpose**: Organizes menu items into logical groups for navigation

---

### MstMenu (Menu Items)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_group_menu | BIGINT | MstGroupMenu | @ManyToOne @JoinColumn | FOREIGN KEY → mst_group_menu(id), NULL | Optional menu group reference | Can be NULL for ungrouped menus | FK to MstGroupMenu or NULL |
| nama | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Menu item name | Display name in navigation | "User Management", "Product List", "Reports Dashboard" |
| path | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Menu navigation path | URL path or route identifier | "/users", "/products", "/dashboard" |
| deskripsi | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Menu description | Tooltip or help text | "Manage system users and permissions", "View and edit product catalog" |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| created_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |
| modified_date | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |

**Table**: `projectz.mst_menu`  
**Java Entity**: `MstMenu extends Auditable`  
**Business Purpose**: Defines system menu items and navigation structure

---

### MstKategoriProduk (Product Categories)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| nama_produk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Product category name | Business category identifier | "Electronics", "Furniture", "Clothing" |
| deskripsi | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Category description | Detailed category explanation | "Electronic devices and components", "Office and home furniture" |
| notes | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Additional notes | Extra information or instructions | "Import restrictions apply", "Seasonal category" |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| modified_at | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |

**Table**: `projectz.mst_kategori_produk`  
**Java Entity**: `MstKategoriProduk` (individual audit fields)  
**Business Purpose**: Classifies products into business categories

---

### MstProduk (Products)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_kategori_produk | BIGINT | MstKategoriProduk | @ManyToOne @JoinColumn | FOREIGN KEY → mst_kategori_produk(id), NOT NULL | Product category reference | Must reference valid category | FK to MstKategoriProduk |
| nama_produk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Product name | Business product identifier | "iPhone 15", "Office Chair", "Laptop Dell" |
| merk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Product brand | Manufacturer or brand name | "Apple", "Samsung", "Dell", "HP" |
| model | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Product model | Specific model identifier | "iPhone 15 Pro", "Galaxy S24", "Inspiron 15" |
| warna | VARCHAR(30) | String | @Column | NOT NULL, MAX 30 chars | Product color | Color specification | "Black", "White", "Silver", "Blue" |
| deskripsi_produk | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Product description | Detailed product information | "Latest smartphone with advanced camera", "Ergonomic office chair with lumbar support" |
| stok | INT | Integer | @Column | NOT NULL | Stock quantity | Current inventory count, must be ≥ 0 | 0, 25, 100, 500 |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| modified_at | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |

**Table**: `projectz.mst_produk`  
**Java Entity**: `MstProduk` (individual audit fields)  
**Business Rules**: Stock quantity constraint (≥ 0)  
**Business Purpose**: Manages product catalog and inventory

---

### MstSupplier (Suppliers)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| nama_supplier | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Supplier company name | Business name identifier | "PT ABC Electronics", "CV Furniture Jaya" |
| alamat_supplier | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Supplier address | Complete business address | "Jl. Industri No. 45, Bekasi", "Jl. Raya Jakarta No. 123" |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Record creation timestamp | Auto-set on creation | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User ID who created record | References user.id, audit field | 1, 5, 10 |
| modified_at | DATETIME2(6) | LocalDateTime | @Column | NULL | Last modification timestamp | Auto-set on update | 2025-01-14 14:20:30.654321 |
| modified_by | BIGINT | Long | @Column | NULL | User ID who modified record | References user.id, audit field | 2, 7, 12 |

**Table**: `projectz.mst_supplier`  
**Java Entity**: `MstSupplier` (individual audit fields)  
**Business Purpose**: Manages supplier information and vendor relationships

---

## Mapping Tables

### MapAksesMenu (Access-Menu Permissions)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id_akses | BIGINT | Long | @Column (in embedded ID) | PRIMARY KEY, FOREIGN KEY → mst_akses(id) | Access role reference | Must reference valid access role | 1, 2, 3 |
| id_menu | BIGINT | Long | @Column (in embedded ID) | PRIMARY KEY, FOREIGN KEY → mst_menu(id) | Menu item reference | Must reference valid menu item | 1, 2, 3 |

**Table**: `projectz.map_akses_menu`  
**Java Entity**: `MapAksesMenu` with `MapAksesMenuId` embedded key  
**Composite Primary Key**: (id_akses, id_menu)  
**Business Purpose**: Defines which menus each access role can access

**JPA Implementation**:
```java
@EmbeddedId
private MapAksesMenuId id;

@MapsId("idAkses")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "id_akses", nullable = false)
private MstAkses akses;

@MapsId("idMenu")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "id_menu", nullable = false)
private MstMenu menu;
```

---

### MapProdukSupplier (Product-Supplier Relationships)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id_produk | BIGINT | Long | @Column (in embedded ID) | PRIMARY KEY, FOREIGN KEY → mst_produk(id) | Product reference | Must reference valid product | 1, 2, 3 |
| id_supplier | BIGINT | Long | @Column (in embedded ID) | PRIMARY KEY, FOREIGN KEY → mst_supplier(id) | Supplier reference | Must reference valid supplier | 1, 2, 3 |

**Table**: `projectz.map_produk_supplier`  
**Java Entity**: `MapProdukSupplier` with `MapProdukSupplierId` embedded key  
**Composite Primary Key**: (id_produk, id_supplier)  
**Business Purpose**: Maps products to their suppliers (many-to-many relationship)

**JPA Implementation**:
```java
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
```

---

## Audit Log Tables

### LogKategoriProduk (Product Category Audit Trail)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_kategori_produk | BIGINT | Long | @Column | NULL | Original category ID | May be NULL for deleted records | 1, 2, 3, NULL |
| nama_produk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Category name snapshot | Historical data preservation | "Electronics", "Furniture" |
| deskripsi | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Description snapshot | Historical data preservation | "Electronic devices and components" |
| notes | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Notes snapshot | Historical data preservation | "Import restrictions apply" |
| flag | CHAR(1) | String | @Column | NOT NULL, LENGTH 1 | Operation type flag | C=Create, U=Update, D=Delete | "C", "U", "D" |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Log entry timestamp | When change occurred | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User who made change | References user.id | 1, 5, 10 |

**Table**: `projectz.log_kategori_produk`  
**Java Entity**: `LogKategoriProduk`  
**Business Purpose**: Tracks all changes to product categories for audit compliance

---

### LogProduk (Product Audit Trail)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_produk | BIGINT | Long | @Column | NULL | Original product ID | May be NULL for deleted records | 1, 2, 3, NULL |
| id_kategori_produk | BIGINT | Long | @Column | NOT NULL | Category ID snapshot | Historical relationship data | 1, 2, 3 |
| nama_produk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Product name snapshot | Historical data preservation | "iPhone 15", "Office Chair" |
| merk | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Brand snapshot | Historical data preservation | "Apple", "Samsung" |
| model | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Model snapshot | Historical data preservation | "iPhone 15 Pro", "Galaxy S24" |
| warna | VARCHAR(30) | String | @Column | NOT NULL, MAX 30 chars | Color snapshot | Historical data preservation | "Black", "White", "Silver" |
| deskripsi_produk | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Description snapshot | Historical data preservation | "Latest smartphone with advanced camera" |
| stok | INT | Integer | @Column | NOT NULL | Stock snapshot | Historical inventory data | 0, 25, 100, 500 |
| flag | CHAR(1) | String | @Column | NOT NULL, LENGTH 1 | Operation type flag | C=Create, U=Update, D=Delete | "C", "U", "D" |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Log entry timestamp | When change occurred | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User who made change | References user.id | 1, 5, 10 |

**Table**: `projectz.log_produk`  
**Java Entity**: `LogProduk`  
**Business Purpose**: Tracks all changes to products including inventory and specifications

---

### LogSupplier (Supplier Audit Trail)

| Field | Database Type | Java Type | JPA Annotation | Constraints | Description | Business Rules | Example Values |
|-------|---------------|-----------|----------------|-------------|-------------|----------------|----------------|
| id | BIGINT IDENTITY(1,1) | Long | @Id @GeneratedValue | PRIMARY KEY, NOT NULL | Auto-increment primary key | System generated, immutable | 1, 2, 3 |
| id_supplier | BIGINT | Long | @Column | NULL | Original supplier ID | May be NULL for deleted records | 1, 2, 3, NULL |
| nama_supplier | VARCHAR(50) | String | @Column | NOT NULL, MAX 50 chars | Supplier name snapshot | Historical data preservation | "PT ABC Electronics", "CV Furniture Jaya" |
| alamat_supplier | VARCHAR(255) | String | @Column | NOT NULL, MAX 255 chars | Address snapshot | Historical data preservation | "Jl. Industri No. 45, Bekasi" |
| flag | CHAR(1) | String | @Column | NOT NULL, LENGTH 1 | Operation type flag | C=Create, U=Update, D=Delete | "C", "U", "D" |
| created_at | DATETIME2(6) | LocalDateTime | @Column | NOT NULL | Log entry timestamp | When change occurred | 2025-01-13 10:30:45.123456 |
| created_by | BIGINT | Long | @Column | NOT NULL | User who made change | References user.id | 1, 5, 10 |

**Table**: `projectz.log_supplier`  
**Java Entity**: `LogSupplier`  
**Business Purpose**: Tracks all changes to supplier information for vendor management

---

## Data Types and Constraints Reference

### Common Data Types

| Database Type | Java Type | Description | Max Length | Constraints |
|---------------|-----------|-------------|------------|-------------|
| BIGINT IDENTITY(1,1) | Long | Auto-increment primary key | N/A | NOT NULL, UNIQUE |
| VARCHAR(n) | String | Variable character string | n characters | Length limit |
| DATETIME2(6) | LocalDateTime | Date and time with microseconds | N/A | Precision to 6 decimal places |
| DATE | LocalDate | Date only | N/A | YYYY-MM-DD format |
| INT | Integer | 32-bit integer | N/A | -2,147,483,648 to 2,147,483,647 |
| BIT | Boolean | Boolean flag | N/A | TRUE/FALSE/NULL |
| CHAR(1) | String | Fixed single character | 1 character | Exact length |

### Constraint Types

| Constraint | Purpose | Implementation | Example |
|------------|---------|----------------|---------|
| PRIMARY KEY | Unique row identifier | Auto-increment BIGINT | id BIGINT IDENTITY(1,1) |
| FOREIGN KEY | Referential integrity | References parent table | FOREIGN KEY (id_akses) REFERENCES mst_akses(id) |
| UNIQUE | Prevent duplicates | Unique index | UNIQUE (username), UNIQUE (email) |
| NOT NULL | Mandatory fields | Column constraint | nama VARCHAR(50) NOT NULL |
| CHECK | Data validation | Value constraints | CHECK (stok >= 0) |

### Business Validation Rules

| Field Type | Validation Rule | Implementation | Example |
|------------|-----------------|----------------|---------|
| Email | Format validation | Application layer regex | Must contain @ and valid domain |
| Password | Complexity rules | Application layer | Minimum 8 characters, hash before storage |
| Phone | Format validation | Application layer | Support international formats |
| Stock | Non-negative | Database constraint | CHECK (stok >= 0) |
| Username | Uniqueness | Database constraint | UNIQUE (username) |
| Date | Past/Future validation | Application layer | Birth date must be in past |

### Audit Field Patterns

#### Auditable Base Class Pattern
Used by: MstAkses, MstUser, MstGroupMenu, MstMenu
```java
@MappedSuperclass
public abstract class Auditable {
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "modified_by")
    private Long modifiedBy;
    
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
```

#### Individual Audit Fields Pattern
Used by: MstKategoriProduk, MstProduk, MstSupplier
```java
@Column(name = "created_at", nullable = false)
private LocalDateTime createdAt;

@Column(name = "created_by", nullable = false)
private Long createdBy;

@Column(name = "modified_at")
private LocalDateTime modifiedAt;

@Column(name = "modified_by")
private Long modifiedBy;
```

#### Log Table Pattern
Used by: LogKategoriProduk, LogProduk, LogSupplier
```java
@Column(name = "flag", nullable = false, length = 1)
private String flag; // C=Create, U=Update, D=Delete

@Column(name = "created_at", nullable = false)
private LocalDateTime createdAt;

@Column(name = "created_by", nullable = false)
private Long createdBy;
```