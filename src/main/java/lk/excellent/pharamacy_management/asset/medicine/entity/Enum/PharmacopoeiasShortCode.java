package lk.excellent.pharamacy_management.asset.medicine.entity.Enum;

public enum PharmacopoeiasShortCode {
    BP("British Pharmacopoeia"),
    USP("United States Pharmacopoeia"),
    BPC("British Pharmaceutical Codex"),
    SLP("Sri Lanka Pharmacopoeia"),
    IP("Indian Pharmacopoeia"),
    JP("Japanese Pharmacopoeia"),
    ChP("Chinese Pharmacopoeia"),
    CSL("Czechoslovak Pharmacopoeia"),
    PhBoh("Czech Pharmacopoeia"),
    PhEur("European Pharmacopoeia"),
    PhFr("French Pharmacopoeia"),
    DAB("German Pharmacopoeia"),
    PhHg("Hungarian Pharmacopoeia"),
    PhInt("InternationalPharmacopoeia"),
    YP("Yugoslav Pharmacopoeia");
    private final String pharmacopoeiasShortCode;

    private PharmacopoeiasShortCode(String pharmacopoeiasShortCode) {
        this.pharmacopoeiasShortCode = pharmacopoeiasShortCode;
    }

    public String getPharmacopoeiasShortCode() {
        return this.pharmacopoeiasShortCode;
    }
}
