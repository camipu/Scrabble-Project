package edu.upc.prop.clusterxx;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    Joc joc = new Joc(2, "castellano", new String[]{"María", "Juan"});
    joc.imprimirInfoJugadors();
    joc.colocarFitxa(0,joc.obtenirPersona(0).obtenirFitxa(0), 0, 0);
    Fitxa f1 = new Fitxa("C", 3);
    Fitxa f2 = new Fitxa("A", 3);
    Fitxa f3 = new Fitxa("S", 3);
    Fitxa f4 = new Fitxa("A", 3);
    joc.colocarFitxa(f1, 3, 3);
    joc.colocarFitxa(f2, 4, 3);
    joc.colocarFitxa(f3, 5, 3);
    joc.colocarFitxa(f4, 6, 3);
    joc.obtenirTaulell().imprimirTaulell();
    joc.imprimirInfoJugadors();
    joc.obtenirTaulell().imprimirTaulell();
    int [][] v= {{6 , 3}};
    HashMap<String,Integer> palabras = new HashMap<String, Integer>();
    palabras = joc.obtenirTaulell().buscaPalabrasValidas(v);
    System.out.println(palabras);
//    joc.imprimirInfoJugadors();
  }
}