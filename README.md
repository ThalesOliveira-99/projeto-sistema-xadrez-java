# ♟️ Sistema de Jogo de Xadrez (Chess System) em Java

![Java](https://img.shields.io/badge/Java-SE_24-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Eclipse IDE](https://img.shields.io/badge/Eclipse_IDE-Project-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white)
![Console App](https://img.shields.io/badge/CLI-Console_Application-4EAA25?style=for-the-badge&logo=gnu-bash&logoColor=white)
![OOP](https://img.shields.io/badge/POO-Object--Oriented-0052CC?style=for-the-badge)

---

## 📖 Sobre o Projeto

O **Sistema de Xadrez (`SistemaXadrez`)**[cite: 10] é uma aplicação interativa de terminal (CLI - *Command Line Interface*) desenvolvida em **Java SE Puro**[cite: 8], sem o uso de frameworks ou bibliotecas externas. 

O projeto foi desenvolvido e aperfeiçoado durante as aulas práticas do **Curso de Java da DevSuperior**, servindo como um estudo profundo e consolidação dos conceitos mais avançados de **Programação Orientada a Objetos (POO)**, modelagem de domínio, estruturas de dados BIDIMENSIONAIS (matrizes) e controle de fluxo.

O jogo simula uma partida completa de xadrez no console, permitindo a movimentação de peças brancas e pretas com validação de regras em tempo real, tratamento elegante de movimentos inválidos e destaque visual com cores via códigos ANSI.

---

## 🎯 Conceitos de POO e Arquitetura Aplicados

O grande diferencial técnico deste repositório é a separação limpa de responsabilidades e a aplicação rigorosa dos pilares da Orientação a Objetos:

1. **Arquitetura em Camadas (Domain-Driven):**
   - **Camada de Tabuleiro (`boardgame`):** Estrutura genérica e reutilizável que gerencia posições, matriz de peças e regras básicas de um tabuleiro sem conhecer o jogo de xadrez em si.
   - **Camada de Xadrez (`chess`):** Especifica as regras do jogo, contendo a partida (`ChessMatch`), as peças do xadrez (Rei, Rainha, Bispo, Cavalo, Torre, Peão) e as coordenadas específicas do xadrez (ex: `a1` até `h8`).
   - **Camada de Aplicação (`application`):** Gerencia a interface com o usuário (leitura de entradas no console, renderização do tabuleiro e loop da partida).

2. **Pilares da POO em Prática:**
   - **Encapsulation (Encapsulamento):** Proteção do estado interno do tabuleiro e das peças, impedindo modificações indevidas fora das regras de negócio.
   - **Inheritance (Herança):** Criação de peças específicas (ex: `King`, `Rook`) que herdam comportamentos comuns de uma peça de xadrez (`ChessPiece`) e de uma peça de tabuleiro (`Piece`).
   - **Polymorphism (Polimorfismo):** Implementação da matriz de movimentos possíveis (`possibleMoves()`), onde cada tipo de peça calcula seus próprios movimentos de forma única no tabuleiro.
   - **Abstraction (Abstração):** Uso de classes e métodos abstratos para garantir que apenas peças concretas com movimentos definidos possam ser instanciadas.

3. **Tratamento Customizado de Exceções:**
   - Criação de exceções de domínio (`BoardException` e `ChessException`) para capturar e exibir mensagens amigáveis em caso de jogadas ilegais, posições inexistentes ou tentativas de mover peças adversárias, sem interromper ou quebrar a execução do programa.

4. **Jogadas Especiais Implementadas:**
   - 🏰 **Roque Pequeno e Roque Grande (*Castling*)**
   - ⚔️ **En Passant**
   - 👑 **Promoção do Peão (*Promotion*)**
   - ⚠️ **Lógica de Xeque (*Check*) e Xeque-Mate (*Checkmate*)**

---

## 🛠️ Tecnologias e Ferramentas

* **[Java SE 24](https://www.oracle.com/java/):** Linguagem de programação central utilizada no padrão Java Standard Edition[cite: 8].
* **[Eclipse IDE](https://www.eclipse.org/):** Ambiente de Desenvolvimento Integrado (IDE) utilizado para a estruturação nativa do projeto (`.project`[cite: 10] e `.classpath`[cite: 8]).
* **[Git & GitHub](https://github.com/):** Controle de versionamento de código, ignorando arquivos compilados de bytecode (`*.class`)[cite: 9].

---

## 🚀 Como Executar o Projeto

Diferente de projetos web, este é um sistema nativo Java que é executado diretamente no terminal ou console do computador.

### Pré-requisitos
* **Java Development Kit (JDK)** instalado no sistema (compatível com Java SE 17+ ou JavaSE-24)[cite: 8].
* **Terminal com suporte a cores ANSI** (Recomendado: Git Bash no Windows, Terminal do Linux/macOS, ou Windows Terminal moderno) para que as peças brancas e pretas sejam coloridas corretamente.

### 1️⃣ Executando via Eclipse IDE (Recomendado)
Como este projeto já possui a estrutura nativa de metadados do Eclipse (`.project`[cite: 10] e `.classpath`[cite: 8]):
1. Abra o Eclipse IDE e vá em **File ➔ Import...**.
2. Selecione **General ➔ Existing Projects into Workspace** e clique em Next.
3. Clique em **Browse...**, selecione a pasta do repositório `SistemaXadrez`[cite: 10] e clique em **Finish**.
4. No painel à esquerda, expanda a pasta `src`[cite: 8] ➔ `application` e encontre a classe principal **`Program.java`**.
5. Clique com o botão direito em `Program.java` ➔ **Run As ➔ Java Application**.
6. O tabuleiro será renderizado interativamente na aba **Console** na parte inferior da IDE!

### 2️⃣ Executando via Linha de Comando (Terminal / Git Bash)
Se preferir compilar e rodar manualmente pelo terminal na pasta raiz do projeto:

```bash
# 1. Acesse o diretório contendo os arquivos-fonte
cd src

# 2. Compile todas as classes Java gerando o bytecode (.class)
javac application/Program.java

# 3. Execute a aplicação principal
java application/Program