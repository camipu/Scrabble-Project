package edu.upc.prop.clusterxx;

import java.util.AbstractMap.SimpleEntry;
import java.util.PriorityQueue;

/**
 * Classe que gestiona les estadístiques d'una partida o conjunt de partides.
 * Permet emmagatzemar les puntuacions dels jugadors, així com calcular la puntuació mínima,
 * la puntuació total i la mitjana.
 *
 * Utilitza una cua de prioritat per ordenar les puntuacions.
 */
public class Estadistiques {
    private PriorityQueue<SimpleEntry<String, Integer>> puntuacions;
    private int puntuacioMinima;
    private int puntuacioTotal;
    private int puntuacioMitjana;

    /**
     * Constructor per defecte que inicialitza les estadístiques.
     * Estableix la puntuació total i mitjana a 0, i la mínima al valor màxim d'enter.
     * Crea una cua de prioritat per emmagatzemar les puntuacions,
     * ordenades de major a menor valor.
     */
    public Estadistiques() {
        this.puntuacioTotal = 0;
        this.puntuacioMinima = Integer.MAX_VALUE;
        this.puntuacioMitjana = 0;

        // Comparator para ordenar por puntuación de mayor a menor
        this.puntuacions = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue()
        );
    }

    /**
     * Afegeix una puntuació per a un jugador determinat.
     * Si la puntuació és vàlida (no negativa), s’actualitzen la puntuació total,
     * la mínima i s’afegeix la nova entrada a la cua de prioritats.
     *
     * @param puntuacio Puntuació obtinguda pel jugador
     * @param jugador Nom del jugador associat a la puntuació
     */
    public void afegirPuntuacio(int puntuacio, String jugador) {
        if (puntuacio < 0) {
            System.out.print("La puntuació no pot ser negativa.");
        }
        else {
            this.puntuacioTotal += puntuacio;

            if (puntuacio < this.puntuacioMinima) {
                this.puntuacioMinima = puntuacio;
            }

            // Crear un par y añadirlo a la PriorityQueue
            SimpleEntry<String, Integer> puntuacioJugador = new SimpleEntry<>(jugador, puntuacio);
            puntuacions.add(puntuacioJugador);
            }
    }

    /**
     * Calcula la puntuació mitjana dels jugadors i l’emmagatzema.
     * La mitjana es calcula dividint la puntuació total entre el nombre de jugadors.
     * Si no hi ha cap puntuació registrada, no es fa cap càlcul.
     */
    public void calcularPuntuacioMitjana() {
        if (!puntuacions.isEmpty()) {
            this.puntuacioMitjana = this.puntuacioTotal / puntuacions.size();
        }
    }

    /**
     * Retorna la puntuació més alta registrada fins al moment.
     * @return Una entrada amb el nom del jugador i la seva puntuació més alta,
     *         o {@code null} si no hi ha cap puntuació registrada
     */
    public SimpleEntry<String, Integer> getPuntuacioMaxima() {
        return puntuacions.peek(); // Devuelve el jugador con la puntuación más alta
    }

    /**
     * Retorna la puntuació total acumulada de tots els jugadors.
     *
     * @return La suma de totes les puntuacions afegides.
     */
    public int getPuntuacioTotal() {
        return puntuacioTotal;
    }

    /**
     * Retorna la puntuació mínima de tots els jugadors.
     *
     * @return La puntuacio mínima.
     */
    public int getPuntuacioMinima() {
        return puntuacioMinima;
    }

    /**
     * Retorna la puntuació mitjana de tots els jugadors.
     *
     * @return La puntuacio mitjana.
     */
    public int getPuntuacioMitjana() {
        return puntuacioMitjana;
    }

    /**
     * Retorna totes les puntuacions dels jugadors registrats.
     *
     * @return Una Priority Queue amb els noms dels jugadors i les seves
     * respectives puntuacions.
     */
    public PriorityQueue<SimpleEntry<String, Integer>> getPuntuacions() {
        return puntuacions;
    }
}