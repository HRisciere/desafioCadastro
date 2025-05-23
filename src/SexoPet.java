public enum SexoPet {
    MACHO("Macho"),
    FEMEA("Fêmea");

    private String sexoDoPet;

    SexoPet(String sexoDoPet) {
        this.sexoDoPet = sexoDoPet;
    }

    public String getSexoDoPet() {
        return sexoDoPet;
    }
}
