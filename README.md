# 🚗 Progetto Veicoli — Spring Boot REST API

A vehicle management API built with **Spring Boot 4**, **JPA/Hibernate**, and **PostgreSQL** — designed as a hands-on exercise in modeling real-world inheritance, normalized lookup data, and clean layered architecture.

This started as a school assignment and grew into something closer to a real backend: a multi-type vehicle catalog (cars, motorcycles, bicycles) with shared parent attributes, type-specific child data, normalized reference tables, and a flexible search engine across the whole fleet.

---

## Why this project is interesting

Most "CRUD exercise" projects model one flat table. This one doesn't get to take that shortcut — vehicles genuinely *are* different shapes of data:

- A **car** has doors and a license plate.
- A **motorcycle** has displacement (cc) and a license plate.
- A **bicycle** has gears, brakes, and suspension — and no plate at all.

So instead of forcing everything into one table full of nullable columns, this project uses **JPA's `JOINED` inheritance strategy**: one `veicoli` parent table holds the shared attributes, and each vehicle type gets its own child table joined by primary key. It's the same modeling decision you'd make on a real production schema with polymorphic entities — not a toy simplification.

---

## What's inside

### 🏗️ Architecture
Clean, consistent layering across every entity:

```
Controller → Service Interface → Service Implementation → Repository → Database
```

with a dedicated **DTO + mapper** layer separating what the API exposes from what JPA persists — no entities leaking straight out to the client.

### 🧬 Data model highlights

- **`Veicoli`** (parent) — shared fields: colore, marca, modello, anno di produzione, plus FK references to normalized lookup tables for fuel type and category.
- **`Macchina` / `Moto` / `Bici`** (children) — type-specific fields, mapped via `JOINED` inheritance with a discriminator column.
- **`Targa`** — license plates extracted into their own entity with a `@OneToOne` relationship to `Macchina`/`Moto`, enforcing real-world plate uniqueness at the database level — not just "a string field that happens to be unique."
- **Lookup tables** (`tipo_alimentazione`, `categoria`, `tipo_freno`, `tipo_sospensione`) — fuel types, vehicle categories, brake types, and suspension types are normalized into their own tables rather than hardcoded as Java enums, so adding a new fuel type is a data change, not a redeploy.

### ✅ Validation & data integrity

- Field-level validation with **separate rule sets for create vs. update** (`ValidationGroups`), so a `PATCH` request isn't forced to resubmit every field just to change one.
- A **regex-validated license plate format**, because "any non-empty string" isn't actually a license plate.
- **Service-level uniqueness checks** for plates (including the tricky "this plate already belongs to *this* vehicle, that's fine" case during updates).
- **Cross-table consistency checks** — a fuel type tagged for cars can't be assigned to a bicycle, even though they live in the same lookup table.

### 🔍 Search

A flexible, multi-criteria search across the *entire* vehicle fleet — not per-type — using a named JPQL query with optional filters (vehicle type, brand, model, color, year, fuel type, category, and plate). Every filter is optional and combinable, so `?marca=fiat` and `?marca=fiat&colore=rosso&tipoVeicolo=MACCHINA` both just work.

Getting this right surfaced some genuinely subtle JPQL/Hibernate behavior — implicit path joins silently becoming inner joins and quietly dropping valid rows, and PostgreSQL's JDBC driver guessing the wrong type for `NULL` parameters inside `LOWER()`. Both are documented in the query itself.

### 📖 API documentation

Fully documented and explorable via **Swagger / OpenAPI** (`springdoc-openapi`) — every endpoint, request shape, and validation rule is visible and testable from the browser, no Postman collection required.

---

## Tech stack

| | |
|---|---|
| **Language** | Java 25 |
| **Framework** | Spring Boot 4.1 |
| **Persistence** | Spring Data JPA / Hibernate |
| **Database** | PostgreSQL |
| **Validation** | Jakarta Bean Validation |
| **API docs** | Springdoc OpenAPI / Swagger UI |
| **Boilerplate reduction** | Lombok |

---

## Project structure

```
com.veicoli.sb
├── controllers/        REST endpoints
├── dto/
│   ├── input/           Request DTOs (with validation groups)
│   └── output/          Response DTOs
├── exceptions/          Custom exception type
├── mapping/              Entity → DTO mappers
├── models/                JPA entities (inheritance hierarchy + lookup tables)
├── repositories/          Spring Data JPA repositories
└── services/
    ├── interfaces/        Service contracts
    └── implementations/   Business logic
```

---

## Running it locally

```bash
# 1. Create a PostgreSQL database matching application.properties
# 2. Run the app — Hibernate will generate the schema
./mvnw spring-boot:run

# 3. Explore the API
http://localhost:8080/swagger-ui/index.html
```

---

## What I'd add next

- A front-end client to actually interact with the API visually — browse the fleet, filter/search, and manage vehicles through a UI instead of Swagger
