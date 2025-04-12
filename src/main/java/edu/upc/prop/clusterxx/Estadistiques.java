package edu.upc.prop.clusterxx;

import java.util.AbstractMap.SimpleEntry;
import java.util.PriorityQueue;

public class Estadistiques {
    private PriorityQueue<SimpleEntry<String, Integer>> puntuacions;
    private int puntuacioMinima;
    private int puntuacioTotal;
    private int puntuacioMitjana;

    public Estadistiques() {
        this.puntuacioTotal = 0;
        this.puntuacioMinima = Integer.MAX_VALUE;
        this.puntuacioMitjana = 0;

        // Comparator para ordenar por puntuación de mayor a menor
        this.puntuacions = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue()
        );
    }

    public void afegirPuntuacio(int puntuacio, String jugador) {
        this.puntuacioTotal += puntuacio;

        if (puntuacio < this.puntuacioMinima) {
            this.puntuacioMinima = puntuacio;
        }

        // Crear un par y añadirlo a la PriorityQueue
        SimpleEntry<String, Integer> puntuacioJugador = new SimpleEntry<>(jugador, puntuacio);
        puntuacions.add(puntuacioJugador);
    }

    public void calcularPuntuacioMitjana() {
        if (!puntuacions.isEmpty()) {
            this.puntuacioMitjana = this.puntuacioTotal / puntuacions.size();
        }
    }

    public SimpleEntry<String, Integer> getPuntuacioMaxima() {
        return puntuacions.peek(); // Devuelve el jugador con la puntuación más alta
    }

    // Getters
    public int getPuntuacioTotal() {
        return puntuacioTotal;
    }


    public int getPuntuacioMinima() {
        return puntuacioMinima;
    }

    public int getPuntuacioMitjana() {
        return puntuacioMitjana;
    }

    public PriorityQueue<SimpleEntry<String, Integer>> getPuntuacions() {
        return puntuacions;
    }
}