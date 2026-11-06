package in.bloodbridge.service;

import in.bloodbridge.dto.CompatibilityResultDto;
import in.bloodbridge.enums.BloodGroup;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompatibilityService {

    public static final String MANDATORY_DISCLAIMER =
            "BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility or blood compatibility. " +
            "Always confirm with a qualified medical professional and the treating blood bank. In an emergency, contact your hospital or call 112.";

    public static final String COMPATIBILITY_DISCLAIMER =
            "This shows general red-cell compatibility only. It is not medical advice or approval. " +
            "Final compatibility, donor eligibility and cross-matching must be confirmed by qualified medical professionals and the treating blood bank.";

    private static final Map<BloodGroup, List<BloodGroup>> RECIPIENT_TO_DONORS = new EnumMap<>(BloodGroup.class);
    private static final Map<BloodGroup, List<BloodGroup>> DONOR_TO_RECIPIENTS = new EnumMap<>(BloodGroup.class);

    static {
        // Recipient compatibility per PRD Section 6.4 table:
        // O- can receive from: O-
        RECIPIENT_TO_DONORS.put(BloodGroup.O_NEG, List.of(BloodGroup.O_NEG));
        // O+ can receive from: O+, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.O_POS, Arrays.asList(BloodGroup.O_POS, BloodGroup.O_NEG));
        // A- can receive from: A-, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.A_NEG, Arrays.asList(BloodGroup.A_NEG, BloodGroup.O_NEG));
        // A+ can receive from: A+, A-, O+, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.A_POS, Arrays.asList(BloodGroup.A_POS, BloodGroup.A_NEG, BloodGroup.O_POS, BloodGroup.O_NEG));
        // B- can receive from: B-, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.B_NEG, Arrays.asList(BloodGroup.B_NEG, BloodGroup.O_NEG));
        // B+ can receive from: B+, B-, O+, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.B_POS, Arrays.asList(BloodGroup.B_POS, BloodGroup.B_NEG, BloodGroup.O_POS, BloodGroup.O_NEG));
        // AB- can receive from: AB-, A-, B-, O-
        RECIPIENT_TO_DONORS.put(BloodGroup.AB_NEG, Arrays.asList(BloodGroup.AB_NEG, BloodGroup.A_NEG, BloodGroup.B_NEG, BloodGroup.O_NEG));
        // AB+ can receive from: all eight groups
        RECIPIENT_TO_DONORS.put(BloodGroup.AB_POS, Arrays.asList(BloodGroup.values()));

        // Invert for canDonateTo (Donor -> Recipients)
        for (BloodGroup donor : BloodGroup.values()) {
            List<BloodGroup> recipients = new ArrayList<>();
            for (Map.Entry<BloodGroup, List<BloodGroup>> entry : RECIPIENT_TO_DONORS.entrySet()) {
                if (entry.getValue().contains(donor)) {
                    recipients.add(entry.getKey());
                }
            }
            DONOR_TO_RECIPIENTS.put(donor, recipients);
        }
    }

    public boolean isRbcCompatible(BloodGroup donorGroup, BloodGroup recipientGroup) {
        if (donorGroup == null || recipientGroup == null) return false;
        List<BloodGroup> compatibleDonors = RECIPIENT_TO_DONORS.get(recipientGroup);
        return compatibleDonors != null && compatibleDonors.contains(donorGroup);
    }

    public List<BloodGroup> getCompatibleDonorsForRecipient(BloodGroup recipientGroup) {
        return RECIPIENT_TO_DONORS.getOrDefault(recipientGroup, Collections.emptyList());
    }

    public List<BloodGroup> getCompatibleRecipientsForDonor(BloodGroup donorGroup) {
        return DONOR_TO_RECIPIENTS.getOrDefault(donorGroup, Collections.emptyList());
    }

    public CompatibilityResultDto getCompatibilityDetails(BloodGroup bloodGroup) {
        List<BloodGroup> donors = getCompatibleDonorsForRecipient(bloodGroup);
        List<BloodGroup> recipients = getCompatibleRecipientsForDonor(bloodGroup);

        String explanation = generateExplanation(bloodGroup);

        return CompatibilityResultDto.builder()
                .bloodGroup(bloodGroup)
                .bloodGroupLabel(bloodGroup.getLabel())
                .compatibleDonorGroups(donors)
                .compatibleRecipientGroups(recipients)
                .explanation(explanation)
                .disclaimer(COMPATIBILITY_DISCLAIMER)
                .build();
    }

    public List<CompatibilityResultDto> getAllCompatibilityGuides() {
        List<CompatibilityResultDto> list = new ArrayList<>();
        for (BloodGroup bg : BloodGroup.values()) {
            list.add(getCompatibilityDetails(bg));
        }
        return list;
    }

    private String generateExplanation(BloodGroup bg) {
        switch (bg) {
            case O_NEG:
                return "O- red blood cells lack A, B, and Rh antigens, making O- the Universal Red Blood Cell Donor. However, O- individuals can safely receive red blood cells only from O- donors.";
            case O_POS:
                return "O+ contains the Rh antigen but no A or B antigens. It can donate red blood cells to Rh-positive individuals (O+, A+, B+, AB+) and receive from O+ and O-.";
            case A_NEG:
                return "A- contains the A antigen without Rh factor. It can donate red blood cells to A-, A+, AB-, and AB+ patients, and receive from A- and O-.";
            case A_POS:
                return "A+ contains both A and Rh antigens. It can donate red blood cells to A+ and AB+ recipients, and receive from A+, A-, O+, and O-.";
            case B_NEG:
                return "B- contains the B antigen without Rh factor. It can donate red blood cells to B-, B+, AB-, and AB+ recipients, and receive from B- and O-.";
            case B_POS:
                return "B+ contains both B and Rh antigens. It can donate red blood cells to B+ and AB+ recipients, and receive from B+, B-, O+, and O-.";
            case AB_NEG:
                return "AB- contains both A and B antigens without Rh. It can donate red blood cells to AB- and AB+, and receive from any Rh-negative group (AB-, A-, B-, O-).";
            case AB_POS:
                return "AB+ contains A, B, and Rh antigens. It is the Universal Red Blood Cell Recipient and can receive red cells from all eight groups, but can only donate red cells to AB+ individuals.";
            default:
                return "Standard ABO and Rh antigen red cell compatibility.";
        }
    }
}
