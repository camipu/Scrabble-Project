package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaOcupada;
import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaBuida;

/**
 * Representa una casella del tauler de joc.
 * Cada casella té una posició definida per coordenades (x,y), pot contenir una fitxa
 * i té associada una estratègia de puntuació.
 */
public class Casella {
    private final int x;
    private final int y;
    private Fitxa fitxa;
    private boolean casellaJugada;
    private final EstrategiaPuntuacio estrategia;

    /**
     * Constructor que crea una nova casella buida.
     *
     * @param x Coordenada x de la casella
     * @param y Coordenada y de la casella
     */
    public Casella(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.fitxa = null;
        this.estrategia = assignarEstrategia(x, y, size);
        this.casellaJugada = false;
    }

    private EstrategiaPuntuacio assignarEstrategia(int i, int j, int size) {
        int centro = size / 2;
        int offset = size / 4;
        // Triple paraula (TW)
        if ((i == 0 || i == centro || i == size - 1) && (j == 0 || j == centro || j == size - 1)) {
            return new EstrategiaMultiplicadorParaula(3);
        }

        // Doble paraula (DW) aqui potser falta afegir que i = centro && j = centro
        if (i == j || i + j == size - 1) {
            return new EstrategiaMultiplicadorParaula(2);
        }

        // Triple lletra (TL)
        if ((i == offset || i == size - 1 - offset) && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }
        if ((i == offset || i == size - 1 - offset) && j == centro || i == centro && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }

        // Doble lletra (DL)
        if (i == centro || j == centro) {
            if (i != centro || j != centro) {
                return new EstrategiaMultiplicadorLletra(2);
            }
        }

        return new EstrategiaNormal();
    }


    /**
     * Calcula els punts que aporta la fitxa en aquesta casella segons l'estratègia assignada.
     *
     * @return Punts calculats per a la fitxa en aquesta casella
     */
    public int calcularPunts() {
        return estrategia.calcularPunts(fitxa);
    }

    /**
     * Marca la casella com a jugada en el torn actual.
     */
    public void jugarCasella() {
        casellaJugada = true;
    }

    /**
     * Obté la coordenada x de la casella.
     *
     * @return Coordenada x
     */
    public int obtenirX() {
        return x;
    }

    /**
     * Obté la coordenada y de la casella.
     *
     * @return Coordenada y
     */
    public int obtenirY() {
        return y;
    }

    /**
     * Obté la fitxa col·locada en aquesta casella.
     *
     * @return La fitxa col·locada o null si la casella està buida
     */
    public Fitxa obtenirFitxa() {
        return fitxa;
    }

    /**
     * Obté l'estratègia de puntuació associada a aquesta casella.
     *
     * @return L'estratègia de puntuació
     */
    public EstrategiaPuntuacio obtenirEstrategia() {
        return estrategia;
    }

    /**
     * Obté el multiplicador de punts associat a l'estratègia de la casella.
     *
     * @return Valor del multiplicador
     */
    public int obtenirMultiplicador() {
        return estrategia.obtenirMultiplicador();
    }

    /**
     * Comprova si la casella està buida (no té fitxa).
     *
     * @return true si la casella està buida, false si conté una fitxa
     */
    public boolean esBuida() {
        return fitxa == null;
    }

    /**
     * Comprova si la casella està jugada.
     *
     * @return true si la casella està jugada, false altrament.
     */
    public boolean esJugada() {
        return casellaJugada;
    }

    /**
     * Intenta col·locar una fitxa a la casella si està buida.
     *
     * @param fitxa Fitxa a col·locar
     *
     */
    public void colocarFitxa(Fitxa fitxa) {
        if (esBuida()) {
            this.fitxa = fitxa;
        }
        else throw new ExcepcioCasellaOcupada("La casella " + obtenirX() + " y " + obtenirY() + " esta ocupada");
    }

    /**
     * Retira la fitxa de la casella, si en conté.
     *
     * @param fitxa Fitxa a col·locar
     *
     */
    public void retirarFitxa(Fitxa fitxa) {
        if (!esBuida()) {
            this.fitxa = null;
        }
        else throw new ExcepcioCasellaBuida("La casella " + obtenirX() + " y " + obtenirY() + " esta buida");
    }

    /**
     * Retorna una representació en text de la casella.
     * Si està buida, retorna dos espais en blanc.
     * Si conté un dígraf, retorna la representació de la fitxa.
     * Si conté una lletra simple, retorna un espai seguit de la lletra.
     *
     * @return Representació textual de la casella
     */
    @Override
    public String toString() {
        if (esBuida()) return "  "; // Casella buida, es retorna un espai
        return fitxa.esDigraf() ? fitxa.toString() : " " + fitxa.toString();
    }

}