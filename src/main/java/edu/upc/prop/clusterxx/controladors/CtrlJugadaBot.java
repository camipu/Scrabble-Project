package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.DAWG;
import edu.upc.prop.clusterxx.Jugada;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Casella;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador que gestiona la generació de jugades del bot de Scrabble.
 * Calcula la millor jugada que pot fer un bot segons el nivell de dificultat.
 */
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
     * Calcula una jugada vàlida segons les fitxes disponibles i el nivell de dificultat especificat.
     * @param taulell Estat actual del taulell.
     * @param dawg Estructura de dades amb les paraules vàlides (DAWG).
     * @param nivellDificultat Nivell de dificultat (1: fàcil, 2: mitjà, 3: difícil).
     * @param fitxesDisponibles fitxes Instància del jugador Bot.
     * @return La jugada segons el nivell indicat, o null si no hi ha jugades possibles.
     */
    public Jugada calcularJugada(Taulell taulell, DAWG dawg, int nivellDificultat, List<Fitxa> fitxesDisponibles) {
        List<Jugada> jugadesPossibles = generarJugades(taulell, dawg, fitxesDisponibles);

        // Si no hi ha jugades vàlides retorna jugada buida
        if (jugadesPossibles.isEmpty()) {
            return new Jugada("", new ArrayList<>(), -1, false);
        }

        // Ordenem les jugades segons la puntuació
        jugadesPossibles.sort((j1, j2) -> Integer.compare(j2.getPuntuacio(), j1.getPuntuacio()));

        int mida = jugadesPossibles.size();
        switch (nivellDificultat) {
            case 1: // Fàcil: escull la pitjor jugada
                return jugadesPossibles.get(mida-1);
            case 2: // Mitjà: escull una jugada intermèdia
                return jugadesPossibles.get(mida/2);
            default: // Difícil: escull sempre la millor jugada
                return jugadesPossibles.get(0);
        }
    }

    /**
     * Genera totes les jugades vàlides possibles des de qualsevol posició del taulell.
     * @param taulell Estat actual del joc.
     * @param dawg Estructura de dades amb les paraules vàlides (DAWG).
     * @param fitxesDisponibles Fitxes disponibles del bot.
     * @return Llista de jugades vàlides generades.
     */
    private List<Jugada> generarJugades(Taulell taulell, DAWG dawg, List<Fitxa> fitxesDisponibles) {
        List<Jugada> jugades = new ArrayList<>();
        int mida = taulell.getSize();
        boolean taulerBuit = taulell.esBuit();

        // Per cada direcció (horitzontal i vertical)
        for (int direccio = 0; direccio < 2; direccio++) {
            boolean horitzontal = (direccio == 0);
            
            // Per cada posició del tauler
            for (int fila = 0; fila < mida; fila++) {
                for (int columna = 0; columna < mida; columna++) {
                    // Si és la primera jugada (taulell buit), ha de passar pel centre
                    if (!taulerBuit || fila == mida/2 || columna == mida/2) {

                        // Construïm el prefix existent (cap enrere)
                        StringBuilder prefix = new StringBuilder();
                        int offset = 1;
                        while (true) {
                            int f = horitzontal ? fila : fila - offset;
                            int c = horitzontal ? columna - offset : columna;
                            if (f < 0 || c < 0 || taulell.getTaulell()[f][c].esBuida()) break;
                            prefix.insert(0, taulell.getTaulell()[f][c].obtenirFitxa().obtenirLletra());
                            offset++;
                        }
                        
                        // Generem totes les paraules vàlides possibles que comencin amb aquest prefix 
                        String paraulaInicial = prefix.toString();
                        jugades.addAll(generarParaules(paraulaInicial, fitxesDisponibles, new ArrayList<>(), dawg.getArrel(), fila, columna, horitzontal, taulell, dawg));
                    }
                }
            }
        }
        return jugades;
    }

    /**
     * Genera recursivament paraules vàlides a partir d’un prefix i les fitxes restants.
     * @param prefix Prefix actual de paraula.
     * @param fitxesRestants Fitxes disponibles per col·locar.
     * @param casellesJugades Caselles utilitzades fins ara.
     * @param nodeActual Node actual dins del DAWG.
     * @param filaActual Fila de la casella actual.
     * @param columnaActual Columna de la casella actual.
     * @param horitzontal Direcció horitzontal o vertical.
     * @param taulell Estat actual del taulell.
     * @param dawg Diccionari de paraules.
     * @return Llista de jugades vàlides generades.
     */
    private List<Jugada> generarParaules(String prefix, List<Fitxa> fitxesRestants,
                                         List<Casella> casellesJugades, DAWG.Node nodeActual, int filaActual, int columnaActual,
                                         boolean horitzontal, Taulell taulell, DAWG dawg) {
        List<Jugada> resultats = new ArrayList<>();

        // Si surto del taulell retorno els resultats trobats
        int mida = taulell.getSize();
        if (filaActual >= mida || columnaActual >= mida) return resultats;

        // Agafo la casella i miro si està ocupada
        Casella casellaActual = taulell.getTaulell()[filaActual][columnaActual];
        if (!casellaActual.esBuida()) {
            // Si la casella està ocupada 
            // Continuar la paraula amb la fitxa ja existent al taulell
            String token = casellaActual.obtenirFitxa().obtenirLletra();

            // Miro si la paraula formada amb la nova fitxa és vàlida
            DAWG.Node nodeSeguent = nodeActual.getFill(token);
            if (nodeSeguent == null) return resultats;

            // Construiexo el nouPrefix
            String nouPrefix = prefix + token;
            if (nodeSeguent.esFinal()) {
                // Validar i afegir la jugada si és vàlida
                Jugada novaJugada = taulell.construirJugadaBot(nouPrefix, casellesJugades, dawg);
                if (novaJugada.getJugadaValida()) {
                    // Augmento la puntuació 50 si juga totes les fitxes
                    if (fitxesRestants.size() == 0) {
                        novaJugada.setPuntuacio(novaJugada.getPuntuacio()+50);
                    }
                    resultats.add(novaJugada);
                }
            }

            // Em moc a la següent posició
            int f = horitzontal ? filaActual : filaActual + 1;
            int c = horitzontal ? columnaActual + 1 : columnaActual;

            // Continuar recursivament
            resultats.addAll(generarParaules(nouPrefix, fitxesRestants, casellesJugades, nodeSeguent, f, c, horitzontal, taulell, dawg));
        }
        else {
            // Si no hi ha fitxa col·locada explorar possibles combinacions
            // amb les fitxes del faristol (fitxesRestants)
            for (int i = 0; i < fitxesRestants.size(); i++) {
                List<Fitxa> novesFitxesRestants = new ArrayList<>(fitxesRestants);
                Fitxa fitxa = novesFitxesRestants.remove(i);

                if (fitxa.obtenirLletra().equals("#")) {
                    // Si la fitxa és comodí: prova tots els fills del node actual
                    // (tots formaran prefix vàlids)
                    for (Map.Entry<String, DAWG.Node> entry : nodeActual.getFills().entrySet()) {
                        String tokenSubstitut = entry.getKey();
                        DAWG.Node nodeSeguent = entry.getValue();
                        // Creo fitxa "comodí" amb 0 punts per provar
                        Fitxa fitxaComodi = new Fitxa(tokenSubstitut, 0);

                        // Formo nova paraula i crido recursivament
                        String nouPrefix = prefix+tokenSubstitut;
                        processarFitxaIContinuar(nouPrefix, fitxaComodi, novesFitxesRestants, casellesJugades, nodeSeguent, filaActual, columnaActual, horitzontal, taulell, dawg, resultats);
                    }
                }
                else {
                    // Sinó és comodi creo nova paraula
                    String token = fitxa.obtenirLletra();
                    DAWG.Node nodeSeguent = nodeActual.getFill(token);

                    if (nodeSeguent != null) {
                        String nouPrefix = prefix+token;
                        processarFitxaIContinuar(nouPrefix, fitxa, novesFitxesRestants, casellesJugades, nodeSeguent, filaActual, columnaActual, horitzontal, taulell, dawg, resultats);
                    }
                }
            }
        }
        return resultats;
    }

    /**
     * Afegeix una fitxa a una nova casella i continua generant paraules amb recursió.
     * @param paraula Nova paraula generada.
     * @param fitxa Fitxa que s'està col·locant.
     * @param fitxesRestants Fitxes encara disponibles.
     * @param casellesJugades Caselles utilitzades fins ara.
     * @param nodeSeguent Node següent al DAWG.
     * @param filaActual Fila on col·locar la fitxa.
     * @param columnaActual Columna on col·locar la fitxa.
     * @param horitzontal Direcció horitzontal o vertical.
     * @param taulell Taulell del joc.
     * @param dawg Diccionari de paraules.
     * @param resultats Llista acumulativa de jugades vàlides.
     */
    private void processarFitxaIContinuar(String prefix, Fitxa fitxa, List<Fitxa> fitxesRestants,
                                       List<Casella> casellesJugades, DAWG.Node nodeSeguent,
                                       int filaActual, int columnaActual, boolean horitzontal,
                                       Taulell taulell, DAWG dawg, List<Jugada> resultats) {

        // Afegeixo la casella actual a casellesJugades
        List<Casella> novesCasellesJugades = new ArrayList<>(casellesJugades);
        Casella novaCasella = new Casella(filaActual, columnaActual, taulell.getSize());
        novaCasella.colocarFitxa(fitxa);
        novesCasellesJugades.add(novaCasella);
        
        // Si el prefix és una paraula l'afageixo a resultats
        if (nodeSeguent.esFinal()) {
            Jugada novaJugada = taulell.construirJugadaBot(prefix, novesCasellesJugades, dawg);
            if (novaJugada.getJugadaValida()) {
                // Augmento la puntuació 50 si juga totes les fitxes
                if (fitxesRestants.size() == 0) {
                    novaJugada.setPuntuacio(novaJugada.getPuntuacio()+50);
                }
                resultats.add(novaJugada);
            }
        }
 
        // Em moc a la següent posició
        int f = horitzontal ? filaActual : filaActual + 1;
        int c = horitzontal ? columnaActual + 1 : columnaActual;

        // Continuo recursivament
        resultats.addAll(generarParaules(prefix, fitxesRestants, novesCasellesJugades, nodeSeguent, f, c, horitzontal, taulell, dawg));
    }
}