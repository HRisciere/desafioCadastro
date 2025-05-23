public class Pet {
    private String nome, sobrenome, raca;
    private TipoPet tipoPet;
    private SexoPet sexoPet;
    private String[] endereco;
    private String idade;
    private String peso;

    public Pet(String nome, String sobrenome, TipoPet tipoPet, SexoPet sexoPet, String[] endereco, String idade, String peso, String raca) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipoPet = tipoPet;
        this.sexoPet = sexoPet;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "1 - " + nome + " " + sobrenome + "\n"+
                "2 - " + tipoPet.getNomeTipoDePet() + "\n" +
                "3 - " + sexoPet.getSexoDoPet() + "\n" +
                "4 - " + String.join(", ", endereco) + "\n" +
                "5 - " + idade + "\n" +
                "6 - " + (peso.equals("NÃO INFORMADO") ? peso : peso + "kg") + "\n" +
                "7 - " + raca ;
    }
}
