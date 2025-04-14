package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Joc;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;

import java.util.List;

public class CtrDomini {
    private Taulell taulell;          // El tablero de Scrabble
    private List<Jugador> jugadors;  // Lista de jugadores
    private int jugadorActual; // Índice del jugador que está en turno
    private static CtrDomini instance = null;


    public static CtrDomini getInstance() {
        if (instance == null) {
            instance = new CtrDomini();
        }
        return instance;
    }

    public void iniciarJoc(int numJugadors, String idioma, String[] noms) {
        Joc joc = new Joc(numJugadors, idioma, noms);
        taulell = joc.obtenirTaulell();
    }

}

