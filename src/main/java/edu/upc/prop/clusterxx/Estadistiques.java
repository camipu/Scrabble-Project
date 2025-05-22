package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioEstadistiquesBuides;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Collections;


/**
 * Classe Estadístiques
 *
 * Controla les estadístiques globals del sistema. Registra i organitza la informació dels jugadors i les seves puntuacions.
 * Té un mapa de puntuacions per jugador, un ranquing ordenat i altres estidisitques com la puntuacio total.
 */
public class Estadistiques implements Serializable {

    private Map<String, Integer> puntuacions = new HashMap<>();
    private TreeMap<Integer, Set<String>> ranking = new TreeMap<>(Collections.reverseOrder());
    private int puntuacioTotal = 0;
    private static Estadistiques instance;

    private Estadistiques() {}

    /**
     * Retorna la instància única del controlador d’estadístiques.
     * Si encara no s’ha creat, s’inicialitza.
     *
     * @return Instància única de Estadistiques
     */
    public static Estadistiques getInstance() {
        if (instance == null) {
            instance = new Estadistiques();
        }
        //carregar si hi ha de fitxer
        return instance;
    }

    /**
     * Afegeix una puntuació al jugador entrat per paràmetre i actualitza les estadístiques globals.
     * Si el jugador ja tenia una puntuació registrada, s'actualitza
     * i es reordena el rànquing. També s’actualitza la puntuació total del sistema.
     *
     * @param puntuacio Puntuació a afegir al jugador
     * @param jugador Nom del jugador
     * @throws IllegalArgumentException si la puntuació és negativa
     */
    public void afegirPuntuacio(int puntuacio, String jugador) {
        if (puntuacio < 0) {
            throw new IllegalArgumentException("La puntuació no pot ser negativa");
        }

        if (puntuacions.containsKey(jugador)) {
            int antiga = puntuacions.get(jugador);
            ranking.get(antiga).remove(jugador);
            if (ranking.get(antiga).isEmpty()) {
                ranking.remove(antiga);
            }
            puntuacioTotal -= antiga;
        }

        int nova = puntuacions.getOrDefault(jugador, 0) + puntuacio;
        puntuacions.put(jugador, nova);
        puntuacioTotal += nova;

        ranking.computeIfAbsent(nova, k -> new HashSet<>()).add(jugador);
    }

    /**
     * Elimina un determinat jugador de totes les estadístiques i actualitza les estadístiques globals.
     *
     * @param jugador Nom del jugador al que es vol eliminar
     * @throws IllegalArgumentException Si el jugador no existeix
     */
    public void eliminarJugador(String jugador) {
        if (!puntuacions.containsKey(jugador)) throw new IllegalArgumentException("El jugador no existeix");

        int punts = puntuacions.remove(jugador);
        puntuacioTotal -= punts;

        Set<String> jugadors = ranking.get(punts);
        jugadors.remove(jugador);
        if (jugadors.isEmpty()) {
            ranking.remove(punts);
        }
    }

    /**
     * Retira una quantitat de punts a un jugador i actualitza les estadístiques globals.
     * Si la nova puntuació és menor o igual a zero, el jugador s’elimina del registre.
     * També s’actualitzen el rànquing i la puntuació total acumulada.
     *
     * @param jugador Nom del jugador
     * @param punts Quantitat de punts a retirar
     * @throws IllegalArgumentException si els punts són negatius o si el jugador no existeix
     */
    public void retirarPuntuacio(String jugador, int punts) {
        if (punts < 0) {
            throw new IllegalArgumentException("Els punts a restar no poden ser negatius");
        }
        if (!puntuacions.containsKey(jugador)) {
            throw new IllegalArgumentException("El jugador no existeix");
        }

        int actuals = puntuacions.get(jugador);
        int nous = actuals - punts;

        Set<String> jugadors = ranking.get(actuals);
        jugadors.remove(jugador);
        if (jugadors.isEmpty()) {
            ranking.remove(actuals);
        }

        puntuacioTotal -= actuals;

        if (nous <= 0) {
            puntuacions.remove(jugador);
        } else {
            puntuacions.put(jugador, nous);
            puntuacioTotal += nous;
            ranking.computeIfAbsent(nous, k -> new HashSet<>()).add(jugador);
        }
    }


    /**
     * Retorna el jugador amb la puntuacio mes alta.
     *
     * @return Una entrada (nom del jugador y puntuació) del millor jugador.
     */
    public Map.Entry<String, Integer> obtenirMillor() {
        if (ranking.isEmpty()) throw new ExcepcioEstadistiquesBuides("No hi ha puntuacions registrades");
        String jugador = ranking.firstEntry().getValue().iterator().next();
        int puntuacio = puntuacions.get(jugador);
        return new AbstractMap.SimpleEntry<>(jugador, puntuacio);
    }

    /**
     * Retorna el jugador amb la puntuació mes baixa.
     *
     * @return Una entrada (nom del jugador y puntuació) del pitjor jugador.
     */
    public Map.Entry<String, Integer> obtenirPitjor() {
        if (ranking.isEmpty()) throw new ExcepcioEstadistiquesBuides("No hi ha puntuacions registrades");
        String jugador = ranking.lastEntry().getValue().iterator().next();
        int puntuacio = puntuacions.get(jugador);
        return new AbstractMap.SimpleEntry<>(jugador, puntuacio);
    }

    /**
     * Retorna la puntuació mitjana de tots els jugadors
     *
     * @return La puntucacio total entre el nombre de jugadors.
     */
    public float obtenirMitja() {
        if (puntuacions.isEmpty()) return 0f;
        return (float) puntuacioTotal / puntuacions.size();
    }

    /**
     * Retorna totes les puntuacions registrades
     *
     * @return Map de les puntuacions
     */
    public Map<String, Integer> getPuntuacions() {
        return puntuacions;
    }

    /**
     * Retorna la puntuacio total dels jugadors
     *
     * @return La suma de totes les puntuacions
     */
    public int getPuntuacioTotal() {
        return puntuacioTotal;
    }

    public void carregarDes(Estadistiques altres) {
        this.puntuacions = new HashMap<>(altres.puntuacions);
        this.ranking = new TreeMap<>(altres.ranking);
        this.puntuacioTotal = altres.puntuacioTotal;
    }

}
