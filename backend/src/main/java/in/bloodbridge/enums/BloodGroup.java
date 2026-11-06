package in.bloodbridge.enums;

public enum BloodGroup {
    A_POS("A+", "A", "+"),
    A_NEG("A-", "A", "-"),
    B_POS("B+", "B", "+"),
    B_NEG("B-", "B", "-"),
    AB_POS("AB+", "AB", "+"),
    AB_NEG("AB-", "AB", "-"),
    O_POS("O+", "O", "+"),
    O_NEG("O-", "O", "-");

    private final String label;
    private final String aboType;
    private final String rhFactor;

    BloodGroup(String label, String aboType, String rhFactor) {
        this.label = label;
        this.aboType = aboType;
        this.rhFactor = rhFactor;
    }

    public String getLabel() {
        return label;
    }

    public String getAboType() {
        return aboType;
    }

    public String getRhFactor() {
        return rhFactor;
    }

    public static BloodGroup fromLabel(String label) {
        if (label == null) return null;
        String clean = label.trim().toUpperCase();
        for (BloodGroup bg : values()) {
            if (bg.label.equalsIgnoreCase(clean) || bg.name().equalsIgnoreCase(clean)) {
                return bg;
            }
        }
        throw new IllegalArgumentException("Unknown blood group: " + label);
    }
}
