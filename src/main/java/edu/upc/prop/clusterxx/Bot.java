package edu.upc.prop.clusterxx;

import java.util.*;
import edu.upc.prop.clusterxx.Taulell;


public class Bot extends Jugador {
    private int nivellDificultat; // 1: fàcil, 2: mitjà, 3: difícil

    /**
     * Constructor amb nom, sac i nivell de dificultat
     * @param nom Nom del bot
     * @param sac Sac de fitxes
     * @param nivellDificultat Nivell de dificultat del bot (1-3)
     */
    public Bot(String nom, Faristol faristol, int nivellDificultat) {
        super(nom, faristol);

        if (nivellDificultat < 1 || nivellDificultat > 3) {
            throw new IllegalArgumentException("Nivell de dificultat ha de ser entre 1 i 3.");        }
        this.nivellDificultat = nivellDificultat;
    }

    /**
     * Calcula una jugada segons les fitxes del faristol u la dificultat
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @return Un objecte Jugada amb la millor jugada trobada
     */
    public Jugada calcularJugada(Taulell taulell, DAWG dawg) {
        List<Jugada> jugadesPossibles = generarJugadesPossibles(taulell, dawg);

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
    private List<Jugada> generarJugadesPossibles(Taulell taulell, DAWG dawg) {
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
                        jugades.addAll(generarJugadesDesdePos(taulell, fila, columna, horitzontal, dawg));
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
    private List<Jugada> generarJugadesDesdePos(Taulell taulell, int fila, int columna, boolean horitzontal, DAWG dawg) {
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

        // Construir sufix correctament
        // StringBuilder sufix = new StringBuilder();
        // int offsetSufix = 1;
        // while (true) {
        //     int f = horitzontal ? fila : fila + offsetSufix;
        //     int c = horitzontal ? columna + offsetSufix : columna;
        //     if (f >= mida || c >= mida || caselles[f][c].esBuida()) break;
        //     sufix.append(caselles[f][c].obtenirFitxa().obtenirLletra());
        //     offsetSufix++;
        // }

        String paraulaInicial = prefix.toString();
        
        // Generar les paraules possibles
        return generarParaules(paraulaInicial, this.obtenirFaristol().obtenirFitxes(), new ArrayList<>(), fila, columna, horitzontal, taulell, dawg);
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
    private List<Jugada> generarParaules(String paraulaActual, List<Fitxa> fitxesRestants, List<Fitxa> fitxesUtilitzades,
                                            int fila, int columna, boolean horitzontal, Taulell taulell, DAWG dawg) {
            List<Jugada> resultats = new ArrayList<>();
            int mida = taulell.getSize();
            int pos = paraulaActual.length();
            int f = horitzontal ? fila : fila + pos;
            int c = horitzontal ? columna + pos : columna;

            if (f >= mida || c >= mida) return resultats;

            Casella casella = taulell.getTaulell()[f][c];
            if (!casella.esBuida()) {
                String novaParaula = paraulaActual + casella.obtenirFitxa().obtenirLletra();
                
                if (dawg.esPrefix(novaParaula)) {
                    Jugada novaJugada = construirJugada(novaParaula, fila, columna, horitzontal, fitxesUtilitzades, taulell, dawg);
                    if (taulell.validarJugada(novaJugada, dawg)) resultats.add(novaJugada);
                    resultats.addAll(generarParaules(novaParaula, fitxesRestants, fitxesUtilitzades, fila, columna, horitzontal, taulell, dawg));
                }
            }
            else {
                for (int i = 0; i < fitxesRestants.size(); i++) {
                    Fitxa fitxa = fitxesRestants.get(i);
                    String novaParaula = paraulaActual + fitxa.obtenirLletra();
                    if (!dawg.esPrefix(novaParaula)) continue;

                    List<Fitxa> novesRestants = new ArrayList<>(fitxesRestants);
                    novesRestants.remove(i);

                    List<Fitxa> novesUtilitzades = new ArrayList<>(fitxesUtilitzades);
                    novesUtilitzades.add(fitxa);

                    Jugada novaJugada = construirJugada(novaParaula, fila, columna, horitzontal, fitxesUtilitzades, taulell, dawg);
                    if (taulell.validarJugada(novaJugada, dawg)) resultats.add(novaJugada);

                    resultats.addAll(generarParaules(novaParaula, novesRestants, novesUtilitzades, fila, columna, horitzontal, taulell, dawg));
                }
            }
            return resultats;
        }

    private Jugada construirJugada(String paraula, int fila, int columna, boolean horitzontal,
                                List<Fitxa> fitxesUtilitzades, Taulell taulell, DAWG dawg) {
        List<Casella> casellesJugades = new ArrayList<>();
        int idx = 0;
        int mida = taulell.getSize();

        for (int i = 0; i < paraula.length(); i++) {
            int f = horitzontal ? fila : fila + i;
            int c = horitzontal ? columna + i : columna;
            if (f >= mida || c >= mida) break;
            if (taulell.getTaulell()[f][c].esBuida()) {
                Casella nova = new Casella(f, c, mida);
                nova.colocarFitxa(fitxesUtilitzades.get(idx++));
                casellesJugades.add(nova);
            }
        }

        Jugada jugada = new Jugada(paraula, casellesJugades, 0);
        int puntuacio = taulell.calcularPuntuacioParaula(jugada);
        jugada.setPuntuacio(puntuacio);
        return jugada;
    }
}