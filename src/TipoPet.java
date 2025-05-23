public enum TipoPet {
    CACHORRO("Cachorro"),
    GATO("Gato");

    private String nomeTipoDePet;

    TipoPet(String nomeTipoDePet) {
        this.nomeTipoDePet = nomeTipoDePet;
    }

    public String getNomeTipoDePet() {
        return nomeTipoDePet;
    }
}
