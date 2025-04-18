package edu.upc.prop.clusterxx.drivers;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Colors;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio;
import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Jugada;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorLletra;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorParaula;
import edu.upc.prop.clusterxx.controladors.CtrlPartida;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DriverCamilaInicialiarPartida {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        inicialitzarPartida(sc);
    }

    private static void inicialitzarPartida(Scanner sc) {
        int midaTaulell = 15;
        int midaFaristol = 5;
        int numBots = 0;

        int[] dificultats = llegirDificultatsBots(sc, numBots);

        String[] nomsJugadors = llegirNomsJugadors(sc, 1);

        CtrlPartida ctrlPartida = CtrlPartida.getInstance();
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, "castellano", nomsJugadors, dificultats);

        jugarTorns(sc, ctrlPartida);
    }

    private static int[] llegirDificultatsBots(Scanner sc, int numBots) {
        int[] dificultats = new int[numBots];
        for (int i = 0; i < numBots; ++i) {
            System.out.print("Introdueix la dificultat del bot " + (i + 1) + " (1 = fàcil, 2 = mitjà, 3 = difícil): ");
            dificultats[i] = sc.nextInt();
            sc.nextLine();
        }
        return dificultats;
    }

    private static String[] llegirNomsJugadors(Scanner sc, int numJugadors) {
        String[] noms = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            System.out.print("Nom del jugador " + (i + 1) + ": ");
            noms[i] = sc.nextLine();
        }
        return noms;
    }

    private static void jugarTorns(Scanner sc, CtrlPartida ctrlPartida) {
        boolean first = true; // Variable per controlar si és el primer torn
        while (!ctrlPartida.esFinalDePartida()) {
            imprimirSeparador();
            imprimirTorn(ctrlPartida);
            imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
            boolean passatorn = false;


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
                    case 3 -> canviarFitxes(sc, ctrlPartida);
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
            System.out.print("vols colocar o retirar una fitxa? (1 = colocar, 2 = retirar): ");
            int opcio = sc.nextInt();
            sc.nextLine();

            if (opcio == 1) {
                System.out.print("Tria que fitxa vols jugar: ");
                String fitxa = sc.nextLine();
                System.out.print("Tria la fila on vols jugar: ");
                int fila = sc.nextInt();
                System.out.print("Tria la columna on vols jugar: ");
                int columna = sc.nextInt();
                sc.nextLine();

                Jugada jugada = ctrlPartida.colocarFitxa(fitxa, fila, columna);
                if (jugada.getJugadaValida()) {
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
        System.out.println("-----------------------------------------------");
    }
}
