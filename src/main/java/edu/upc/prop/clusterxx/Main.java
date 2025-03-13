package edu.upc.prop.clusterxx;

import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
//    Fitxa a = new Fitxa('A', 1);
//    Fitxa b = new Fitxa('B', 2);
//    Fitxa c = new Fitxa('C', 2);
//    Fitxa d  = new Fitxa('D', 3);
//
//    Sac sac = new Sac();
//    sac.afegirFitxa(a);
//    sac.afegirFitxa(b);
//    sac.afegirFitxa(c);
//    sac.afegirFitxa(d);

    Taulell t = new Taulell();
    t.imprimirTaulell();
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}