package in.bloodbridge.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.entity.*;
import in.bloodbridge.enums.*;
import in.bloodbridge.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserRepository userRepository, DonorProfileRepository donorProfileRepository, PatientProfileRepository patientProfileRepository, HospitalProfileRepository hospitalProfileRepository, BloodRequestRepository bloodRequestRepository, BloodAvailabilityRepository availabilityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.hospitalProfileRepository = hospitalProfileRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.availabilityRepository = availabilityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final HospitalProfileRepository hospitalProfileRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final BloodAvailabilityRepository availabilityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Running BloodBridge Dev Data Initializer...");

        // 1. Seed Admin
        if (!userRepository.existsByEmailIgnoreCase("admin@bloodbridge.org")) {
            User admin = User.builder()
                    .email("admin@bloodbridge.org")
                    .phone("9876543210")
                    .passwordHash(passwordEncoder.encode("Admin@123"))
                    .role(Role.ROLE_ADMIN)
                    .status(UserStatus.ACTIVE)
                    .emailVerifiedAt(Instant.now())
                    .phoneVerifiedAt(Instant.now())
                    .build();
            userRepository.save(admin);
            log.info("Seeded Admin: admin@bloodbridge.org / Admin@123");
        }

        // 2. Seed Verified Hospital
        if (!userRepository.existsByEmailIgnoreCase("apollo.hospital@bloodbridge.org")) {
            User hospitalUser = User.builder()
                    .email("apollo.hospital@bloodbridge.org")
                    .phone("9876500001")
                    .passwordHash(passwordEncoder.encode("Hospital@123"))
                    .role(Role.ROLE_HOSPITAL)
                    .status(UserStatus.ACTIVE)
                    .emailVerifiedAt(Instant.now())
                    .phoneVerifiedAt(Instant.now())
                    .build();
            hospitalUser = userRepository.save(hospitalUser);

            HospitalProfile hospital = HospitalProfile.builder()
                    .user(hospitalUser)
                    .name("Apollo Blood Centre & Multi-Speciality Hospital")
                    .registrationNumber("DL-HOSP-2026-9081")
                    .type("NABH_ACCREDITED")
                    .address("Sarita Vihar, Delhi Mathura Road")
                    .city("New Delhi")
                    .state("Delhi")
                    .pinCode("110076")
                    .lat(28.5355)
                    .lon(77.2874)
                    .officialPhone("01126925858")
                    .contactPerson("Dr. Rajesh Malhotra (Transfusion Head)")
                    .verificationStatus(VerificationStatus.VERIFIED)
                    .build();
            hospital = hospitalProfileRepository.save(hospital);

            // Seed published blood availability
            availabilityRepository.save(BloodAvailability.builder()
                    .hospitalProfile(hospital)
                    .bloodGroup(BloodGroup.O_NEG)
                    .status(BloodAvailabilityStatus.AVAILABLE)
                    .unitsMin(4)
                    .unitsMax(8)
                    .updatedBy(hospitalUser)
                    .build());

            availabilityRepository.save(BloodAvailability.builder()
                    .hospitalProfile(hospital)
                    .bloodGroup(BloodGroup.A_POS)
                    .status(BloodAvailabilityStatus.AVAILABLE)
                    .unitsMin(15)
                    .unitsMax(22)
                    .updatedBy(hospitalUser)
                    .build());

            availabilityRepository.save(BloodAvailability.builder()
                    .hospitalProfile(hospital)
                    .bloodGroup(BloodGroup.B_POS)
                    .status(BloodAvailabilityStatus.LOW)
                    .unitsMin(2)
                    .unitsMax(3)
                    .updatedBy(hospitalUser)
                    .build());

            log.info("Seeded Hospital: apollo.hospital@bloodbridge.org / Hospital@123");
        }

        // 3. Seed Donors
        seedDonor("rahul.donor@bloodbridge.org", "9876500002", "Rahul Sharma", BloodGroup.O_NEG,
                "New Delhi", "Delhi", "110025", 28.5600, 77.2700, LocalDate.now().minusDays(110));

        seedDonor("priya.donor@bloodbridge.org", "9876500004", "Priya Singh", BloodGroup.A_POS,
                "New Delhi", "Delhi", "110017", 28.5300, 77.2100, LocalDate.now().minusDays(45));

        seedDonor("amit.donor@bloodbridge.org", "9876500005", "Amit Verma", BloodGroup.B_POS,
                "Noida", "Uttar Pradesh", "201301", 28.5700, 77.3200, LocalDate.now().minusDays(180));

        seedDonor("sneha.donor@bloodbridge.org", "9876500006", "Sneha Reddy", BloodGroup.AB_POS,
                "Gurugram", "Haryana", "122001", 28.4595, 77.0266, null);

        // 4. Seed Patient Requester and Requests
        if (!userRepository.existsByEmailIgnoreCase("ananya.patient@bloodbridge.org")) {
            User patientUser = User.builder()
                    .email("ananya.patient@bloodbridge.org")
                    .phone("9876500003")
                    .passwordHash(passwordEncoder.encode("Patient@123"))
                    .role(Role.ROLE_PATIENT)
                    .status(UserStatus.ACTIVE)
                    .emailVerifiedAt(Instant.now())
                    .phoneVerifiedAt(Instant.now())
                    .build();
            patientUser = userRepository.save(patientUser);

            PatientProfile pp = PatientProfile.builder()
                    .user(patientUser)
                    .fullName("Ananya Patel")
                    .city("New Delhi")
                    .state("Delhi")
                    .relationshipDefault("Daughter")
                    .build();
            patientProfileRepository.save(pp);

            // Seed active emergency blood request
            BloodRequest br1 = BloodRequest.builder()
                    .requestCode("BB-20261106-K7M9P2")
                    .requester(patientUser)
                    .patientName("Ramesh Patel")
                    .bloodGroup(BloodGroup.O_NEG)
                    .units(2)
                    .unitsFulfilled(0)
                    .hospitalName("AIIMS Emergency Trauma Centre")
                    .hospitalAddress("Ring Road, Ansari Nagar East")
                    .city("New Delhi")
                    .state("Delhi")
                    .pinCode("110029")
                    .lat(28.5672)
                    .lon(77.2100)
                    .requiredBy(Instant.now().plus(24, ChronoUnit.HOURS))
                    .priority(RequestPriority.CRITICAL)
                    .status(RequestStatus.ACTIVE)
                    .contactPreference(ContactPreference.PHONE)
                    .notes("Emergency surgery patient requiring universal RBC donor. Immediate dispatch appreciated.")
                    .fingerprintHash("seed-hash-ramesh-patel-o-neg")
                    .verifiedBy(patientUser)
                    .verifiedAt(Instant.now())
                    .build();
            bloodRequestRepository.save(br1);

            BloodRequest br2 = BloodRequest.builder()
                    .requestCode("BB-20261106-H4W8Q1")
                    .requester(patientUser)
                    .patientName("Sunita Devi")
                    .bloodGroup(BloodGroup.A_POS)
                    .units(3)
                    .unitsFulfilled(1)
                    .hospitalName("Fortis Escorts Heart Institute")
                    .hospitalAddress("Okhla Road, Sukhdev Vihar")
                    .city("New Delhi")
                    .state("Delhi")
                    .pinCode("110025")
                    .lat(28.5615)
                    .lon(77.2764)
                    .requiredBy(Instant.now().plus(48, ChronoUnit.HOURS))
                    .priority(RequestPriority.URGENT)
                    .status(RequestStatus.DONOR_RESPONDED)
                    .contactPreference(ContactPreference.PHONE)
                    .notes("Cardiothoracic procedure units backup.")
                    .fingerprintHash("seed-hash-sunita-devi-a-pos")
                    .verifiedBy(patientUser)
                    .verifiedAt(Instant.now())
                    .build();
            bloodRequestRepository.save(br2);

            log.info("Seeded Patient: ananya.patient@bloodbridge.org / Patient@123 and 2 requests");
        }
    }

    private void seedDonor(String email, String phone, String name, BloodGroup bloodGroup,
                           String city, String state, String pinCode, double lat, double lon, LocalDate lastDonation) {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            User user = User.builder()
                    .email(email)
                    .phone(phone)
                    .passwordHash(passwordEncoder.encode("Donor@123"))
                    .role(Role.ROLE_DONOR)
                    .status(UserStatus.ACTIVE)
                    .emailVerifiedAt(Instant.now())
                    .phoneVerifiedAt(Instant.now())
                    .build();
            user = userRepository.save(user);

            DonorProfile donor = DonorProfile.builder()
                    .user(user)
                    .fullName(name)
                    .dob(LocalDate.of(1996, 5, 14))
                    .bloodGroup(bloodGroup)
                    .city(city)
                    .state(state)
                    .pinCode(pinCode)
                    .approxLat(lat)
                    .approxLon(lon)
                    .lastDonationDate(lastDonation)
                    .availability(DonorAvailability.AVAILABLE)
                    .showInSearch(true)
                    .shareContactOnAccept(false)
                    .emergencyAlertsEnabled(true)
                    .verifiedByAdmin(true)
                    .totalDonations(lastDonation != null ? 3 : 0)
                    .lastActiveAt(Instant.now())
                    .build();
            donorProfileRepository.save(donor);
            log.info("Seeded Donor: {} (Blood Group {}) / Donor@123", email, bloodGroup.getLabel());
        }
    }
}
