/*
 * Main.java
 *
 * Neste codigo, uma instancia de ContadorCentral e compartilhada por duas
 * threads. Algumas execucoes funcionam perfeitamente, enquanto outras apresentam
 * erros de contagem devido ao problema de "leitura suja"/"atualizacao perdida".
 * Exemplo:
 * thread 1                thread 2
 * ======================= =========================
 * Le numPessoas = 0
 *                         Le numPessoas = 0
 * soma 1
 *                         soma 3
 *                         Escreva 3 em numPessoas
 * Escreve 1 em numPessoas
 *
 * Resultado: perdeu-se o incremento feito pela thread 2
 *
 * * Rodar a partir da linha de comando
 * ===================================
 * 1. Salve este arquivo em <dir>/
 * 2. Lance o shell: executar -> cmd
 * 3. posicione no diret�rio <dir>
 * 4. compile: javac Main.java
 * 5. execute: java -cp . Main
 */

import java.util.*;
import java.text.*;

/**
 *
 * @author TACLA
 */

/**
 * Contador � um objeto compartilhado pelas threads entrada 1 e entrada 2.
 */
class ContadorCentral {
    protected int numPessoas = 0;
}

/**
 * Roleta controla uma entrada do parque.
 */
class Roleta implements Runnable {
    public int totPessoas = 0;
    public int incr = 0;
    public ContadorCentral contadorCentral;

    public void run() {
        Thread thread = Thread.currentThread();

        for (int i = 0; i < 40000000; i++) {
            totPessoas = totPessoas + incr;
            //A solução proposta para este problema é a utilização de um bloco synchronized em torno
            //do objeto compartilhado "ContadorCentral", o que garante que apenas uma thread poderá atualizar
            //o valor por vez e, portanto, evita que ocorra a "leitura suja"/"atualização perdida"
            synchronized (contadorCentral){
                contadorCentral.numPessoas = contadorCentral.numPessoas + incr;
            }
        }
    }
}

public class Main {
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        ContadorCentral contador = new ContadorCentral();
        // if (args.length > 0)
        // Roleta.limite = Integer.parseInt(args[0]);
        Roleta e1 = new Roleta();
        e1.contadorCentral = contador;
        e1.incr = 1;

        Roleta e2 = new Roleta();
        e2.contadorCentral = contador;
        e2.incr = 3;

        Thread t1 = new Thread(e1, "Entrada 1");
        Thread t2 = new Thread(e2, "Entrada 2");
        t1.start();
        t2.start();
        // aguarda as duas threads encerrarem para terminar a main
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        } finally {
            DecimalFormat estilo = new DecimalFormat("###,###,###,###");
            System.out.println("\n*** FIM DA CONTAGEM ***");
            System.out.println("*** Entrada 1: " + estilo.format(e1.totPessoas)
                    + " pessoas");
            System.out.println("*** Entrada 2: " + estilo.format(e2.totPessoas)
                    + " pessoas");
            System.out.println("*** Total: "
                    + estilo.format(e2.totPessoas + e1.totPessoas));
            System.out.println("*** Total CENTRALIZADO: "
                    + estilo.format(contador.numPessoas));
        }
    }
}