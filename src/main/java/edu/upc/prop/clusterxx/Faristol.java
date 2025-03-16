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

    public void afegirFitxa(char lletra) {
        Fitxa fitxa = sac.agafarFitxa(lletra);
        if (fitxa != null) {
            fitxes.add(fitxa);  // Afegim la fitxa si no és null
        }
    }

    public void afegirFitxa(Fitxa fitxa) {
       fitxes.add(fitxa);
    }

    public boolean eliminarFitxa(char lletra) {
        for (Fitxa fitxa : fitxes) {
            if (fitxa.getLletra() == lletra) {
                return fitxes.remove(fitxa); // Elimina la fitxa trobada
            }
        }
        return false;
    }

    public boolean eliminarFitxa(Fitxa fitxa) {
        return fitxes.remove(fitxa);
    }

    // Mètode per obtenir la fitxa a un índex específic
    public Fitxa obtenirFitxa(int index) {
        return fitxes.get(index);
    }

    // Mètode per obtenir el nombre de fitxes al Faristol
    public int obtenirNumFitxes() {
        return fitxes.size();
    }

    // Mètode per barrejar les fitxes al Faristol
    public void barrejarFitxes() {
        Collections.shuffle(fitxes);
    }

    // Mètode per imprimir les fitxes del Faristol
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
