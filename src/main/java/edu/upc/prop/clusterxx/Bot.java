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
    public Bot(String nom, Sac sac, int nivellDificultat) {
        super(nom, sac);
        if (nivellDificultat >= 1 && nivellDificultat <= 3) {
            this.nivellDificultat = nivellDificultat;
        } else {
            this.nivellDificultat = 3; // Valor per defecte si el nivell no és vàlid
        }
    }
    
    /**
     * Calcula la millor jugada possible amb les fitxes del faristol
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @return Un objecte Jugada amb la millor jugada trobada
     */
    public Jugada calcularMillorJugada(Taulell taulell, DAWG dawg) {
        // Obtenim totes les jugades possibles
        List<Jugada> jugadesPossibles = generarJugadesPossibles(taulell, dawg);
        
        if (jugadesPossibles.isEmpty()) {
            System.out.println("No s'ha trobat cap jugada possible.");
            return null;
        }
        
        // Ordenem les jugades per puntuació
        Collections.sort(jugadesPossibles, new Comparator<Jugada>() {
            @Override
            public int compare(Jugada j1, Jugada j2) {
                return Integer.compare(j2.getPuntuacio(), j1.getPuntuacio());
            }
        });
        
        // Segons el nivell de dificultat, escollim una jugada millor o pitjor
        switch (nivellDificultat) {
            case 1: // Fàcil: escull una jugada aleatòria entre les 70% pitjors
                int mida = jugadesPossibles.size();
                int limitInferior = (int) (mida * 0.3); // 30% millors jugades
                int indiceAleatori = limitInferior + new Random().nextInt(mida - limitInferior);
                return jugadesPossibles.get(Math.min(indiceAleatori, mida - 1));
            
            case 2: // Mitjà: escull una jugada aleatòria entre les 50% millors
                mida = jugadesPossibles.size();
                int limiteMig = mida / 2;
                indiceAleatori = new Random().nextInt(limiteMig);
                return jugadesPossibles.get(indiceAleatori);
                
            case 3: // Difícil: escull sempre la millor jugada
            default:
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
        
        // Si el tauler està buit, la primera jugada ha de ser al centre
        boolean taulerBuit = taulell.esBuit();
        
        // Per cada direcció (horitzontal i vertical)
        for (int direccio = 0; direccio < 2; direccio++) {
            boolean horitzontal = direccio == 0;
            
            // Per cada posició del tauler
            for (int fila = 0; fila < mida; fila++) {
                for (int columna = 0; columna < mida; columna++) {
                    // Si el tauler està buit, només considerem jugades al centre
                    if (taulerBuit && (fila != mida/2 || columna != mida/2)) {
                        continue;
                    }
                    
                    // Si la casella no està buida, no podem començar una paraula aquí
                    if (!caselles[fila][columna].esBuida()) {
                        continue;
                    }
                    
                    // Comprovem si hi ha alguna fitxa adjacent (només si el tauler no està buit)
                    if (!taulerBuit && !hiHaFitxaAdjacent(caselles, fila, columna, mida)) {
                        continue;
                    }
                    
                    // Generem jugades que comencen en aquesta posició
                    List<Jugada> jugadesDesdePos = generarJugadesDesdePos(fila, columna, horitzontal, taulell, dawg);
                    jugades.addAll(jugadesDesdePos);
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
        // Comprovem les quatre direccions adjacents
        int[][] direccions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : direccions) {
            int novaFila = fila + dir[0];
            int novaColumna = columna + dir[1];
            
            if (novaFila >= 0 && novaFila < mida && novaColumna >= 0 && novaColumna < mida) {
                if (!caselles[novaFila][novaColumna].esBuida()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Genera totes les jugades possibles des d'una posició inicial
     * @param fila Fila inicial
     * @param columna Columna inicial
     * @param horitzontal Direcció de la paraula (true: horitzontal, false: vertical)
     * @param taulell El taulell actual
     * @param dawg El DAWG amb les paraules vàlides
     * @return Llista de jugades possibles des d'aquesta posició
     */
    private List<Jugada> generarJugadesDesdePos(int fila, int columna, boolean horitzontal, 
                                               Taulell taulell, DAWG dawg) {
        List<Jugada> jugades = new ArrayList<>();
        
        // Per simplificar, generem paraules només amb les fitxes disponibles (sense utilitzar les del tauler)
        generarParaulesRecursiu("", faristol.obtenirFitxes(), new ArrayList<>(), fila, columna, 
                               horitzontal, jugades, taulell, dawg);
        
        return jugades;
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
    private void generarParaulesRecursiu(String paraulaActual, List<Fitxa> fitxesRestants, 
                                       List<Fitxa> fitxesUtilitzades, int fila, int columna, 
                                       boolean horitzontal, List<Jugada> jugades,
                                       Taulell taulell, DAWG dawg) {
        // Si la paraula actual és vàlida (té més de 2 lletres), l'afegim com a jugada
        if (paraulaActual.length() > 2 && dawg.conteParaula(paraulaActual)) {
            // Calculem la puntuació de la paraula
            int puntuacio = calcularPuntuacioParaula(paraulaActual, fitxesUtilitzades, fila, columna, horitzontal, taulell);
            
            // Creem la jugada amb les dades calculades
            Jugada novaJugada = new Jugada(paraulaActual, fitxesUtilitzades, fila, columna, horitzontal, puntuacio);
            jugades.add(novaJugada);
        }
        
        // Si no queden fitxes per utilitzar, acabem
        if (fitxesRestants.isEmpty()) {
            return;
        }
        
        // Provem d'afegir cada fitxa restant a la paraula
        for (int i = 0; i < fitxesRestants.size(); i++) {
            Fitxa fitxaActual = fitxesRestants.get(i);
            
            // Afegim la fitxa actual a la paraula
            String lletra = String.valueOf(fitxaActual.obtenirLletra());
            String novaParaula = paraulaActual + lletra;
            
            // Si la paraula no pot ser el prefix de cap paraula vàlida, no continuem
            // Aquí utilitzaríem una funció específica del DAWG per verificar prefixos
            if (!dawg.esPrefix(novaParaula)) {
                continue;
            }
            
            // Creem noves llistes de fitxes per continuar la recursió
            List<Fitxa> novesFitxesRestants = new ArrayList<>(fitxesRestants);
            novesFitxesRestants.remove(i);
            
            List<Fitxa> novesFitxesUtilitzades = new ArrayList<>(fitxesUtilitzades);
            novesFitxesUtilitzades.add(fitxaActual);
            
            // Continuem la recursió amb la nova paraula
            generarParaulesRecursiu(novaParaula, novesFitxesRestants, novesFitxesUtilitzades, 
                                   fila, columna, horitzontal, jugades, taulell, dawg);
        }
    }
    
    /**
     * Calcula la puntuació d'una paraula segons les fitxes utilitzades i la posició al tauler
     * @param paraula Paraula formada
     * @param fitxesUtilitzades Fitxes utilitzades per formar la paraula
     * @param fila Fila inicial
     * @param columna Columna inicial
     * @param horitzontal Direcció de la paraula
     * @param taulell El taulell actual
     * @return Puntuació total de la jugada
     */
    private int calcularPuntuacioParaula(String paraula, List<Fitxa> fitxesUtilitzades, 
                                        int fila, int columna, boolean horitzontal, Taulell taulell) {
        Casella[][] caselles = taulell.getTaulell();
        int mida = taulell.getSize();
        
        int puntuacioTotal = 0;
        int multiplicadorParaula = 1;
        
        for (int i = 0; i < paraula.length(); i++) {
            int filaActual = horitzontal ? fila : fila + i;
            int columnaActual = horitzontal ? columna + i : columna;
            
            // Si estem fora del tauler, no és una jugada vàlida
            if (filaActual >= mida || columnaActual >= mida) {
                return 0;
            }
            
            Casella casellaActual = caselles[filaActual][columnaActual];
            Fitxa fitxaActual = fitxesUtilitzades.get(i);
            
            // Calculem els punts de la lletra segons la casella
            int puntsLletra = fitxaActual.obtenirPunts();
            
            if (casellaActual.obtenirEstrategia() instanceof EstrategiaMultiplicadorLletra) {
                EstrategiaMultiplicadorLletra estrategia = (EstrategiaMultiplicadorLletra) casellaActual.obtenirEstrategia();
                puntsLletra *= estrategia.obtenirMultiplicador();
            } else if (casellaActual.obtenirEstrategia() instanceof EstrategiaMultiplicadorParaula) {
                EstrategiaMultiplicadorParaula estrategia = (EstrategiaMultiplicadorParaula) casellaActual.obtenirEstrategia();
                multiplicadorParaula *= estrategia.obtenirMultiplicador();
            }
            
            puntuacioTotal += puntsLletra;
        }
        
        // Apliquem el multiplicador de paraula
        puntuacioTotal *= multiplicadorParaula;
        
        // Bonus per utilitzar totes les fitxes (7)
        if (fitxesUtilitzades.size() == 7) {
            puntuacioTotal += 50;
        }
        
        return puntuacioTotal;
    }
    
    /**
     * Executa el torn del Bot automàticament
     * @param taulell El taulell actual del joc
     * @param dawg El DAWG amb les paraules vàlides
     * @param sac El sac de fitxes per reomplir el faristol
     * @return True si s'ha realitzat una jugada, false si no s'ha pogut
     */
    public boolean executarTorn(Taulell taulell, DAWG dawg, Sac sac) {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "== TORN DEL BOT: " + nom + " ==" + Colors.RESET);
        
        // Calculem la millor jugada
        Jugada millorJugada = calcularMillorJugada(taulell, dawg);
        
        if (millorJugada == null) {
            System.out.println("El bot " + nom + " no ha trobat cap jugada possible. Passa torn.");
            return false;
        }
        
        // Executem la jugada
        System.out.println("El bot " + nom + " col·loca la paraula: " + millorJugada.getParaula());
        System.out.println("Posició: Fila " + millorJugada.getFila() + ", Columna " + millorJugada.getColumna() + 
                         ", Direcció: " + (millorJugada.esHoritzontal() ? "horitzontal" : "vertical"));
        System.out.println("Puntuació obtinguda: " + millorJugada.getPuntuacio());
        
        // Col·loquem les fitxes al tauler
        List<Fitxa> fitxes = millorJugada.getFitxesUtilitzades();
        int fila = millorJugada.getFila();
        int columna = millorJugada.getColumna();
        boolean horitzontal = millorJugada.esHoritzontal();
        
        for (int i = 0; i < fitxes.size(); i++) {
            Fitxa fitxa = fitxes.get(i);
            int filaActual = horitzontal ? fila : fila + i;
            int columnaActual = horitzontal ? columna + i : columna;
            
            // Col·loquem la fitxa i l'eliminem del faristol
            taulell.colocarFitxa(fitxa, filaActual, columnaActual);
            eliminarFitxa(fitxa);
        }
        
        // Afegim els punts al jugador
        afegirPunts(millorJugada.getPuntuacio());
        
        // Reomplim el faristol
        // FALTA

        return true;
    }
}