-- V1: Initial BloodBridge Schema Migration
-- 14 normalized PostgreSQL tables with relations, constraints and indices

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL, -- ROLE_DONOR, ROLE_PATIENT, ROLE_HOSPITAL, ROLE_ADMIN
    full_name VARCHAR(150) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_users_phone ON users(phone_number);

-- 2. Donor Profiles Table
CREATE TABLE IF NOT EXISTS donor_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    blood_group VARCHAR(10) NOT NULL, -- A_POS, A_NEG, B_POS, B_NEG, AB_POS, AB_NEG, O_POS, O_NEG
    date_of_birth DATE NOT NULL,
    gender VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    address_line VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    last_donation_date DATE,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    emergency_consent BOOLEAN NOT NULL DEFAULT TRUE,
    notify_email BOOLEAN NOT NULL DEFAULT TRUE,
    notify_sms BOOLEAN NOT NULL DEFAULT TRUE,
    notify_whatsapp BOOLEAN NOT NULL DEFAULT TRUE,
    total_donations INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_donor_blood_group ON donor_profiles(blood_group);
CREATE INDEX IF NOT EXISTS idx_donor_city ON donor_profiles(city);
CREATE INDEX IF NOT EXISTS idx_donor_pincode ON donor_profiles(pincode);
CREATE INDEX IF NOT EXISTS idx_donor_available ON donor_profiles(is_available);
CREATE INDEX IF NOT EXISTS idx_donor_lat_long ON donor_profiles(latitude, longitude);

-- 3. Patient Profiles Table
CREATE TABLE IF NOT EXISTS patient_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    emergency_contact_name VARCHAR(150),
    emergency_contact_phone VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(20),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 4. Hospital Profiles Table
CREATE TABLE IF NOT EXISTS hospital_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    hospital_name VARCHAR(200) NOT NULL,
    registration_number VARCHAR(100) NOT NULL UNIQUE,
    hospital_type VARCHAR(50) NOT NULL DEFAULT 'GENERAL',
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    contact_person VARCHAR(150),
    contact_email VARCHAR(150),
    contact_phone VARCHAR(20),
    helpline_number VARCHAR(20),
    verification_status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, REJECTED
    website_url VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_hospital_verification ON hospital_profiles(verification_status);
CREATE INDEX IF NOT EXISTS idx_hospital_city ON hospital_profiles(city);

