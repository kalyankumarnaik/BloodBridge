# Architecture and Implementation Decisions

**Date:** 2026-11-06  
**Document:** docs/DECISIONS.md

## 1. Runtime & Framework Versions
- **Java Runtime:** Java 25 (`Oracle JDK 25.0.2 LTS` detected and configured in `JAVA_HOME`).
- **Spring Boot:** Spring Boot `3.4.3` (compatible with Java 25 LTS).
- **Maven:** Apache Maven `3.9.9` with Java 25 compiler target.

## 2. Geolocation and Distance Calculation
- **Algorithm:** Haversine formula implemented in `GeolocationService` with bounding box prefiltering on indexed latitude and longitude coordinates.
- **Privacy Standard:** Coarse approximation (~500m precision) is used for public search to protect donor privacy. Exact coordinates and residential addresses are never exposed publicly.

## 3. Database Strategy
- **Production Profile:** PostgreSQL 16+ via Flyway migrations (`V1__initial_schema.sql` and `V2__seed_data.sql`).
- **Dev Profile:** H2 embedded database running in PostgreSQL compatibility mode (`MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE`) for instant zero-dependency local startup and automated test suites.

## 4. Package Structure
- Primary backend package: `in.bloodbridge` as mandated by Section 5 of the PRD.

## 5. Security and Privacy Decisions
- **Token Security:** Short-lived JWT access token (15 mins) and SHA-256 hashed refresh token in database with family-based rotation and instant reuse revocation.
- **Rate Limiting:** Token-bucket rate limiting filter on authentication (`/api/v1/auth/**`) and sensitive emergency endpoints.
- **Privacy by Default:** Contact information is only shared when a donor explicitly accepts a request and grants contact consent. All reveals are recorded in `audit_logs`.
