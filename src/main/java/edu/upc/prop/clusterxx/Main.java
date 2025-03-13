package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Fitxa a = new Fitxa('A', 1);
    Fitxa b = new Fitxa('B', 1);
    Fitxa c = new Fitxa('C', 2);
    Fitxa d = new Fitxa('D', 3);
    Faristol f1 = new Faristol();

    f1.afegirFitxa(a);
    f1.afegirFitxa(b);
    f1.afegirFitxa(c);
    f1.afegirFitxa(d);
    f1.barrejarFitxes();
    f1.imprimirFaristol();




    Taulell t = new Taulell();
    t.colocarFitxa(a, 2, 3);
    t.colocarFitxa(b, 2, 4);
    t.colocarFitxa(c, 2, 5);
    t.colocarFitxa(d, 2, 6);

    t.colocarFitxa(a, 0, 5);
    t.colocarFitxa(b, 1, 5);
    t.colocarFitxa(d, 3, 5);
    //t.colocarFitxa(a, 2, 3); Error casella repetida
    //t.colocarFitxa(a, 1, 30); Error casella NO vàlida
    t.imprimirTaulell();
  }

}