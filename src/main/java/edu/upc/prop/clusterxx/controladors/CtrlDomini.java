package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Jugada;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Sac;
import edu.upc.prop.clusterxx.Torn;
import edu.upc.prop.clusterxx.persistencia.CtrlPersistencia;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe CtrDomini
 * Classe principal del domini del joc de Scrabble que actua com a controlador general.
 * S'encarrega de coordinar els controladors específics com {@code CtrlPartida} i {@code CtrEstadistica},
 * centralitzant la lògica de partida i les estadístiques dels jugadors.
 */
public class CtrlDomini {
    private static CtrlDomini instance = null;
    private CtrlPartida ctrlPartida = null;
    private CtrEstadistica ctrEstadistica = null;

    /**
     * Retorna la instància única del controlador de domini.
     * Si encara no existeix, la crea.
     *
     * @return Instància única de {@code CtrDomini}
     */
    public static CtrlDomini getInstance() {
        if (instance == null) {
            instance = new CtrlDomini();
        }
        return instance;
    }
    /**
     * Constructor privat de {@code CtrDomini}.
     * Inicialitza els controladors del domini, com {@code CtrlPartida} i {@code CtrEstadistica}.
     */
    private CtrlDomini(){
        ctrlPartida = CtrlPartida.getInstance();
        ctrEstadistica = CtrEstadistica.getInstance();
    }

    /**
     * Retorna la puntuació mitjana de totes les partides registrades.
     *
     * @return Puntuació mitjana
     */
    public float obtenirPuntuacioMitjana(){
        return ctrEstadistica.obtenirPuntuacioMitjana();
    }

    /**
     * Retorna la puntuació mínima registrada entre totes les partides.
     *
     * @return Puntuació mínima
     */
    public int obtenirPuntuacioMinima(){
        return ctrEstadistica.obtenirPuntuacioMinima();
    }

    /**
     * Retorna la puntuació màxima registrada entre totes les partides.
     *
     * @return Puntuació màxima
     */
    public int obtenirPuntuacioMaxima(){
        return ctrEstadistica.obtenirPuntuacioMaxima();
    }

    /**
     * Retorna la suma total de totes les puntuacions acumulades.
     *
     * @return Puntuació total
     */
    public int obtenirPuntuacioTotal(){
        return ctrEstadistica.obtenirPuntuacioTotal();
    }

    /**
     * Afegeix una nova puntuació a les estadístiques del sistema.
     *
     * @param jugador Nom del jugador que ha obtingut la puntuació
     * @param puntuacio Puntuació aconseguida pel jugador
     */
    public void afegirPuntuacio(String jugador, int puntuacio) {
        ctrEstadistica.afegirPuntuacio(jugador, puntuacio);
    }

    /**
     * Retira una puntuació a les estadístiques del sistema.
     *
     * @param jugador Nom del jugador al que retirem la puntuació
     * @param puntuacio Puntuació retirada al jugador
     */
    public void retirarPuntuacio(String jugador, int puntuacio) {
        ctrEstadistica.retirarPuntuacio(jugador, puntuacio);
    }

    public String obtenirMillorJugador() {
        return ctrEstadistica.obtenirMillorJugador();
    }

    public Map<String, Integer> obtenirPuntuacions(){
        return ctrEstadistica.obtenirPuntuacions();
    }

    public void eliminarJugador(String nom) {
        ctrEstadistica.eliminarJugador(nom);
    }

    public void actualitzarEstadistiques(Jugador[] jugadors) {
        for (Jugador j : jugadors) {
            ctrEstadistica.afegirPuntuacio(j.obtenirNom(), j.obtenirPunts());
        }
        ctrEstadistica.desarEstadistiques();
    }

