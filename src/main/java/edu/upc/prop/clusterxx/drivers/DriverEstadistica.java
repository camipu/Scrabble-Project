package edu.upc.prop.clusterxx.drivers;

import edu.upc.prop.clusterxx.controladors.CtrEstadistica;
import edu.upc.prop.clusterxx.persistencia.CtrlPersistencia;

import java.util.Map;
import java.util.Scanner;

public class DriverEstadistica {

    public static void main(String[] args) {
        // Carregar estadístiques des de fitxer
        CtrEstadistica ctr = CtrEstadistica.getInstance();
        ctr.carregarEstadistiques(); // <- aquest mètode l'has d'afegir i fer que cridi a CtrlPersistencia.carregarEstadistiques()

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
            scanner.nextLine();

            switch (opcio) {
                case 1 -> {
                    System.out.print("Nom del jugador: ");
                    String nom = scanner.nextLine();
                    System.out.print("Puntuació: ");
                    int puntuacio = scanner.nextInt();
                    scanner.nextLine();
                    ctr.afegirPuntuacio(nom, puntuacio);
                    System.out.println("Puntuació afegida correctament.");
                }
                case 2 -> {
                    System.out.print("Nom del jugador: ");
                    String nom = scanner.nextLine();
                    System.out.print("Puntuació: ");
                    int puntuacio = scanner.nextInt();
                    scanner.nextLine();
                    ctr.retirarPuntuacio(nom, puntuacio);
                    System.out.println("Puntuació retirada correctament.");
                }
                case 3 -> {
                    String millorJugador = ctr.obtenirMillorJugador();
                    int millorPuntuacio = ctr.obtenirPuntuacioMaxima();
                    System.out.println("Millor jugador: " + millorJugador + " amb " + millorPuntuacio + " punts");
                }
                case 4 -> System.out.println("Puntuació mitjana: " + ctr.obtenirPuntuacioMitjana());
                case 5 -> System.out.println("Puntuació total: " + ctr.obtenirPuntuacioTotal());
                case 6 -> {
                    Map<String, Integer> puntuacions = ctr.obtenirPuntuacions();
                    if (puntuacions.isEmpty()) {
                        System.out.println("No hi ha puntuacions registrades.");
                    } else {
                        System.out.println("Llista de puntuacions (ordenades):");
                        puntuacions.entrySet()
                                .stream()
                                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " punts"));
                    }
                }
                case 7 -> {
                    System.out.print("Nom del jugador: ");
                    String nom = scanner.nextLine();
                    ctr.eliminarJugador(nom);
                    System.out.println("Jugador eliminat correctament.");
                }
                case 0 -> {
                    sortir = true;
                    // Guardar estadístiques al sortir
                    ctr.desarEstadistiques(); // <- aquest mètode també crida a CtrlPersistencia.guardarEstadistiques(...)
                    System.out.println("Estadístiques guardades. Sortint del driver.");
                }
                default -> System.out.println("Opció no vàlida. Intenta-ho de nou.");
            }
        }

        scanner.close();
    }
}
