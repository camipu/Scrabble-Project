package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Taulell t = new Taulell(5, "castellano");

    Jugador toni = new Jugador("toni", t);
    Jugador camila = new Jugador("camila", t);
    toni.imprimirFaristol();
    toni.afegirFitxa('A');
    toni.afegirFitxa('Z');
    camila.afegirFitxa('A');
    camila.imprimirFaristol();
    toni.imprimirFaristol();
    t.imprimirSac();
    toni.imprimirFaristol();
    toni.colocarFitxa('A', 2, 2);
    camila.colocarFitxa('A', 1, 2);
    t.imprimirTaulell();
    toni.imprimirFaristol();



  }

}