package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

import java.util.ArrayList;
import java.util.Collections;

public class Faristol {
    private final int size;
    private final ArrayList<Fitxa> fitxes;

    public Faristol(int size) {
        this.fitxes = new ArrayList<>(size);
        this.size = size;
    }

    public boolean esPle(){
        return fitxes.size() >= size;
    }

    public ArrayList<Fitxa> obtenirFitxes() {
        return new ArrayList<>(fitxes); // Retornem còpia per evitar modificació externa
    }

    public Fitxa obtenirFitxa(int index) {
        if (index < 0 || index >= fitxes.size()) {
            throw new IndexOutOfBoundsException("Índex fora de rang al faristol.");
        }
        return fitxes.get(index);
    }

    public int obtenirNumFitxes() {
        return fitxes.size();
    }


    public void afegirFitxa(Fitxa fitxa) {
        if (esPle()) {
            throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir la fitxa " + fitxa);
        }
        fitxes.add(fitxa);
    }


    public void eliminarFitxa(Fitxa fitxa) {
        if (!fitxes.remove(fitxa)) {
            throw new ExcepcioFaristolNoConteLaFitxa("No es pot eliminar fitxa " + fitxa + ", el faristol no la conté.");
        }
    }

    public void barrejarFitxes() {
        Collections.shuffle(fitxes);
    }

    public void imprimirFaristol() {
        System.out.print("[");
        for (int i = 0; i < fitxes.size(); i++) {
            Fitxa fitxa = fitxes.get(i);
            System.out.print("[" + fitxa + " " + Colors.YELLOW_TEXT + fitxa.obtenirPunts() + Colors.RESET + "]");
            if (i < fitxes.size() - 1) System.out.print(" ");
        }
        System.out.println("]");
    }
}
