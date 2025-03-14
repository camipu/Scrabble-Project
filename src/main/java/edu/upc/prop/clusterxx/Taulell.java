package edu.upc.prop.clusterxx;

public class Taulell {
    private static final int SIZE = 15;
    private final Casella[][] taulell;

    public Taulell() {
        taulell = new Casella[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                taulell[i][j] = new Casella(i, j); // Inicialitza cada casella amb coordenades
            }
        }
    }

    public Casella[][] getTaulell() {
        return taulell;
    }

    public boolean colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= SIZE || columna < 0 || columna >= SIZE) {
            System.out.println("Error: Posició fora dels límits.");
            return false;
        }
        return taulell[fila][columna].colocarFitxa(fitxa);
    }

    public void imprimirTaulell() {
        for (int i = 0; i < SIZE; ++i) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                Casella casella = taulell[i][j];
                System.out.print("| " + Colors.YELLOW_TEXT + casella + Colors.RESET + " ");
            }
            System.out.println("|");

            for (int j = 0; j < SIZE; ++j) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }
}
