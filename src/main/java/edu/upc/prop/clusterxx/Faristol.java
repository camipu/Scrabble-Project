package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;

import java.util.ArrayList;
import java.util.Collections;

public class Faristol {
    private static final int MAX_FITXES = 7;
    private final ArrayList<Fitxa> fitxes;
    private final Sac sac;

    public Faristol(Sac sac) {
        this.fitxes = new ArrayList<>(MAX_FITXES);
        this.sac = sac;
        reposarFitxes();  // Iniciem faristol amb fitxes del sac
    }

    public void afegirFitxa(Fitxa fitxa) {
        if (fitxes.size() >= MAX_FITXES) {
            throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir la fitxa " + fitxa);
        }

        Fitxa f = sac.agafarFitxa(fitxa);
        fitxes.add(f);
    }

    public void afegirFitxa() {
        if (fitxes.size() >= MAX_FITXES) {
            throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir cap fitxa");
        }

        Fitxa f = sac.agafarFitxa();
        fitxes.add(f);
    }

    public void eliminarFitxa(Fitxa fitxa) {
        if (!fitxes.remove(fitxa)) {
            throw new ExcepcioFaristolNoConteLaFitxa("No es pot eliminar fitxa " + fitxa + ", el faristol no la conté.");
        }
        reposarFitxes();
    }

    /**
     * Omple el faristol fins al màxim amb fitxes del sac (si n'hi ha disponibles).
     */
    private void reposarFitxes() {
        while (fitxes.size() < MAX_FITXES && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
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
