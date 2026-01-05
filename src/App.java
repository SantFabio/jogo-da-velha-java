import java.util.Scanner;
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

        // Mensagem adicionada de boas-vindas
        System.out.println("Bem-vindo ao Jogo da Velha!");

        // Variável adicionada para controlar se o usuário deseja jogar novamente
        boolean jogarNovamente;

        do {

            inicializarTabuleiro();

            // Definimos aqui qual é o caractere que cada jogador irá utilizar no jogo.
            //TODO 01: chame as funções obterCaractereUsuario() e obterCaractereComputador
            //para definir quais caracteres da lista de caracteres aceitos que o jogador
            //quer configurar para ele e para o computador.
            char caractereUsuario = obterCaractereUsuario();
            char caractereComputador = obterCaractereComputador(caractereUsuario);

            // Esta variavel é utilizada para definir se o usuário começa a jogar ou não.
            // Valor true, usuario começa jogando, valor false computador começa.
            //TODO 02: obtenha o valor booleano sorteado
            boolean vezUsuarioJogar = sortearValorBooleano();

            boolean jogoContinua;

            do {
                // controla se o jogo terminou
                jogoContinua = true;
                exibirTabuleiro();

                if (vezUsuarioJogar) {
                    //TODO 03: Execute a chamada processar vez do usuario
                    processarVezUsuario(caractereUsuario);

                    // Verifica se o usuario venceu
                    //TODO 04: Este if deve executar apenas se teve ganhador 
                    if (teveGanhador(caractereUsuario)) {
                        exibirTabuleiro();
                        exibirVitoriaUsuario();
                        jogoContinua = false;
                    }

                    // define que na proxima execucao do laco o jogador nao joga, ou seja, será a vez do computador
                    vezUsuarioJogar = false;

                } else {
                    //TODO 05: Execute a chamada processar vez do computador
                    processarVezComputador(caractereComputador);

                    // Verifica se o computador venceu
                    //TODO 06: Este if deve executar apenas se teve ganhador
                    if (teveGanhador(caractereComputador)) {

                        //TODO 07: Exiba que o computador ganhou
                        exibirTabuleiro();
                        exibirVitoriaComputador();
                        jogoContinua = false;
                    }

                    //TODO 08: defina qual o vaor a variavel abaixo deve possuir para que a proxima execucao do laco seja a vez do usuário
                    vezUsuarioJogar = true;
                }

                //TODO 09: Este if deve executar apenas se o jogo continua E 
                //ocorreu tempate. Utilize o metodo teveEmpate()
                if (jogoContinua && teveEmpate()) {
                    exibirTabuleiro();
                    exibirEmpate();
                    jogoContinua = false;
                }

            } while (jogoContinua);

            // Pergunta adicionada para saber se o usuário quer jogar novamente
            jogarNovamente = perguntarJogarNovamente();

        } while (jogarNovamente);

        // Mensagem adicionada de encerramento
        System.out.println("Até mais!");
        teclado.close();
    }

    static void inicializarTabuleiro() {
        //TODO 10: Implementar método conforme explicação
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    static char obterCaractereUsuario() {
        //TODO 11: Implementar método conforme explicação
        char c;
        do {
            System.out.print("Escolha o caractere do usuário (" + CARACTERES_IDENTIFICADORES_ACEITOS + "): ");
            c = teclado.next().toUpperCase().charAt(0);
        } while (!CARACTERES_IDENTIFICADORES_ACEITOS.contains(String.valueOf(c)));
        return c;
    }

    static char obterCaractereComputador(char caractereUsuario) {
        //TODO 12: Implementar método conforme explicação
        char c;
        do {
            System.out.print("Escolha o caractere do computador (" + CARACTERES_IDENTIFICADORES_ACEITOS + "): ");
            c = teclado.next().toUpperCase().charAt(0);
        } while (!CARACTERES_IDENTIFICADORES_ACEITOS.contains(String.valueOf(c)) || c == caractereUsuario);
        return c;
    }

    static boolean sortearValorBooleano() {
        return new Random().nextBoolean();
    }

    static void processarVezUsuario(char caractereUsuario) {
        String posicoesLivres = retornarPosicoesLivres();
        int[] jogada = obterJogadaUsuario(posicoesLivres);
        atualizaTabuleiro(jogada, caractereUsuario);
    }

    static void processarVezComputador(char caractereComputador) {
        String[] jogadas = retornarPosicoesLivres().split(";");
        String escolha = jogadas[new Random().nextInt(jogadas.length)];
        atualizaTabuleiro(converterJogadaStringParaVetorInt(escolha), caractereComputador);
    }

    static int[] obterJogadaUsuario(String posicoesLivres) {
        while (true) {
            System.out.print("Digite linha e coluna (ex: 1 1): ");
            int linha = teclado.nextInt() - 1;
            int coluna = teclado.nextInt() - 1;

            if (posicoesLivres.contains("" + linha + coluna)) {
                return new int[]{linha, coluna};
            } else {
                System.out.println("Jogada inválida!");
            }
        }
    }

    static int[] converterJogadaStringParaVetorInt(String jogada) {
        return new int[]{
            Character.getNumericValue(jogada.charAt(0)),
            Character.getNumericValue(jogada.charAt(1))
        };
    }

    static String retornarPosicoesLivres() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == ' ') {
                    sb.append(i).append(j).append(";");
                }
            }
        }
        return sb.toString();
    }

    static boolean teveGanhador(char caractereJogador) {
        return teveGanhadorLinha(caractereJogador)
            || teveGanhadorColuna(caractereJogador)
            || teveGanhadorDiagonalPrincipal(caractereJogador)
            || teveGanhadorDiagonalSecundaria(caractereJogador);
    }

    static boolean teveGanhadorLinha(char caractereJogador) {
        //TODO 21: Implementar método conforme explicação
        for (int i = 0; i < tamanho; i++) {
            boolean ganhou = true;
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] != caractereJogador) {
                    ganhou = false;
                }
            }
            if (ganhou) return true;
        }
        return false;
    }

    static boolean teveGanhadorColuna(char caractereJogador) {
        //TODO 22: Implementar método conforme explicação
        for (int j = 0; j < tamanho; j++) {
            boolean ganhou = true;
            for (int i = 0; i < tamanho; i++) {
                if (tabuleiro[i][j] != caractereJogador) {
                    ganhou = false;
                }
            }
            if (ganhou) return true;
        }
        return false;
    }

    static boolean teveGanhadorDiagonalPrincipal(char caractereJogador) {
        //TODO 23: Implementar método conforme explicação
        for (int i = 0; i < tamanho; i++) {
            if (tabuleiro[i][i] != caractereJogador) {
                return false;
            }
        }
        return true;
    }

    static boolean teveGanhadorDiagonalSecundaria(char caractereJogador) {
        //TODO 24: Implementar método conforme explicação
        for (int i = 0; i < tamanho; i++) {
            if (tabuleiro[i][tamanho - 1 - i] != caractereJogador) {
                return false;
            }
        }
        return true;
    }

    static boolean teveEmpate() {
        return retornarPosicoesLivres().isEmpty();
    }

    static void atualizaTabuleiro(int[] jogada, char caractereJogador) {
        tabuleiro[jogada[0]][jogada[1]] = caractereJogador;
    }

    static void exibirVitoriaComputador() {
        System.out.println("O computador venceu!");
    }

    static void exibirVitoriaUsuario() {
        System.out.println("O usuário venceu!");
    }

    static void exibirEmpate() {
        System.out.println("Ocorreu empate!");
    }

    static void exibirTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(" " + tabuleiro[i][j] + " ");
                if (j < tamanho - 1) System.out.print("|");
            }
            System.out.println();
            if (i < tamanho - 1) System.out.println("---+---+---");
        }
        System.out.println();
    }

    static boolean perguntarJogarNovamente() {
        System.out.print("Deseja jogar novamente? (S/N): ");
        return teclado.next().toUpperCase().charAt(0) == 'S';
    }
}
