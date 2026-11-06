package in.bloodbridge.service;

import in.bloodbridge.dto.CompatibilityResultDto;
import in.bloodbridge.enums.BloodGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompatibilityServiceTest {

    private CompatibilityService compatibilityService;

    @BeforeEach
    void setUp() {
        compatibilityService = new CompatibilityService();
    }

    @Test
    @DisplayName("Universal RBC Donor (O-) can donate to all 8 blood groups")
    void testUniversalDonor() {
        for (BloodGroup recipient : BloodGroup.values()) {
            assertTrue(compatibilityService.isRbcCompatible(BloodGroup.O_NEG, recipient),
                    "O- must be compatible with " + recipient);
        }
    }

    @Test
    @DisplayName("Universal RBC Recipient (AB+) can receive from all 8 blood groups")
    void testUniversalRecipient() {
        for (BloodGroup donor : BloodGroup.values()) {
            assertTrue(compatibilityService.isRbcCompatible(donor, BloodGroup.AB_POS),
                    "AB+ must be able to receive from " + donor);
        }
    }

    @Test
    @DisplayName("O- recipient can only receive from O-")
    void testONegativeRecipient() {
        List<BloodGroup> donors = compatibilityService.getCompatibleDonorsForRecipient(BloodGroup.O_NEG);
        assertEquals(1, donors.size());
        assertEquals(BloodGroup.O_NEG, donors.get(0));

        assertFalse(compatibilityService.isRbcCompatible(BloodGroup.O_POS, BloodGroup.O_NEG));
        assertFalse(compatibilityService.isRbcCompatible(BloodGroup.A_POS, BloodGroup.O_NEG));
        assertFalse(compatibilityService.isRbcCompatible(BloodGroup.B_POS, BloodGroup.O_NEG));
    }

    @ParameterizedTest
    @CsvSource({
            "O_NEG, O_POS, true",
            "O_POS, O_POS, true",
            "A_POS, O_POS, false",
            "B_POS, O_POS, false",
            "A_NEG, A_POS, true",
            "A_POS, A_POS, true",
            "B_NEG, A_POS, false",
            "B_POS, B_POS, true",
            "O_NEG, B_POS, true",
            "AB_NEG, AB_POS, true",
            "AB_POS, AB_NEG, false"
    })
    @DisplayName("Parameterized RBC Compatibility Verification")
    void testRbcCompatibility(BloodGroup donor, BloodGroup recipient, boolean expected) {
        assertEquals(expected, compatibilityService.isRbcCompatible(donor, recipient));
    }

    @Test
    @DisplayName("Compatibility result includes mandatory safety disclaimer")
    void testSafetyDisclaimer() {
        CompatibilityResultDto result = compatibilityService.getCompatibilityDetails(BloodGroup.O_POS);
        assertNotNull(result.getDisclaimer());
        assertTrue(result.getDisclaimer().contains("medical advice"));
    }
}
