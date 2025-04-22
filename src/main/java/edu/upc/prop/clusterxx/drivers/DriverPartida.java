package edu.upc.prop.clusterxx.drivers;

import edu.upc.prop.clusterxx.*;
import edu.upc.prop.clusterxx.controladors.CtrlDomini;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DriverPartida {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CtrlDomini ctrlDomini = CtrlDomini.getInstance();
        inicialitzarPartida(sc, ctrlDomini);
        jugarTorns(sc, ctrlDomini);
    }

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

    private static void imprimirCapcaleraConfiguracio() {
        System.out.println("\n======================================");
        System.out.println("         CONFIGURACIÓ DE PARTIDA       ");
        System.out.println("======================================\n");
    }

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

    private static int obtenirMidaFaristolValida(Scanner sc) {
        return obtenirNumeroFaristol(sc, "Introdueix la mida del faristol: ", "La mida del faristol ha de ser més gran que zero. Torna-ho a intentar: ");
    }

    private static int obtenirNumeroPositiu(Scanner sc, String promptInicial, String promptError) {
        System.out.print(promptInicial);
        int valor = sc.nextInt();

        while (valor < 0) {
            System.out.print(promptError);
            valor = sc.nextInt();
        }

        return valor;
    }

    private static int obtenirNumeroFaristol(Scanner sc, String promptInicial, String promptError) {
        System.out.print(promptInicial);
        int valor = sc.nextInt();

        while (valor < 1) {
            System.out.print(promptError);
            valor = sc.nextInt();
        }

        return valor;
    }

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

    private static String[] obtenirNomsJugadors(Scanner sc, int numJugadors) {
        String[] nomsJugadors = new String[numJugadors];

        for (int i = 0; i < numJugadors; i++) {
            System.out.println("\n--- Jugador " + (i + 1) + " ---");
            System.out.print("Nom: ");
            nomsJugadors[i] = sc.nextLine();
        }

        return nomsJugadors;
    }

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

    private static boolean confirmarConfiguracio(Scanner sc) {
        System.out.print("Vols jugar amb aquesta configuració? (true/false): ");
        String confirmacio = sc.nextLine();

        while (!confirmacio.equals("true") && !confirmacio.equals("false")) {
            System.out.print("Resposta no vàlida. Torna-ho a intentar (true/false): ");
            confirmacio = sc.nextLine();
        }

        return confirmacio.equals("true");
    }

    private static void jugarTorns(Scanner sc, CtrlDomini ctrlDomini) {
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

    private static void gestionarTornBot(CtrlDomini ctrlDomini) {
        imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
        Jugada jugadabot = ctrlDomini.jugadaBot();
        imprimirJugada(jugadabot);
    }

    private static boolean gestionarTornJugadorHuma(Scanner sc, CtrlDomini ctrlDomini, boolean first) {
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

    private static void gestionarUndo(CtrlDomini ctrlDomini) {
        if (ctrlDomini.esPotFerUndo()) {
            ctrlDomini.ferUndo();
        } else {
            System.out.println("No es pot fer undo en aquest moment.");
        }
    }

    private static void guardarPartida(CtrlDomini ctrlDomini) {
        System.out.println("Guardant partida...");
        ctrlDomini.guardarPartida();
        System.out.println("Partida guardada.");
        System.exit(0);
    }

    private static void mostrarFinalPartida(CtrlDomini ctrlDomini) {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== PARTIDA ACABADA ========" + Colors.RESET);
        imprimirTaulell(ctrlDomini.obtenirTaulell());
    }

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

    private static void resetTorn(CtrlDomini ctrlDomini) {
        try {
            ctrlDomini.resetTorn();
            imprimirPrincipiTorn(ctrlDomini);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No es pot reiniciar el torn. Torna-ho a intentar.");
        }
    }

    private static void ImprimirInfo(CtrlDomini ctrlDomini) {
        imprimirSeparador();
        imprimirTaulell(ctrlDomini.obtenirTaulell());
        System.out.println(Colors.YELLOW_TEXT + "Faristol: " + Colors.RESET);
        imprimirFaristol(ctrlDomini.obtenirJugadorActual().obtenirFaristol());
        imprimirSeparador();
    }

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

    private static void imprimirLlegendaTaulell() {
        System.out.println("🎨 Llegenda del Taulell:");
        System.out.println("\033[41m   \033[0m → Multiplicador de PARAULA x3");
        System.out.println("\033[45m   \033[0m → Multiplicador de PARAULA x2");
        System.out.println("\033[44m   \033[0m → Multiplicador de LLETRA x3");
        System.out.println("\033[46m   \033[0m → Multiplicador de LLETRA x2");
        System.out.println("\033[107m   \033[0m → Casella normal");
    }

    private static void imprimirLiniaSeparadora(int size) {
        System.out.print("    ");
        for (int j = 0; j < size; j++) System.out.print("+----");
        System.out.println("+");
    }

    private static String obtenirColorFons(Casella casella) {
        if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula estrategia) {
            return estrategia.obtenirMultiplicador() == 3 ? Colors.RED_BACKGROUND : Colors.PURPLE_BACKGROUND;
        } else if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra estrategia) {
            return estrategia.obtenirMultiplicador() == 3 ? Colors.BLUE_BACKGROUND : Colors.CYAN_BACKGROUND;
        } else {
            return "\033[107m";
        }
    }

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

    private static void imprimirSeparador() {
        System.out.println("================================================");
    }
}