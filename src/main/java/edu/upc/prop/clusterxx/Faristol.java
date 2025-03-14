package edu.upc.prop.clusterxx;
import java.util.*;

//Classe Faristol


public class Faristol {
    private final Vector<Fitxa> fitxes;

    public Faristol() {
        fitxes = new Vector<Fitxa>();
    }

    public Vector<Fitxa> obtenirFitxes() {
        return fitxes;
    }

    public void afegirFitxa(Fitxa fitxa) {
        fitxes.add(fitxa);
    }

    public void eliminarFitxa(Fitxa fitxa) {
        fitxes.remove(fitxa);
    }

    public Fitxa obtenirFitxa(int index) {
        return fitxes.get(index);
    }

    public int obtenirNumFitxes() {
        return fitxes.size();
    }

    public void barrejarFitxes() {
        Collections.shuffle(fitxes);
    }

    public void imprimirFaristol() {
        System.out.print("[");
        for (int i = 0; i < fitxes.size(); i++) {
            Fitxa fitxa = fitxes.get(i);
            System.out.print("[" + fitxa.getLletra() + " " + Colors.YELLOW_TEXT + fitxa.getPunts() + Colors.RESET + "]");

            if (i < fitxes.size() - 1) {
                System.out.print(" "); // Espai entre fitxes
            }
        }
        System.out.println("]");
    }

}