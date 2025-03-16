package edu.upc.prop.clusterxx;

public class Taulell {
    private final int size;
    private final Casella[][] taulell;

    public Taulell(int size) {
        this.size = size;
        taulell = new Casella[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                taulell[i][j] = new Casella(i, j); // Inicialitza cada casella amb coordenades
            }
        }
    }

    public Casella[][] getTaulell() {
        return taulell;
    }

    public int getSize() {return size;}


    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            System.out.println(" Posició fora dels límits.");
        }
        if(!taulell[fila][columna].colocarFitxa(fitxa)) {
            System.out.println("Error: Casella ocupada.");
        }
    }

    public void imprimirTaulell() {
        for (int i = 0; i < size; ++i) {
            System.out.print("+----");
        }
        System.out.println("+");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Casella casella = taulell[i][j];
                System.out.print("| " + Colors.YELLOW_TEXT + casella + Colors.RESET + " ");
            }
            System.out.println("|");

            for (int j = 0; j < size; ++j) {
                System.out.print("+----");
            }
            System.out.println("+");
        }
    }

}
