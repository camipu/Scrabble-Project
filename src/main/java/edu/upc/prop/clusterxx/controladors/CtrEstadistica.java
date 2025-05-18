package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Estadistiques;
import edu.upc.prop.clusterxx.persistencia.CtrlPersistencia;

import java.io.IOException;
import java.util.Map;
/**
 * Classe CtrEstadistica
 *
 * Controlador d'accés a les estadístiques globals del sistema.
 */
public class CtrEstadistica {
    private static CtrEstadistica instance = null;
    private Estadistiques estadistiques;

    /**
     * Retorna la instància única del controlador d’estadístiques i si encara no existeix, s’inicialitza.
     *
     * @return Instància única de CtrEstadistica
     */
    public static CtrEstadistica getInstance() {
        if (instance == null) {
            instance = new CtrEstadistica();
        }
        return instance;
    }

    private CtrEstadistica() {
        estadistiques = CtrlPersistencia.carregarEstadistiques(); // Carrega des de fitxer
    }

    public void desarEstadistiques() {
        try {
            CtrlPersistencia.guardarEstadistiques(estadistiques);
        } catch (IOException e) {
            System.err.println("Error guardant estadístiques: " + e.getMessage());
        }
    }

    public void carregarEstadistiques() {
        this.estadistiques = CtrlPersistencia.carregarEstadistiques();
    }

    /**
     * Afegeix una puntuació al jugador entrat per paràmetre i actualitza les estadístiques globals.
     *
     * @param puntuacio Puntuació a afegir al jugador
     * @param jugador Nom del jugador
     */
    public void afegirPuntuacio(String jugador, int puntuacio) {
        estadistiques.afegirPuntuacio(puntuacio, jugador);
    }

    /**
     * Retira una quantitat de punts a un jugador i actualitza les estadístiques globals.
     *
     * @param jugador Nom del jugador
     * @param punts Quantitat de punts a retirar
     */
    public void retirarPuntuacio(String jugador, int punts) {
        estadistiques.retirarPuntuacio(jugador, punts);
    }

    /**
     * Elimina un determinat jugador de totes les estadístiques i actualitza les estadístiques globals.
     *
     * @param jugador Nom del jugador al que es vol eliminar
     */
    public void eliminarJugador(String jugador) {
        estadistiques.eliminarJugador(jugador);
    }

    /**
     * Retorna el jugador con la puntuacio mes alta.
     *
     * @return Una entrada (nom del jugador y puntuació) del millor jugador.
     */
    public String obtenirMillorJugador() {
        return estadistiques.obtenirMillor().getKey();
    }

    /**
     * Retorna el jugador con la puntuació mes baixa.
     *
     * @return Una entrada (nom del jugador y puntuació) del pitjor jugador.
     */
    public String obtenirPitjorJugador() {
        return estadistiques.obtenirPitjor().getKey();
    }

    /**
     * Retorna la puntuacio total dels jugadors
     *
     * @return La suma de totes les puntuacions
     */
    public int obtenirPuntuacioTotal() {
        return estadistiques.getPuntuacioTotal();
    }

    /**
     * Retorna la puntuació mes alta.
     *
     * @return La puntuació mes alta.
     */
    public int obtenirPuntuacioMaxima() {
        return estadistiques.obtenirMillor().getValue();
    }

    /**
     * Retorna la puntuació més baixa.
     *
     * @return La puntuació més baixa.
     */
    public int obtenirPuntuacioMinima() {
        return estadistiques.obtenirPitjor().getValue();
    }

    /**
     * Retorna la puntuació mitjana de tots els jugadors
     *
     * @return La puntuació total entre el nombre de jugadors.
     */
    public float obtenirPuntuacioMitjana() {
        return estadistiques.obtenirMitja();
    }

    /**
     * Retorna totes les puntuacions registrades
     *
     * @return Map de les puntuacions
     */
    public Map<String, Integer> obtenirPuntuacions() {
        return estadistiques.getPuntuacions();
    }
}
