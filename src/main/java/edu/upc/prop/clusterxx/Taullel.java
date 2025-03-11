package edu.upc.prop.clusterxx;

public class Taullel {
    private static final int SIZE = 15;
    private char[][] taullel;

    public Taullel() {
        taullel = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                taullel[i][j] = '-'; // Inicializa cada celda con un carácter por defecto
            }
        }
    }

    public char[][] getTaullel() {
        return taullel;
    }
}
