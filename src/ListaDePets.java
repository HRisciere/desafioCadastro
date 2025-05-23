public class ListaDePets {
    private Pet[] pets;
    private int quant;

    public ListaDePets() {
        pets = new Pet[10];
        quant = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quant; i++) {
            sb.append(pets[i]).append("\n");
        }
        return sb.toString();
    }

    public void adicionarPet(Pet pet){
        if(quant == pets.length){
            redimensionarLista();
        }
        pets[quant] = pet;
        quant++;
    }

    private void redimensionarLista(){
        int novoTamanho = pets.length * 2;
        Pet[] novoVetor = new Pet[novoTamanho];

        for (int i = 0; i < pets.length; i++){
            novoVetor[i] = pets[i];
        }

        pets = novoVetor;
    }

    public void mostrarLista(){
        for (int i = 0; i < quant; i++) {
            System.out.println(pets[i]);
        }
    }
}
