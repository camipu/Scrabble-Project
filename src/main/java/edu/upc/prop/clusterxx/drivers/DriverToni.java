package edu.upc.prop.clusterxx.drivers;

import edu.upc.prop.clusterxx.*;
import edu.upc.prop.clusterxx.controladors.CtrlPartida;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DriverToni {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CtrlPartida ctrlPartida = inicialitzarPartida(sc);
        jugarTorns(sc, ctrlPartida);
    }
    private static CtrlPartida inicialitzarPartida(Scanner sc) {
        System.out.println("\n======================================");
        System.out.println("         CONFIGURACIÓ DE PARTIDA       ");
        System.out.println("======================================\n");

        // Preguntar mida del taulell
        System.out.print("Introdueix la mida del taulell: ");
        int midaTaulell = sc.nextInt();

        // Preguntar mida del faristol
        System.out.print("Introdueix la mida del faristol: ");
        int midaFaristol = sc.nextInt();

        // Preguntar idioma
        System.out.print("Idiomes/Temàtiques disponibles: ");
        System.out.print("castellano");
        System.out.print("catalan");
        System.out.print("english");
        System.out.print("Introdueix l'idioma/temàtica: ");
        sc.nextLine(); // Consumir salt de línia
        String idioma = sc.nextLine();

        // Preguntar número de bots
        System.out.print("Introdueix el nombre de bots: ");
        int numBots = sc.nextInt();

        // Llegir dificultats dels bots
        int[] dificultatsBots = new int[numBots];
        for (int i = 0; i < numBots; i++) {
            System.out.print("   Dificultat del Bot " + (i + 1) + " (1-Fàcil, 2-Normal, 3-Difícil): ");
            dificultatsBots[i] = sc.nextInt();
        }

        // Preguntar número de jugadors
        System.out.print("Introdueix el nombre de jugadors (persones): ");
        int numJugadors = sc.nextInt();

        // Llegir noms de jugadors
        sc.nextLine(); // Consumir salt de línia
        String[] nomsJugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            System.out.println("\n--- Jugador " + (i + 1) + " ---");
            System.out.print("Nom: ");
            nomsJugadors[i] = sc.nextLine();
        }

        // Combinar noms
        String[] nomsTotals = new String[numBots + numJugadors];
        for (int i = 0; i < numBots; i++) {
            nomsTotals[i] = "Bot" + (i + 1);
        }
        System.arraycopy(nomsJugadors, 0, nomsTotals, numBots, numJugadors);

        // Mostrar resum de configuració
        System.out.println("\n======================================");
        System.out.println("        CONFIGURACIÓ COMPLETADA        ");
        System.out.println("======================================");
        System.out.println("Taulell: " + midaTaulell);
        System.out.println("Faristol: " + midaFaristol);
        System.out.println("Idioma: " + idioma);
        System.out.println("Jugadors totals: " + nomsTotals.length);
        for (int i = 0; i < nomsTotals.length; i++) {
            String tipus = nomsTotals[i].startsWith("Bot") ? "Bot" : "Persona";
            if (tipus.equals("Bot")) {
                System.out.println("  - " + tipus + " \"" + nomsTotals[i] + "\" amb dificultat " + dificultatsBots[i]);
            } else {
                System.out.println("  - " + tipus + " \"" + nomsTotals[i] + "\"");
            }
        }

        System.out.println("======================================\n");
        // Preguntar si es vol jugar
        System.out.print("Vols jugar amb aquesta configuració? (true/false): ");
        boolean jugar = sc.nextBoolean();
        if (!jugar) {
            System.out.println("\nTornant a la configuració inicial...\n");
            return inicialitzarPartida(sc);
        }

        // Inicialitzar partida
        CtrlPartida ctrlPartida = CtrlPartida.getInstance();
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, idioma, nomsTotals, dificultatsBots);
        return ctrlPartida;
    }


    private static void jugarTorns(Scanner sc, CtrlPartida ctrlPartida) {
        boolean first = true; // Variable per controlar si és el primer torn
        while (!ctrlPartida.esFinalDePartida()) {
            imprimirSeparador();
            imprimirTorn(ctrlPartida);
            imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
            if (first) imprimirTaulell(ctrlPartida.obtenirTaulell());
            Jugador jugadorActual = ctrlPartida.obtenirJugadorActual();
            boolean passatorn = false;

            if (jugadorActual.esBot()) {
                Jugada jugadabot = ctrlPartida.jugadaBot();
                imprimirJugada(jugadabot);
                passatorn = true;
                if (first) first = false; // Si el bot juga, ja no és el primer torn
            }

            while (!passatorn) {
                int opcio;
                if (!first) {
                    System.out.print("Que vols fer? (1 = jugar, 2 = passar torn, 3 = canviar fitxes): ");
                    opcio = sc.nextInt();
                }
                else {
                    System.out.print("Has de jugar, no pots passar torn. (1 = jugar): ");
                    opcio = 1; // Si és el primer torn, només es pot jugar
                    first = false;
                }


                switch (opcio) {
                    case 1 -> passatorn = jugarParaula(sc, ctrlPartida);
                    case 2 -> {
                        ctrlPartida.passarTorn();
                        passatorn = true;
                    }
                    case 3 -> {
                        canviarFitxes(sc, ctrlPartida);
                        passatorn = true;
                    }
                    default -> System.out.println("Opció no vàlida. Torna a intentar-ho.");
                }
            }

            imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
            imprimirTaulell(ctrlPartida.obtenirTaulell());
            imprimirSeparador();
        }
    }

    private static boolean jugarParaula(Scanner sc, CtrlPartida ctrlPartida) {
        boolean commit = false;

        while (!commit) {
            imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
            System.out.print("Vols resetejar la jugada? (true/false): ");
            boolean reset = sc.nextBoolean();
            if (reset) {
                ctrlPartida.resetTorn();
                imprimirTaulell(ctrlPartida.obtenirTaulell());
                imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
            }
            System.out.print("vols colocar o retirar una fitxa? (1 = colocar, 2 = retirar): ");
            int opcio = sc.nextInt();
            sc.nextLine();

            if (opcio == 1) {
                System.out.print("Tria que fitxa vols jugar: ");
                String lletra = sc.nextLine();
                if (lletra.equals("#")) {
                    System.out.print("Per quina lletra vols canviar el comodí? ");
                    boolean comodiUsatCorrectament = false;

                    while (!comodiUsatCorrectament) {
                        lletra = sc.nextLine().toUpperCase(); // Por si quieres que sea en mayúsculas
                        Fitxa fitxa = ctrlPartida.obtenirJugadorActual().obtenirFitxa("#");

                        if (fitxa == null) {
                            System.out.println("No tens cap comodí disponible.");
                            break; // o return, segons com vulguis gestionar-ho
                        }

                        comodiUsatCorrectament = ctrlPartida.setLletraComodi(fitxa, lletra);

                        if (!comodiUsatCorrectament) {
                            System.out.println("No hi ha cap fitxa amb la lletra " + lletra + " al diccionari. Intenta de nou.\n");
                            System.out.print("Introdueix una nova lletra per al comodí: ");
                        }
                    }
                }

                System.out.print("Tria la fila on vols jugar: ");
                int fila = sc.nextInt();
                System.out.print("Tria la columna on vols jugar: ");
                int columna = sc.nextInt();
                sc.nextLine();

                Jugada jugada = ctrlPartida.colocarFitxa(lletra, fila, columna);

                if (jugada.getJugadaValida()) {
                    //System.out.println("La jugada és vàlida.");
                    System.out.print("Vols fer commit? (true/false): ");
                    boolean commitJugada = sc.nextBoolean();
                    sc.nextLine();

                    if (commitJugada) {
                        imprimirJugada(jugada);
                        ctrlPartida.commitParaula();
                        commit = true;
                    }
                }
            } else if (opcio == 2) {
                System.out.print("Tria la fila on vols retirar: ");
                int fila = sc.nextInt();
                System.out.print("Tria la columna on vols retirar: ");
                int columna = sc.nextInt();
                sc.nextLine();
                ctrlPartida.retirarFitxa(fila, columna);
            } else {
                System.out.println("Opció no vàlida per a fitxa.");
            }

            imprimirTaulell(ctrlPartida.obtenirTaulell());
        }

        return true;
    }

    private static void canviarFitxes(Scanner sc, CtrlPartida ctrlPartida) {
        System.out.print("Tria quantes fitxes vols canviar: ");
        int numFitxes = sc.nextInt();
        sc.nextLine();

        String[] fitxes = new String[numFitxes];
        for (int i = 0; i < numFitxes; ++i) {
            System.out.print("Tria la fitxa que vols canviar: ");
            fitxes[i] = sc.nextLine();
        }
        Arrays.sort(fitxes);
        ctrlPartida.canviarFitxes(fitxes);
        imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
    }

    private static void imprimirJugada(Jugada jugada) {
        if (jugada == null) {
            System.out.println("La jugada és nul·la.");
            return;
        }

        System.out.println("\n🎯 Jugada realitzada:");
        System.out.println(" - Paraula formada: " + jugada.getParaulaFormada());
        System.out.println(" - Puntuació: " + jugada.getPuntuacio());
        System.out.println(" - És vàlida? " + (jugada.getJugadaValida() ? "Sí" : "No"));
        System.out.println(" - Caselles jugades:");

        for (Casella casella : jugada.getCasellesJugades()) {
            System.out.println("     · " + casella);
        }
    }

    private static void imprimirTorn(CtrlPartida ctrlPartida) {
        Jugador jugadorActual = ctrlPartida.obtenirJugadorActual();
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== TORN DE " + jugadorActual.obtenirNom() + " ========" + Colors.RESET);
        System.out.println(Colors.CYAN_TEXT + "Punts: " + Colors.RESET + jugadorActual.obtenirPunts());
    }

    private static void imprimirTaulell(Taulell taulell) {
        System.out.println("🎨 Llegenda del Taulell:");
        System.out.println("\033[41m   \033[0m → Multiplicador de PARAULA x3");
        System.out.println("\033[45m   \033[0m → Multiplicador de PARAULA x2");
        System.out.println("\033[44m   \033[0m → Multiplicador de LLETRA x3");
        System.out.println("\033[46m   \033[0m → Multiplicador de LLETRA x2");
        System.out.println("\033[107m   \033[0m → Casella normal");

        int size = taulell.getSize();
        for (int i = 0; i < size; ++i) System.out.print("+----");
        System.out.println("+");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Casella casella = taulell.getTaulell()[i][j];
                String colorFons = obtenirColorFons(casella);
                System.out.print("|" + colorFons + Colors.BLACK_TEXT + " " + casella + " " + Colors.RESET);
            }
            System.out.println("|");
            for (int j = 0; j < size; ++j) System.out.print("+----");
            System.out.println("+");
        }
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
