package edu.upc.prop.clusterxx;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    Joc joc = new Joc(2, "castellano", new String[]{"María", "Juan"});
    joc.imprimirInfoJugadors();
    joc.colocarFitxa(0,joc.obtenirPersona(0).obtenirFitxa(0), 0, 0);
    joc.obtenirTaulell().imprimirTaulell();
    joc.imprimirInfoJugadors();
    joc.colocarFitxa(1,joc.obtenirPersona(1).obtenirFitxa(0), 1, 0);
    joc.colocarFitxa(0,joc.obtenirPersona(0).obtenirFitxa(1), 0, 1);
    joc.obtenirTaulell().imprimirTaulell();
    int [][] v= {{1 , 0},{0 , 1}};
    HashMap<String,Integer> palabras = new HashMap<String, Integer>();
    palabras = joc.obtenirTaulell().buscaPalabrasValidas(v);
    System.out.println(palabras);
//    joc.imprimirInfoJugadors();
  }
}