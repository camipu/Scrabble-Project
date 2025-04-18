package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioEstadistiquesBuides;

import java.util.*;

public class Estadistiques {

    private Map<String, Integer> puntuacions = new HashMap<>();
    private TreeMap<Integer, Set<String>> ranking = new TreeMap<>(Collections.reverseOrder());
    private int puntuacioTotal = 0;
    private static Estadistiques instance;

    private Estadistiques() {}

    public static Estadistiques getInstance() {
        if (instance == null) {
            instance = new Estadistiques();
        }
        return instance;
    }

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
     * Retorna el jugador con la puntuación más alta.
     * @return Una entrada (nombre del jugador y puntuación) del mejor jugador.
     */
    public Map.Entry<String, Integer> obtenirMillor() {
        if (ranking.isEmpty()) throw new ExcepcioEstadistiquesBuides("No hi ha puntuacions registrades");
        String jugador = ranking.firstEntry().getValue().iterator().next();
        int puntuacio = puntuacions.get(jugador);
        return new AbstractMap.SimpleEntry<>(jugador, puntuacio);
    }

    /**
     * Retorna el jugador con la puntuación más baja.
     * @return Una entrada (nombre del jugador y puntuación) del peor jugador.
     */
    public Map.Entry<String, Integer> obtenirPitjor() {
        if (ranking.isEmpty()) throw new ExcepcioEstadistiquesBuides("No hi ha puntuacions registrades");
        String jugador = ranking.lastEntry().getValue().iterator().next();
        int puntuacio = puntuacions.get(jugador);
        return new AbstractMap.SimpleEntry<>(jugador, puntuacio);
    }

    public float obtenirMitja() {
        if (puntuacions.isEmpty()) return 0f;
        return (float) puntuacioTotal / puntuacions.size();
    }

    public Map<String, Integer> getPuntuacions() {
        return puntuacions;
    }

    public int getPuntuacioTotal() {
        return puntuacioTotal;
    }
}
