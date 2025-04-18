package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrEstadistica;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

public class DriverEstadistica {

    public static void main(String[] args) {
        CtrEstadistica ctr = CtrEstadistica.getInstance();
        Scanner scanner = new Scanner(System.in);

        boolean sortir = false;
        while (!sortir) {
            System.out.println("\n==== MENÚ D'ESTADÍSTIQUES ====");
            System.out.println("1. Afegir una puntuació");
            System.out.println("2. Retirar una puntuació");
            System.out.println("3. Obtenir millor jugador");
            System.out.println("4. Obtenir puntuació mitjana");
            System.out.println("5. Obtenir puntuació total");
            System.out.println("6. Obtenir totes les puntuacions");
            System.out.println("7. Eliminar jugador");
            System.out.println("0. Sortir");
            System.out.print("Escull una opció: ");

            int opcio = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcio) {
                case 1:
                    System.out.print("Nom del jugador: ");
                    String nom = scanner.nextLine();
                    System.out.print("Puntuació: ");
                    int puntuacio = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    ctr.afegirPuntuacio(nom, puntuacio);
                    System.out.println("Puntuació afegida correctament.");
                    break;

                case 2:
                    System.out.print("Nom del jugador: ");
                    nom = scanner.nextLine();
                    System.out.print("Puntuació: ");
                    puntuacio = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    ctr.retirarPuntuacio(nom, puntuacio);
                    System.out.println("Puntuació retirada correctament.");
                    break;

                case 3:
                    String millorJugador = ctr.obtenirMillorJugador();
                    int millorPuntuacio = ctr.obtenirPuntuacioMaxima();
                    if (millorJugador != null) {
                        System.out.println("Millor jugador: " + millorJugador + " amb " + millorPuntuacio + " punts");
                    } else {
                        System.out.println("No hi ha puntuacions registrades.");
                    }
                    break;

                case 4:
                    System.out.println("Puntuació mitjana: " + ctr.obtenirPuntuacioMitjana());
                    break;

                case 5:
                    System.out.println("Puntuació total: " + ctr.obtenirPuntuacioTotal());
                    break;

                case 6:
                    Map<String, Integer> puntuacions = ctr.obtenirPuntuacions();
                    if (puntuacions.isEmpty()) {
                        System.out.println("No hi ha puntuacions registrades.");
                    } else {
                        System.out.println("Llista de puntuacions (ordenades):");
                        // Mostrar puntuaciones ordenadas
                        puntuacions.entrySet()
                                .stream()
                                .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())  // Ordenamos de mayor a menor
                                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " punts"));
                    }
                    break;

                case 7:
                    System.out.print("Nom del jugador: ");
                    nom = scanner.nextLine();
                    ctr.eliminarJugador(nom);
                    System.out.println("Jugador eliminat correctament.");
                    break;

                case 0:
                    sortir = true;
                    System.out.println("Sortint del driver.");
                    break;

                default:
                    System.out.println("Opció no vàlida. Intenta-ho de nou.");
            }
        }

        scanner.close();
    }
}
