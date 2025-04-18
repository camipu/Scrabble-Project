package edu.upc.prop.clusterxx;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

import java.util.*;

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
     * Inicialitza aquest sac com la copia d'un altre.
     * Es copien tots els atributs.
     *
     * @param copiaSac Sac original del qual es vol fer la còpia
     */
    public Sac(Sac copiaSac) {
        this.fitxes = HashMultiset.create(copiaSac.fitxes);
    }

    /**
     * Afegeix una fitxa al sac. Si la fitxa ja hi és, incrementa la seva quantitat.
     *
     * @param f Fitxa que es vol afegir al sac
     */
    public void afegirFitxa(Fitxa f) {
        fitxes.add(f);
    }

    public void setFitxesOriginals(Set<Fitxa> fitxesOriginals) {
        for (Fitxa f : fitxesOriginals) {
            lletresOriginals.add(f.obtenirLletra());
        }
    }

    public boolean esFitxaOriginal(String lletra) {
        return lletresOriginals.contains(lletra);
    }

    /**
     * Extreu una fitxa aleatòria del sac.
     * La fitxa extreta es selecciona en funció de la seva presència real (tenint en compte la quantitat de cada tipus).
     * Un cop seleccionada, la seva quantitat es redueix en una unitat.
     *
     * @return Fitxa seleccionada aleatòriament del sac
     * @throws ExcepcioSacBuit si el sac està buit i no es pot extreure cap fitxa
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
     * Redueix en una unitat la seva quantitat disponible.
     *
     * @param fitxa Fitxa que es vol extreure del sac
     * @return Fitxa extreta
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
     * @return Nombre total de fitxes al sac
     */
    public int obtenirNumFitxes() {
        return fitxes.size();
    }

    /**
     * Retorna la quantitat disponible d’una fitxa concreta al sac.
     *
     * @param fitxa Fitxa de la qual es vol consultar la quantitat
     * @return Nombre d’exemplars d’aquesta fitxa disponibles al sac
     */
    public int obtenirNumFitxes(Fitxa fitxa) {
        return fitxes.count(fitxa);
    }

    /**
     * Comprova si el sac està buit.
     *
     * @return {@code true} si no hi ha cap fitxa al sac, {@code false} altrament
     */
    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    /**
     * Retorna el contingut intern del sac, amb cada fitxa i la seva quantitat.
     *
     * @return Mapa de fitxes i les seves quantitats disponibles
     */
    public Multiset<Fitxa> obtenirSac() {
        return fitxes;
    }

}
