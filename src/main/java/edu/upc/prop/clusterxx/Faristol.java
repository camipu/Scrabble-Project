package edu.upc.prop.clusterxx;

import java.util.*;

public class Faristol {
    private final ArrayList<Fitxa> fitxes;
    private Sac sac;

    public Faristol(Sac sac) {
        // Prealocatem capacitat per 7 elements per evitar redimensionaments
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
            if (f != null) {
                fitxes.add(f);
            }
        }
    }

    public boolean eliminarFitxa(Fitxa fitxa) {
        if (fitxes.remove(fitxa)) {
            reposarFitxes();
            return true;
        }
        return false;
    }

    private void reposarFitxes() {
        while (fitxes.size() < 7 && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
    }

    // Per compatibilitat, mantenim el tipus de retorn Vector, però podem considerar canviar-lo
    public ArrayList<Fitxa> obtenirFitxes() {
        return fitxes;
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
            System.out.print("[" + fitxa + " " + Colors.YELLOW_TEXT + fitxa.obtenirPunts() + Colors.RESET + "]");
            if (i < fitxes.size() - 1) System.out.print(" ");
        }
        System.out.println("]");
    }
}