    /**
     * Inicialitza una nova partida amb els paràmetres especificats.
     * Aquesta acció es delega al controlador de partida.
     *
     * @param midaTaulell Mida del taulell de joc (per exemple, 15x15)
     * @param midaFaristol Nombre màxim de fitxes que pot tenir cada jugador
     * @param idioma Idioma de les paraules utilitzades en la partida
     * @param nomsJugadors Array amb els noms dels jugadors (pot incloure humans i bots)
     * @param dificultatsBots Array amb la dificultat associada a cada bot (mateix ordre que nomsJugadors)
     */
    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors,int[] dificultatsBots){
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, idioma, nomsJugadors, dificultatsBots);
    }

    /**
     * Retorna el número del torn actual de la partida.
     *
     * @return Número de torn actual
     */
    public int obtenirTorn() {
        return ctrlPartida.obtenirTorn();
    }

    /**
     * Retorna el taulell de joc actual.
     *
     * @return Taulell de la partida
     */
    public Taulell obtenirTaulell() {
        return ctrlPartida.obtenirTaulell();
    }

    /**
     * Retorna el conjunt de jugadors de la partida.
     *
     * @return Array de jugadors participants
     */
    public Jugador[] obtenirJugadors() {
        return ctrlPartida.obtenirJugadors();
    }

    /**
     * Retorna el jugador que ha de jugar el torn actual.
     *
     * @return Jugador actual
     */
    public Jugador obtenirJugadorActual() {
        return ctrlPartida.obtenirJugadorActual();
    }

    /**
     * Retorna el sac de fitxes de la partida.
     *
     * @return Sac amb les fitxes disponibles
     */
    public Sac obtenirSac() {
        return ctrlPartida.obtenirSac();
    }

    /**
     * Col·loca una fitxa del faristol del jugador actual en una posició concreta del taulell.
     *
     * @param lletra Lletra de la fitxa dins del faristol
     * @param i     Coordenada vertical (fila) al taulell
     * @param j     Coordenada horitzontal (columna) al taulell
     * @return
     */
    public Jugada colocarFitxa(String lletra, int i, int j) {
        return ctrlPartida.colocarFitxa(lletra, i, j);
    }

    /**
     * Retira la fitxa situada en la posició indicada del taulell.
     *
     * @param i Coordenada vertical (fila)
     * @param j Coordenada horitzontal (columna)
     */
    public void retirarFitxa(int i, int j) {
        ctrlPartida.retirarFitxa(i, j);
    }

    /**
     * Desfà l’última acció realitzada.
     */
    public void ferUndo() {
        ctrlPartida.undo();
    }

    public boolean esPotFerUndo() {
        return ctrlPartida.esPotFerUndo();
    }

    /**
     * Finalitza la partida actual.
     */
    public void acabarPartida() {
        ctrlPartida.acabarPartida();
    }

    /**
     * Passa el torn al següent jugador.
     * Es perd el torn actual sense col·locar cap fitxa.
     */
    public void passarTorn() {
        ctrlPartida.passarTorn();
    }

    /**
     * Canvia un conjunt de fitxes del faristol per noves del sac.
     *
     * @param fitxesCanviades Índexs de les fitxes del faristol que es volen canviar
     */
    public void canviarFitxes(String[] fitxesCanviades){
        ctrlPartida.canviarFitxes(fitxesCanviades);
    }

    /**
     * Confirma la paraula formada pel jugador actual al taulell.
     * Aquesta acció valida el moviment, actualitza la puntuació i finalitza el torn.
     */
    public void commitParaula(){
        ctrlPartida.commitParaula();
    }

    /**
     * Comprova si la partida ha acabat.
     *
     * @return {@code true} si la partida ha finalitzat, {@code false} altrament
     */
    public boolean partidaAcabada() {
        return ctrlPartida.acabada();
    }

    /**
     * Comprova si es el final de la partida.
     * @return {@code true} si la partida ha finalitzat, {@code false} altrament
     */
    public boolean esFinalDePartida() {
        return ctrlPartida.esFinalDePartida();
    }

    /**
     * Realitza una jugada del bot.
     *
     * @return Una instància de Jugada que és la que acaba de fer el bot
     */
    public Jugada jugadaBot() {
        return ctrlPartida.jugadaBot();
    }

    /**
     * Es torna a l'estat inicial del torn.
     */
    public void resetTorn() {
        ctrlPartida.resetTorn();
    }

    /**
     * Canvia la lletra comodí per la que hagi escollit el jugador
     * @param fitxa La instància de la fitxa comodí
     * @param lletra La lletra escollida pel jugador
     * @return {@code true} si l'operació s'ha pogut fer, {@code false} altrament
     */
    public boolean setLletraComodi(Fitxa fitxa, String lletra) {
        return ctrlPartida.setLletraComodi(fitxa.obtenirLletra(), lletra);
    }


    public void guardarPartida() {
        Torn tornActual = ctrlPartida.obtenirTornActual();
        String nomFitxer = generarNomFitxerAmbData(); // pots fer-ho com a mètode privat aquí també
        try {
            CtrlPersistencia.guardarTorn(nomFitxer, tornActual);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la partida: " + e.getMessage());
        }
    }

    public void carregarPartida(String nomFitxer) {
        try {
            Torn torn = CtrlPersistencia.carregarTorn(nomFitxer);
            ctrlPartida.recuperarTornDesDeFitxer(torn);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al carregar la partida: " + e.getMessage());
        }
    }

    private String generarNomFitxerAmbData() {
        return Arrays.stream(obtenirJugadors())
                .map(Jugador::obtenirNom)
                .collect(Collectors.joining("-")) +
                "_" + java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
    }

    public List<String> llistarPartidesGuardades() {
        return CtrlPersistencia.llistarPartidesGuardades();
    }

    public void desarEstadistiques() {
        ctrEstadistica.desarEstadistiques();
    }

    public void carregarEstadistiques() {
        ctrEstadistica.carregarEstadistiques();
    }
}