package edu.upc.prop.clusterxx;

import java.util.List;

/**
 * Classe que representa una jugada al joc de Scrabble.
 * Emmagatzema la paraula formada, les caselles on s'han col·locat fitxes noves
 * i la puntuació total.
 */
public class Jugada {
    private String paraulaFormada;
    private final List<Casella> casellesJugades;
    private int puntuacio;

    /**
     * Constructor de la classe Jugada.
     * @param paraulaFormada Paraula final formada al taulell.
     * @param casellesJugades Caselles on el jugador ha col·locat fitxes.
     * @param puntuacio Puntuació total obtinguda amb la jugada.
     */
    public Jugada(String paraulaFormada, List<Casella> casellesJugades, int puntuacio) {
        this.paraulaFormada = paraulaFormada;
        this.casellesJugades = casellesJugades;
        this.puntuacio = puntuacio;
    }

    /**
     * Retorna la paraula formada durant el torn.
     *
     * @return Paraula formada en aquest torn
     */
    public String getParaulaFormada() {
        return paraulaFormada;
    }

    /**
     * Assigna la paraula formada durant el torn.
     *
     * @param paraula Paraula que s’ha format en aquest torn
     */
    public void setParaulaFormada(String paraula) {
        this.paraulaFormada = paraula;
    }

    /**
     * Retorna la llista de caselles utilitzades durant el torn.
     *
     * @return Llista de caselles on s’han col·locat fitxes en aquest torn
     */
    public List<Casella> getCasellesJugades() {
        return casellesJugades;
    }

    /**
     * Retorna la puntuació obtinguda durant aquest torn.
     *
     * @return Puntuació del torn
     */
    public int getPuntuacio() {
        return puntuacio;
    }

    /**
     * Assigna la puntuació obtinguda durant aquest torn.
     *
     * @param puntuacio Puntuació a assignar al torn
     */
    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
}
