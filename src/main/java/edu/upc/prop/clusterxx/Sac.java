package edu.upc.prop.clusterxx;

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
    private final Map<Fitxa, Integer> fitxes;

    /**
     * Crea un nou sac buit de fitxes.
     * Inicialitza l’estructura interna per emmagatzemar les fitxes i les seves quantitats.
     */
    public Sac() {
        this.fitxes = new LinkedHashMap<>();
    }

    /**
     * Inicialitza aquest sac com la copia d'un altre.
     * Es copien tots els atributs.
     *
     * @param copiaSac Sac original del qual es vol fer la còpia
     */
    public Sac(Sac copiaSac) {
        this.fitxes = copiaSac.obtenirSac();
    }

    /**
     * Afegeix una fitxa al sac. Si la fitxa ja hi és, incrementa la seva quantitat.
     *
     * @param f Fitxa que es vol afegir al sac
     */
    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
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

        List<Fitxa> disponibles = new ArrayList<>();
        for (Map.Entry<Fitxa, Integer> entry : fitxes.entrySet()) {
            disponibles.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }

        Fitxa seleccionada = disponibles.get(new Random().nextInt(disponibles.size()));
        reduirQuantitat(seleccionada);
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
        Fitxa f = obtenirFitxa(fitxa);
        reduirQuantitat(f);
        return f;
    }

    /**
     * Retorna el nombre total de fitxes disponibles al sac,
     * sumant totes les quantitats de cada tipus de fitxa.
     *
     * @return Nombre total de fitxes al sac
     */
    public int obtenirNumFitxes() {
        return fitxes.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Retorna la quantitat disponible d’una fitxa concreta al sac.
     *
     * @param fitxa Fitxa de la qual es vol consultar la quantitat
     * @return Nombre d’exemplars d’aquesta fitxa disponibles al sac
     */
    public int obtenirNumFitxes(Fitxa fitxa) {
        return fitxes.getOrDefault(fitxa, 0);
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
    public Map<Fitxa, Integer> obtenirSac() {
        return fitxes;
    }

    /**
     * Redueix la quantitat d’una fitxa al sac en una unitat.
     * Si només en queda una, la fitxa s’elimina del sac.
     *
     * @param fitxa Fitxa de la qual es vol reduir la quantitat
     */
    private void reduirQuantitat(Fitxa fitxa) {
        int quantitat = fitxes.getOrDefault(fitxa, 0);
        if (quantitat <= 1) {
            fitxes.remove(fitxa);
        } else {
            fitxes.put(fitxa, quantitat - 1);
        }
    }

    /**
     * Cerca i retorna una fitxa del sac que tingui la mateixa lletra que la fitxa donada.
     *
     * @param fitxa Fitxa amb la lletra que es vol buscar
     * @return Fitxa del sac amb la mateixa lletra
     * @throws ExcepcioSacNoConteLaFitxa si no hi ha cap fitxa al sac amb la lletra indicada
     */
    private Fitxa obtenirFitxa(Fitxa fitxa) {
        for (Fitxa f : fitxes.keySet()) {
            if (f.obtenirLletra().equals(fitxa.obtenirLletra())) {
                return f;
            }
        }
        throw new ExcepcioSacNoConteLaFitxa("No hi ha fitxes disponibles amb la lletra '" + fitxa.obtenirLletra() + "'");
    }
}
