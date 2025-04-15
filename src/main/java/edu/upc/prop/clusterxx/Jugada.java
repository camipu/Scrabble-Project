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

    public String getParaulaFormada() {
        return paraulaFormada;
    }

    public void setParaulaFormada(String paraula) {
        this.paraulaFormada = paraula;
    }

    public List<Casella> getCasellesJugades() {
        return casellesJugades;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
}
