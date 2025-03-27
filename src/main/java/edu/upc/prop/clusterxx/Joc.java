package edu.upc.prop.clusterxx;

import java.util.*;

public class Joc {
    int numJugadors;
    Jugador[] jugadors;
    Diccionari diccionari;
    Taulell taulell = new Taulell(15, this);
    Sac sac;

    public Joc(int numJugadors, String idioma, String[] noms) {
        this.numJugadors = numJugadors;
        this.diccionari = new Diccionari(idioma);
        this.sac = new Sac(idioma);
        this.taulell = new Taulell(15, this);

        // Inicialización correcta del array de jugadores
        this.jugadors = new Jugador[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            jugadors[i] = new Jugador(noms[i], sac);
        }
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
