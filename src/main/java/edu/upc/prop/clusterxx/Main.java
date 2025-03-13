package edu.upc.prop.clusterxx;

import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
    Fitxa a = new Fitxa('A', 1);

    Taulell t = new Taulell();
    t.colocarFitxa(a, 2, 3);
    //t.colocarFitxa(a, 2, 3);
    //t.colocarFitxa(a, 1, 30);
    t.imprimirTaulell();
  }

}