package edu.upc.prop.clusterxx;

public class Taulell {
    private static final int SIZE = 15;
    private char[][] taullel;

    public taulell () {
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

    public void imprimirTaulell()
}
