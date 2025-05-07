package edu.upc.prop.clusterxx.drivers;

import edu.upc.prop.clusterxx.*;
import edu.upc.prop.clusterxx.controladors.CtrlDomini;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Driver per provar la funcionalitat de la classe Partida i els seus components relacionats.
 * Permet crear i jugar partides de Scrabble mitjançant una interfície de consola.
 */
public class DriverPartida {
    /**
     * Mètode principal que inicia l'execució del driver.
     * Inicialitza una partida i comença a jugar els torns.
     *
     * @param args Arguments de la línia de comandes (no s'utilitzen)
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        CtrlDomini ctrlDomini = CtrlDomini.getInstance();

        System.out.println("Vols (1) començar una nova partida o (2) carregar una partida guardada?");
        int opcio = sc.nextInt();
        sc.nextLine(); // netejar el buffer

        if (opcio == 2) {
            File carpeta = new File("data/partides/");
            if (!carpeta.exists() || carpeta.listFiles() == null || carpeta.listFiles().length == 0) {
                System.out.println("No hi ha cap partida guardada. Començant una de nova...");
                inicialitzarPartida(sc, ctrlDomini);
            } else {
                System.out.println("Partides disponibles:");
                File[] arxius = carpeta.listFiles((dir, name) -> name.endsWith(".scrabble"));
                for (int i = 0; i < arxius.length; i++) {
                    System.out.println((i + 1) + ". " + arxius[i].getName().replace(".scrabble", ""));
                }

                System.out.print("Tria el número de la partida que vols carregar: ");
                int index = sc.nextInt() - 1;
                sc.nextLine();

                if (index >= 0 && index < arxius.length) {
                    String nom = arxius[index].getName().replace(".scrabble", "");
                    try {
                        ctrlDomini.carregarPartida(nom);
                        System.out.println("Partida carregada correctament.");
                        jugarTorns(sc, ctrlDomini);
                        return;
                    } catch (Exception e) {
                        System.out.println("No s'ha pogut carregar la partida: " + e.getMessage());
                        return;
                    }
                } else {
                    System.out.println("Index no vàlid. Sortint.");
                    return;
                }
            }
        } else {
            inicialitzarPartida(sc, ctrlDomini);
        }

        jugarTorns(sc, ctrlDomini);
    }



    /**
     * Configura i inicialitza una nova partida amb els paràmetres introduïts per l'usuari.
     * Permet configurar la mida del taulell, la mida del faristol, l'idioma, i afegir jugadors humans i bots.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrDomini Controlador del domini per gestionar la partida
     */
    private static void inicialitzarPartida(Scanner sc, CtrlDomini ctrDomini) {
        imprimirCapcaleraConfiguracio();

        // Mida del taulell
        int midaTaulell = obtenirMidaTaulellValida(sc);

        // Mida del faristol
        int midaFaristol = obtenirMidaFaristolValida(sc);

        // Idioma
        String idioma = obtenirIdiomaValid(sc);

        // Bots
        int numBots = obtenirNumeroPositiu(sc, "Introdueix el nombre de bots: ", "El nombre de bots ha de ser positiu. Torna-ho a intentar: ");
        int[] dificultatsBots = obtenirDificultatsBot(sc, numBots);

        // Jugadors humans
        int numJugadors = obtenirNumeroPositiu(sc, "Introdueix el nombre de jugadors (persones): ", "El nombre de jugadors ha de ser positiu. Torna-ho a intentar: ");
        sc.nextLine(); // Consumir salt de línia
        String[] nomsJugadors = obtenirNomsJugadors(sc, numJugadors);

        // Mostrar configuració
        mostrarConfiguracio(midaTaulell, midaFaristol, idioma, numBots, dificultatsBots, numJugadors, nomsJugadors);

        // Confirmació
        if (!confirmarConfiguracio(sc)) {
            inicialitzarPartida(sc, ctrDomini);
            return;
        }

        System.out.print("Inicialitzant partida...\n");
        ctrDomini.inicialitzarPartida(midaTaulell, midaFaristol, idioma, nomsJugadors, dificultatsBots);
    }

    /**
     * Mostra la capçalera de configuració de la partida a la consola.
     */
    private static void imprimirCapcaleraConfiguracio() {
        System.out.println("\n======================================");
        System.out.println("         CONFIGURACIÓ DE PARTIDA       ");
        System.out.println("======================================\n");
    }

