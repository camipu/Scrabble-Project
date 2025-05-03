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
    private boolean jugadaValida;

    /**
     * Constructor de la classe Jugada.
     * @param paraulaFormada Paraula final formada al taulell.
     * @param casellesJugades Caselles on el jugador ha col·locat fitxes.
     * @param puntuacio Puntuació total obtinguda amb la jugada.
     */
    public Jugada(String paraulaFormada, List<Casella> casellesJugades, int puntuacio, boolean jugadaValida) {
        this.paraulaFormada = paraulaFormada;
        this.casellesJugades = casellesJugades;
        this.puntuacio = puntuacio;
        this.jugadaValida = jugadaValida;
    }
    
    /**
     * Constructor per jugades d'usuari. Valida la jugada i calcula la puntuació.
     * 
     * @param casellesJugades Caselles on l'usuari ha col·locat fitxes
     * @param dawg Diccionari per validar les paraules
     * @param taulell El taulell de joc actual
     */
    public Jugada(List<Casella> casellesJugades, DAWG dawg, Taulell taulell) {
        this.casellesJugades = casellesJugades;
        this.jugadaValida = ValidadorJugada.validarJugada(casellesJugades, dawg, taulell);
        
        boolean horitzontal = ValidadorJugada.esJugadaHoritzontal(casellesJugades);
        this.paraulaFormada = ValidadorJugada.construirParaula(casellesJugades, horitzontal, taulell);
        
        if (this.jugadaValida) {
            this.puntuacio = CalculadorPuntuacio.calcularPuntuacioJugada(casellesJugades, horitzontal, taulell);
        } else {
            this.puntuacio = 0;
        }
    }
    
    /**
     * Constructor per jugades de bot. Valida únicament les paraules perpendiculars i calcula la puntuació.
     * 
     * @param paraulaFormada Paraula formada pel bot
     * @param casellesJugades Caselles on el bot ha col·locat fitxes
     * @param dawg Diccionari per validar les paraules
     * @param taulell El taulell de joc actual
     * @param horitzontal Indica si la jugada és en direcció horitzontal
     */
    public Jugada(String paraulaFormada, List<Casella> casellesJugades, DAWG dawg, Taulell taulell, boolean horitzontal) {
        this.paraulaFormada = paraulaFormada;
        this.casellesJugades = casellesJugades;
        
        // Només validem la posició i les paraules perpendiculars per al bot
        this.jugadaValida = ValidadorJugada.posicioValida(casellesJugades, taulell) && 
                           ValidadorJugada.validarParaulesPerpendiculars(casellesJugades, dawg, horitzontal, taulell);
        
        if (this.jugadaValida) {
            this.puntuacio = CalculadorPuntuacio.calcularPuntuacioJugada(casellesJugades, horitzontal, taulell);
        } else {
            this.puntuacio = 0;
        }
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
     * @param paraula Paraula que s'ha format en aquest torn
     */
    public void setParaulaFormada(String paraula) {
        this.paraulaFormada = paraula;
    }

    /**
     * Retorna la llista de caselles utilitzades durant el torn.
     *
     * @return Llista de caselles on s'han col·locat fitxes en aquest torn
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

    /**
     * Retorna si una jugada es vàlida
     *
     * @return {@code true} si la jugada és vàlida, {@code false} altrament.
     */
    public boolean getJugadaValida() {
        return jugadaValida;
    }

    /**
     * Assigna una jugada a la validació que es passi per paràmetre.
     * @param jugadaValida Booleà que indica si la jugada és vàlida
     */
    public void setJugadaValida(Boolean jugadaValida) {
        this.jugadaValida = jugadaValida;
    }
}