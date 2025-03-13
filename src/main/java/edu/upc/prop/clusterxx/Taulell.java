package edu.upc.prop.clusterxx;
import java.util.*;

public class Taulell {
    private static final int SIZE = 15;
    private final char[][] taulell;

    public Taulell () {
        taulell = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                taulell[i][j] = '-'; // Inicializa cada celda con un carácter por defecto
            }
        }
    }

    public char[][] getTaulell() {
        return taulell;
    }

//    // Codis ANSI per a colors
//    private static final String RESET = "\u001B[0m";
//    private static final String YELLOW = "\u001B[33m";

    public void imprimirTaulell() {
        // Imprimir la línia superior
        for (int i = 0; i < SIZE; ++i) {
            System.out.print("+---");
        }
        System.out.println("+");

        // Imprimir les files del taulell
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                char casella = taulell[i][j];

                // Si la casella no és '-', imprimeix-la amb fons vermell
                if (casella != '-') {
                    System.out.print("| " + Colors.YELLOW_TEXT + casella + Colors.RESET + " ");
                } else {
                    System.out.print("| " + " " + " ");
                }
            }
            System.out.println("|");

            // Imprimir la línia separadora entre files
            for (int j = 0; j < SIZE; ++j) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }

    public boolean colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= SIZE || columna < 0 || columna >= SIZE) {
            System.out.println("Error: Posició fora dels límits del taulell.");
            return false;
        }
        if (taulell[fila][columna] != '-') {
            System.out.println("Error: La posició ja està ocupada.");
            return false;
        }

        taulell[fila][columna] = fitxa.getLletra();
        return true;
    }
}