    /**
     * Llegeix la mida del taulell de joc i valida que sigui correcta.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @return Mida del taulell vàlida
     */
    private static int obtenirMidaTaulellValida(Scanner sc) {
        int midaTaulell;
        boolean midaValida = false;

        while (!midaValida) {
            System.out.print("Introdueix la mida del taulell: ");
            midaTaulell = sc.nextInt();

            try {
                // Intentar crear el Taulell
                new Taulell(midaTaulell);
                midaValida = true;  // Si no lanza excepción, se considera válida
                return midaTaulell;
            } catch (IllegalArgumentException e) {
                // Atrapar la excepción y mostrar un mensaje al usuario
                System.out.println(e.getMessage());
                // El bucle continuará pidiendo la medida del tablero
            }
        }
        return 0; // Este return nunca se alcanzará, pero es necesario para el compilador
    }

    /**
     * Llegeix la mida del faristol i valida que sigui correcta.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @return Mida del faristol vàlida
     */
    private static int obtenirMidaFaristolValida(Scanner sc) {
        return obtenirNumeroFaristol(sc, "Introdueix la mida del faristol: ", "La mida del faristol ha de ser més gran que zero. Torna-ho a intentar: ");
    }

    /**
     * Llegeix un número positiu de l'entrada de l'usuari.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param promptInicial Missatge inicial per demanar el número
     * @param promptError Missatge d'error si el número no és vàlid
     * @return Número positiu vàlid
     */
    private static int obtenirNumeroPositiu(Scanner sc, String promptInicial, String promptError) {
        System.out.print(promptInicial);
        int valor = sc.nextInt();

        while (valor < 0) {
            System.out.print(promptError);
            valor = sc.nextInt();
        }

        return valor;
    }

    /**
     * Llegeix un número de faristol de l'entrada de l'usuari.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param promptInicial Missatge inicial per demanar el número
     * @param promptError Missatge d'error si el número no és vàlid
     * @return Número de faristol vàlid
     */
    private static int obtenirNumeroFaristol(Scanner sc, String promptInicial, String promptError) {
        System.out.print(promptInicial);
        int valor = sc.nextInt();

        while (valor < 1) {
            System.out.print(promptError);
            valor = sc.nextInt();
        }

        return valor;
    }

    /**
     * Llegeix l'idioma/temàtica de l'entrada de l'usuari i valida que sigui correcta.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @return Idioma vàlid
     */
    private static String obtenirIdiomaValid(Scanner sc) {
        System.out.println("Idiomes/Temàtiques disponibles: ");
        System.out.println("castellano");
        System.out.println("catalan");
        System.out.println("english");
        System.out.print("Introdueix l'idioma/temàtica: ");
        sc.nextLine(); // Consumir salt de línia
        String idioma = sc.nextLine();

        while (!idioma.equals("castellano") && !idioma.equals("catalan") && !idioma.equals("english")) {
            System.out.print("Idioma no vàlid. Torna-ho a intentar: ");
            idioma = sc.nextLine();
        }

        return idioma;
    }

    /**
     * Llegeix la dificultat dels bots de l'entrada de l'usuari i valida que sigui correcta.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param numBots Número de bots
     * @return Array amb les dificultats dels bots
     */
    private static int[] obtenirDificultatsBot(Scanner sc, int numBots) {
        int[] dificultatsBots = new int[numBots];

        for (int i = 0; i < numBots; i++) {
            System.out.print("   Dificultat del Bot " + (i + 1) + " (1-Fàcil, 2-Normal, 3-Difícil): ");
            dificultatsBots[i] = sc.nextInt();

            while(dificultatsBots[i] < 1 || dificultatsBots[i] > 3) {
                System.out.print("Dificultat no vàlida. Torna-ho a intentar: ");
                dificultatsBots[i] = sc.nextInt();
            }
        }

        return dificultatsBots;
    }

    /**
     * Llegeix els noms dels jugadors humans de l'entrada de l'usuari.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param numJugadors Número de jugadors humans
     * @return Array amb els noms dels jugadors humans
     */
    private static String[] obtenirNomsJugadors(Scanner sc, int numJugadors) {
        String[] nomsJugadors = new String[numJugadors];

        for (int i = 0; i < numJugadors; i++) {
            System.out.println("\n--- Jugador " + (i + 1) + " ---");
            System.out.print("Nom: ");
            nomsJugadors[i] = sc.nextLine();
        }

        return nomsJugadors;
    }

