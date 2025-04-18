package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlPartida;

import java.util.List;
import java.util.Scanner;

public class DriverCamilaInicialiarPartida {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

//        System.out.print("Introdueix la mida del taulell: ");
        int midaTaulell = 15;
//
//        System.out.print("Introdueix la mida del faristol: ");
        int midaFaristol = 5;
//
//        System.out.print("Introdueix quants bots vols");
        int numBots = 0;

        int[] dificultats = new int[numBots];
        for (int i = 0; i < dificultats.length; ++i) {
            System.out.print("Introdueix la dificultat del bot " + (i + 1) + " (1 = fàcil, 2 = mitjà, 3 = difícil): ");
            dificultats[i] = sc.nextInt();
        }

        //sc.nextLine(); // Netegem el salt de línia

        //System.out.print("Introdueix l'idioma (ex: catala): ");
        String idioma = "castellano";

//        System.out.print("Introdueix el nombre de jugadors: ");
        int numJugadors = 2;
       // sc.nextLine(); // Netegem el salt de línia

        String[] nomsJugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            System.out.print("Nom del jugador " + (i + 1) + ": ");
            nomsJugadors[i] = sc.nextLine();
        }


        CtrlPartida ctrlPartida = CtrlPartida.getInstance();
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, "castellano", nomsJugadors,dificultats);

//        ctrlPartida.obtenirTaulell().colocarFitxa(new Fitxa("C",3), 7, 7);
//        imprimirFaristol(ctrlPartida.obtenirJugadorActual().obtenirFaristol());
//        Jugador jugador = ctrlPartida.obtenirJugadorActual();
//        boolean bot = jugador.esBot();
//        Jugada jugadabot;
//        jugadabot = ctrlPartida.jugadaBot();


//        ctrlPartida.colocarFitxa(1, 7, 8);
//        ctrlPartida.colocarFitxa(1, 7, 9);
//        ctrlPartida.commitParaula();


//        System.out.print("S'ha inicialitzat la partida amb " + midaTaulell + "x" + midaTaulell + " i " + midaFaristol + " fitxes al faristol.\n");
//
//        while (!ctrlPartida.acabada()) {
//            System.out.println("Torn del jugador: " + ctrlPartida.obtenirJugadorActual().obtenirNom());
//            boolean canviTorn = false;
//            while (!canviTorn) {
//                System.out.println("Torn" + ctrlPartida.obtenirTorn());
//                System.out.println("Torn del jugador: " + ctrlPartida.obtenirJugadorActual().obtenirNom());
//                imprimirTaulell(ctrlPartida.obtenirTaulell());
//                ctrlPartida.obtenirJugadorActual().obtenirFaristol().imprimirFaristol();
//                mostrarOpcions();
//                int opcio = sc.nextInt();
//                sc.nextLine(); // Netegem el salt de línia
//            }
//
//        }
    }

    private static void imprimirTaulell(Taulell taulell) {
        int size = taulell.getSize();
        for (int i = 0; i < size; ++i) {
            System.out.print("+----");
        }
        System.out.println("+");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Casella casella = taulell.getTaulell()[i][j];
                String colorFons = obtenirColorFons(casella);
                System.out.print("|" + colorFons + Colors.BLACK_TEXT + " " + casella + " " + Colors.RESET);
            }
            System.out.println("|");

            for (int j = 0; j < size; ++j) {
                System.out.print("+----");
            }
            System.out.println("+");
        }
    }

    private static String obtenirColorFons(Casella casella) {
        if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula) {
            EstrategiaPuntuacio.EstrategiaMultiplicadorParaula estrategia = (EstrategiaPuntuacio.EstrategiaMultiplicadorParaula) casella.obtenirEstrategia();
            return estrategia.obtenirMultiplicador() == 3 ? Colors.RED_BACKGROUND : Colors.PURPLE_BACKGROUND;
        } else if (casella.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra) {
            EstrategiaPuntuacio.EstrategiaMultiplicadorLletra estrategia = (EstrategiaPuntuacio.EstrategiaMultiplicadorLletra) casella.obtenirEstrategia();
            return estrategia.obtenirMultiplicador() == 3 ? Colors.BLUE_BACKGROUND : Colors.CYAN_BACKGROUND;
        } else {
            // Caselles normals -> Blanc brillant (si el terminal ho suporta)
            return "\033[107m";  // Alternativa més brillant per a WHITE_BACKGROUND
        }
    }

    private static void mostrarOpcions() {
        System.out.println("===== MENÚ DE PARTIDA =====");
        System.out.println("1. Jugar paraula");
        System.out.println("2. Passar torn");
        System.out.println("3. Canviar fitxes");
        System.out.println("4. Mostrar faristol");
        System.out.println("5. Mostrar taulell");
        System.out.println("6. Acabar partida");
        System.out.print("Selecciona una opció (1-6): ");
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
        public void mostrarContingutSac(Sac sac) {
        sac.obtenirSac().forEach((fitxa, quantitat) ->
                System.out.println(fitxa.obtenirLletra() + " -> " + quantitat + " fitxes, " + fitxa.obtenirPunts() + " punts"));
    }
}
