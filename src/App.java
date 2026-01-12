import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class App {
    // Estes caracteres são aceitos como caracteres para representarem
    // os jogadores. Utizado para evitar caracteres que não combinem com
    // o desenho do tabuleiro.
    final static String CARACTERES_IDENTIFICADORES_ACEITOS = "XOUC"; //U -> usuário, C -> Computador

    // Tamanho do tabuleiro 3x3. Para o primeiro nivel de dificuldade
    // considere que este valor não será alterado. 
    // Depois que você conseguir implementar o raciocionio para o tabuleiro 3x3
    // tente ajustar o código para funcionar para qualquer tamanho de tabuleiro
    final static int TAMANHO_TABULEIRO = 3;

    static char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    static int tamanho = tabuleiro.length;
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        inicializarTabuleiro();

        char caractereUsuario = obterCaractereUsuario();
        char caractereComputador = obterCaractereComputador(caractereUsuario);

        boolean vezUsuarioJogar = sortearValorBooleano();
        boolean jogoContinua;

        do {
            jogoContinua = true;
            exibirTabuleiro();

            if (vezUsuarioJogar){
                processarVezUsuario(caractereUsuario);

                if (teveGanhador(caractereUsuario)) {
                    exibirTabuleiro();
                    exibirVitoriaUsuario();
                    jogoContinua = false;
                }
                vezUsuarioJogar = false;
            } else {
                processarVezComputador(caractereComputador);

                if (teveGanhador(caractereComputador)) {
                    exibirTabuleiro();
                    exibirVitoriaComputador();
                    jogoContinua = false;
                }
                vezUsuarioJogar = true;
            }

            if (jogoContinua && teveEmpate()) {
                exibirTabuleiro();
                exibirEmpate();
                jogoContinua = false;
            }
        } while (jogoContinua);

        teclado.close();
    }

    static void inicializarTabuleiro() {
        for(int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for(int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    static char obterCaractereUsuario() {
        char c;
        do {
            System.out.print("Escolha seu caractere (" + CARACTERES_IDENTIFICADORES_ACEITOS + "): ");
            c = teclado.nextLine().toUpperCase().charAt(0);
        } while (!CARACTERES_IDENTIFICADORES_ACEITOS.contains(String.valueOf(c)));
        return c;
    }

    static char obterCaractereComputador(char caractereUsuario) {
        char c;
        do {
            System.out.print("Escolha o caractere do computador (" + CARACTERES_IDENTIFICADORES_ACEITOS + "): ");
            c = teclado.nextLine().toUpperCase().charAt(0);
        } while (!CARACTERES_IDENTIFICADORES_ACEITOS.contains(String.valueOf(c)) || c == caractereUsuario);
        return c;
    }

    static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {
        String jogadaFormatada = String.valueOf(linha) + String.valueOf(coluna);
        return posicoesLivres.contains(jogadaFormatada);
    }

    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {
        int[] jogada = new int[2];
        boolean jogadaValida = false;

        do {
            try {
                System.out.print("Digite linha e coluna (ex: 1 2): ");
                String entrada = teclado.nextLine();
                String[] posicoes = entrada.split(" ");

                if (posicoes.length != 2) {
                    System.out.println("Você deve digitar dois valores separados por espaço.");
                    continue;
                }

                int linha = Integer.parseInt(posicoes[0]) - 1;
                int coluna = Integer.parseInt(posicoes[1]) - 1;

                if (jogadaValida(posicoesLivres, linha, coluna)) {
                    jogada[0] = linha;
                    jogada[1] = coluna;
                    jogadaValida = true;
                } else {
                    System.out.println("Jogada inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Você deve digitar apenas números.");
            }

        } while (!jogadaValida);

        return jogada;
    }

    static int[] obterJogadaComputador(String posicoesLivres) {
        String[] posicoesLivresListaString;
        String jogadaSorteada;
        int posSorteada;
        int[] jogadaSorteadaInt;

        Random random = new Random();

        posicoesLivresListaString = posicoesLivres.split(";");
        posSorteada = random.nextInt(posicoesLivresListaString.length);
        jogadaSorteada = posicoesLivresListaString[posSorteada];

        jogadaSorteadaInt = converterJogadaStringParaVetorInt(jogadaSorteada);
        return jogadaSorteadaInt;
    }

    static int[] converterJogadaStringParaVetorInt(String jogada) {
        int[] vetor = new int[2];
        vetor[0] = Character.getNumericValue(jogada.charAt(0));
        vetor[1] = Character.getNumericValue(jogada.charAt(1));
        return vetor;
    }

    static void processarVezUsuario(char caractereUsuario) {
        System.out.println("Sua vez!");
        int[] jogadaUsuario = obterJogadaUsuario(retornarPosicoesLivres(), teclado);
        atualizaTabuleiro(jogadaUsuario, caractereUsuario);
    }

    static void processarVezComputador(char caractereComputador) {
        int[] jogadaComputador;
        String posicoesLivres = retornarPosicoesLivres();
        jogadaComputador = obterJogadaComputador(posicoesLivres);
        atualizaTabuleiro(jogadaComputador, caractereComputador);
    }

    static String retornarPosicoesLivres() {
        StringBuilder posicoes = new StringBuilder();
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == ' ') {
                    posicoes.append(i).append(j).append(";");
                }
            }
        }
        return posicoes.toString();
    }

    static boolean teveGanhador(char caractereJogador) {
        return teveGanhadorLinha(caractereJogador)
                || teveGanhadorColuna(caractereJogador)
                || teveGanhadorDiagonalPrincipal(caractereJogador)
                || teveGanhadorDiagonalSecundaria(caractereJogador);
    }

    static boolean teveGanhadorLinha(char caractereJogador) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            boolean ganhou = true;
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] != caractereJogador) {
                    ganhou = false;
                    break;
                }
            }
            if (ganhou) return true;
        }
        return false;
    }

    static boolean teveGanhadorColuna(char caractereJogador) {
        for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
            boolean ganhou = true;
            for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
                if (tabuleiro[i][j] != caractereJogador) {
                    ganhou = false;
                    break;
                }
            }
            if (ganhou) return true;
        }
        return false;
    }

    static boolean teveGanhadorDiagonalPrincipal(char caractereJogador) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (tabuleiro[i][i] != caractereJogador) return false;
        }
        return true;
    }

    static boolean teveGanhadorDiagonalSecundaria(char caractereJogador) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (tabuleiro[i][TAMANHO_TABULEIRO - 1 - i] != caractereJogador) return false;
        }
        return true;
    }

    static void atualizaTabuleiro(int[] jogada, char caractereJogador) {
        tabuleiro[jogada[0]][jogada[1]] = caractereJogador;
    }

    static void exibirVitoriaComputador() {
        System.out.println("\nO computador venceu!\n");
        System.out.println("   ___________");
        System.out.println("  |  X     X  |");
        System.out.println("  |     ^     |");
        System.out.println("  |   \\___/  |");
        System.out.println("  |___________|");
    }

    static void exibirVitoriaUsuario() {
        System.out.println("\nO usuário venceu!\n");
        System.out.println("  \\o/");
        System.out.println("   |");
        System.out.println("  / \\");
    }

    static void exibirEmpate() {
        System.out.println("\nOcorreu empate!\n");
        System.out.println("  0  X  0");
        System.out.println(" =========");
    }

    static boolean teveEmpate() {
        return Objects.equals(retornarPosicoesLivres(), "");
    }

    static boolean sortearValorBooleano() {
        Random random = new Random();
        return random.nextBoolean();
    }

    static void limparTela() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            if (os.contains("windows") || os.contains("win") || os.contains("dos")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }
            pb.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }

    static void exibirTabuleiro() {
        limparTela();
        System.out.print("  ");
        for (int col = 0; col < tamanho; col++) {
            System.out.print((col + 1));
            if (col < tamanho - 1) {
                System.out.print("   ");
            }
        }
        System.out.println();

        for (int linha = 0; linha < tamanho; linha++) {
            System.out.print((linha + 1) + " ");
            for (int col = 0; col < tamanho; col++) {
                System.out.print(tabuleiro[linha][col]);
                if (col < tamanho - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (linha < tamanho - 1) {
                System.out.println(" ---+---+---");
            }
        }
    }
}
