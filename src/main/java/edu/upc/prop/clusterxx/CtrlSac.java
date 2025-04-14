package edu.upc.prop.clusterxx;

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

    public void mostrarContingut() {
        sac.mostrarContingut();
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
