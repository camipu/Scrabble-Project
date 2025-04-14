package edu.upc.prop.clusterxx;

import java.util.*;

public class Bot extends Jugador {
    private int nivellDificultat; // 1: fàcil, 2: mitjà, 3: difícil

    /**
     * Constructor amb nom, sac i nivell de dificultat
     * @param nom Nom del bot
     * @param sac Sac de fitxes
     * @param nivellDificultat Nivell de dificultat del bot (1-3)
     */
    public Bot(String nom, int nivellDificultat) {
        super(nom);

        if (nivellDificultat < 1 || nivellDificultat > 3) {
            throw new ExcepcioNivellDificultatInvalid(nivellDificultat);
        }
        this.nivellDificultat = nivellDificultat;
    }

    /**
     * Calcula la millor jugada possible amb les fitxes del faristol
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @return Un objecte Jugada amb la millor jugada trobada
     */
    public Jugada calcularJugada(Taulell taulell, DAWG dawg) {
        List<Jugada> jugadesPossibles = generarJugadesPossibles(taulell, dawg);

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
        Casella[][] caselles = taulell.getTaulell();
        boolean taulerBuit = taulell.esBuit();

        // Per cada direcció (horitzontal i vertical)
        for (int direccio = 0; direccio < 2; direccio++) {
            boolean horitzontal = direccio == 0;
            // Per cada posició del tauler
            for (int fila = 0; fila < mida; fila++) {
                for (int columna = 0; columna < mida; columna++) {
                    if (taulerBuit && (fila != mida/2 || columna != mida/2)) continue;
                    if (!caselles[fila][columna].esBuida()) continue;
                    if (!taulerBuit && !hiHaFitxaAdjacent(caselles, fila, columna, mida)) continue;
                    jugades.addAll(generarJugadesDesdePos(fila, columna, horitzontal, taulell, dawg));
                }
            }
        }
        return jugades;
    }

