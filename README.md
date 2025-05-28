# Sistema de Cadastros de Pets 🐾
Projeto Java para cadastro, busca, alteração e exclusão de dados de pets utilizando arquivos `.txt`.

## Funcionalidades ⚙️
- Cadastrar um novo pet, utilizando nome, tipo (cachorro ou gato), sexo, endereço, idade, peso e a raça
- Alterar os dados do pet
- Deletar um pet
- Listar todos os pets
- Listar pets por algum critério (idade, nome, raça)

## Armazenamento das informações 💾
Os dados dos pets cadastrados ficarão armazenados em um arquivo `.txt`, no seguinte formato:

`20231101T1234-NOMESOBRENOME.TXT`.

## Pré-requisitos 📋
- Java JDK 8 ou superior instalado.
- Git (opcional, se quiser clonar o repositório).
- Terminal ou uma IDE (IntelliJ IDEA, VSCode com extensão Java, Eclipse, NetBeans IDE).

## Como executar 🎯
1. Primeiramente faça o fork, e depois crie um clone do repositório na sua máquina local. Outra opção é fazer 
o download do repositório, em formato `.zip`.
2. Utilizando o terminal, acesse a pasta raiz(desafioCadastro) do projeto, por exemplo:
`cd C:\Users\User\Desktop\desafioCadastro`.
3. Utilize o seguinte comando para realizar a compilação dos arquivos da pasta src: `javac src/*.java`
4. Para executar o programa: `java -cp src Main`

## Oberservações 📝
- A pasta petsCadastrados/ será criada automaticamente na raiz do projeto quando você cadastrar o primeiro pet.
- O arquivo `formulario.txt` já está incluso na pasta resources.
- O repositório possui um arquivo de configuração chamado `cz.toml`, ele é utilizado na ferramenta `Commitizen`,
ela é utilizada para padronização de mensagens de commit e automatização de versão e criação de tags. O arquivo não é 
necessário para executar o app, mas caso queira utilizar acesse o seguinte link para mais informações. Link: [Commitizen](https://commitizen-tools.github.io/commitizen/).

## Dicas 💡
- Para adicionar novos campos ao formulário, edite o arquivo resources/formulario.txt e ajuste o código conforme necessário.
- Evite mover a pasta petsCadastrados ou o resources para evitar problemas de caminho relativo.