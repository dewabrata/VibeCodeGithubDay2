# Entity Relationships Guide - ProjectZ

## Overview

This guide provides detailed explanations of all entity relationships in the ProjectZ database schema, including cardinalities, foreign key constraints, and business logic implications.

## Relationship Categories

### 1. One-to-Many Relationships
Direct parent-child relationships with foreign key constraints.

### 2. Many-to-Many Relationships  
Complex relationships implemented through mapping tables.

### 3. Audit Relationships
Logical relationships for tracking changes (no foreign key constraints).

## Detailed Relationship Documentation

### One-to-Many Relationships

#### 1. MstAkses → MstUser (1:N)
**Relationship**: One access role can be assigned to many users.

**Implementation**:
- **Parent**: `MstAkses` (id)
- **Child**: `MstUser` (id_akses)
- **Foreign Key**: `FK_MstUser__MstAkses`
- **Constraint**: `FOREIGN KEY (id_akses) REFERENCES projectz.mst_akses(id)`

**Business Logic**:
- Users must have an assigned access role to determine their permissions
- Access roles define what menus and functions users can access
- Deleting an access role should be restricted if users are assigned to it
- Role changes affect all users assigned to that role

**JPA Annotations**:
```java
// In MstUser entity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_akses")
private MstAkses akses;
```

**Cardinality**: One access role : Many users (1:N)

---

#### 2. MstGroupMenu → MstMenu (1:N) [Optional]
**Relationship**: One menu group can contain many menu items.

**Implementation**:
- **Parent**: `MstGroupMenu` (id)
- **Child**: `MstMenu` (id_group_menu)
- **Foreign Key**: `FK_MstMenu__MstGroupMenu`
- **Constraint**: `FOREIGN KEY (id_group_menu) REFERENCES projectz.mst_group_menu(id)`
- **Nullable**: Yes (menu items can exist without a group)

**Business Logic**:
- Menu items can be organized into logical groups for better navigation
- Menu items can exist independently without being part of a group
- Groups help organize the menu hierarchy in the user interface
- Deleting a group should either reassign menus or set their group to NULL

**JPA Annotations**:
```java
// In MstMenu entity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_group_menu")
private MstGroupMenu groupMenu;
```

**Cardinality**: One menu group : Many menu items (1:N, optional)

---

#### 3. MstKategoriProduk → MstProduk (1:N)
**Relationship**: One product category can contain many products.

**Implementation**:
- **Parent**: `MstKategoriProduk` (id)
- **Child**: `MstProduk` (id_kategori_produk)
- **Foreign Key**: `FK_MstProduk__MstKategoriProduk`
- **Constraint**: `FOREIGN KEY (id_kategori_produk) REFERENCES projectz.mst_kategori_produk(id)`
- **Required**: Yes (products must have a category)

**Business Logic**:
- All products must belong to a category for classification
- Categories help organize products for inventory management
- Category changes affect product organization and reporting
- Deleting a category requires reassigning all products to another category

**JPA Annotations**:
```java
// In MstProduk entity
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "id_kategori_produk", nullable = false)
private MstKategoriProduk kategoriProduk;
```

**Cardinality**: One product category : Many products (1:N, required)

---

### Many-to-Many Relationships

#### 1. MstAkses ↔ MstMenu (N:M via MapAksesMenu)
**Relationship**: Access roles can have permissions to multiple menus, and menus can be accessible by multiple roles.

**Implementation**:
- **Mapping Table**: `MapAksesMenu`
- **Composite Primary Key**: (id_akses, id_menu)
- **Foreign Keys**: 
  - `FK_MapAksesMenu__MstAkses`: `id_akses → mst_akses(id)`
  - `FK_MapAksesMenu__MstMenu`: `id_menu → mst_menu(id)`

**Business Logic**:
- Defines which menu items each access role can view/access
- Flexible permission system allowing fine-grained access control
- Adding/removing menu permissions affects user access immediately
- Prevents duplicate permission assignments through composite primary key

**JPA Annotations**:
```java
// MapAksesMenu entity
@Entity
@Table(name = "map_akses_menu", schema = "projectz")
public class MapAksesMenu {
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
}

// Composite Key
@Embeddable
public class MapAksesMenuId implements Serializable {
    @Column(name = "id_akses", nullable = false)
    private Long idAkses;

    @Column(name = "id_menu", nullable = false)
    private Long idMenu;
}
```

**Cardinality**: Many access roles : Many menu items (N:M)

---

#### 2. MstProduk ↔ MstSupplier (N:M via MapProdukSupplier)
**Relationship**: Products can be supplied by multiple suppliers, and suppliers can provide multiple products.

**Implementation**:
- **Mapping Table**: `MapProdukSupplier`
- **Composite Primary Key**: (id_produk, id_supplier)
- **Foreign Keys**:
  - `FK_MapProdukSupplier__MstProduk`: `id_produk → mst_produk(id)`
  - `FK_MapProdukSupplier__MstSupplier`: `id_supplier → mst_supplier(id)`

**Business Logic**:
- Supports multiple sourcing strategies for products
- Enables supplier comparison and price negotiation
- Tracks which suppliers can provide which products
- Prevents duplicate supplier-product relationships

