package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

import java.util.ArrayList;
import java.util.Collections;

public class Faristol {
    private final ArrayList<Fitxa> fitxes;
    private final Sac sac;

    public Faristol(Sac sac) {
        this.fitxes = new ArrayList<>(7);
        this.sac = sac;
        inicialitzarFaristol();
    }

    private void inicialitzarFaristol() {
        while (fitxes.size() < 7 && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
    }

    public void afegirFitxa(Fitxa fitxa) {
        if (fitxes.size() < 7) {
            Fitxa f = sac.agafarFitxa(fitxa);
            fitxes.add(f);
        }
        else throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir la fitxa" + fitxa);
    }

    public void afegirFitxa() {
        if (fitxes.size() < 7) {
            Fitxa f = sac.agafarFitxa();
            fitxes.add(f);
        }
        else throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir cap fitxa");
    }

    public void eliminarFitxa(Fitxa fitxa) {
        if (fitxes.remove(fitxa)) {
            reposarFitxes();
        }
        else throw new ExcepcioFaristolNoConteLaFitxa("No es pot eliminar fitxa " + fitxa + ", el faristol no la conté.");
    }

    private void reposarFitxes() {
        while (fitxes.size() < 7 && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
    }

    public ArrayList<Fitxa> obtenirFitxes() {
        return fitxes;
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
