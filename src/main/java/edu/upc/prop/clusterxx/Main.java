package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Joc joc = new Joc(2, "castellano");
    joc.imprimirInfoJugadors();
    joc.colocarFitxa(0,joc.getPersona(0).obtenirFitxa(0), 0, 0);
    joc.getTaulell().imprimirTaulell();
    joc.imprimirInfoJugadors();
//    joc.imprimirInfoJugadors();
  }

}