    /**
     * Comprova si hi ha alguna fitxa adjacent a la posició donada
     * @param caselles Matriu de caselles del tauler
     * @param fila Fila de la posició
     * @param columna Columna de la posició
     * @param mida Mida del tauler
     * @return True si hi ha alguna fitxa adjacent, false altrament
     */
    private boolean hiHaFitxaAdjacent(Casella[][] caselles, int fila, int columna, int mida) {
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int nf = fila + d[0], nc = columna + d[1];
            if (nf >= 0 && nf < mida && nc >= 0 && nc < mida && !caselles[nf][nc].esBuida()) return true;
        }
        return false;
    }

    /**
     * Genera totes les jugades possibles des d'una posició inicial,
     * incloent lletres ja presents al taulell i comprovant que la jugada
     * connecti amb altres paraules (excepte si és la primera jugada),
     * i que la paraula principal i les paraules perpendiculars siguin vàlides.
     */
    private List<Jugada> generarJugadesDesdePos(int fila, int columna, boolean horitzontal, Taulell taulell, DAWG dawg) {
        List<Jugada> jugades = new ArrayList<>();
        Casella[][] caselles = taulell.getTaulell();
        int mida = taulell.getSize();
        boolean taulerBuit = taulell.esBuit();

        // Construir prefix previ (cap enrere)
        StringBuilder prefix = new StringBuilder();
        int offset = 1;
        while (true) {
            int f = horitzontal ? fila : fila - offset;
            int c = horitzontal ? columna - offset : columna;
            if (f < 0 || c < 0 || caselles[f][c].esBuida()) break;
            prefix.insert(0, caselles[f][c].obtenirFitxa().obtenirLletra());
            offset++;
        }

        // Construir sufix correctament
        StringBuilder sufix = new StringBuilder();
        int offsetSufix = 1;
        while (true) {
            int f = horitzontal ? fila : fila + offsetSufix;
            int c = horitzontal ? columna + offsetSufix : columna;
            if (f >= mida || c >= mida || caselles[f][c].esBuida()) break;
            sufix.append(caselles[f][c].obtenirFitxa().obtenirLletra());
            offsetSufix++;
        }

        String paraulaInicial = prefix.toString();
        List<Jugada> jugadesGenerades = new ArrayList<>();
        
        // Generar les paraules possibles
        generarParaulesRecursiu(paraulaInicial.toString(), this.obtenirFaristol().obtenirFitxes(), new ArrayList<>(), fila, columna, horitzontal, jugadesGenerades, taulell, dawg);

        for (Jugada j : jugadesGenerades) {
            // Construir la paraula real que es formarà al taulell
            String paraulaFinal = prefix.toString() + j.getParaula() + sufix.toString();

            // Verificar si és una paraula vàlida en el diccionari
            if (dawg.conteParaula(paraulaFinal) &&
                (taulerBuit || tocaCasellaOcupada(j, taulell)) &&
                taulell.paraulesPerpendicularesValides(j, dawg)) {
                jugades.add(j);
            }
        }

        return jugades;
    }

    /**
     * Comprova si una jugada toca alguna casella ja ocupada (per fer-la vàlida en partides no buides)
     * @param jugada Jugada proposada
     * @param taulell El taulell actual
     * @return True si alguna lletra de la jugada toca una lletra ja existent
     */
    private boolean tocaCasellaOcupada(Jugada jugada, Taulell taulell) {
        Casella[][] caselles = taulell.getTaulell();
        int fila = jugada.getFila();
        int columna = jugada.getColumna();
        boolean horitzontal = jugada.esHoritzontal();
        int mida = taulell.getSize();
        
        // Comprovar si alguna casella de la paraula toca una casella ocupada
        for (int i = 0; i < jugada.getParaula().length(); i++) {
            int f = horitzontal ? fila : fila + i;
            int c = horitzontal ? columna + i : columna;
            
            // Comprovar les quatre direccions adjacents
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] dir : dirs) {
                int nf = f + dir[0];
                int nc = c + dir[1];
                if (nf >= 0 && nf < mida && nc >= 0 && nc < mida && !caselles[nf][nc].esBuida()) {
                    return true;
                }
            }
            
            // També cal verificar si estem connectant amb alguna casella ja ocupada
            if (f >= 0 && f < mida && c >= 0 && c < mida && !caselles[f][c].esBuida()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera recursivament totes les paraules possibles amb les fitxes disponibles
     * @param paraulaActual Paraula que s'està formant actualment
     * @param fitxesRestants Fitxes que encara no s'han utilitzat
     * @param fitxesUtilitzades Fitxes que s'han utilitzat per formar la paraula actual
     * @param fila Fila inicial
     * @param columna Columna inicial
     * @param horitzontal Direcció de la paraula
     * @param jugades Llista on s'afegiran les jugades vàlides trobades
     * @param taulell El taulell actual
     * @param dawg El DAWG amb les paraules vàlides
     */
    private void generarParaulesRecursiu(String paraulaActual, List<Fitxa> fitxesRestants, List<Fitxa> fitxesUtilitzades,
                                         int fila, int columna, boolean horitzontal, List<Jugada> jugades,
                                         Taulell taulell, DAWG dawg) {
        Casella[][] caselles = taulell.getTaulell();
        int mida = taulell.getSize();
        int pos = paraulaActual.length();

        int filaActual = horitzontal ? fila : fila + pos;
        int columnaActual = horitzontal ? columna + pos : columna;

        // Si sortim fora del taulell, parem
        if (filaActual >= mida || columnaActual >= mida) return;

        Casella casella = caselles[filaActual][columnaActual];

        if (!casella.esBuida()) {
            // Ja hi ha una lletra, cal que coincideixi amb el que estem formant
            String lletra = casella.obtenirFitxa().obtenirLletra();
            String novaParaula = paraulaActual + lletra;

            if (!dawg.esPrefix(novaParaula)) return;

            generarParaulesRecursiu(novaParaula, fitxesRestants, fitxesUtilitzades,
                                     fila, columna, horitzontal, jugades, taulell, dawg);

        } else {
            // Per cada fitxa disponible, prova de col·locar-la
            for (int i = 0; i < fitxesRestants.size(); i++) {
                Fitxa fitxa = fitxesRestants.get(i);
                String lletra = String.valueOf(fitxa.obtenirLletra());
                String novaParaula = paraulaActual + lletra;

                if (!dawg.esPrefix(novaParaula)) continue;

                List<Fitxa> novesRestants = new ArrayList<>(fitxesRestants);
                novesRestants.remove(i);

                List<Fitxa> novesUtilitzades = new ArrayList<>(fitxesUtilitzades);
                novesUtilitzades.add(fitxa);

                if (novaParaula.length() > 2 && dawg.conteParaula(novaParaula)) {
                    int puntuacio = taulell.calcularPuntuacioParaula(novaParaula, novesUtilitzades, fila, columna, horitzontal);
                    jugades.add(new Jugada(novaParaula, new ArrayList<>(novesUtilitzades), fila, columna, horitzontal, puntuacio));
                }

                generarParaulesRecursiu(novaParaula, novesRestants, novesUtilitzades,
                                         fila, columna, horitzontal, jugades, taulell, dawg);
            }
        }
    }

    /**
     * Executa el torn del Bot automàticament
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @param sac El sac de fitxes per reomplir el faristol
     * @return True si s'ha realitzat una jugada, false si no s'ha pogut
     */
     public boolean executarTorn(Taulell taulell, DAWG dawg, Sac sac) {
        System.out.println("== TORN DEL BOT: " + nom + " ==");
        Jugada millor = calcularMillorJugada(taulell, dawg);
        if (millor == null) return false;

        System.out.println("El bot col·loca: " + millor.getParaula());

        List<Fitxa> fitxes = millor.getFitxesUtilitzades();
        int fila = millor.getFila(), columna = millor.getColumna();
        boolean horitzontal = millor.esHoritzontal();

        int j = 0; // Índex per a fitxes del faristol
        for (int i = 0; i < millor.getParaula().length(); i++) {
            int filaActual = horitzontal ? fila : fila + i;
            int columnaActual = horitzontal ? columna + i : columna;
            Casella casella = taulell.getTaulell()[filaActual][columnaActual];

            if (casella.esBuida()) {
                Fitxa fitxa = fitxes.get(j++); // Només col·loquem si és buida
                taulell.colocarFitxa(fitxa, filaActual, columnaActual);
                eliminarFitxa(fitxa);
            }
        }

        afegirPunts(millor.getPuntuacio());
        return true;
    }
}