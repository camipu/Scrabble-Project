package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaOcupada;

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
     * @param estrategia Estratègia de puntuació que s'aplicarà a aquesta casella
     */
    public Casella(int x, int y, EstrategiaPuntuacio estrategia) {
        this.x = x;
        this.y = y;
        this.fitxa = null;
        this.estrategia = estrategia;
        this.casellaJugada = false;
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
     * Comprova si la casella ha estat jugada en el torn actual.
     *
     * @return true si la casella ha estat jugada, false en cas contrari
     */
    public boolean casellaJugada() {
        return casellaJugada;
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