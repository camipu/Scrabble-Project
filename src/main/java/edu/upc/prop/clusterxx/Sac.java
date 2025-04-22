package edu.upc.prop.clusterxx;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Representa el sac de fitxes del joc.
 * Conté totes les fitxes que encara no s’han utilitzat i la quantitat disponible de cadascuna.
 *
 * El sac permet extreure fitxes de manera aleatòria i controlar quantes queden.
 */
public class Sac {
    private final Multiset<Fitxa> fitxes;
    private Set<String> lletresOriginals;

    /**
     * Crea un nou sac buit de fitxes.
     * Inicialitza l’estructura interna per emmagatzemar les fitxes i les seves quantitats.
     */
    public Sac() {
        this.fitxes = HashMultiset.create();
    }

    /**
     * Inicialitza aquest sac com la còpia d'un altre sac.
     * Es copien tots els atributs del sac original.
     *
     * @param copiaSac El sac original del qual es vol fer la còpia.
     */
    public Sac(Sac copiaSac) {
        this.fitxes = copiaSac.obtenirSac();
        this.lletresOriginals = copiaSac.obtenirLletresOriginals();
    }

    /**
     * Retorna les lletres inicials del sac.
     * @return Un set de les lletres originals.
     */
    public Set<String> obtenirLletresOriginals() {
        return lletresOriginals;
    }

    /**
     * Afegeix una fitxa al sac. Si la fitxa ja existeix al sac, incrementa la seva quantitat.
     *
     * @param f La fitxa que es vol afegir al sac.
     */
    public void afegirFitxa(Fitxa f) {
        fitxes.add(f);
    }

    /**
     * Inicialitza les lletres originals de les fitxes en el sac.
     *
     */
    public void setFitxesOriginals() {
        lletresOriginals = new HashSet<>();
        Set<Fitxa> fitxesOriginals = fitxes.elementSet();
        for (Fitxa f : fitxesOriginals) {
            lletresOriginals.add(f.obtenirLletra());
        }
    }

    /**
     * Comprova si una lletra és una fitxa original.
     *
     * @param lletra La lletra a verificar.
     * @return {@code true} si la lletra és una fitxa original, {@code false} altrament.
     */
    public boolean esFitxaOriginal(String lletra) {
        return lletresOriginals.contains(lletra);
    }

    /**
     * Extreu una fitxa aleatòria del sac.
     * La fitxa es selecciona en funció de la seva presència real (tenint en compte la quantitat de cada tipus).
     * Un cop seleccionada, la seva quantitat es redueix en una unitat.
     *
     * @return La fitxa seleccionada aleatòriament del sac.
     * @throws ExcepcioSacBuit Si el sac està buit i no es pot extreure cap fitxa.
     */
    public Fitxa agafarFitxa() {
        if (esBuit()) {
            throw new ExcepcioSacBuit("No es pot agafar una fitxa: el sac està buit.");
        }

        List<Fitxa> disponibles = new ArrayList<>(fitxes);
        Fitxa seleccionada = disponibles.get(new Random().nextInt(disponibles.size()));
        fitxes.remove(seleccionada);
        return seleccionada;
    }

    /**
     * Extreu una instància concreta d’una fitxa del sac.
     * Redueix la seva quantitat disponible en una unitat.
     *
     * @param fitxa La fitxa que es vol extreure del sac.
     * @return La fitxa extreta del sac.
     * @throws ExcepcioSacNoConteLaFitxa Si no hi ha cap fitxa disponible amb la lletra especificada.
     */
    public Fitxa agafarFitxa(Fitxa fitxa) {
        for (Fitxa f : fitxes.elementSet()) {
            if (f.obtenirLletra().equals(fitxa.obtenirLletra())) {
                fitxes.remove(f);
                return f;
            }
        }
        throw new ExcepcioSacNoConteLaFitxa("No hi ha fitxes disponibles amb la lletra '" + fitxa.obtenirLletra() + "'");
    }

    /**
     * Retorna el nombre total de fitxes disponibles al sac,
     * sumant totes les quantitats de cada tipus de fitxa.
     *
     * @return El nombre total de fitxes al sac.
     */
    public int obtenirNumFitxes() {
        return fitxes.size();
    }

    /**
     * Retorna la quantitat disponible d’una fitxa concreta al sac.
     *
     * @param fitxa La fitxa de la qual es vol consultar la quantitat.
     * @return El nombre d'exemplars d'aquesta fitxa disponibles al sac.
     */
    public int obtenirNumFitxes(Fitxa fitxa) {
        return fitxes.count(fitxa);
    }

    /**
     * Comprova si el sac està buit.
     *
     * @return {@code true} si no hi ha cap fitxa al sac, {@code false} altrament.
     */
    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    /**
     * Retorna el contingut intern del sac, amb cada fitxa i la seva quantitat.
     *
     * @return Un conjunt de fitxes i les seves quantitats disponibles.
     */
    public Multiset<Fitxa> obtenirSac() {
        return fitxes;
    }
}