-- 5. Blood Requests Table
CREATE TABLE IF NOT EXISTS blood_requests (
    id BIGSERIAL PRIMARY KEY,
    request_code VARCHAR(50) NOT NULL UNIQUE,
    requester_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    hospital_id BIGINT REFERENCES hospital_profiles(id) ON DELETE SET NULL,
    hospital_name VARCHAR(200) NOT NULL,
    hospital_address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    patient_name VARCHAR(150) NOT NULL,
    patient_age INTEGER,
    patient_gender VARCHAR(20),
    blood_group VARCHAR(10) NOT NULL,
    units_required INTEGER NOT NULL DEFAULT 1,
    units_fulfilled INTEGER NOT NULL DEFAULT 0,
    urgency VARCHAR(50) NOT NULL DEFAULT 'HIGH', -- LOW, MEDIUM, HIGH, CRITICAL_EMERGENCY
    required_date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, VERIFIED, ACTIVE, DONOR_RESPONDED, FULFILLED, CANCELLED, EXPIRED
    contact_name VARCHAR(150) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    contact_preference VARCHAR(20) NOT NULL DEFAULT 'PHONE', -- PHONE, SMS, WHATSAPP
    reason_or_notes TEXT,
    verified_by_user_id BIGINT REFERENCES users(id),
    verified_at TIMESTAMP WITHOUT TIME ZONE,
    fulfilled_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_requests_status ON blood_requests(status);
CREATE INDEX IF NOT EXISTS idx_requests_blood_group ON blood_requests(blood_group);
CREATE INDEX IF NOT EXISTS idx_requests_city ON blood_requests(city);
CREATE INDEX IF NOT EXISTS idx_requests_urgency ON blood_requests(urgency);

-- 6. Donor Responses Table
CREATE TABLE IF NOT EXISTS donor_responses (
    id BIGSERIAL PRIMARY KEY,
    request_id BIGINT NOT NULL REFERENCES blood_requests(id) ON DELETE CASCADE,
    donor_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    response_status VARCHAR(50) NOT NULL DEFAULT 'ACCEPTED', -- ACCEPTED, DECLINED, ARRIVED, DONATED, CANCELLED
    units_pledged INTEGER NOT NULL DEFAULT 1,
    notes TEXT,
    responded_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_request_donor UNIQUE (request_id, donor_user_id)
);

CREATE INDEX IF NOT EXISTS idx_responses_request ON donor_responses(request_id);
CREATE INDEX IF NOT EXISTS idx_responses_donor ON donor_responses(donor_user_id);

-- 7. Donation History Table
CREATE TABLE IF NOT EXISTS donation_history (
    id BIGSERIAL PRIMARY KEY,
    donor_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    request_id BIGINT REFERENCES blood_requests(id) ON DELETE SET NULL,
    hospital_name VARCHAR(200) NOT NULL,
    donation_date DATE NOT NULL,
    blood_group VARCHAR(10) NOT NULL,
    units_donated INTEGER NOT NULL DEFAULT 1,
    donation_type VARCHAR(50) NOT NULL DEFAULT 'WHOLE_BLOOD', -- WHOLE_BLOOD, PLATELETS, PLASMA
    certificate_number VARCHAR(100),
    verified_by_hospital_id BIGINT REFERENCES hospital_profiles(id) ON DELETE SET NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_donation_history_donor ON donation_history(donor_user_id);
CREATE INDEX IF NOT EXISTS idx_donation_history_date ON donation_history(donation_date);

-- 8. Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(50) NOT NULL, -- EMERGENCY_REQUEST, REQUEST_STATUS, DONOR_RESPONSE, REMINDER, SYSTEM
    channel VARCHAR(30) NOT NULL DEFAULT 'IN_APP', -- IN_APP, EMAIL, SMS, WHATSAPP
    reference_id VARCHAR(100),
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    sent_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_notifications_user_unread ON notifications(user_id, is_read);

-- 9. Notification Preferences Table
CREATE TABLE IF NOT EXISTS notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    email_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    sms_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    whatsapp_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    in_app_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    emergency_alerts_only BOOLEAN NOT NULL DEFAULT FALSE,
    max_distance_km INTEGER NOT NULL DEFAULT 25,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 10. Hospital Verifications Table
CREATE TABLE IF NOT EXISTS hospital_verifications (
    id BIGSERIAL PRIMARY KEY,
    hospital_profile_id BIGINT NOT NULL REFERENCES hospital_profiles(id) ON DELETE CASCADE,
    document_name VARCHAR(200) NOT NULL,
    document_type VARCHAR(100) NOT NULL,
    document_url_or_number VARCHAR(255) NOT NULL,
    verification_notes TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, REJECTED
    reviewed_by_user_id BIGINT REFERENCES users(id),
    reviewed_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_hospital_verifications_status ON hospital_verifications(status);

-- 11. User Reports Table
CREATE TABLE IF NOT EXISTS user_reports (
    id BIGSERIAL PRIMARY KEY,
    reporter_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reported_user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    request_id BIGINT REFERENCES blood_requests(id) ON DELETE SET NULL,
    report_type VARCHAR(50) NOT NULL, -- FRAUD, SUSPICIOUS_REQUEST, INCORRECT_INFO, HARASSMENT, OTHER
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN', -- OPEN, INVESTIGATING, RESOLVED, DISMISSED
    resolution_notes TEXT,
    handled_by_user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_user_reports_status ON user_reports(status);

-- 12. Audit Logs Table
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100),
    ip_address VARCHAR(100),
    user_agent TEXT,
    details TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_action ON audit_logs(action);
CREATE INDEX IF NOT EXISTS idx_audit_logs_created ON audit_logs(created_at);

-- 13. Refresh Tokens Table
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_refresh_tokens_token ON refresh_tokens(token);

-- 14. Blood Availability Table
CREATE TABLE IF NOT EXISTS blood_availability (
    id BIGSERIAL PRIMARY KEY,
    hospital_profile_id BIGINT NOT NULL REFERENCES hospital_profiles(id) ON DELETE CASCADE,
    blood_group VARCHAR(10) NOT NULL,
    units_available INTEGER NOT NULL DEFAULT 0,
    component_type VARCHAR(50) NOT NULL DEFAULT 'WHOLE_BLOOD', -- WHOLE_BLOOD, PRBC, PLATELETS, FFP
    last_updated_by_user_id BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_hospital_blood_comp UNIQUE (hospital_profile_id, blood_group, component_type)
);

CREATE INDEX IF NOT EXISTS idx_blood_availability_group ON blood_availability(blood_group);
