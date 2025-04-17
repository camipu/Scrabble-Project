package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Estadistiques;

import java.util.AbstractMap;
import java.util.PriorityQueue;

public class CtrEstadistica {
    private static CtrEstadistica instance = null;
    private Estadistiques estadistiques;

    public static CtrEstadistica getInstance() {
        if (instance == null) {
            instance = new CtrEstadistica();
        }
        return instance;
    }

    private CtrEstadistica() {
        estadistiques = new Estadistiques();
    }

    // Agregar una puntuación para un jugador???
    public void afegirPuntuacio(String jugador, int puntuacio) {
        estadistiques.afegirPuntuacio(puntuacio, jugador);
    }

    // Obtener el jugador con la puntuación más alta
    public AbstractMap.SimpleEntry<String, Integer> obtenirMillorPuntuacio() {
        return estadistiques.getPuntuacioMaxima();
    }

    // Obtener la puntuación total
    public int obtenirPuntuacioTotal() {
        return estadistiques.getPuntuacioTotal();
    }

    // Obtener la puntuación mínima
    public int obtenirPuntuacioMinima() {
        return estadistiques.getPuntuacioMinima();
    }

    // Obtener la puntuación media
    public float obtenirPuntuacioMitjana() {
        return estadistiques.getPuntuacioMitjana();
    }

    // Obtener la lista de puntuaciones
    public PriorityQueue<AbstractMap.SimpleEntry<String, Integer>> obtenirPuntuacions() {
        return estadistiques.getPuntuacions();
    }

}
