# BloodBridge (भारत) 🩸
### Smart Blood Donation & Emergency Donor Connection Platform

[![Java 25](https://img.shields.io/badge/Java-25%20LTS-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.3-61DAFB?logo=react&logoColor=black)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.6-3178C6?logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-3.4-38B2AC?logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/Compliance-DPDP%20Act%202023-emerald)]()

---

> ### ⚠️ Mandatory Medical & Transfusion Disclaimer (PRD §8.4)
> **BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility or blood compatibility. Always confirm with a qualified medical professional and the treating blood bank. In an emergency, contact your hospital or call 112.**  
> *Under the Drugs & Cosmetics Act, 1940 and Supreme Court directives, commercial buying, selling, or financial brokerage of human blood in India is strictly illegal. BloodBridge is 100% voluntary and non-commercial.*

---

## 🌟 Executive Overview

**BloodBridge** is a location-based emergency blood donor connection platform built specifically for India (`en-IN`, `Asia/Kolkata`, `+91` phone format, 6-digit PIN codes). It connects patients, families, voluntary blood donors, and accredited hospital blood banks when every minute counts.

The platform eliminates the chaos of panicked messaging forwards by combining **algorithmic ABO/Rh matching**, **Haversine proximity search**, and a **privacy-first consent gate** compliant with the **Digital Personal Data Protection (DPDP) Act 2023**.

---

## 🚀 Key Features

1. **Smart Donor Discovery (Privacy-First)**:
   - Search consenting voluntary donors by blood group, city, PIN code, and radius (5–100 km).
   - Donors' exact coordinates, phone numbers, and full names are masked by default. Distance bands (`< 2 km`, `2–5 km`, etc.) and area circles are displayed.
   - Contact coordinates are unlocked **only** when a donor grants explicit consent to a verified emergency request.

2. **Emergency Blood Request Lifecycle**:
   - Unique request ID generation (`BB-YYYYMMDD-XXXXXX`).
   - Anti-spam SHA-256 fingerprinting prevents duplicate request broadcasting within a 2-hour window.
   - Formal state machine workflow: `PENDING` ➔ `VERIFIED` ➔ `ACTIVE` ➔ `DONOR_RESPONDED` ➔ `FULFILLED` (or terminal `CANCELLED` / `EXPIRED`).
   - 1-click viral WhatsApp sharing with pre-formatted emergency text.

3. **ABO / Rh Compatibility Engine**:
   - Complete 8×8 scientific red blood cell compatibility matrix.
   - Explains universal donor (O-) and universal recipient (AB+) dynamics, antigen-antibody interactions, and mandatory hospital cross-matching requirements.

4. **Role-Specific Dashboards**:
   - **Donor**: Quick availability toggle (`Available Now`, `On Call`, `Inactive`), 90-day courtesy resting interval tracker, incoming consent requests, active emergency feed.
   - **Patient / Family**: Real-time request stepper, responding donors list with direct "Call Donor" button (once consented), fulfill and cancel actions.
   - **Hospital / Blood Bank**: Verify and activate pending requests, publish live component availability (units in stock, critical low badges).
   - **Administrator**: Recharts visual analytics, hospital verification approvals queue, user moderation (suspend/ban), immutable security audit logs with SHA-256 hashed IP addresses, formula-safe CSV reports.

5. **Multi-Channel Targeted Alerts**:
   - In-app notification feed, configurable SMS provider interface (`Logging`, `MSG91`, `Twilio`), and click-to-chat WhatsApp links.

---

## 🛠️ Technology Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 25 LTS, Spring Boot 3.3.5, Spring Security 6 (Stateless JWT, BCrypt 12), Spring Data JPA, Hibernate, Bucket4j Rate Limiting, Springdoc OpenAPI / Swagger |
| **Frontend** | React 18, TypeScript (Strict), Vite, Tailwind CSS, Lucide React, Recharts, Leaflet / OpenStreetMap |
| **Database** | PostgreSQL 16 (Production) / In-memory PostgreSQL mode (Dev/Test), Flyway Migrations |
| **DevOps** | Docker, Docker Compose, Multi-stage builds, Nginx reverse proxy |

---

## 🏃 Quick Start (Local Development)

### 1. Prerequisites
- **Java 25** (`Oracle OpenJDK 25` or `Eclipse Temurin`)
- **Node.js 20+** and **npm 10+**
- **Maven 3.9+**

### 2. Run Backend
```powershell
cd backend
mvn clean spring-boot:run
```
- API Base: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Healthcheck: `http://localhost:8080/actuator/health`

### 3. Run Frontend
```powershell
cd frontend
npm run dev
```
- Application: `http://localhost:5173` (with live `/api` proxy to port 8080)

---

## 🐳 Docker Compose Deployment (Production)

Deploy the full stack with PostgreSQL 16, Spring Boot backend, and Nginx frontend in one command:

```bash
docker-compose up --build -d
```
- Open `http://localhost` (or `http://localhost:3000`).

---

## 🔑 Demo Accounts (Pre-Seeded)

The frontend includes a **1-click quick-fill** card on the `/login` page for instant evaluation:

| Role | Email | Password | Primary Purpose |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@bloodbridge.in` | `Admin@12345` | Hospital approvals, analytics, audit trail |
| **Voluntary Donor** | `donor.rahul@example.com` | `Donor@12345` | Availability controls, incoming emergency requests |
| **Patient / Family** | `patient.priya@example.com` | `Patient@12345` | Emergency requests, call consenting donors |
| **Hospital Admin** | `hospital.admin@citycare.org` | `Hospital@12345` | Medical request verification, blood stock updates |

---

## 🧪 Testing & Verification

### Backend Automated Test Suite
```powershell
cd backend
mvn test
```
- **21 passed tests** verifying ABO/Rh matrices, Haversine bounding boxes, Request State Machine transitions, and disclaimer verifications.

### Frontend Compilation & Bundle Build
```powershell
cd frontend
npm run build
```
- Fully type-checked by `tsc` with zero errors. Minified Vite production bundle generated in `frontend/dist/`.

---

## 📄 License & Compliance

Built for public humanitarian service across India in compliance with the **Digital Personal Data Protection (DPDP) Act, 2023** and **National Blood Transfusion Council (NBTC)** standards.
