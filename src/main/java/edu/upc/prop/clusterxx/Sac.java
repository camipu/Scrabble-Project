package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

import java.util.*;

public class Sac {
    private final Map<Fitxa, Integer> fitxes;

    // Constructor
    public Sac() {
        this.fitxes = new LinkedHashMap<>();
    }

    public Sac(Sac copiaSac) {
        this.fitxes = copiaSac.obtenirSac();
    }

    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
    }

    public Fitxa agafarFitxa() {
        if (esBuit()) {
            throw new ExcepcioSacBuit("No es pot agafar una fitxa: el sac està buit.");
        }

        List<Fitxa> disponibles = new ArrayList<>();
        for (Map.Entry<Fitxa, Integer> entry : fitxes.entrySet()) {
            disponibles.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }

        Fitxa seleccionada = disponibles.get(new Random().nextInt(disponibles.size()));
        reduirQuantitat(seleccionada);
        return seleccionada;
    }

    public Fitxa agafarFitxa(Fitxa fitxa) {
        Fitxa f = obtenirFitxa(fitxa);
        reduirQuantitat(f);
        return f;
    }

    public int obtenirNumFitxes() {
        return fitxes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int obtenirNumFitxes(Fitxa fitxa) {
        return fitxes.getOrDefault(fitxa, 0);
    }

    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    public Map<Fitxa, Integer> obtenirSac() {
        return fitxes;
    }


    private void reduirQuantitat(Fitxa fitxa) {
        int quantitat = fitxes.getOrDefault(fitxa, 0);
        if (quantitat <= 1) {
            fitxes.remove(fitxa);
        } else {
            fitxes.put(fitxa, quantitat - 1);
        }
    }

    private Fitxa obtenirFitxa(Fitxa fitxa) {
        for (Fitxa f : fitxes.keySet()) {
            if (f.obtenirLletra().equals(fitxa.obtenirLletra())) {
                return f;
            }
        }
        throw new ExcepcioSacNoConteLaFitxa("No hi ha fitxes disponibles amb la lletra '" + fitxa.obtenirLletra() + "'");
    }
}
