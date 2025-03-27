package edu.upc.prop.clusterxx;

import java.util.*;

public class Faristol {
    private final Vector<Fitxa> fitxes;
    private Sac sac;

    public Faristol(Sac sac) {
        this.fitxes = new Vector<>();
        this.sac = sac;
        inicialitzarFaristol();  // Omplim el faristol amb 7 fitxes
    }

    // Omple el faristol amb 7 fitxes inicials
    private void inicialitzarFaristol() {
        while (fitxes.size() < 7 && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
    }

    // Afegeix una fitxa al faristol des del sac (si hi ha espai)
    public void afegirFitxa(Fitxa fitxa) {
        if (fitxes.size() < 7) {
            Fitxa f = sac.agafarFitxa(fitxa);
            if (f != null) {
                fitxes.add(f);
            }
        }
    }

    // Elimina una fitxa i la reposa automàticament
    public boolean eliminarFitxa(Fitxa fitxa) {
        if (fitxes.remove(fitxa)) {
            reposarFitxes(); // Reposar si es treu una fitxa
            return true;
        }
        return false;
    }

    // Garanteix que el faristol sempre tingui 7 fitxes si el sac no està buit
    private void reposarFitxes() {
        while (fitxes.size() < 7 && !sac.esBuit()) {
            fitxes.add(sac.agafarFitxa());
        }
    }

    // Altres mètodes auxiliars...
    public Vector<Fitxa> obtenirFitxes() {
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
