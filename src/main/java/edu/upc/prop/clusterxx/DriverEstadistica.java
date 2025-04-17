package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrEstadistica;

import java.util.AbstractMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DriverEstadistica {

    public static void main(String[] args) {
        CtrEstadistica ctr = CtrEstadistica.getInstance();
        Scanner scanner = new Scanner(System.in);

        boolean sortir = false;
        while (!sortir) {
            System.out.println("\n==== MENÚ D'ESTADÍSTIQUES ====");
            System.out.println("1. Afegir una puntuació");
            System.out.println("2. Obtenir millor puntuació");
            System.out.println("3. Obtenir puntuació mitjana");
            System.out.println("4. Obtenir puntuació total");
            System.out.println("5. Obtenir puntuació mínima");
            System.out.println("6. Obtenir totes les puntuacions");
            System.out.println("0. Sortir");
            System.out.print("Escull una opció: ");

            int opcio = scanner.nextInt();
            scanner.nextLine(); // consumir salt de línia

            switch (opcio) {
                case 1:
                    System.out.print("Nom del jugador: ");
                    String nom = scanner.nextLine();
                    System.out.print("Puntuació: ");
                    int puntuacio = scanner.nextInt();
                    scanner.nextLine(); // consumir salt de línia
                    ctr.afegirPuntuacio(nom, puntuacio);
                    System.out.println("Puntuació afegida correctament.");
                    break;

                case 2:
                    AbstractMap.SimpleEntry<String, Integer> millor = ctr.obtenirMillorPuntuacio();
                    if (millor != null) {
                        System.out.println("Millor puntuació: " + millor.getKey() + " amb " + millor.getValue() + " punts");
                    } else {
                        System.out.println("No hi ha puntuacions registrades.");
                    }
                    break;

                case 3:
                    System.out.println("Puntuació mitjana: " + ctr.obtenirPuntuacioMitjana());
                    break;

                case 4:
                    System.out.println("Puntuació total: " + ctr.obtenirPuntuacioTotal());
                    break;

                case 5:
                    int min = ctr.obtenirPuntuacioMinima();
                    if (min != Integer.MAX_VALUE) {
                        System.out.println("Puntuació mínima: " + min);
                    } else {
                        System.out.println("No hi ha puntuacions registrades.");
                    }
                    break;

                case 6:
                    PriorityQueue<AbstractMap.SimpleEntry<String, Integer>> cues = new PriorityQueue<>(ctr.obtenirPuntuacions());
                    if (cues.isEmpty()) {
                        System.out.println("No hi ha puntuacions registrades.");
                    } else {
                        System.out.println("Llista de puntuacions (ordenades):");
                        int i = 1;
                        while (!cues.isEmpty()) {
                            AbstractMap.SimpleEntry<String, Integer> e = cues.poll();
                            System.out.println(i + ". " + e.getKey() + ": " + e.getValue() + " punts");
                            i++;
                        }
                    }
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