    /**
     * Mostra la configuració de la partida a la consola.
     *
     * @param midaTaulell Mida del taulell
     * @param midaFaristol Mida del faristol
     * @param idioma Idioma seleccionat
     * @param numBots Número de bots
     * @param dificultatsBots Dificultats dels bots
     * @param numJugadors Número de jugadors humans
     * @param nomsJugadors Noms dels jugadors humans
     */
    private static void mostrarConfiguracio(int midaTaulell, int midaFaristol, String idioma,
                                            int numBots, int[] dificultatsBots,
                                            int numJugadors, String[] nomsJugadors) {
        System.out.println("\n======================================");
        System.out.println("        CONFIGURACIÓ COMPLETADA        ");
        System.out.println("======================================");
        System.out.println("Taulell: " + midaTaulell);
        System.out.println("Faristol: " + midaFaristol);
        System.out.println("Idioma: " + idioma);
        System.out.println("Jugadors totals: " + (numBots + numJugadors));

        // Mostrar bots
        for (int i = 0; i < numBots; i++) {
            System.out.println("  - Bot \"Bot" + (i + 1) + "\" amb dificultat " + dificultatsBots[i]);
        }

        // Mostrar jugadors humans
        for (String nomsJugador : nomsJugadors) {
            System.out.println("  - Persona \"" + nomsJugador + "\"");
        }

        System.out.println("======================================\n");
    }

    /**
     * Demana confirmació a l'usuari per continuar amb la configuració de la partida.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @return true si l'usuari vol continuar, false en cas contrari
     */
    private static boolean confirmarConfiguracio(Scanner sc) {
        System.out.print("Vols jugar amb aquesta configuració? (true/false): ");
        String confirmacio = sc.nextLine();

        while (!confirmacio.equals("true") && !confirmacio.equals("false")) {
            System.out.print("Resposta no vàlida. Torna-ho a intentar (true/false): ");
            confirmacio = sc.nextLine();
        }

        return confirmacio.equals("true");
    }

    /**
     * Juga els torns de la partida fins que es finalitzi.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void jugarTorns(Scanner sc, CtrlDomini ctrlDomini) throws IOException {
        boolean first = true; // Variable per controlar si és el primer torn

        while (!ctrlDomini.esFinalDePartida()) {
            imprimirSeparador();

            int torn = ctrlDomini.obtenirTorn();

            while (torn == ctrlDomini.obtenirTorn()) {
                imprimirPrincipiTorn(ctrlDomini);
                Jugador jugadorActual = ctrlDomini.obtenirJugadorActual();

                if (jugadorActual.esBot()) {
                    gestionarTornBot(ctrlDomini);
                    first = false; // Si el bot juga, ja no és el primer torn
                } else {
                    first = gestionarTornJugadorHuma(sc, ctrlDomini, first);
                }
            }
            imprimirSeparador();
        }

        if (ctrlDomini.esFinalDePartida()) {
            ctrlDomini.acabarPartida();
            mostrarFinalPartida(ctrlDomini);
        }
    }

    /**
     * Gestiona el torn d'un bot, mostrant el seu faristol i la jugada realitzada.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void gestionarTornBot(CtrlDomini ctrlDomini) {
        imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
        Jugada jugadabot = ctrlDomini.jugadaBot();
        imprimirJugada(jugadabot);
    }

    /**
     * Gestiona el torn d'un jugador humà, mostrant les opcions disponibles i processant la seva elecció.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     * @param first Indica si és el primer torn de la partida
     * @return Retorna el valor actualitzat de first
     */
    private static boolean gestionarTornJugadorHuma(Scanner sc, CtrlDomini ctrlDomini, boolean first) throws IOException {
        int opcio;

        if (!first) {
            System.out.print("Que vols fer? (1 = jugar, 2 = passar torn, 3 = canviar fitxes, 4 = undo, 5 = guadar i sortir, 6 = sortir sense guardar): ");
            opcio = sc.nextInt();
        } else {
            System.out.print("Que vols fer?. (1 = jugar, 5 = guadar i sortir, 6 = sortir sense guardar): ");
            opcio = sc.nextInt();
            first = false;
        }

        switch (opcio) {
            case 1 -> jugarParaula(sc, ctrlDomini);
            case 2 -> ctrlDomini.passarTorn();
            case 3 -> canviarFitxes(sc, ctrlDomini);
            case 4 -> gestionarUndo(ctrlDomini);
            case 5 -> {
                guardarPartida(ctrlDomini);
                return first;
            }
            case 6 -> {
                System.out.println("Sortint sense guardar...");
                System.exit(0);
                return first;
            }
            default -> System.out.println("Opció no vàlida. Torna a intentar-ho.");
        }

        return first;
    }