**JPA Annotations**:
```java
// MapProdukSupplier entity
@Entity
@Table(name = "map_produk_supplier", schema = "projectz")
public class MapProdukSupplier {
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
}

// Composite Key
@Embeddable
public class MapProdukSupplierId implements Serializable {
    @Column(name = "id_produk", nullable = false)
    private Long idProduk;

    @Column(name = "id_supplier", nullable = false)
    private Long idSupplier;
}
```

**Cardinality**: Many products : Many suppliers (N:M)

---

### Audit Relationships (Logical)

These relationships track changes but don't enforce foreign key constraints to preserve audit trail integrity.

#### 1. MstKategoriProduk → LogKategoriProduk (1:N)
**Relationship**: One category can have multiple audit log entries.

**Implementation**:
- **Master Table**: `MstKategoriProduk` (id)
- **Log Table**: `LogKategoriProduk` (id_kategori_produk)
- **No Foreign Key Constraint**: Preserves logs even if master record is deleted

**Business Logic**:
- Tracks all changes to product categories (create, update, delete)
- `flag` field indicates operation type: 'C' (Create), 'U' (Update), 'D' (Delete)
- `id_kategori_produk` may be NULL for deleted records
- Maintains complete audit trail for compliance and debugging

**Log Entry Process**:
1. Before any change to MstKategoriProduk
2. Create snapshot in LogKategoriProduk with appropriate flag
3. Include user ID and timestamp
4. Preserve original data values

---

#### 2. MstProduk → LogProduk (1:N)
**Relationship**: One product can have multiple audit log entries.

**Implementation**:
- **Master Table**: `MstProduk` (id)
- **Log Table**: `LogProduk` (id_produk)
- **No Foreign Key Constraint**: Preserves logs even if master record is deleted

**Business Logic**:
- Tracks all changes to products including category changes
- Records complete product state at time of change
- Enables inventory history analysis and compliance reporting
- Supports rollback functionality and change analysis

**Additional Tracking**:
- Category relationship changes via `id_kategori_produk`
- Stock level changes for inventory auditing
- Product specification changes

---

#### 3. MstSupplier → LogSupplier (1:N)
**Relationship**: One supplier can have multiple audit log entries.

**Implementation**:
- **Master Table**: `MstSupplier` (id)
- **Log Table**: `LogSupplier` (id_supplier)
- **No Foreign Key Constraint**: Preserves logs even if master record is deleted

**Business Logic**:
- Tracks supplier information changes
- Maintains vendor relationship history
- Supports compliance and vendor management reporting
- Enables supplier performance analysis over time

---

## Relationship Constraints and Rules

### Cascade Behaviors

#### Delete Constraints
1. **MstAkses**: Restrict delete if users are assigned
2. **MstGroupMenu**: Set NULL in MstMenu or restrict delete
3. **MstKategoriProduk**: Restrict delete if products exist
4. **MstUser**: Clean up related access mappings
5. **MstMenu**: Clean up access mappings
6. **MstProduk**: Clean up supplier mappings
7. **MstSupplier**: Clean up product mappings

#### Update Constraints
1. **Primary Key Changes**: Generally not allowed (IDENTITY columns)
2. **Foreign Key Updates**: Automatic propagation where configured

### Business Rule Enforcement

#### Application Level
- User registration validation
- Stock quantity non-negative checks
- Email format validation
- Password complexity requirements
- Menu path uniqueness

#### Database Level
- Primary key constraints
- Foreign key constraints
- Unique constraints (username, email)
- NOT NULL constraints
- Check constraints (where applicable)

### Performance Considerations

#### Indexing Strategy
```sql
-- Relationship indexes
CREATE INDEX IX_MstUser__IDAkses ON projectz.MstUser(IDAkses);
CREATE INDEX IX_MstMenu__IDGroupMenu ON projectz.MstMenu(IDGroupMenu);
CREATE INDEX IX_MstProduk__IDKategoriProduk ON projectz.MstProduk(IDKategoriProduk);

-- Mapping table indexes
CREATE INDEX IX_MapProdukSupplier__IDSupplier ON projectz.MapProdukSupplier(IDSupplier);
```

#### Query Optimization
- Use lazy loading for relationships
- Implement pagination for large result sets
- Consider read-only transactions for reports
- Use projection queries when full entities aren't needed

### Integration Patterns

#### Service Layer Implementation
```java
@Service
@Transactional
public class ProductService {
    
    public void assignSupplier(Long productId, Long supplierId) {
        // Validate entities exist
        MstProduk product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        MstSupplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        
        // Create mapping
        MapProdukSupplierId mapId = new MapProdukSupplierId();
        mapId.setIdProduk(productId);
        mapId.setIdSupplier(supplierId);
        
        MapProdukSupplier mapping = new MapProdukSupplier();
        mapping.setId(mapId);
        mapping.setProduk(product);
        mapping.setSupplier(supplier);
        
        mapProdukSupplierRepository.save(mapping);
    }
}
```

#### Repository Patterns
- Use Spring Data JPA repository interfaces
- Implement custom queries for complex relationships
- Use @Query annotations for optimized relationship queries
- Consider specifications for dynamic filtering