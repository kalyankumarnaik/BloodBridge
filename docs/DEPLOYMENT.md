# BloodBridge Deployment & Run Guide

---

## 1. Zero-Config Local Development Run

The application is engineered for zero-config execution out of the box using JDK 25 and Node.js.

### Prerequisites
- Java 25 (e.g. `Oracle OpenJDK 25` or `Eclipse Temurin`)
- Node.js 20+ and npm 10+
- Maven 3.9+ (or use the provided Maven wrapper)

### Step 1: Start Backend (In-Memory PostgreSQL Mode)
In development (`spring.profiles.active=dev`), the backend uses an embedded H2 database running in PostgreSQL compatibility mode with automatic schema initialization and demo seed data:

```powershell
cd backend
mvn clean spring-boot:run
```
The backend API starts on `http://localhost:8080`.
Swagger / OpenAPI Docs are accessible at: `http://localhost:8080/swagger-ui.html`.

### Step 2: Start Frontend Development Server
In a separate terminal:
```powershell
cd frontend
npm run dev
```
The React + Vite application will open at `http://localhost:5173`.
All requests to `/api` are automatically proxied to `http://localhost:8080`.

---

## 2. Production Deployment via Docker Compose

To deploy the entire production stack (PostgreSQL 16, Spring Boot backend, and Nginx frontend):

### Step 1: Configure Environment Variables
Copy `.env.example` to `.env` and configure passwords and secrets:
```bash
cp .env.example .env
```

### Step 2: Build & Launch Containers
```bash
docker-compose up --build -d
```

### Healthcheck & Container Verification
```bash
docker-compose ps
```
- `bloodbridge-db`: PostgreSQL 16 (Healthchecked on port 5432)
- `bloodbridge-backend`: Spring Boot (Healthchecked via `/actuator/health` on port 8080)
- `bloodbridge-frontend`: Nginx (Serving minified assets on ports 80 and 3000)

Access the live application at: `http://localhost`.

---

## 3. Demo Accounts for Testing

When running with the `dev` profile or using seeded data, you can sign in using these pre-configured accounts:

| Role | Username / Email | Password | Primary Capabilities |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@bloodbridge.in` | `Admin@12345` | KPIs, hospital verification queue, user moderation, audit logs |
| **Hospital Admin** | `hospital.admin@citycare.org` | `Hospital@12345` | Verify emergency requests, publish live component availability |
| **Voluntary Donor**| `donor.rahul@example.com` | `Donor@12345` | Availability toggle, respond to requests, grant contact consent |
| **Patient / Family**| `patient.priya@example.com` | `Patient@12345` | Create emergency requests, view responses, call consenting donors |

---

## 4. Running Automated Tests

### Backend Unit & Integration Tests (JUnit 5 & Mockito)
```powershell
cd backend
mvn test
```
Runs 21 unit and integration test suites verifying:
- ABO/Rh Red Blood Cell Compatibility Matrix (8x8)
- Haversine Distance & Bounding Box Calculations
- Request State Machine Transitions (`PENDING` -> `VERIFIED` -> `ACTIVE` -> `DONOR_RESPONDED` -> `FULFILLED`)
- Medical Disclaimer Enforcements

### Frontend Build Verification (TypeScript & Vite)
```powershell
cd frontend
npm run build
```
Executes `tsc` type checking and compiles minified production chunks into `frontend/dist/`.
