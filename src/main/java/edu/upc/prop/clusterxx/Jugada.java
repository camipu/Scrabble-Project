package edu.upc.prop.clusterxx;

import java.util.List;

/**
 * Classe que representa una jugada al joc Scrabble.
 * Conté informació sobre la paraula formada, les fitxes utilitzades,
 * la posició inicial al taulell, la direcció i la puntuació.
 */
public class Jugada {
    private String paraula;
    private List<Fitxa> fitxesUtilitzades;
    private int fila;
    private int columna;
    private boolean horitzontal;
    private int puntuacio;
    
    /**
     * Constructor de la classe Jugada
     * 
     * @param paraula La paraula formada en aquesta jugada
     * @param fitxesUtilitzades Les fitxes utilitzades per formar la paraula
     * @param fila La fila inicial on comença la paraula al taulell
     * @param columna La columna inicial on comença la paraula al taulell
     * @param horitzontal Direcció de la paraula (true: horitzontal, false: vertical)
     * @param puntuacio La puntuació obtinguda amb aquesta jugada
     */
    public Jugada(String paraula, List<Fitxa> fitxesUtilitzades, int fila, int columna, 
                  boolean horitzontal, int puntuacio) {
        this.paraula = paraula;
        this.fitxesUtilitzades = fitxesUtilitzades;
        this.fila = fila;
        this.columna = columna;
        this.horitzontal = horitzontal;
        this.puntuacio = puntuacio;
    }
    
    /**
     * Obté la paraula formada
     * @return La paraula formada
     */
    public String getParaula() {
        return paraula;
    }
    
    /**
     * Obté les fitxes utilitzades per formar la paraula
     * @return Llista de fitxes utilitzades
     */
    public List<Fitxa> getFitxesUtilitzades() {
        return fitxesUtilitzades;
    }
    
    /**
     * Obté la fila inicial on comença la paraula
     * @return Fila inicial
     */
    public int getFila() {
        return fila;
    }
    
    /**
     * Obté la columna inicial on comença la paraula
     * @return Columna inicial
     */
    public int getColumna() {
        return columna;
    }
    
    /**
     * Comprova si la paraula està en direcció horitzontal
     * @return true si és horitzontal, false si és vertical
     */
    public boolean esHoritzontal() {
        return horitzontal;
    }
    
    /**
     * Obté la puntuació de la jugada
     * @return Puntuació de la jugada
     */
    public int getPuntuacio() {
        return puntuacio;
    }
    
    /**
     * Estableix la puntuació de la jugada
     * @param puntuacio Nova puntuació
     */
    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
    
    /**
     * Retorna una representació en text de la jugada
     * @return String amb la informació de la jugada
     */
    @Override
    public String toString() {
        return "Jugada{" +
                "paraula='" + paraula + '\'' +
                ", posició=(" + fila + "," + columna + ")" +
                ", direcció=" + (horitzontal ? "horitzontal" : "vertical") +
                ", puntuació=" + puntuacio +
                '}';
    }
}