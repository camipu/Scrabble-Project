package edu.upc.prop.clusterxx;

import java.util.*;

public class Joc {
    int numJugadors;
    Jugador[] jugadors;
    Diccionari diccionari;
    Taulell taulell = new Taulell(15);
    Sac sac;

    public Joc(int numJugadors, String idioma) {
        this.numJugadors = numJugadors;
        this.diccionari = new Diccionari(idioma);
        this.sac = new Sac(idioma);
        this.jugadors = new Jugador[numJugadors];

        Scanner scanner = new Scanner(System.in);

        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT +
                " 👾 Benvingut a Scrabble 🎲 " + Colors.RESET);
        System.out.println(Colors.PURPLE_TEXT + "⚡ Es jugarà amb l'idioma: " + idioma.toUpperCase() + Colors.RESET);
        System.out.println();

        for (int i = 0; i < numJugadors; i++) {
            System.out.print(Colors.CYAN_TEXT + "👉 Jugador " + (i + 1) + ", introdueix el teu nom: " + Colors.RESET);

            if (scanner.hasNextLine()) {
                String nombre = scanner.nextLine();
                jugadors[i] = new Jugador(nombre, sac);
                System.out.println(Colors.GREEN_BACKGROUND + Colors.BLACK_TEXT +
                        " ✅ " + nombre + " ha sigut afegit al joc. " + Colors.RESET);
            } else {
                System.out.println(Colors.RED_TEXT + "❌ Error: No s'ha pogut llegir correctament el nom." + Colors.RESET);
                return;
            }
            System.out.println();
        }

        System.out.println(Colors.RED_BACKGROUND + Colors.WHITE_TEXT +
                " 🚀TOTS ELS JUGADORS LLESTOS, QUE COMENCI EL JOC! " + Colors.RESET);
    }

    public boolean validarParaula(String paraula) {
        return diccionari.esParaulaValida(paraula);
    }

    public void imprimirSac() {
        sac.mostrarContingut();
    }

    public Sac getSac() {
        return sac;
    }
    public Diccionari getDiccionari() {return diccionari;}
    public Taulell getTaulell() {return taulell;}
    public Jugador getPersona(int i) {return jugadors[i];}

    public void colocarFitxa(int numJugador, Fitxa fitxa, int i, int j) {
        taulell.colocarFitxa(fitxa, i, j);
        jugadors[numJugador].eliminarFitxa(fitxa);
    };

    public void imprimirInfoJugadors() {
        for (int i = 0; i < numJugadors; i++) {jugadors[i].imprimirInfo();}
    }

    public boolean esParaulaValida(String paraula){
        return diccionari.esParaulaValida(paraula);
    }
}
