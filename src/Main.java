import java.io.*;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ListaDePets listaDePets = new ListaDePets();
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);

        int opcao = -1;

        do {
            System.out.println("""
                    1. Cadastrar um novo pet
                    2. Alterar os dados do pet cadastrado
                    3. Deletar um pet cadastrado
                    4. Listar todos os pets cadastrados
                    5. Listar pets por algum critério (idade, nome, raça)
                    6. Sair
                    """);

            System.out.print("Escolha umas das opcoes anteriores: ");
            String entrada = input.nextLine();

            try {
                opcao = Integer.parseInt(entrada);
                switch (opcao) {
                    case 1:
                        cadastrarPet();
                        break;
                    case 2:
                        alterarDadosDoPet();
                        break;
                    case 3:
                        deletarPetPorCriterio();
                        break;
                    case 4:
                        listarTodosOsPets();
                        break;
                    case 5:
                        listarPestsPorCriterio();
                        break;
                    case 6:
                        System.exit(0);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida!");
            }

        } while (opcao <= 0 || opcao > 6);

    }

    private static void cadastrarPet() {
        Scanner input = new Scanner(System.in);

        String[] linhas = lerArquivo();

        String nome = " ";
        String sobrenome = " ";

        String tipoPet;
        TipoPet tipoPet1 = null;

        String sexoPet;
        SexoPet sexoPet1 = null;

        String[] endereco = new String[4];
        String numeroCasa;
        String cidade;
        String rua;
        String bairro;

        String idade = " ", peso = " ";

        String raca = " ";

        for (int j = 0; j < linhas.length; j++) {
            System.out.println(linhas[j]);
            switch (j) {
                case 0:
                    while (true) {
                        try {
                            System.out.println("Informe o nome: ");
                            nome = input.nextLine();

                            if (nome.isBlank()) {
                                nome = NAO_INFORMADO;
                                sobrenome = "";
                                break;
                            }

                            if (!(nome.matches("[A-Za-zÀ-ÿ ]+"))) {
                                throw new IllegalArgumentException("O nome deve conter apenas letras");
                            }

                            System.out.println("Informe o sobrenome: ");
                            sobrenome = input.nextLine().trim();

                            if (sobrenome.isBlank() || !(sobrenome.matches("[A-Za-zÀ-ÿ ]+"))) {
                                throw new IllegalArgumentException("O sobrenome não pode estar em branco e deve conter apenas letras");
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Tente novamente.\n");
                        }
                    }
                    break;

                case 1:
                    while (true) {
                        tipoPet = input.nextLine().trim();
                        if (tipoPet.equalsIgnoreCase("cachorro")) {
                            tipoPet1 = TipoPet.CACHORRO;
                            break;
                        } else if (tipoPet.equalsIgnoreCase("gato")) {
                            tipoPet1 = TipoPet.GATO;
                            break;
                        } else {
                            System.out.println("Tipo inválido! Tente novamente.");
                        }
                    }
                    break;


                case 2:
                    while (true) {
                        sexoPet = input.nextLine().trim();
                        if (sexoPet.equalsIgnoreCase("macho")) {
                            sexoPet1 = SexoPet.MACHO;
                            break;
                        } else if (sexoPet.equalsIgnoreCase("femea") || sexoPet.equalsIgnoreCase("fêmea")) {
                            sexoPet1 = SexoPet.FEMEA;
                            break;
                        } else {
                            System.out.println("Tipo inválido! Tente novamente.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Informe o número da casa");
                    numeroCasa = input.nextLine();

                    if (numeroCasa.isBlank()) {
                        numeroCasa = NAO_INFORMADO;
                    }
                    cidade = lerCampoObrigatorio(input, "A cidade");
                    rua = lerCampoObrigatorio(input, "A rua");
                    bairro = lerCampoObrigatorio(input, "O bairro");

                    for (int k = 0; k < endereco.length; k++) {
                        switch (k) {
                            case 0:
                                endereco[1] = numeroCasa;
                                break;
                            case 1:
                                endereco[3] = cidade;
                                break;
                            case 2:
                                endereco[0] = rua;
                                break;
                            case 3:
                                endereco[2] = bairro;
                                break;
                        }
                    }
                    break;

                case 4:
                    while (true) {
                        try {
                            idade = input.nextLine().trim().toLowerCase();

                            if (idade.isBlank()) {
                                idade = NAO_INFORMADO;
                                break;
                            }

                            if (idade.contains("mes") || idade.contains("m")) {
                                idade = idade.replaceAll("[^0-9]", "");

                                if (idade.isEmpty()) {
                                    throw new IllegalArgumentException("Informe os meses corretamente.");
                                }

                                int meses = Integer.parseInt(idade);

                                if (meses < 0 || meses > 240) {
                                    throw new IllegalArgumentException("Digite um valor entre 0 e 240 meses.");
                                }

                                double idadeNumerico = meses / 12.0;
                                idade = String.format("%.2f", idadeNumerico);
                            } else {
                                if (!idade.matches("\\d+([.,]\\d+)?")) {
                                    throw new IllegalArgumentException("Digite apenas números!");
                                }

                                double idadeNumerico = Double.parseDouble(idade.replace(",", "."));

                                if (idadeNumerico < 0 || idadeNumerico > 20) {
                                    throw new IllegalArgumentException("Digite um valor de 0 até 20");
                                }

                                int anos = (int) idadeNumerico;
                                int meses = (int) Math.round((idadeNumerico - anos) * 12);

                                if (anos > 0 && meses > 0) {
                                    idade = String.format("%d anos e %d meses", anos, meses);
                                } else if (anos > 0) {
                                    idade = String.format("%d anos", anos);
                                } else {
                                    idade = String.format("%d meses", meses);
                                }
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    break;


                case 5:
                    while (true) {
                        try {
                            peso = input.nextLine().trim();

                            if (peso.isBlank()) {
                                peso = NAO_INFORMADO;
                                break;
                            }

                            if (!peso.matches("\\d+([.,]\\d+)?")) {
                                throw new IllegalArgumentException("Digite apenas números!");
                            }

                            double pesoNumerico = Double.parseDouble(peso.replace(",", "."));

                            if (pesoNumerico > 60 || pesoNumerico < 0.5) {
                                throw new IllegalArgumentException("Digite um valor acima de 0.5 ou abaixo de 60");
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage() + " Tente novamente.\n");
                        }
                    }
                    break;

                case 6:
                    while (true) {
                        try {
                            raca = input.nextLine();

                            if (raca.isBlank()) {
                                raca = NAO_INFORMADO;
                                break;
                            }

                            if (!raca.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                                throw new IllegalArgumentException("Não pode utilizar números nem caracteres especiais.");
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }

                    }
                    break;
            }

        }
        listaDePets.adicionarPet(new Pet(nome, sobrenome, tipoPet1, sexoPet1, endereco, idade, peso, raca));
        listaDePets.mostrarLista();


        File diretorio = new File("petsCadastrados");

        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String format = LocalDateTime.now().format(dateTimeFormatter);

        String nomeCompleto = (nome + sobrenome).replaceAll("\\s+", "");
        String nomeArquivo = String.format("%s-%s.txt", format, nomeCompleto).toUpperCase();

        File respostas = new File(diretorio, nomeArquivo);

        try (FileWriter fileWriter = new FileWriter(respostas)) {

            fileWriter.write(listaDePets.toString());

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

    }

    private static String lerCampoObrigatorio(Scanner input, String nomeDoCampo) {
        String valor;
        while (true) {
            System.out.println("Informe " + nomeDoCampo + ":");
            valor = input.nextLine();
            if (valor.isBlank()) {
                System.out.println(nomeDoCampo + " não pode ficar em branco!");
            } else {
                break;
            }
        }
        return valor;
    }

    private static String[] lerArquivo() {
        String caminho = "resources/formulario.txt";

        String[] linhas = new String[7];
        try (FileReader fileReader = new FileReader(caminho);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            int i = 0;
            String linha;

            while (((linha = bufferedReader.readLine()) != null)) {

                linhas[i] = linha;
                i++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo." + e.getMessage());
        }
        return linhas;
    }

    private static void listarTodosOsPets() {

        File diretorio = new File("petsCadastrados");

        if (!diretorio.exists() || !diretorio.isDirectory()) {
            System.out.println("Nenhum pet foi cadastrado.");
            return;
        }

        File[] arquivos = diretorio.listFiles();

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum pet foi encontrado na pasta");
            return;
        }

        for (File arquivo : arquivos) {

            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                String linha;

                while ((linha = leitor.readLine()) != null) {
                    System.out.println(linha);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
            }
            System.out.println();
        }
    }

    private static void listarPestsPorCriterio() {

        Scanner sc = new Scanner(System.in);
        CriteriosBusca criterios = coletarCriteriosBusca();

        List<File> encontrados = buscarPetsPorCriterio(criterios.tipo, criterios.criterio1, criterios.criterio2);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum pet corresponde aos critérios.");
            return;
        }

        int i = 1;

        for (File arquivo : encontrados) {
            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                String[] dados = new String[7];
                String linha;
                int j = 0;

                while ((linha = leitor.readLine()) != null) {
                    String[] partes = linha.split(" - ");
                    if (partes.length == 2) {
                        dados[j] = partes[1].trim();
                        j++;
                    }
                }

                if (j < 7) continue; // pula se os dados estiverem incompletos

                String nome = dados[0];
                String tipo = dados[1];
                String sexo = dados[2];
                String endereco = dados[3];
                String idade = dados[4];
                String peso = dados[5];
                String raca = dados[6];

                System.out.printf("%d. %s - %s - %s - %s - %s - %s - %s%n",
                        i++, nome, tipo, sexo, endereco, idade, peso, raca);

            } catch (IOException e) {
                System.out.println("Erro ao ler " + arquivo.getName());
            }
        }

    }

    private static List<File> buscarPetsPorCriterio(TipoPet tipoPetBuscado, String criterio1, String criterio2) {
        File diretorio = new File("petsCadastrados");
        List<File> arquivosCorrespondentes = new ArrayList<>();

        if (!diretorio.exists() || !diretorio.isDirectory()) {
            System.out.println("A pasta de pets não existe.");
            return arquivosCorrespondentes;
        }

        File[] arquivos = diretorio.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum pet foi encontrado.");
            return arquivosCorrespondentes;
        }

        for (File arquivo : arquivos) {
            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                String[] dados = new String[7];
                String linha;
                int i = 0;

                while ((linha = leitor.readLine()) != null && i < 7) {
                    dados[i++] = linha.split(" - ")[1].trim();
                }

                if (i < 7) {
                    continue;
                }

                String nome = Normalizer.normalize(dados[0], Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                String tipo = dados[1];
                String sexo = Normalizer.normalize(dados[2], Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                String endereco = dados[3];
                String idade = dados[4].replaceAll("[^\\d]", "");
                String peso = dados[5].replaceAll("[^\\d.]", "");
                String raca = dados[6];

                if (!tipo.equalsIgnoreCase(tipoPetBuscado.name())) {
                    continue;
                }

                boolean cond1 = nome.toLowerCase().contains(criterio1) || sexo.toLowerCase().contains(criterio1) ||
                        idade.equals(criterio1) || peso.equals(criterio1) || raca.toLowerCase().contains(criterio1) ||
                        endereco.toLowerCase().contains(criterio1);

                boolean cond2 = true;
                if (criterio2 != null) {
                    cond2 = nome.toLowerCase().contains(criterio2) || sexo.toLowerCase().contains(criterio2) ||
                            idade.equals(criterio2) || peso.equals(criterio2) || raca.toLowerCase().contains(criterio2) ||
                            endereco.toLowerCase().contains(criterio2);
                }

                if (cond1 && cond2) {
                    arquivosCorrespondentes.add(arquivo);
                }

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
            }
        }

        return arquivosCorrespondentes;
    }

    private static CriteriosBusca coletarCriteriosBusca() {
        Scanner sc = new Scanner(System.in);
        TipoPet tipoPetBuscado;

        while (true) {
            try {
                System.out.println("Escolha o tipo de animal:");
                System.out.println("1. Cachorro");
                System.out.println("2. Gato");

                String entrada = sc.nextLine().trim(); // Remove espaços em branco

                if (entrada.isEmpty()) {
                    System.out.println("Entrada em branco não é permitida.");
                    System.out.println();
                    continue;
                }

                int tipoEscolhido = Integer.parseInt(entrada);

                if (tipoEscolhido == 1) {
                    tipoPetBuscado = TipoPet.CACHORRO;
                    break;

                } else if (tipoEscolhido == 2) {
                    tipoPetBuscado = TipoPet.GATO;
                    break;

                } else {
                    System.out.println("Só pode responder utilizando os números 1 ou 2.");
                    System.out.println();
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira o número 1 ou 2.");
            }
        }

        String criterio1 = null, criterio2 = null;
        int opcaoBusca;

        while (true) {
            System.out.println("""
                    Escolha o primeiro critério de busca:
                    1. Nome ou Sobrenome
                    2. Sexo
                    3. Idade
                    4. Peso
                    5. Raça
                    6. Endereço
                    """);

            try {
                opcaoBusca = Integer.parseInt(sc.nextLine());

                if (opcaoBusca < 1 || opcaoBusca > 6) {
                    System.out.println("Opção inválida. Escolha um número de 1 a 6.\n");
                    continue;
                }

                switch (opcaoBusca) {
                    case 1:
                        System.out.println("Digite o nome ou sobrenome:");
                        criterio1 = Normalizer.normalize(sc.nextLine(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                        if (criterio1.isBlank()) {
                            criterio1 = "nao informado";
                        }
                        break;

                    case 2:
                        while (true) {
                            System.out.println("Digite o sexo (Macho ou Fêmea):");
                            String entradaSexo = sc.nextLine().trim();

                            if (entradaSexo.isEmpty()) {
                                System.out.println("Entrada em branco não é permitida.\n");
                                continue;
                            }

                            String sexoNormalizado = Normalizer.normalize(entradaSexo, Normalizer.Form.NFD)
                                    .replaceAll("[^\\p{ASCII}]", "")
                                    .toLowerCase();

                            if (sexoNormalizado.equals("macho") || sexoNormalizado.equals("femea")) {
                                criterio1 = sexoNormalizado;
                                break;
                            } else {
                                System.out.println("Sexo inválido! Digite apenas 'Macho' ou 'Fêmea'.\n");
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Digite a idade:");
                        criterio1 = sc.nextLine().trim();
                        if (criterio1.isBlank()) {
                            criterio1 = "nao informado";
                        }
                        break;

                    case 4:
                        System.out.println("Digite o peso:");
                        criterio1 = sc.nextLine().trim();
                        if (criterio1.isBlank()) {
                            criterio1 = "nao informado";
                        }
                        break;

                    case 5:
                        System.out.println("Digite a raça:");
                        criterio1 = sc.nextLine().trim();
                        if (criterio1.isBlank()) {
                            criterio1 = "nao informado";
                        }
                        break;

                    case 6:
                        System.out.println("Digite o endereço:");
                        criterio1 = sc.nextLine().trim();
                        break;
                }
                break;

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número de 1 a 6.\n");
            }
        }

        String resposta;
        while (true) {
            System.out.println("Deseja adicionar mais um critério? (s/n)");
            resposta = sc.nextLine();

            if (resposta.matches("(?i)^[sn]$")) {
                break;
            } else {
                System.out.println("Entrada inválida! Digite apenas 's' ou 'n'.\n");
            }
        }

        if (resposta.equalsIgnoreCase("s")) {
            while (true) {
                System.out.println("""
                        Escolha o segundo critério de busca:
                        1. Nome ou Sobrenome
                        2. Sexo
                        3. Idade
                        4. Peso
                        5. Raça
                        6. Endereço
                        """);

                try {
                    int opcaoBusca2 = sc.nextInt();
                    sc.nextLine();

                    if (opcaoBusca2 < 1 || opcaoBusca2 > 6) {
                        System.out.println("Opção inválida. Digite um número entre 1 e 6.\n");
                        continue;
                    }

                    switch (opcaoBusca2) {
                        case 1:
                            System.out.println("Digite o nome ou sobrenome:");
                            criterio2 = Normalizer.normalize(sc.nextLine(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                            if (criterio2.isBlank()) {
                                criterio2 = "nao informado";
                            }
                            break;

                        case 2:
                            while (true) {
                                System.out.println("Digite o sexo (Macho ou Fêmea):");
                                String entradaSexo = sc.nextLine().trim();

                                if (entradaSexo.isEmpty()) {
                                    System.out.println("Entrada em branco não é permitida.\n");
                                    continue;
                                }

                                String sexoNormalizado = Normalizer.normalize(entradaSexo, Normalizer.Form.NFD)
                                        .replaceAll("[^\\p{ASCII}]", "")
                                        .toLowerCase();

                                if (sexoNormalizado.equals("macho") || sexoNormalizado.equals("femea")) {
                                    criterio2 = sexoNormalizado;
                                    break;

                                } else {
                                    System.out.println("Sexo inválido! Digite apenas 'Macho' ou 'Fêmea'.\n");
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Digite a idade:");
                            criterio2 = sc.nextLine().trim();
                            if (criterio2.isBlank()) {
                                criterio2 = "nao informado";
                            }
                            break;

                        case 4:
                            System.out.println("Digite o peso:");
                            criterio2 = sc.nextLine().trim();
                            if (criterio2.isBlank()) {
                                criterio2 = "nao informado";
                            }
                            break;

                        case 5:
                            System.out.println("Digite a raça:");
                            criterio2 = sc.nextLine().trim();
                            if (criterio2.isBlank()) {
                                criterio2 = "nao informado";
                            }
                            break;

                        case 6:
                            System.out.println("Digite o endereço:");
                            criterio2 = sc.nextLine().trim();
                            break;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida! Digite um número de 1 a 6.\n");
                }
            }

        }

        return new CriteriosBusca(tipoPetBuscado, criterio1, criterio2);
    }

    private static void deletarPetPorCriterio() {

        Scanner sc = new Scanner(System.in);
        List<File> encontrados;

        while (true) {
            CriteriosBusca criterios = coletarCriteriosBusca();
            encontrados = buscarPetsPorCriterio(criterios.tipo, criterios.criterio1, criterios.criterio2);

            if (encontrados.isEmpty()) {
                System.out.println("Nenhum pet encontrado com os critérios informados.");

                String resposta;
                while (true) {
                    System.out.println("Deseja tentar buscar novamente? (s/n)");
                    resposta = sc.nextLine();

                    if (resposta.matches("(?i)^[sn]$")) {
                        break;
                    } else {
                        System.out.println("Entrada inválida! Digite apenas 's' para sim ou 'n' para não.");
                    }
                }

                if (resposta.equals("n") || resposta.equals("N")) {
                    return; // sai do método
                } else {
                    continue; // volta ao início do while para coletar novos critérios
                }
            }

            // Se chegou aqui, é porque encontrou pets — pode sair do loop
            // e seguir com a exibição
            int i = 1;
            for (File petEncontrado : encontrados) {
                try (BufferedReader leitor = new BufferedReader(new FileReader(petEncontrado))) {
                    String[] dados = new String[7];
                    String linha;
                    int j = 0;

                    while ((linha = leitor.readLine()) != null) {
                        dados[j++] = linha.split(" - ")[1];
                    }

                    String nome = dados[0];
                    String tipo = dados[1];
                    String sexo = dados[2];
                    String endereco = dados[3];
                    String idade = dados[4];
                    String peso = dados[5];
                    String raca = dados[6];

                    System.out.printf("%d. %s - %s - %s - %s - %s - %s - %s%n",
                            i++, nome, tipo, sexo, endereco, idade, peso, raca);
                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo: " + petEncontrado.getName());
                }
            }

            break; // sai do while(true) principal após listar os pets encontrados
        }

        int escolha;
        while (true) {
            System.out.println("Digite o número do Pet que deseja deletar");

            try {
                escolha = sc.nextInt();
                sc.nextLine();
                if (escolha < 1 || escolha > encontrados.size()) {
                    System.out.println("Número inválido. Digite um número da lista.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número válido.");
                sc.nextLine();
            }
        }

        File petSelecionado = encontrados.get(escolha - 1);
        String confirmacao;

        while (true) {
            System.out.println("Tem certeza que deseja deletar este pet? Digite SIM para confirmar ou NÃO para cancelar.");
            confirmacao = sc.nextLine();
            if (confirmacao.equalsIgnoreCase("sim")) {
                if (petSelecionado.delete()) {
                    System.out.println("Pet deletado com sucesso!");
                } else {
                    System.out.println("Erro ao deletar o pet.");
                }
                break;

            } else if (confirmacao.equalsIgnoreCase("não")) {
                System.out.println("Operação cancelada.");
                break;
            } else {
                System.out.println("Entrada inválida! Digite exatamente SIM ou NÃO.");
            }
        }
    }

    private static void alterarDadosDoPet() {
        Scanner sc = new Scanner(System.in);
        List<File> encontrados;

        while (true) {
            CriteriosBusca criterios = coletarCriteriosBusca();
            encontrados = buscarPetsPorCriterio(criterios.tipo, criterios.criterio1, criterios.criterio2);

            if (encontrados.isEmpty()) {
                System.out.println("Nenhum pet encontrado com os critérios informados.");

                String resposta;
                while (true) {
                    System.out.println("Deseja tentar buscar novamente? (s/n)");
                    resposta = sc.nextLine();

                    if (resposta.matches("(?i)^[sn]$")) break;
                    System.out.println("Entrada inválida! Digite apenas 's' ou 'n'.");
                }

                if (resposta.equalsIgnoreCase("n")) return;
                else continue;
            }

            // Listar os pets encontrados
            int i = 1;
            for (File arquivo : encontrados) {
                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                    String[] dados = new String[7];
                    String linha;
                    int j = 0;

                    while ((linha = leitor.readLine()) != null && j < 7) {
                        String[] partes = linha.split(" - ", 2);
                        if (partes.length == 2) {
                            dados[j++] = partes[1].trim();
                        }
                    }

                    if (j < 7) continue;

                    System.out.printf("%d. %s - %s - %s - %s - %s - %s - %s%n",
                            i++, dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6]);

                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                }
            }

            int escolha;
            while (true) {
                System.out.println("Digite o número do pet que deseja alterar:");
                try {
                    escolha = sc.nextInt();
                    sc.nextLine(); // consumir quebra de linha

                    if (escolha < 1 || escolha > encontrados.size()) {
                        System.out.println("Número inválido. Tente novamente.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                    sc.nextLine();
                }
            }

            File petSelecionado = encontrados.get(escolha - 1);
            String[] dadosAntigos = new String[7];
            String linha;

            int j = 0;
            try (BufferedReader leitor = new BufferedReader(new FileReader(petSelecionado))) {
                while ((linha = leitor.readLine()) != null) {
                    dadosAntigos[j++] = linha.split(" - ")[1];
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler os dados do pet.");
                return;
            }

            // Exibir dados atuais e pedir novos valores
            System.out.println("Insira os novos dados para o pet (pressione Enter para manter o valor atual):");

            System.out.print("Nome [" + dadosAntigos[0] + "]: ");
            String novoNome = sc.nextLine();
            if (novoNome.isBlank()) novoNome = dadosAntigos[0];

            // Tipo (não pode alterar)
            String tipo = dadosAntigos[1];

            // Sexo (não pode alterar)
            String sexo = dadosAntigos[2];

            System.out.print("Endereço [" + dadosAntigos[3] + "]: ");
            String novoEndereco = sc.nextLine();
            if (novoEndereco.isBlank()) novoEndereco = dadosAntigos[3];

            System.out.print("Idade [" + dadosAntigos[4] + "]: ");
            String novaIdade = sc.nextLine();
            if (novaIdade.isBlank()) novaIdade = dadosAntigos[4];

            System.out.print("Peso [" + dadosAntigos[5] + "]: ");
            String novoPeso = sc.nextLine();
            if (novoPeso.isBlank()) novoPeso = dadosAntigos[5];

            System.out.print("Raça [" + dadosAntigos[6] + "]: ");
            String novaRaca = sc.nextLine();
            if (novaRaca.isBlank()) novaRaca = dadosAntigos[6];

            // Sobrescreve o arquivo com os novos dados
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(petSelecionado, false))) {
                escritor.write("1 - " + novoNome);
                escritor.newLine();
                escritor.write("2 - " + tipo);
                escritor.newLine();
                escritor.write("3 - " + sexo);
                escritor.newLine();
                escritor.write("4 - " + novoEndereco);
                escritor.newLine();
                escritor.write("5 - " + novaIdade);
                escritor.newLine();
                escritor.write("6 - " + novoPeso);
                escritor.newLine();
                escritor.write("7 - " + novaRaca);
                escritor.newLine();

                System.out.println("Dados do pet atualizados com sucesso!");

            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados atualizados.");
            }

            break; // finaliza o processo após uma alteração bem-sucedida
        }
    }
}