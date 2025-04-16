package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CtrlJugadaBot {
    private static CtrlJugadaBot instance = null;


    public static CtrlJugadaBot getInstance() {
        if (instance == null) {
            instance = new CtrlJugadaBot();
        }
        return instance;
    }

    private CtrlJugadaBot() {
    }

    /**
     * Calcula una jugada segons l'estat del taulell i la dificultat
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @return Un objecte Jugada amb la millor jugada trobada
     */
    public Jugada calcularJugada(Taulell taulell, DAWG dawg, int nivellDificultat, Bot bot) {
        List<Jugada> jugadesPossibles = generarJugadesPossibles(taulell, dawg, bot);

        // PASSAR Retornar jugada amb caselles buida!
        if (jugadesPossibles.isEmpty()) return null;

        jugadesPossibles.sort((j1, j2) -> Integer.compare(j2.getPuntuacio(), j1.getPuntuacio()));

        int mida = jugadesPossibles.size();
        switch (nivellDificultat) {
            case 1: // Fàcil: escull la pitjor jugada
                return jugadesPossibles.get(mida-1);
            case 2: // Mitjà: escull una jugada aleatòria
                int indexAleatori = new Random().nextInt(mida);
                return jugadesPossibles.get(indexAleatori);
            default: // Difícil: escull sempre la millor jugada
                return jugadesPossibles.get(0);
        }
    }

    /**
     * Genera totes les jugades possibles amb les fitxes del faristol
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @return Llista de jugades possibles
     */
    private List<Jugada> generarJugadesPossibles(Taulell taulell, DAWG dawg, Bot bot) {
        List<Jugada> jugades = new ArrayList<>();
        int mida = taulell.getSize();
        boolean taulerBuit = taulell.esBuit();

        // Per cada direcció (horitzontal i vertical)
        for (int direccio = 0; direccio < 2; direccio++) {
            boolean horitzontal = (direccio == 0);
            // Per cada posició del tauler
            for (int fila = 0; fila < mida; fila++) {
                for (int columna = 0; columna < mida; columna++) {
                    // Si taulerBuit (primera jugada) ha de ser al mig
                    if (!taulerBuit || fila == mida/2 || columna == mida/2) {
                        jugades.addAll(generarJugadesDesdePos(taulell, fila, columna, horitzontal, dawg, bot.obtenirFaristol()));
                    }
                }
            }
        }
        return jugades;
    }

    /**
     * Genera totes les jugades possibles des d'una posició inicial,
     * incloent lletres ja presents al taulell i comprovant que la jugada
     * connecti amb altres paraules (excepte si és la primera jugada),
     * i que la paraula principal i les paraules perpendiculars siguin vàlides.
     */
    private List<Jugada> generarJugadesDesdePos(Taulell taulell, int fila, int columna, boolean horitzontal, DAWG dawg, Faristol faristol) {
        // Construir prefix previ (cap enrere)
        StringBuilder prefix = new StringBuilder();
        int offset = 1;
        while (true) {
            int f = horitzontal ? fila : fila - offset;
            int c = horitzontal ? columna - offset : columna;
            if (f < 0 || c < 0 || taulell.getTaulell()[f][c].esBuida()) break;
            prefix.insert(0, taulell.getTaulell()[f][c].obtenirFitxa().obtenirLletra());
            offset++;
        }

        String paraulaInicial = prefix.toString();

        // Generar les paraules possibles
        return generarParaules(paraulaInicial, faristol.obtenirFitxes(), new ArrayList<>(),dawg.getArrel(), fila, columna, horitzontal, taulell, dawg);
    }

    /**
     * Genera recursivament totes les paraules possibles amb les fitxes disponibles
     * @param paraulaActual Paraula que s'està formant actualment
     * @param fitxesRestants Fitxes que encara no s'han utilitzat
     * @param fitxesUtilitzades Fitxes que s'han utilitzat per formar la paraula actual
     * @param fila Fila inicial
     * @param columna Columna inicial
     * @param horitzontal Direcció de la paraula
     * @param taulell El taulell actual
     * @param dawg El DAWG amb les paraules vàlides
     */
    private List<Jugada> generarParaules(String prefix, List<Fitxa> fitxesRestants,
                                         List<Casella> casellesJugades, DAWG.Node nodeActual, int fila, int columna,
                                         boolean horitzontal, Taulell taulell, DAWG dawg) {
        List<Jugada> resultats = new ArrayList<>();
        int mida = taulell.getSize();
        int offset = prefix.length();
        int f = horitzontal ? fila : fila + offset;
        int c = horitzontal ? columna + offset : columna;

        if (f >= mida || c >= mida) return resultats;

        Casella casellaActual = taulell.getTaulell()[f][c];
        if (!casellaActual.esBuida()) {
            String token = casellaActual.obtenirFitxa().obtenirLletra();
            DAWG.Node nodeSeguent = nodeActual.getFill(token);
            if (nodeSeguent == null) return resultats;

            String novaParaula = prefix + token;

            Jugada novaJugada = taulell.construirJugadaBot(novaParaula, casellesJugades, dawg);
            if (novaJugada.getJugadaValida()) resultats.add(novaJugada);

            resultats.addAll(generarParaules(novaParaula, fitxesRestants, casellesJugades, nodeSeguent, fila, columna, horitzontal, taulell, dawg));
        }
        else {
            for (int i = 0; i < fitxesRestants.size(); i++) {
                Fitxa fitxa = fitxesRestants.get(i);
                String token = fitxa.obtenirLletra();
                DAWG.Node nodeSeguent = nodeActual.getFill(token);
                if (nodeSeguent == null) continue;

                String novaParaula = prefix + token;

                // Provo de afegir una fitxa
                fitxesRestants.remove(i);
                Casella novaCasella = new Casella(f, c, taulell.getSize());
                novaCasella.colocarFitxa(fitxa);
                casellesJugades.add(novaCasella);

                Jugada novaJugada = taulell.construirJugadaBot(novaParaula, casellesJugades, dawg);
                if (novaJugada.getJugadaValida()) resultats.add(novaJugada);

                resultats.addAll(generarParaules(novaParaula, fitxesRestants, casellesJugades, nodeSeguent, fila, columna, horitzontal, taulell, dawg));

                // Desfaig el canvi
                casellesJugades.remove(novaCasella);
                fitxesRestants.add(i, fitxa);
            }
        }
        return resultats;
    }
}