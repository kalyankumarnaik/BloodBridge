-- V2: Seed Data for Initial Testing and Demo Environments

-- Insert Default Admin
INSERT INTO users (email, password_hash, role, full_name, phone_number, is_active, is_verified, created_at)
VALUES 
('admin@bloodbridge.org', '$2a$10$gT2qTsz0V8H92tS0z3U0meu.s0Vf6r0dK7O7U8vA.oIe5wN1HlM5e', 'ROLE_ADMIN', 'Platform Administrator', '+919876543210', true, true, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insert Hospital User
INSERT INTO users (email, password_hash, role, full_name, phone_number, is_active, is_verified, created_at)
VALUES 
('apollo.hospital@bloodbridge.org', '$2a$10$gT2qTsz0V8H92tS0z3U0meu.s0Vf6r0dK7O7U8vA.oIe5wN1HlM5e', 'ROLE_HOSPITAL', 'Apollo Blood Centre & Hospital', '+919876500001', true, true, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insert Donor User
INSERT INTO users (email, password_hash, role, full_name, phone_number, is_active, is_verified, created_at)
VALUES 
('rahul.donor@bloodbridge.org', '$2a$10$gT2qTsz0V8H92tS0z3U0meu.s0Vf6r0dK7O7U8vA.oIe5wN1HlM5e', 'ROLE_DONOR', 'Rahul Sharma', '+919876500002', true, true, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insert Patient User
INSERT INTO users (email, password_hash, role, full_name, phone_number, is_active, is_verified, created_at)
VALUES 
('ananya.patient@bloodbridge.org', '$2a$10$gT2qTsz0V8H92tS0z3U0meu.s0Vf6r0dK7O7U8vA.oIe5wN1HlM5e', 'ROLE_PATIENT', 'Ananya Patel', '+919876500003', true, true, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;
