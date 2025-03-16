package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Taulell t = new Taulell(5);
    Joc joc = new Joc(2,"castellano");

    Jugador toni = new Jugador("toni", joc, t);
    Jugador camila = new Jugador("camila", joc, t);
    toni.imprimirFaristol();
    toni.afegirFitxa('A');
    toni.afegirFitxa('Z');
    camila.afegirFitxa('A');
    camila.imprimirFaristol();
    toni.imprimirFaristol();
    joc.imprimirSac();
    toni.imprimirFaristol();
    toni.colocarFitxa('A', 2, 2);
    camila.colocarFitxa('A', 1, 2);
    t.imprimirTaulell();
    toni.imprimirFaristol();



  }

}