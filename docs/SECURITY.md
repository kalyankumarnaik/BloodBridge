# BloodBridge Security & Compliance Architecture

Version: 1.0.0  
Market: India (`en-IN`, `Asia/Kolkata`)  
Statutory Standard: Digital Personal Data Protection (DPDP) Act 2023, National Blood Transfusion Council (NBTC)

---

## 1. Authentication & Session Security

1. **Stateless JWT with Short Lifetime**:
   - Access tokens have a 15-minute lifespan (`900,000 ms`).
   - Signed with HMAC-SHA256 (`HS256`) using a minimum 256-bit cryptographically random secret.
   - Claims include `sub` (userId), `email`, `role`, `iat`, and `exp`.

2. **Refresh Token Rotation & Theft Detection**:
   - Refresh tokens (7-day validity) are stored hashed with SHA-256 in the database.
   - Every refresh request invalidates the presenting refresh token and issues a new pair.
   - If an invalidated token is submitted again (replay attack indicating token theft), the backend invalidates all active sessions for that user account immediately.

3. **Password Security**:
   - Passwords must be at least 8 characters with at least one uppercase letter, one lowercase letter, one number, and one special character.
   - Stored using `BCrypt` with cost factor 12.

4. **Account Lockout & Rate Limiting**:
   - In-memory token bucket rate limiter (`RateLimitingFilter`):
     - Auth endpoints (`/api/auth/*`): 25 requests / min per IP.
     - General endpoints: 120 requests / min per IP.
     - Rate-limited responses return HTTP `429 Too Many Requests` with a `Retry-After` header.
   - 5 consecutive failed login attempts lock the user account for 15 minutes.

---

## 2. Privacy by Default (DPDP Act 2023 Compliance)

1. **Zero Public Phone / Coordinates Exposure**:
   - Public donor discovery queries return first names with initials (`Rahul S.`) and opaque UUIDs.
   - Exact home coordinates are strictly masked. The API returns distance bands (`< 2 km`, `2–5 km`, `5–10 km`, `10–25 km`).
   - Phone numbers are shared ONLY after explicit, unexpired consent is granted by the donor in response to a verified emergency blood request.

2. **Right to be Forgotten (Data Erasure)**:
   - Users can self-serve account and data deletion requests under DPDP Section 12.
   - Sensitive personal identifiers (phone, address, coordinates) are expunged from primary profile tables.

3. **Immutable Audit Logging**:
   - All critical actions (logins, consent grants, status changes, administrative actions) are recorded in an append-only `audit_logs` table.
   - Requester IP addresses are hashed using SHA-256 before persistence to prevent storing raw PII while preserving audit integrity.

---

## 3. Hospital Verification & Anti-Abuse

1. **Document Verification**:
   - Hospitals must provide State Blood Bank license numbers or NABH accreditation.
   - Document uploads are validated for MIME type using magic bytes (PDF, JPG, PNG) and restricted to 5MB maximum size.
   - Documents are stored outside the public web root.

2. **Request Fingerprinting & Deduplication**:
   - Emergency blood requests generate an SHA-256 fingerprint from `patientName + hospitalAddress + bloodGroup + rounded_hour`.
   - Identical requests within a 2-hour window are rejected with HTTP 409 Conflict to prevent spamming donors.

3. **CSV Export Sanitization (Formula Injection Prevention)**:
   - Administrative CSV exports sanitize all string fields: any value starting with `=`, `+`, `-`, or `@` is prefixed with a single quote (`'`) to prevent spreadsheet formula execution exploits (CSV Injection).

---

## 4. Medical Safety & Disclaimer Notice

BloodBridge enforces the mandatory Section 8.4 disclaimer across all touchpoints:
> *"BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility or blood compatibility. Always confirm with a qualified medical professional and the treating blood bank. In an emergency, contact your hospital or call 112."*

Buying, selling, or commercial brokerage of human blood is strictly prohibited per the Drugs and Cosmetics Act, 1940 and Supreme Court directives.
