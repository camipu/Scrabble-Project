package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Joc joc = new Joc(2, "castellano");
    joc.imprimirInfoJugadors();
    joc.colocarFitxa(1,joc.getPersona(1).obtenirFitxa(2), 2, 2);
    joc.imprimirInfoJugadors();
//    joc.getTaulell().imprimirTaulell();
//    joc.imprimirInfoJugadors();
  }

}