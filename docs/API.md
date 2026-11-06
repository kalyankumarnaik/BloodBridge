# BloodBridge REST API Specification

Version: 1.0.0  
Base URL: `/api`  
Standard Headers: `Content-Type: application/json`, `Authorization: Bearer <jwt_access_token>`

All successful responses follow the envelope format:
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { ... },
  "timestamp": "2026-11-06T10:00:00Z"
}
```

---

## 1. Authentication & Session Management (`/api/auth`)

### 1.1 Login
- **Endpoint**: `POST /api/auth/login`
- **Rate Limit**: 25 requests / min (per IP)
- **Lockout**: 5 consecutive failures triggers 15-minute lock.
- **Request Body**:
```json
{
  "username": "donor.rahul@example.com",
  "password": "Donor@12345"
}
```
- **Response `200 OK`**:
```json
{
  "accessToken": "eyJhbGciOi...",
  "refreshToken": "7c48b291...",
  "userId": "usr_94b1...",
  "email": "donor.rahul@example.com",
  "role": "ROLE_DONOR",
  "status": "ACTIVE",
  "fullName": "Rahul Sharma",
  "bloodGroup": "O_POSITIVE",
  "city": "Bangalore"
}
```

### 1.2 Voluntary Donor Registration
- **Endpoint**: `POST /api/auth/register`
- **Request Body**:
```json
{
  "fullName": "Rahul Sharma",
  "email": "donor.rahul@example.com",
  "phone": "+919876543210",
  "password": "Donor@12345",
  "role": "ROLE_DONOR",
  "bloodGroup": "O_POSITIVE",
  "dateOfBirth": "1998-05-14",
  "city": "Bangalore",
  "state": "Karnataka",
  "pinCode": "560001",
  "availability": "AVAILABLE_NOW",
  "emergencySmsConsent": true
}
```

### 1.3 Hospital Registration
- **Endpoint**: `POST /api/auth/register-hospital`
- **Request Body**:
```json
{
  "hospitalName": "City Care Multi-Specialty Hospital",
  "email": "bloodbank@citycare.org",
  "phone": "+918025024444",
  "password": "Hospital@12345",
  "contactPerson": "Dr. S. Mehta",
  "licenseNumber": "KA-BB-2023-4921",
  "nabhAccredited": true,
  "address": "98 HAL Old Airport Rd",
  "city": "Bangalore",
  "state": "Karnataka",
  "pinCode": "560017"
}
```

### 1.4 Refresh Token Rotation
- **Endpoint**: `POST /api/auth/refresh`
- **Request Body**: `{ "refreshToken": "<token>" }`
- **Behavior**: Issues a brand new access token & rotates the refresh token. Old token is immediately invalidated. Reuse of a revoked token triggers revocation of the entire token family.

### 1.5 DPDP Account & Data Deletion
- **Endpoint**: `POST /api/auth/delete-account`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**: `{ "reason": "Moving abroad" }`

---

## 2. Smart Donor Discovery & Privacy (`/api/donors`)

### 2.1 Search Donors (Privacy-Preserving)
- **Endpoint**: `GET /api/donors/search`
- **Query Params**:
  - `bloodGroup` (e.g. `O_POSITIVE`, `O_NEGATIVE`)
  - `city` (e.g. `Bangalore`)
  - `pinCode` (e.g. `560001`)
  - `radiusKm` (e.g. `25`)
  - `availability` (`AVAILABLE_NOW`, `ON_CALL`)
  - `verifiedOnly` (`true` / `false`)
- **Response**: Returns anonymized cards with distance bands. Phone and exact coordinates are strictly omitted.
```json
[
  {
    "donorUuid": "550e8400-e29b-41d4-a716-446655440000",
    "displayName": "Rahul S.",
    "bloodGroup": "O_POSITIVE",
    "city": "Bangalore",
    "state": "Karnataka",
    "pinCodePrefix": "560***",
    "distanceBand": "2–5 km",
    "availability": "AVAILABLE_NOW",
    "totalDonations": 3,
    "isVerified": true,
    "hasActiveConsent": false,
    "contactPhone": null
  }
]
```

### 2.2 Request Donor Contact
- **Endpoint**: `POST /api/donors/contact-requests`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
```json
{
  "donorUserId": "550e8400-e29b-41d4-a716-446655440000",
  "bloodRequestId": "BB-20261106-492182",
  "reason": "Emergency surgery at Apollo Hospital"
}
```

### 2.3 Respond to Contact Request (Consent Grant)
- **Endpoint**: `POST /api/donors/contact-requests/{id}/respond?accept=true`
- **Headers**: `Authorization: Bearer <donor_token>`

---

## 3. Emergency Blood Requests (`/api/requests`)

### 3.1 Create Emergency Blood Request
- **Endpoint**: `POST /api/requests`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
```json
{
  "patientName": "Aarav Sharma",
  "bloodGroup": "O_POSITIVE",
  "unitsRequired": 2,
  "priority": "CRITICAL",
  "hospitalName": "Manipal Hospital",
  "hospitalAddress": "98 HAL Old Airport Rd",
  "city": "Bangalore",
  "state": "Karnataka",
  "pinCode": "560017",
  "requiredBy": "2026-11-06T20:00:00Z",
  "contactPreference": "PHONE",
  "additionalNotes": "Urgent platelet transfusion for dengue treatment"
}
```

### 3.2 Verify Blood Request (Hospital / Admin)
- **Endpoint**: `POST /api/requests/{id}/verify`
- **Role Required**: `ROLE_HOSPITAL` or `ROLE_ADMIN`
- **Transition**: `PENDING` -> `VERIFIED` -> `ACTIVE`

### 3.3 Respond to Blood Request
- **Endpoint**: `POST /api/requests/{id}/respond?status=ACCEPTED&reason=Heading+to+hospital`
- **Role Required**: `ROLE_DONOR`
- **Transition**: Moves request to `DONOR_RESPONDED` state.

### 3.4 Fulfill Request
- **Endpoint**: `POST /api/requests/{id}/fulfill`
- **Role Required**: Request owner, Hospital, or Admin.
- **Transition**: Moves request to terminal `FULFILLED` state.

---

## 4. Blood Component Availability & Blood Banks (`/api/blood-banks`)

### 4.1 Public Availability Feed
- **Endpoint**: `GET /api/blood-banks/public`
- **Query Params**: `bloodGroup`, `city`

### 4.2 Update Blood Bank Stock
- **Endpoint**: `POST /api/hospital/availability?bloodGroup=O_POSITIVE&status=AVAILABLE&unitsMin=15`
- **Role Required**: `ROLE_HOSPITAL`

---

## 5. Administration & Moderation (`/api/admin`)

- `GET /api/admin/analytics`: Platform KPIs, registration trends, and blood group distribution.
- `GET /api/admin/hospitals/pending`: Queue of hospital registrations awaiting verification.
- `POST /api/admin/hospitals/{id}/review?status=APPROVED&reason=Verified+license`: Approve/reject hospital.
- `PATCH /api/admin/users/{userId}/status?status=SUSPENDED&reason=Abuse`: Moderate bad actors.
- `GET /api/admin/reports/csv?type=emergency-requests`: Sanitized CSV export with formula injection prevention.
- `GET /api/admin/audit-logs`: Query immutable security audit events with SHA-256 hashed IP addresses.
