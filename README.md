# 🎮 Jogo da Velha - Hackathon 1000devs

Este projeto é o resultado do **Desafio da Primeira Hackathon** da iniciativa **1000devs**. O objetivo foi desenvolver um Jogo da Velha (Tic-Tac-Toe) funcional via console em Java, seguindo um esqueleto de código pré-definido.

## 🏆 O Desafio

O desafio consistiu em implementar a lógica de um jogo da velha completo, utilizando um esqueleto de código obrigatório onde cada método possuía uma responsabilidade específica e um nível de complexidade definido.

**Requisitos do Hackathon:**
- Utilizar estritamente o esqueleto fornecido.
- Implementar a lógica dos métodos comentados (TODOs).
- Trabalho em equipe e controle de demandas.
- Entrega do código funcional e apresentação de lições aprendidas.

🎥 **[Vídeo de Referência do Funcionamento](https://youtu.be/wydUvY_U0NM)**

## 📋 Funcionalidades Implementadas

- **Modo PvE:** Jogador vs Computador.
- **Inteligência Artificial:** O computador reage às jogadas (nível básico/médio).
- **Interface:** Tabuleiro visual via console com limpeza de tela automática.
- **Regras Completas:** Validação de jogadas, detecção de vitória (linhas, colunas, diagonais) e empate ("velha").

## 🚀 Como Executar

1. Certifique-se de ter o **Java JDK** instalado.
2. Clone este repositório ou baixe os arquivos.
3. Abra o projeto no VS Code (ou sua IDE de preferência).
4. Execute o arquivo `src/App.java`.

## 🕹️ Como Jogar

1. O jogo solicitará que você escolha seu caractere (ex: `X`).
2. Em seguida, escolha o caractere do computador (ex: `O`).
3. O sistema sorteará quem começa jogando.
4. Quando for sua vez, digite a **Linha** e a **Coluna** onde deseja jogar (separados por espaço).
   - Exemplo: `1 1` (marca o centro do tabuleiro).
5. O jogo termina quando houver um vencedor ou o tabuleiro encher.

## 📂 Estrutura do Código

O projeto segue uma estrutura modularizada em `src/App.java`:
- **Controle de Fluxo:** `main`, `processarVezUsuario`, `processarVezComputador`.
- **Lógica de Jogo:** `teveGanhador`, `teveEmpate`, `retornarPosicoesLivres`.
- **Interface:** `exibirTabuleiro`, `limparTela`, `exibirVitoria...`.
