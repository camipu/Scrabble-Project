package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlPartida;

import java.util.Scanner;

public class ProvaDriverParida {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introdueix la mida del taulell: ");
        int midaTaulell = sc.nextInt();

        System.out.print("Introdueix la mida del faristol: ");
        int midaFaristol = sc.nextInt();

        System.out.print("Introdueix la dificultat (0 = fàcil, 1 = mitjà, 2 = difícil): ");
        int dificultat = sc.nextInt();
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

        // Inicialitzem el controlador amb la informació recollida
        CtrlPartida partida = new CtrlPartida(midaTaulell, midaFaristol, dificultat, idioma, nomsJugadors);

        System.out.println("La partida ha estat inicialitzada correctament.");

        while(!partida.acabada()) {
            System.out.println("Torn del jugador: " + partida.obtenirJugadorActual().obtenirNom());
            partida.obtenirTaulell().imprimirTaulell();
            partida.obtenirJugadorActual().obtenirFaristol().imprimirFaristol();
            System.out.println("Quina fitxa vols col·locar?");
            int index = sc.nextInt();
            System.out.println("En quina posició? (fila, columna)");
            int fila = sc.nextInt();
            int columna = sc.nextInt();

            partida.colocarFitxa(partida.obtenirJugadorActual().obtenirFaristol().obtenirFitxa(index), fila, columna);
            System.out.println("Fitxa col·locada correctament.");
            partida.obtenirTaulell().imprimirTaulell();
        }
    }
}
