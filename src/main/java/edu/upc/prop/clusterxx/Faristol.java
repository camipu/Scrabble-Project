package edu.upc.prop.clusterxx;
import java.util.*;

//Classe Faristol


public class Faristol {
    private Vector<Fitxa> fitxes;

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

    public int obtenirNumeroDeFitxes() {
        return fitxes.size();
    }

    public void barrejarFitxes() {
        Collections.shuffle(fitxes);
    }

    public void imprimirFaristol() {
        System.out.print("[");
        for (Fitxa fitxa : fitxes) {
            System.out.print(" " + fitxa);
        }
        System.out.println(" ]");
    }
}