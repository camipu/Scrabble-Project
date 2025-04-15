package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlPartida;

import java.util.Scanner;

public class DriverCamilaInicialiarPartida {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introdueix la mida del taulell: ");
        int midaTaulell = sc.nextInt();

        System.out.print("Introdueix la mida del faristol: ");
        int midaFaristol = sc.nextInt();

        System.out.print("Introdueix quants bots vols");
        int numBots = sc.nextInt();

        int[] dificultats = new int[numBots];
        for (int i = 0; i < dificultats.length; ++i) {
            System.out.print("Introdueix la dificultat del bot " + (i + 1) + " (1 = fàcil, 2 = mitjà, 3 = difícil): ");
            dificultats[i] = sc.nextInt();
        }

        sc.nextLine(); // Netegem el salt de línia

        System.out.print("Introdueix l'idioma (ex: catala): ");
        String idioma = sc.nextLine();

        System.out.print("Introdueix el nombre de jugadors: ");
        int numJugadors = sc.nextInt();
        sc.nextLine(); // Netegem el salt de línia

        String[] nomsJugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            System.out.print("Nom del jugador " + (i + 1) + ": ");
            nomsJugadors[i] = sc.nextLine();
        }

        CtrlPartida ctrlPartida = CtrlPartida.getInstance();
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, 1, idioma, nomsJugadors, dificultats);
    }
}
