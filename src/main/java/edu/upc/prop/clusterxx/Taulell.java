package edu.upc.prop.clusterxx;

public class Taulell {
    private static final int SIZE = 15;
    private char[][] taulell;

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

    public void imprimirTaulell() {
        // Imprimir la línia superior
        for (int i = 0; i < SIZE; ++i) {
            System.out.print("+---");
        }
        System.out.println("+");

        // Imprimir les files del taulell
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.print("| " + taulell[i][j] + " ");
            }
            System.out.println("|");

            // Imprimir la línia separadora entre files
            for (int j = 0; j < SIZE; ++j) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }
}
