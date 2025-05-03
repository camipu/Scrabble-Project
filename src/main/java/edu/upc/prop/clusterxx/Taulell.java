package edu.upc.prop.clusterxx;

/**
 * Classe Taulell
 *
 * Representa el taulell de joc de Scrabble com una matriu de caselles.
 * Cada casella pot contenir una fitxa i tenir una estratègia de puntuació associada.
 *
 * La mida del taulell és configurable i pot variar segons la configuració inicial de la partida.
 */
public class Taulell {
    private final int size;
    private final Casella[][] taulell;

    /**
     * Crea un nou taulell de joc amb la mida especificada.
     * La mida ha de ser un nombre imparell per garantir la simetria del taulell.
     * Cada casella del taulell s'inicialitza amb la seva posició i estratègia de puntuació corresponent.
     *
     * @param size Mida del taulell (nombre de files i columnes)
     * @throws IllegalArgumentException si la mida especificada és parella
     */
    public Taulell(int size) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("La mida del tauler ha de ser imparella per garantir simetria.");
        }
        else if (size < 5) {
            throw new IllegalArgumentException("La mida del tauler ha de ser com a mínim 5.");
        }


        this.size = size;
        taulell = new Casella[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                taulell[i][j] = new Casella(i, j, size);
            }
        }
    }

    /**
     * Inicialitza aquest taulell com la còpia d'un altre taulell.
     * Es copien tots els atributs del taulell original.
     *
     * @param copiaTaulell El taulell original del qual es vol fer la còpia.
     */
    public Taulell(Taulell copiaTaulell) {
        this.size = copiaTaulell.getSize();
        this.taulell = new Casella[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Casella original = copiaTaulell.getCasella(i, j);
                this.taulell[i][j] = new Casella(original); // copia profunda
            }
        }
    }

    /**
     * Retorna la mida del taulell (nombre de files i columnes).
     *
     * @return Mida del taulell
     */
    public int getSize() {
        return size;
    }

    /**
     * Retorna la matriu completa de caselles que formen el taulell.
     *
     * @return Matriu de caselles del taulell
     */
    public Casella[][] getTaulell() {
        return taulell;
    }

    /**
     * Retorna la casella situada a les coordenades especificades.
     *
     * @param x Fila (coordenada vertical)
     * @param y Columna (coordenada horitzontal)
     * @return Casella situada a (x, y)
     */
    public Casella getCasella(int x, int y) {return taulell[x][y];}

    /**
     * Retorna la fitxa situada en una posició especifica del taulell.
     *
     * @param fila La fila de la fitxa
     * @param columna La columna de la fitxa
     * @return La fitxa colocada a (fila, columna)
     */
    public Fitxa obtenirFitxa(int fila, int columna) {
        return taulell[fila][columna].obtenirFitxa();
    }

    /**
     * Mètode per assignar una fitxa a una casella del taulell.
     *
     * @param fitxa Fitxa que es vol assignar a la casella
     * @param fila Fila de la casella
     * @param columna Columna de la casella
     * @throws IllegalArgumentException si la posció de la casella es fora de límits.
     */
    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            throw new IllegalArgumentException("Posició fora dels límits.");
        }
        else taulell[fila][columna].colocarFitxa(fitxa);
    }

    /**
     * Mètode per desassignar una fitxa d'una casella del taulell.
     *
     * @param fila Fila de la casella
     * @param columna Columna de la casella
     * @throws IllegalArgumentException si la posció de la casella es fora de límits.
     */
    public void retirarFitxa( int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            throw new IllegalArgumentException("Posició fora dels límits.");
        }
        else taulell[fila][columna].retirarFitxa();
    }

    /**
     * Comprova si totes les caselles del taulell estan buides.
     *
     * @return {@code true} si el taulell està completament buit, {@code false} altrament
     */
    public boolean esBuit() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!taulell[i][j].esBuida()) {
                    return false;
                }
            }
        }
        return true;
    }
}