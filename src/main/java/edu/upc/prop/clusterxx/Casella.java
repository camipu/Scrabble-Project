package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaOcupada;
import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaBuida;

import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorParaula;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorLletra;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaNormal;

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
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Les coordenades no poden ser negatives.");
        }
        if (x >= size || y >= size) {
            throw new IllegalArgumentException("Les coordenades han de ser menors que la mida del tauler.");
        }
        this.x = x;
        this.y = y;
        this.fitxa = null;
        this.estrategia = assignarEstrategia(x, y, size);
        this.casellaJugada = false;
    }

    /**
     * Inicialitza aquesta casella com a còpia d'una altra.
     * Assigna la mateixa posició, fitxa (si n'hi ha), estratègia de puntuació i estat de jugada.
     *
     * @param copia Casella de la qual copiar les dades
     */
    public Casella(Casella copia) {
        this.x = copia.obtenirX();
        this.y = copia.obtenirY();

        if (!copia.esBuida()) this.fitxa = new Fitxa(copia.obtenirFitxa());
        else this.fitxa = null;

        // Clonar l'estratègia segons el tipus concret
        if (copia.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaNormal) {
            this.estrategia = new EstrategiaPuntuacio.EstrategiaNormal();
        } else if (copia.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra) {
            int m = copia.obtenirEstrategia().obtenirMultiplicador();
            this.estrategia = new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(m);
        } else if (copia.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula) {
            int m = copia.obtenirEstrategia().obtenirMultiplicador();
            this.estrategia = new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(m);
        } else {
            // Per si afegeixes noves estratègies en el futur
            throw new IllegalArgumentException("Estratègia de puntuació desconeguda");
        }

        this.casellaJugada = copia.casellaJugada;
    }


    /**
     * Calcula els punts que aporta la fitxa en aquesta casella segons l'estratègia assignada.
     *
     * @return Punts calculats per a la fitxa en aquesta casella
     */
    public int calcularPunts() {
        if (esJugada()) return fitxa.obtenirPunts();
        else return estrategia.calcularPunts(fitxa);
    }

    /**
     * Marca la casella com a jugada en el torn actual.
     */
    public void jugarCasella() {
        if (casellaJugada) {
            throw new IllegalStateException("La casella ja ha estat jugada.");
        }
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
        if(fitxa == null) {
            throw new ExcepcioCasellaBuida("La casella " + obtenirX() + " y " + obtenirY() + " està buida");
        }
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
     * Col·loca una fitxa a la casella si aquesta està buida.
     *
     * @param fitxa Fitxa a col·locar
     * @throws ExcepcioCasellaOcupada si la casella ja conté una fitxa
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
     * @throws ExcepcioCasellaBuida si la casella no conté cap fitxa
     */
    public void retirarFitxa() {
        if (!esBuida()) {
            this.fitxa = null;
        }
        else throw new ExcepcioCasellaBuida("La casella " + obtenirX() + " y " + obtenirY() + " esta buida");
    }

    /**
     * Assigna una estratègia de puntuació a la casella segons la seva posició al tauler.
     * Les estratègies es basen en les coordenades i la mida del tauler, segons la configuració
     * estàndard de Scrabble (triple/doble paraula o lletra).
     *
     * @param i Coordenada vertical de la casella (fila)
     * @param j Coordenada horitzontal de la casella (columna)
     * @param size Mida del tauler (número de files/columnes)
     * @return L'estratègia de puntuació assignada a la casella
     */
    private EstrategiaPuntuacio assignarEstrategia(int i, int j, int size) {
        int centro = size / 2;
        int offset = size / 4;
        // Triple paraula (TW)
        if ((i == 0 || i == centro || i == size - 1) && (j == 0 || j == centro || j == size - 1)) {
            return new EstrategiaMultiplicadorParaula(3);
        }
        // Triple lletra (TL)
        if ((i == offset || i == size - 1 - offset) && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }
        if ((i == offset || i == size - 1 - offset) && j == centro || i == centro && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }

        // Doble paraula (DW) aqui potser falta afegir que i = centro && j = centro
        if (i == j || i + j == size - 1) {
            return new EstrategiaMultiplicadorParaula(2);
        }

        // Doble lletra (DL)
        if (i == centro || j == centro) {
            return new EstrategiaMultiplicadorLletra(2);
        }

        return new EstrategiaNormal();
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