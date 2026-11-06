# BloodBridge Gap Analysis

**Date:** 2026-11-06  
**Version:** 1.0 (PRD Alignment)

## 1. Initial State Inspection
An inspection of the workspace revealed:
- `powershell.bat` was configured to support running commands with the detected Java 25 (`C:\Program Files\Java\jdk-25.0.2`) and Maven 3.9.9.
- Initial backend skeleton created under `backend/src/main/java/com/bloodbridge/` with 14 basic entities and DTOs.
- No frontend code or test suites were yet generated.

## 2. Gaps Identified Against PRD v1.0
1. **Package Namespace:**
   - PRD Section 5 specifies `in.bloodbridge` as the canonical package name.
   - Refactoring needed from `com.bloodbridge` to `in.bloodbridge`.

2. **Entity & Schema Details:**
   - `blood_requests` must use format `BB-YYYYMMDD-XXXXXX` (base-32 6-char random alphanumeric) and store `fingerprint_hash` for duplicate prevention.
   - Formal `RequestStateMachine` class enforcing exact transitions:
     `PENDING -> VERIFIED -> ACTIVE -> DONOR_RESPONDED -> FULFILLED` (or terminal `CANCELLED` / `EXPIRED`).
   - Need dedicated `contact_requests` table and model for privacy-first consent workflow before sharing contact details.
   - Refresh token storage must store SHA-256 hash with token family ID for reuse detection and revocation.
   - Add `SmsProvider` interface with `LoggingSmsProvider`, `Msg91SmsProvider`, `TwilioSmsProvider`.

3. **Mandatory Safety Disclaimers:**
   - Exact disclaimer specified in Section 8.4 must be prominently surfaced on footer, search, guide, request, and donor dashboards:
     *"BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility or blood compatibility. Always confirm with a qualified medical professional and the treating blood bank. In an emergency, contact your hospital or call 112."*

4. **Frontend Architecture:**
   - Complete React 18+ TypeScript application with Tailwind CSS, Framer Motion, Lucide React, Recharts, Leaflet, and full role-based routing.
   - 21 required pages with dark/light theme support.

5. **Testing & Quality Assurance:**
   - JUnit 5 and Mockito service and controller tests.
   - Compatibility matrix 8x8 unit test.
   - State machine transition tests.
