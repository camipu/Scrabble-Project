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
    Fitxa f5 = new Fitxa("S", 3);

    Fitxa f6 = new Fitxa("A", 3);
    Fitxa f7 = new Fitxa("L", 3);
    Fitxa f8 = new Fitxa("A", 3);
    Fitxa f9 = new Fitxa("D", 3);
    Fitxa f10 = new Fitxa("A", 3);
    Fitxa f11 = new Fitxa("E", 3);
    Fitxa f12 = new Fitxa("N", 3);
    //Col·locar SALA (vertical)
    joc.colocarFitxa(f1, 3, 3);
    joc.colocarFitxa(f2, 4, 3);
    joc.colocarFitxa(f3, 5, 3);
    joc.colocarFitxa(f4, 6, 3);
    //Col·locar la S connectora
    joc.colocarFitxa(f5, 7, 3);
    //Col·locar ALAS (horitzontal)
    joc.colocarFitxa(f6, 7, 4);
    joc.colocarFitxa(f7, 7, 5);
    joc.colocarFitxa(f8, 7, 6);
    joc.colocarFitxa(f9, 7, 7);
    joc.colocarFitxa(f10, 7, 8);
    joc.colocarFitxa(f11, 7, 1);
    joc.colocarFitxa(f12, 7, 2);



    joc.obtenirTaulell().imprimirTaulell();
    joc.imprimirInfoJugadors();
    joc.obtenirTaulell().imprimirTaulell();
    int [][] v= {{7 , 3}};
    HashMap<String,Integer> palabras = new HashMap<String, Integer>();
    palabras = joc.obtenirTaulell().buscaPalabrasValidas(v);
    System.out.println(palabras);

//    joc.imprimirInfoJugadors();
  }
}