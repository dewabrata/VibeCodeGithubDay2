# ProjectZ API - Copilot Instructions

## Architecture Overview
This is a Spring Boot 3.x REST API for product and user access management with JWT authentication, role-based access control (RBAC), and audit logging. The system follows a layered architecture with clear separation between presentation, business logic, and data layers.

## Key Architectural Patterns

### Entity Design
- **Auditable Base Class**: All master entities extend `Auditable.java` for consistent audit fields (`created_by`, `created_date`, `modified_by`, `modified_date`)
- **Schema Convention**: All tables use `projectz` schema with consistent naming (`mst_*` for master data, `log_*` for audit logs, `map_*` for many-to-many relationships)
- **Composite Keys**: M2M mapping tables use composite primary keys (e.g., `MapAksesMenu`, `MapProdukSupplier`)

### Security Architecture
- **JWT-based Authentication**: Uses `io.jsonwebtoken` (jjwt) library with configurable expiration times
- **Role-Based Access**: Users have roles via `MstAkses` entity, with menu permissions mapped through `MapAksesMenu`
- **Filter Chain**: `JwtRequestFilter` extracts and validates JWT tokens before Spring Security filter chain
- **Public Endpoints**: Auth endpoints, Swagger UI, and H2 console are publicly accessible

### Data Layer Patterns
- **Repository Pattern**: All repositories extend `JpaRepository<Entity, Long>`
- **Custom Queries**: Use Spring Data JPA method naming conventions (e.g., `findByUsername`)
- **Database Migration**: Flyway manages schema versioning (`V1__init.sql` for schema, `V2__seed.sql` for data)

## Development Workflow

### Running the Application
```bash
# Build the project
mvn clean package

# Run with dev profile (uses H2 in-memory database)
java -jar target/projectz-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# Or use the provided batch file
./run-dev.bat
```

### Development Environment
- **Database**: H2 in-memory for development (`application-dev.yml`)
- **H2 Console**: Available at `/h2-console` (JDBC URL: `jdbc:h2:mem:projectz`)
- **API Documentation**: Swagger UI at `/swagger-ui`
- **Default Schema**: All operations use `projectz` schema

### Testing Endpoints
- **Authentication**: `POST /api/v1/auth/login`
- **User Info**: `GET /api/v1/auth/me` (requires JWT)
- **Health Check**: Standard Spring Boot endpoints

## Code Conventions

### Entity Mapping
- Use `@Table(name = "table_name", schema = "projectz")` for all entities
- Foreign key relationships use `@ManyToOne(fetch = FetchType.LAZY)` with `@JoinColumn`
- Unique constraints defined at table level: `@UniqueConstraint(name = "UX_TableName__ColumnName")`

### Controller Standards
- All REST controllers use `/api/v1/` prefix
- Resource-based URLs (e.g., `/api/v1/users`, `/api/v1/products`)
- Use `@RequiredArgsConstructor` with final fields for dependency injection
- Return `ResponseEntity<T>` for consistent response handling

### Service Layer
- Business logic resides in `@Service` classes
- Use constructor injection with `@RequiredArgsConstructor`
- Handle entity not found scenarios appropriately
- Implement audit logging for data changes

### Security Configuration
```java
// JWT configuration in application-dev.yml
app:
  jwt:
    secret: "======================projectz-dev-secret-key======================"
    expiration: 86400000  # 24 hours
    refresh-token:
      expiration: 604800000  # 7 days
```

### MapStruct Integration
- Use `@Mapper(componentModel = "spring")` for dependency injection
- Map entities to DTOs with explicit `@Mapping` annotations
- Handle password and sensitive fields with `@Mapping(target = "field", ignore = true)`

## Key Integration Points

### Database Schema
- **Master Data**: `mst_user`, `mst_akses`, `mst_menu`, `mst_group_menu`, `mst_kategori_produk`, `mst_produk`, `mst_supplier`
- **Mappings**: `map_akses_menu` (role-menu permissions), `map_produk_supplier` (product-supplier relationships)
- **Audit Logs**: `log_kategori_produk`, `log_produk`, `log_supplier` for change tracking

### Authentication Flow
1. User authenticates via `/api/v1/auth/login` with username/password
2. System validates credentials and returns JWT token in `AuthResponseDto`
3. Subsequent requests include JWT in Authorization header: `Bearer <token>`
4. `JwtRequestFilter` validates token and sets Spring Security context

### Error Handling
- Use Spring's `@ControllerAdvice` for global exception handling
- Return consistent error response format
- Handle JWT expiration and validation errors gracefully

## Common Tasks

### Adding New Entity
1. Create entity class extending `Auditable` with proper `@Table` annotation
2. Add repository interface extending `JpaRepository`
3. Implement service class with CRUD operations
4. Create REST controller with standard endpoints
5. Add Flyway migration script if needed

### Adding New Endpoint
1. Define method in appropriate service class
2. Add REST endpoint in controller with proper HTTP method annotation
3. Use MapStruct for DTO mapping if needed
4. Add appropriate security annotations if required

### Database Changes
1. Create new Flyway migration file with version number: `V{n}__description.sql`
2. Update entity classes if schema changes affect existing entities
3. Test migration with clean H2 database startup
