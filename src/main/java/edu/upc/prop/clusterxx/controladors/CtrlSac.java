package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Sac;

public class CtrlSac {
    private final Sac sac;

    public CtrlSac(Sac sac) {
        this.sac = sac;
    }

    public Fitxa agafarFitxaAleatoria() {
        return sac.agafarFitxa();
    }

    public Fitxa agafarFitxa(Fitxa fitxa) {
        return sac.agafarFitxa(fitxa);
    }

    public void afegirFitxa(Fitxa fitxa) {
        sac.afegirFitxa(fitxa);
    }

    public boolean esBuit() {
        return sac.esBuit();
    }

    public int quantitatFitxes(Fitxa fitxa) {
        return sac.obtenirNumFitxes(fitxa);
    }

    public int obtenirNumFitxes() {
        return sac.obtenirNumFitxes();
    }

    public Sac getSac() {
        return sac;
    }
}