    /**
     * Realitza un undo (desfà l'últim moviment) si és possible.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void gestionarUndo(CtrlDomini ctrlDomini) {
        if (ctrlDomini.esPotFerUndo()) {
            ctrlDomini.ferUndo();
        } else {
            System.out.println("No es pot fer undo en aquest moment.");
        }
    }

    /**
     * Guarda l'estat actual de la partida i surt del joc.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void guardarPartida(CtrlDomini ctrlDomini) throws IOException {
        System.out.println("Guardant partida...");
        ctrlDomini.guardarPartida();
        System.out.println("Partida guardada.");
        System.exit(0);
    }

    /**
     * Mostra el resum final de la partida amb el taulell i el rànquing de jugadors.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void mostrarFinalPartida(CtrlDomini ctrlDomini) {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== PARTIDA ACABADA ========" + Colors.RESET);
        imprimirTaulell(ctrlDomini.obtenirTaulell());
        imprimirRanking(ctrlDomini.obtenirJugadors());
    }

    /**
     * Mostra la informació inicial de cada torn: jugador actual, punts, faristol i taulell.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void imprimirPrincipiTorn(CtrlDomini ctrlDomini) {
        imprimirSeparador();
        imprimirSeparador();
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== INICI DEL TORN"+ ctrlDomini.obtenirTorn() +"========" + Colors.RESET);
        System.out.println(Colors.YELLOW_TEXT + "Jugador: " + Colors.RESET + ctrlDomini.obtenirJugadorActual().obtenirNom());
        System.out.println(Colors.CYAN_TEXT + "Punts: " + Colors.RESET + ctrlDomini.obtenirJugadorActual().obtenirPunts());

        if (!ctrlDomini.obtenirJugadorActual().esBot()) {
            System.out.println(Colors.YELLOW_TEXT + "Faristol: " + Colors.RESET);
            imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
            imprimirTaulell(ctrlDomini.obtenirTaulell());
        }

        imprimirSeparador();
        imprimirSeparador();
    }

    /**
     * Gestiona el procés de jugar una paraula, permetent col·locar i retirar fitxes.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void jugarParaula(Scanner sc, CtrlDomini ctrlDomini) {
        int torn = ctrlDomini.obtenirTorn();
        boolean reset = false;
        while (torn == ctrlDomini.obtenirTorn() && !reset) {
            System.out.print("Vols col·locar o retirar una fitxa? (1 = col·locar, 2 = retirar, 3 = reiniciar torn): ");
            int opcio = sc.nextInt();
            sc.nextLine(); // Netejar buffer

            switch (opcio) {
                case 1 -> {
                    imprimirSeparador();
                    boolean fitxacolocada = gestionarColocacio(sc, ctrlDomini);
                    if (fitxacolocada) {
                        ImprimirInfo(ctrlDomini);
                    } else {
                        imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
                    }
                }
                case 2 -> retirarFitxa(sc, ctrlDomini);
                case 3 ->{
                    resetTorn(ctrlDomini);
                    reset = true;
                }
                default -> System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
    }

    /**
     * Reinicia el torn actual, tornant a l'estat inicial del torn.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void resetTorn(CtrlDomini ctrlDomini) {
        try {
            ctrlDomini.resetTorn();
            imprimirPrincipiTorn(ctrlDomini);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No es pot reiniciar el torn. Torna-ho a intentar.");
        }
    }

    /**
     * Mostra informació actualitzada del taulell i el faristol després d'una jugada.
     *
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void ImprimirInfo(CtrlDomini ctrlDomini) {
        imprimirSeparador();
        imprimirTaulell(ctrlDomini.obtenirTaulell());
        System.out.println(Colors.YELLOW_TEXT + "Faristol: " + Colors.RESET);
        imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
        imprimirSeparador();
    }

    /**
     * Processa la retirada d'una fitxa del taulell, demanant la posició a l'usuari.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void retirarFitxa(Scanner sc, CtrlDomini ctrlDomini) {
        System.out.print("Introdueix la fila de la fitxa que vols retirar: ");
        int fila = sc.nextInt();
        System.out.print("Introdueix la columna de la fitxa que vols retirar: ");
        int columna = sc.nextInt();
        sc.nextLine(); // Netejar buffer

        try {
            ctrlDomini.retirarFitxa(fila, columna);
            imprimirTaulell(ctrlDomini.obtenirTaulell());
            imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
        } catch (Exception e) {
            System.out.print("Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Gestiona la col·locació d'una fitxa al taulell, demanant la lletra i la posició.
     * Permet l'ús de comodins.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     * @return true si s'ha col·locat una fitxa correctament, false en cas contrari
     */
    private static boolean gestionarColocacio(Scanner sc, CtrlDomini ctrlDomini) {
        System.out.print("Escriu la lletra de la fitxa que vols jugar: ");
        String lletra = sc.nextLine();

        if (lletra.equals("#")) {
            lletra = gestionarComodi(sc, ctrlDomini);
            if (lletra == null) return false; // No hi havia comodí
        }

        System.out.print("Introdueix la fila on vols col·locar la fitxa: ");
        int fila = sc.nextInt();
        System.out.print("Introdueix la columna on vols col·locar la fitxa: ");
        int columna = sc.nextInt();
        sc.nextLine(); // Netejar buffer

        try {
            Jugada jugada = ctrlDomini.colocarFitxa(lletra, fila, columna);

            if (jugada.getJugadaValida()) {
                System.out.print("Paraula formada: "+ jugada.getParaulaFormada() + "\n");
                System.out.print("Vols confirmar la jugada? (true/false): ");
                boolean confirmacio = sc.nextBoolean();
                sc.nextLine();

                if (confirmacio) {
                    imprimirJugada(jugada);
                    ctrlDomini.commitParaula();
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.print("Intenta-ho de nou. Tria que vols fer: \n");
            return false;
        }
    }

    /**
     * Gestiona l'ús d'un comodí, demanant a l'usuari per quina lletra vol substituir-lo.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     * @return La lletra per la qual es substitueix el comodí, o null si no hi ha comodins disponibles
     */
    private static String gestionarComodi(Scanner sc, CtrlDomini ctrlDomini) {
        Fitxa fitxa;
        try {
            fitxa = ctrlDomini.obtenirJugadorActual().obtenirFitxa("#");
        } catch (IllegalArgumentException e) {
            System.out.print("No tens cap comodí disponible. ");
            return null;
        }

        System.out.print("Per quina lletra vols substituir el comodí? ");
        while (true) {
            String lletra = sc.nextLine().toUpperCase();
            if (ctrlDomini.setLletraComodi(fitxa, lletra)) {
                return lletra;
            } else {
                System.out.println("No hi ha cap paraula amb la lletra '" + lletra + "' al diccionari.");
                System.out.print("Introdueix una altra lletra: ");
            }
        }
    }

    /**
     * Gestiona el canvi de fitxes del faristol del jugador amb el sac.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     */
    private static void canviarFitxes(Scanner sc, CtrlDomini ctrlDomini) {
        Faristol faristol = ctrlDomini.obtenirJugadorActual().obtenirFaristol();
        int maxFitxes = faristol.obtenirNumFitxes();

        System.out.print("Tria quantes fitxes vols canviar: ");
        int numFitxes = sc.nextInt();

        while (numFitxes < 1 || numFitxes > maxFitxes) {
            System.out.print("El nombre de fitxes ha de ser entre 1 i " + maxFitxes + ". Torna-ho a intentar: ");
            numFitxes = sc.nextInt();
        }
        sc.nextLine();

        intentarCanviarFitxes(sc, ctrlDomini, numFitxes);
    }

    /**
     * Processa l'intercanvi de fitxes seleccionades amb el sac.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     * @param ctrlDomini Controlador del domini per gestionar la partida
     * @param numFitxes El nombre de fitxes a canviar
     */
    private static void intentarCanviarFitxes(Scanner sc, CtrlDomini ctrlDomini, int numFitxes) {
        boolean canviFet = false;

        while (!canviFet) {
            String[] fitxes = new String[numFitxes];

            for (int i = 0; i < numFitxes; ++i) {
                System.out.print("Tria la fitxa que vols canviar: ");
                fitxes[i] = sc.nextLine();
            }

            Arrays.sort(fitxes);

            try {
                ctrlDomini.canviarFitxes(fitxes);
                canviFet = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Intenta-ho de nou. ");
            }
        }
    }

    /**
     * Imprimeix la jugada realitzada, mostrant la paraula formada, la puntuació i les caselles jugades.
     *
     * @param jugada Jugada realitzada
     */
    private static void imprimirJugada(Jugada jugada) {
        System.out.println("\n🎯 Jugada realitzada:");
        System.out.println(" - Paraula formada: " + jugada.getParaulaFormada());
        System.out.println(" - Puntuació: " + jugada.getPuntuacio());
        System.out.println(" - És vàlida? " + (jugada.getJugadaValida() ? "Sí" : "No"));
        System.out.println(" - Caselles jugades:");

        for (Casella casella : jugada.getCasellesJugades()) {
            System.out.println("     · " + casella);
        }
    }

    /**
     * Imprimeix el rànquing final dels jugadors al finalitzar la partida.
     *
     * @param jugadors Array de jugadors
     */
    private static void imprimirRanking(Jugador[] jugadors) {
        // Imprimim el rànquing
        System.out.println("Rànquing final de jugadors:");

        // Iterem per cada jugador ja ordenat
        for (Jugador jugador : jugadors) {
            System.out.println(jugador.obtenirNom() + ": " + jugador.obtenirPunts() + " punts");
        }
    }


    /**
     * Imprimeix el taulell de joc amb les caselles i els seus colors.
     *
     * @param taulell Taulell a imprimir
     */
    private static void imprimirTaulell(Taulell taulell) {
        imprimirLlegendaTaulell();

        int size = taulell.getSize();

        // Imprimir índices de columna con padding
        System.out.print("     "); // Espacio para alinear con los números de fila
        for (int i = 0; i < size; i++) {
            System.out.printf(" %2d  ", i);
        }
        System.out.println();

        // Línea superior
        imprimirLiniaSeparadora(size);

        // Filas del taulell
        for (int i = 0; i < size; ++i) {
            System.out.printf("%2d  ", i); // Índice de fila
            for (int j = 0; j < size; ++j) {
                Casella casella = taulell.getTaulell()[i][j];
                String colorFons = obtenirColorFons(casella);
                System.out.print("|" + colorFons + Colors.BLACK_TEXT + " " + casella + " " + Colors.RESET);
            }
            System.out.println("|");

            // Línea divisoria
            imprimirLiniaSeparadora(size);
        }
    }

    /**
     * Imprimeix la llegenda del taulell, mostrant els colors i els seus significats.
     */
    private static void imprimirLlegendaTaulell() {
        System.out.println("🎨 Llegenda del Taulell:");
        System.out.println("\033[41m   \033[0m → Multiplicador de PARAULA x3");
        System.out.println("\033[45m   \033[0m → Multiplicador de PARAULA x2");
        System.out.println("\033[44m   \033[0m → Multiplicador de LLETRA x3");
        System.out.println("\033[46m   \033[0m → Multiplicador de LLETRA x2");
        System.out.println("\033[107m   \033[0m → Casella normal");
    }

    /**
     * Imprimeix una línia separadora per al taulell.
     *
     * @param size Mida del taulell
     */
    private static void imprimirLiniaSeparadora(int size) {
        System.out.print("    ");
        for (int j = 0; j < size; j++) System.out.print("+----");
        System.out.println("+");
    }

    /**
     * Obté el color de fons per a una casella del taulell.
     *
     * @param casella Casella del taulell
     * @return Color de fons corresponent a la casella
     */
    private static String obtenirColorFons(Casella casella) {
        if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula estrategia) {
            return estrategia.obtenirMultiplicador() == 3 ? Colors.RED_BACKGROUND : Colors.PURPLE_BACKGROUND;
        } else if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra estrategia) {
            return estrategia.obtenirMultiplicador() == 3 ? Colors.BLUE_BACKGROUND : Colors.CYAN_BACKGROUND;
        } else {
            return "\033[107m";
        }
    }

    /**
     * Imprimeix el faristol d'un jugador, mostrant les fitxes disponibles i els seus punts.
     *
     * @param faristol Faristol a imprimir
     */
    public static void imprimirFaristol(Faristol faristol) {
        List<Fitxa> fitxes = faristol.obtenirFitxes();
        System.out.print("[");

        for (int i = 0; i < fitxes.size(); i++) {
            Fitxa fitxa = fitxes.get(i);
            System.out.print("[" + fitxa + " " + Colors.YELLOW_TEXT + fitxa.obtenirPunts() + Colors.RESET + "]");
            if (i < fitxes.size() - 1) System.out.print(" ");
        }

        System.out.println("]");
    }

    /**
     * Imprimeix un missatge de separació a la consola.
     */
    private static void imprimirSeparador() {
        System.out.println("================================================");
    }
}