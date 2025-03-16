package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    Fitxa a = new Fitxa('A', 1);
    Fitxa b = new Fitxa('(', 1);
    Fitxa c = new Fitxa('C', 2);
    Fitxa d = new Fitxa('D', 3);
    Faristol f1 = new Faristol();

    f1.afegirFitxa(a);
    f1.afegirFitxa(b);
    f1.afegirFitxa(c);
    f1.afegirFitxa(d);
    f1.barrejarFitxes();
    f1.imprimirFaristol();
    Taulell t = new Taulell(5);

    Jugador toni = new Jugador("toni", f1, t);
    toni.colocarFitxa(a, 2, 3);
    toni.colocarFitxa(b, 1, 3);
    t.imprimirTaulell();

    //Nuevo diccionario
    Diccionari dic = new Diccionari("castellano");
    if (dic.esPalabraValida("amarillo")) System.out.print("| " + Colors.YELLOW_TEXT + "vàlida" + Colors.RESET + " ") ;
  }

}