package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe CtrlPartida
 *
 * Controlador principal encarregat de gestionar el desenvolupament d'una partida de Scrabble.
 * Centralitza i coordina els components fonamentals del joc, incloent-hi el taulell, el sac de fitxes,
 * el conjunt de jugadors, l'historial de jugades i el diccionari (DAWG) per validar paraules.
 *
 * També gestiona l'estat de la partida, el control de torns, el registre de jugades,
 * així com la interacció amb els bots a través de {@code CtrlJugadaBot}.
 */

public class CtrlPartida {
    private static CtrlPartida instance = null;
    private CtrlJugadaBot ctrlBot = null;
    private HistorialJoc historial;
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private DAWG dawg;
    private boolean acabada;
    private int torn;
    private List<Casella> casellasTorn = new ArrayList<>();
    Jugada jugadaActual = null;
    private int tornsSenseCanvi = -1;


    /**
     * Obté la instància única del controlador de partida (singleton).
     *
     * @return Instància única de CtrlPartida
     */
    public static CtrlPartida getInstance() {
        if (instance == null) {
            instance = new CtrlPartida();
        }
        return instance;
    }

    /**
     * Constructor privat de {@code CtrlPartida}.
     */
    private CtrlPartida() {}

    /**
     * Inicialitza l'estructura DAWG (Directed Acyclic Word Graph) amb les paraules i fitxes
     * corresponents a l'idioma especificat.
     * Llegeix dos fitxers de recursos: llista de paraules vàlides i fitxes i símbols vàlids.
     * @param idioma Nom de l'idioma (i nom de la carpeta de recursos) que s'ha d'utilitzar per carregar les dades
     * @throws RuntimeException si no es poden trobar o llegir els fitxers corresponents
     */
    public void inicialitzarDawg(String idioma) {
        List<String> palabras = new ArrayList<>();
        List<String> tokens = new ArrayList<>();

        try {
            // Leer palabras desde el recurso
            InputStream inputParaules = getClass().getClassLoader().getResourceAsStream(idioma + "/" + idioma + ".txt");
            if (inputParaules == null) {
                throw new RuntimeException("No s'ha pogut trobar el fitxer: " + idioma + "/" + idioma + ".txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputParaules))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.isEmpty()) {
                        palabras.add(linea);
                    }
                }
            }

            // Leer tokens desde el recurso (solo la primera palabra de cada línea)
            InputStream inputFitxes = getClass().getClassLoader().getResourceAsStream(idioma + "/fitxes" + idioma + ".txt");
            if (inputFitxes == null) {
                throw new RuntimeException("No s'ha pogut trobar el fitxer: " + idioma + "/fitxes" + idioma + ".txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputFitxes))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.startsWith("#")) continue;
                    String[] partes = linea.split("\\s+");
                    if (partes.length > 0) {
                        tokens.add(partes[0]);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al llegir els fitxers: " + e.getMessage(), e);
        }
        dawg = new DAWG(tokens, palabras);
    }

    /**
     * Inicialitza el controlador de jugades del bot.
     */
    private void inicialitzarCtrlBot() {
        ctrlBot = CtrlJugadaBot.getInstance();
    }

    /**
     * Inicialitza una nova partida de Scrabble amb tots els components necessaris.
     * Crea el taulell, el sac de fitxes, el diccionari (DAWG), els jugadors (humans i bots),
     * l’historial de joc i estableix el primer torn.
     *
     * @param midaTaulell Mida del taulell (per exemple, 15 per a un taulell 15x15)
     * @param midaFaristol Nombre màxim de fitxes que pot tenir cada jugador al faristol
     * @param idioma Idioma del joc, que determina les paraules vàlides i les fitxes disponibles
     * @param nomsJugadors Array amb els noms dels jugadors (podent incloure bots)
     * @param dificultatsBots Array amb la dificultat dels bots, en el mateix ordre que {@code nomsJugadors}
     */
    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors,int[] dificultatsBots) {
        acabada = false;
        torn = 0;
        inicialitzarTaulell(midaTaulell);
        inicialitzarDawg(idioma);
        sac = new Sac();
        inicialitzarSac(idioma);
        inicialitzarJugadors(nomsJugadors,dificultatsBots,midaFaristol);
        inicialitzarCtrlBot();
        historial = new HistorialJoc(new java.util.Date());
        passarTorn();
    }

    /**
     * Recupera l’estat d’una partida a partir d’un torn guardat.
     * Aquest mètode carrega l’estat del taulell, el sac, els jugadors i el número de torn,
     * així com si la partida ja havia acabat.
     *
     * @param nouTorn Torn que conté l’estat complet de la partida a restaurar
     */
    public void recuperarTorn(Torn nouTorn) {
        torn = nouTorn.obtenirTorn();
        acabada = nouTorn.esAcabada();
        taulell = nouTorn.obtenirTaulell();
        sac = nouTorn.obtenirSac();
        jugadors = nouTorn.obtenirJugadors();
        tornsSenseCanvi = nouTorn.obtenirTorn();
        List<Casella> casellasTorn = new ArrayList<>();

        historial.retirarTorns(torn);
    }


    /**
     * Reinicia l'estat del torn actual a l'estat inicial.
     * Recupera el torn actual de l'historial i restaura l'estat de la partida.
     */
    public void resetTorn(){
        Torn tornActual = historial.obtenirTorn(torn);
        recuperarTorn(tornActual);
    }

    /**
     * Indica si la partida ha finalitzat.
     *
     * @return {@code true} si la partida està acabada, {@code false} altrament
     */
    public boolean acabada() {return acabada;}

    /**
     * Ordena els jugadors de la partida en funció de la seva puntuació,
     * de major a menor.
     */
    private void ordenarJugadors(){
        for (int i = 0; i < jugadors.length; ++i) {
            for (int j = 0; j < jugadors.length - 1; ++j) {
                if (jugadors[j].obtenirPunts() < jugadors[j + 1].obtenirPunts()) {
                    Jugador temp = jugadors[j];
                    jugadors[j] = jugadors[j + 1];
                    jugadors[j + 1] = temp;
                }
            }
        }
    }
    /**
     * Finalitza la partida actual.
     * Marca la partida com acabada, reparteix els punts finals i ordena els jugadors segons la seva puntuació.
     */
    public void acabarPartida() {
        // Marca la partida com acabada
        acabada = true;

        int puntsRestants = 0;
        int jugadorAmbFaristolBuit = jugadors[(torn-1)%jugadors.length].obtenirFaristol().esBuit() ? torn%jugadors.length : -1;

        // Calcular els punts restants al faristol de cada jugador i restar-los de la seva puntuació
        for (Jugador jugador : jugadors) {
            int penalitzacio = jugador.obtenirPuntsFaristol();
            if (penalitzacio > 0) {
                jugador.eliminarPunts(penalitzacio);
                //Evitem errors
                if (jugador.obtenirPunts() < 0) {
                    jugador.eliminarPunts(jugador.obtenirPunts());
                }
                puntsRestants += penalitzacio;
            }
        }

        // Si un jugador s'ha quedat sense fitxes, se li sumen els punts restants dels altres
        if (jugadorAmbFaristolBuit != -1) {
            jugadors[jugadorAmbFaristolBuit].afegirPunts(puntsRestants);
        }

        // Ordenar els jugadors per puntuació de major a menor
        ordenarJugadors();
        guardarEstadistiques();
    }


    /**
     * Guarda les estadístiques finals dels jugadors al finalitzar la partida.
     * Afegeix la puntuació de cada jugador al registre d'estadístiques global.
     */
    private void guardarEstadistiques() {
        Estadistiques estadistiques = Estadistiques.getInstance();

        for (Jugador jugador : jugadors) {
            String nom = jugador.obtenirNom();
            int puntuacio = jugador.obtenirPunts();

            estadistiques.afegirPuntuacio(puntuacio, nom);
        }
    }


    /**
     * Retorna el sac de fitxes actual de la partida.
     *
     * @return Sac amb les fitxes disponibles
     */
    public Sac obtenirSac() {
        return sac;
    }

    /**
     * Retorna el taulell de joc en el seu estat actual.
     *
     * @return Taulell de la partida
     */
    public Taulell obtenirTaulell() {
        return taulell;
    }

    /**
     * Retorna l’array de jugadors que participen en la partida.
     *
     * @return Conjunt de jugadors
     */
    public Jugador[] obtenirJugadors() {
        return jugadors;
    }

    /**
     * Retorna el jugador que ha de jugar el torn actual.
     *
     * @return Jugador actual
     */
    public Jugador obtenirJugadorActual() {
        return jugadors[torn%jugadors.length];
    }

    /**
     * Retorna l’índex del jugador al qual li correspon jugar aquest torn.
     *
     * @return Índex del torn actual dins el conjunt de jugadors
     */
    public int obtenirTorn() {
        return torn;
    }

    /**
     * Passa el torn al següent jugador.
     * Incrementa el comptador de torns i de torns sense canvi, afegeix el torn actual a l’historial,
     * i comprova si s’ha d’acabar la partida.
     */
    public void passarTorn() {
        ++torn;
        ++tornsSenseCanvi;
        inicialitzarCasellasTorn();
        historial.afegirTorn(new Torn(sac, taulell, jugadors, torn, acabada, tornsSenseCanvi));
        acabada = esFinalDePartida();
    }

    /**
     * Desfà l’última acció de joc recuperant l’estat del torn anterior.
     *
     * Si hi ha almenys un torn anterior registrat a l’historial, es fa un pas enrere
     * i es restaura l’estat del joc tal com estava abans del darrer torn.
     */
    public void undo() {
        int tornAnterior = torn - 1;

        // Buscar el último torn que NO era d'un bot
        while (tornAnterior >= 1) {
            Torn tornRecuperar = historial.obtenirTorn(tornAnterior);
            Jugador j = tornRecuperar.obtenirJugadors()[tornAnterior % jugadors.length];
            if (!j.esBot()) {
                System.out.print("Recuperant torn " + tornRecuperar.obtenirTorn() + " del jugador " + j.obtenirNom());
                System.out.print(j.esBot());
                recuperarTorn(tornRecuperar);

                // Recalcular el estado de la partida
                tornsSenseCanvi = tornRecuperar.obtenirTornsSenseCanvis();
                acabada = esFinalDePartida();
                return;
            }
            tornAnterior--;
        }

        throw new RuntimeException("No es pot fer undo\n");
    }
    /**
     * Comprova si s’ha d’acabar la partida segons les condicions establertes.
     * @return {@code true} si la partida ha de finalitzar, {@code false} altrament
     */
    public boolean esFinalDePartida() {
        return (sac.esBuit() && jugadors[torn%jugadors.length].obtenirFaristol().esBuit()) || tornsSenseCanvi >= (jugadors.length)*2;
    }


    /**
     * Comprova si es pot utilitzar la funcionalitat d'undo en l'estat actual de la partida.
     * Només es permet fer undo si hi ha més d'un torn i només hi ha un jugador humà.
     *
     * @return {@code true} si es pot fer undo, {@code false} altrament
     */
    public boolean esPotFerUndo() {
        // No es pot fer undo si encara no s'ha fet cap torn
        if (torn == 1) return false;

        // Comptem quants jugadors humans hi ha
        int humans = 0;
        for (Jugador jugador : jugadors) {
            if (!jugador.esBot()) {
                humans++;
                // Si hi ha més d’un jugador humà, no es permet fer undo
                if (humans > 1) return false;
            }
        }

        // Si només hi ha un jugador humà, es pot fer undo
        return true;
    }


    /**
     * Inicialitza el sac de fitxes a partir del fitxer de configuració corresponent a l’idioma especificat.
     *
     * El fitxer ha de trobar-se a la ruta {@code /{idioma}/fitxes{idioma}.txt} i ha de tenir el següent format per línia:
     * {@code lletra quantitat punts}.
     *
     * @param idioma Idioma del joc utilitzat per determinar la ruta del fitxer de fitxes
     * @throws RuntimeException si el fitxer no es troba
     * @throws IllegalArgumentException si hi ha errors de format en el fitxer
     */
    private void inicialitzarSac(String idioma) {
        String nomFitxer = "/" + idioma + "/fitxes" + idioma + ".txt";
        InputStream input = getClass().getResourceAsStream(nomFitxer);
        if (input == null) {
            throw new RuntimeException("No s'ha pogut trobar el fitxer: " + nomFitxer);
        }
        List<String> lines = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.toList());


        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Format incorrecte al fitxer: " + line);
            }

