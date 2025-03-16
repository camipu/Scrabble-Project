package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Sac sac = new Sac("castellano");
    Taulell t = new Taulell(5, "castellano");

    Jugador toni = new Jugador("toni", sac, t);
    toni.imprimirFaristol();
    toni.afegirFitxa('A');
    toni.imprimirFaristol();
    toni.colocarFitxa('A', 2, 2);
    t.imprimirTaulell();
    toni.imprimirFaristol();



  }

}