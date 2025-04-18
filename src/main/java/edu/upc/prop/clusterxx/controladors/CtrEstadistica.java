package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Estadistiques;

import java.util.Map;

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
        estadistiques = Estadistiques.getInstance();
    }

    public void afegirPuntuacio(String jugador, int puntuacio) {
        estadistiques.afegirPuntuacio(puntuacio, jugador);
    }

    public void retirarPuntuacio(String jugador, int punts) {
        estadistiques.retirarPuntuacio(jugador, punts);
    }


    public void eliminarJugador(String jugador) {
        estadistiques.eliminarJugador(jugador);
    }

    public String obtenirMillorJugador() {
        return estadistiques.obtenirMillor().getKey();
    }

    public String obtenirPitjorJugador() {
        return estadistiques.obtenirPitjor().getKey();
    }

    public int obtenirPuntuacioTotal() {
        return estadistiques.getPuntuacioTotal();
    }

    public int obtenirPuntuacioMaxima() {
        return estadistiques.obtenirMillor().getValue();
    }

    public int obtenirPuntuacioMinima() {
        return estadistiques.obtenirPitjor().getValue();
    }

    public float obtenirPuntuacioMitjana() {
        return estadistiques.obtenirMitja();
    }

    public Map<String, Integer> obtenirPuntuacions() {
        return estadistiques.getPuntuacions();
    }
}
