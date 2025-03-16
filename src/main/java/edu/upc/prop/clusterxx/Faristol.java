package edu.upc.prop.clusterxx;
import java.util.*;

//Classe Faristol


public class Faristol {
    private final Vector<Fitxa> fitxes;
    private Sac sac;

    public Faristol(Sac sac) {
        fitxes = new Vector<Fitxa>();
        this.sac = sac;
    }

    public Vector<Fitxa> obtenirFitxes() {
        return fitxes;
    }

    public void afegirFitxa(Fitxa fitxa) {
        fitxes.add(fitxa);
    }

    public boolean eliminarFitxa(Fitxa fitxa) {
        return fitxes.remove(fitxa); // `remove` retorna true si es troba i elimina l'element
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
            System.out.print("[" + fitxa + " " + Colors.YELLOW_TEXT + fitxa.getPunts() + Colors.RESET + "]");

            if (i < fitxes.size() - 1) {
                System.out.print(" "); // Espai entre fitxes
            }
        }
        System.out.println("]");
    }

}