            try {
                String lletra = parts[0];
                int quantitat = Integer.parseInt(parts[1]);
                int punts = Integer.parseInt(parts[2]);

                if (quantitat <= 0 || punts < 0) {
                    throw new IllegalArgumentException("Quantitat o punts invàlids a la línia: " + line);
                }

                for (int i = 0; i < quantitat; i++) {
                    sac.afegirFitxa(new Fitxa(lletra, punts));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error de format numèric a la línia: " + line, e);
            }
        }

        sac.setFitxesOriginals();

    }

    /**
     * Inicialitza els jugadors humans i els bots per a la partida.
     * Crea el faristol per a cada jugador, assigna el nom i la dificultat si escau,
     * i omple els faristols amb fitxes del sac.
     *
     * @param nomsJugadors Array amb els noms dels jugadors humans
     * @param dificultatsBots Array amb la dificultat de cada bot (mateix ordre que la seva creació)
     * @param midaFaristol Nombre màxim de fitxes que pot contenir cada faristol
     */
    private void inicialitzarJugadors(String[] nomsJugadors, int[] dificultatsBots, int midaFaristol) {
        jugadors = new Jugador[nomsJugadors.length + dificultatsBots.length];
        int i;
        for (i = 0; i < nomsJugadors.length; i++) {
            Faristol faristolJugador = new Faristol(midaFaristol);
            jugadors[i] = new Jugador(nomsJugadors[i], faristolJugador);
            inicialitzarFaristol(jugadors[i]);
        }
        for (int j = i; j < dificultatsBots.length + i; ++j) {
            Faristol faristolBot = new Faristol(midaFaristol);
            jugadors[j] = new Bot("bot" + (j - i + 1), faristolBot, dificultatsBots[j-i]) ;
            inicialitzarFaristol(jugadors[j]);
        }
    }

    /**
     * Inicialitza el taulell d'una partida donada una mida.
     *
     * @param midaTaulell Indica el tamany que tindrà el taulell.
     */
    private void inicialitzarTaulell(int midaTaulell) {
        taulell = new Taulell(midaTaulell);
    }

    /**
     * Inicialitza el faristol d'un jugador determinat. L'omple d'algunes de les fitxes disponibles al sac.
     *
     * @param jugador Instancia de jugador inicialitzat.
     */
    private void inicialitzarFaristol(Jugador jugador) {
        while(!jugador.faristolPle()) {
            Fitxa novaFitxa = sac.agafarFitxa();
            jugador.afegirFitxa(novaFitxa);
        }
    }

    /**
     * Agafa una fitxa del sac i la afegeix al jugador que li toqui el torn.
     */
    public void agafarDelSac(){
        Fitxa novaFitxa = sac.agafarFitxa();
        jugadors[torn%jugadors.length].afegirFitxa(novaFitxa);
    }

    /**
     * Canvia la lletra comodí per la que hagi escollit el jugador
     * @param fitxa La instància de la fitxa comodí
     * @param lletraComodi La lletra escollida pel jugador
     * @return {@code true} si l'operació s'ha pogut fer, {@code false} altrament
     */
    public boolean setLletraComodi(Fitxa fitxa, String lletraComodi) {
        if (fitxa == null || !fitxa.esComodi() || !sac.esFitxaOriginal(lletraComodi)) {
            return false;
        }
        Fitxa novaFitxa = new Fitxa(lletraComodi, 0);
        jugadors[torn%jugadors.length].eliminarFitxa(fitxa);
        jugadors[torn%jugadors.length].afegirFitxa(novaFitxa);
        return true;
    }

    /**
     * Canvia les fitxes que el jugador del torn trïi per unes noves aleatories del sac. Despres es passarà el torn.
     * @param fitxesCanviades Conjunt de lletres que el jugador ha decidit canviar.
     */
    public void canviarFitxes(String[] fitxesCanviades) {
        List<Fitxa> fitxesCanviadesAux = new ArrayList<>();
        for (int i = 0; i < fitxesCanviades.length; ++i) {
            Fitxa novaFitxa = sac.agafarFitxa();
            fitxesCanviadesAux.add(novaFitxa);
        }
        for (String lletra : fitxesCanviades) {
            Fitxa aux = jugadors[torn%jugadors.length].obtenirFaristol().obtenirFitxa(lletra);
            sac.afegirFitxa(aux);
            jugadors[torn%jugadors.length].eliminarFitxa(aux);
        }
        for (Fitxa fitxa : fitxesCanviadesAux) {
            jugadors[torn%jugadors.length].afegirFitxa(fitxa);
        }
        passarTorn();
    }

    /**
     * Coloca una fitxa en una casella i ho registra com a jugada.
     * @param fitxa La lletra que es vol colocar
     * @param fila La fila de la casella en la qual es vol colocar
     * @param columna La columna de la casella en la qual es vol colocar
     * @return La instancia de Jugada construïda
     */
    public Jugada colocarFitxa(String fitxa, int fila, int columna) {
        Fitxa aux = jugadors[torn%jugadors.length].obtenirFaristol().obtenirFitxa(fitxa);
        taulell.colocarFitxa(aux, fila, columna);
        jugadors[torn%jugadors.length].eliminarFitxa(aux);
        casellasTorn.add(taulell.getCasella(fila, columna));
        jugadaActual = taulell.construirJugada(casellasTorn, dawg);
        return jugadaActual;
    }

    /**
     * Retira una fitxa d'una casella i ho registra com a jugada.
     * @param fila La fila de la casella de la qual es vol retirar
     * @param columna La columna de la casella de la qual es vol retirar
     * @return La instancia de Jugada construïda
     */
    public Jugada retirarFitxa(int fila, int columna) {
        if (taulell.getCasella(fila, columna).esJugada()) {
            throw new IllegalArgumentException("La casella està jugada");
        }
        taulell.retirarFitxa(fila, columna);
        casellasTorn.remove(taulell.getCasella(fila, columna));
        jugadors[torn%jugadors.length].afegirFitxa(taulell.obtenirFitxa(fila, columna));
        jugadaActual = taulell.construirJugada(casellasTorn, dawg);
        return jugadaActual;
    }

    /**
     * Es reomple el faristol fins que torni a estar ple o fins que no quedin més fitxes.
     */
    private void rellenarFaristol(){
        int i = 0;
        while (i < casellasTorn.size() && !sac.esBuit()){
            Fitxa novaFitxa = sac.agafarFitxa();
            jugadors[torn%jugadors.length].afegirFitxa(novaFitxa);
            i++;
        }
    }

    /**
     * Registra la jugada actual com a vàlida i actualitza l’estat de la partida.
     *
     * @pre {@code jugadaActual} ha de ser una paraula vàlida i col·locada correctament al taulell
     * @post S’afegeixen els punts al jugador actual, es reomple el seu faristol,
     * es reinicia el comptador de torns sense canvi i es passa al següent torn
     */
    public void commitParaula() {
        jugadors[torn%jugadors.length].afegirPunts(jugadaActual.getPuntuacio());

        if (!jugadors[torn%jugadors.length].esBot() && jugadaActual.getCasellesJugades().size() == jugadors[torn%jugadors.length].obtenirFaristol().obtenirSize()) {
            jugadors[torn%jugadors.length].afegirPunts(50);
        }
        for (Casella casella : jugadaActual.getCasellesJugades()) {
            taulell.getCasella(casella.obtenirX(), casella.obtenirY()).jugarCasella();
        }
        if (!casellasTorn.isEmpty()) tornsSenseCanvi = -1;
        rellenarFaristol();
        passarTorn();
    }

    /**
     * Inicialitza l'estructura que guarda les caselles que s'utilitzaran en el torn.
     */
    private void inicialitzarCasellasTorn() {
        casellasTorn = new ArrayList<>();
    }

    /**
     * Executa la jugada decidida pel bot en el torn actual. Calcula la millor jugada possible segons la seva dificultat.
     * @return Retorna la instància de jugada creada.
     */
    public Jugada jugadaBot() {
        int nivellDificultat = ((Bot)jugadors[torn%jugadors.length]).obtenirDificultat();
        Bot bot = (Bot) jugadors[torn%jugadors.length];
        jugadaActual = ctrlBot.calcularJugada(taulell, dawg, nivellDificultat, bot.obtenirFaristol().obtenirFitxes());
        casellasTorn = jugadaActual.getCasellesJugades();

        for (Casella casella : casellasTorn) {
            Fitxa fitxa = casella.obtenirFitxa();
            jugadors[torn%jugadors.length].eliminarFitxa(casella.obtenirFitxa());
            taulell.colocarFitxa(casella.obtenirFitxa(), casella.obtenirX(), casella.obtenirY());
        }

        commitParaula();

        return jugadaActual;

    }


}
