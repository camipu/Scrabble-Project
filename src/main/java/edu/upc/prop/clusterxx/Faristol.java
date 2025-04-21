package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Representa el faristol d’un jugador, on es col·loquen les fitxes disponibles durant la partida.
 * El faristol té una mida fixa i pot contenir diverses fitxes que el jugador pot utilitzar.
 */
public class Faristol {
    private final int size;
    private final ArrayList<Fitxa> fitxes;

    /**
     * Crea un nou faristol amb una mida especificada.
     * Inicialitza la llista de fitxes buida amb capacitat per al nombre indicat.
     *
     * @param size Nombre màxim de fitxes que pot contenir el faristol
     */
    public Faristol(int size) {
        this.fitxes = new ArrayList<>(size);
        if (size <= 0) {
            throw new IllegalArgumentException("La mida del faristol ha de ser positiva.");
        }
        this.size = size;
    }

    /**
     * Inicialitza aquest faristol com la copia d'un altre.
     * Es copien totes les fitxes una per una (nova instància per a cada fitxa)
     * i es manté la mateixa mida.
     *
     * @param copiaFaristol Faristol original del qual es vol fer la còpia
     */
    public Faristol(Faristol copiaFaristol) {
        ArrayList<Fitxa> fitxesOriginals = copiaFaristol.obtenirFitxes();
        this.fitxes = new ArrayList<>();

        for (Fitxa f : fitxesOriginals) {
            this.fitxes.add(new Fitxa(f));
        }

        this.size = copiaFaristol.obtenirSize();
    }

    /**
     * Comprova si el faristol està ple.
     *
     * @return {@code true} si el nombre de fitxes és igual o superior al límit,
     *         {@code false} altrament
     */
    public boolean esPle(){
        return fitxes.size() >= size;
    }

    /**
     * Comprova si el faristol està buit.
     *
     * @return {@code true} si el nombre de fitxes és igual a 0,
     *         {@code false} altrament
     */
    public boolean esBuit(){
        return fitxes.size() == 0;
    }

    /**
     * Retorna una còpia de la llista de fitxes del faristol.
     * Això evita que es puguin modificar les fitxes originals des de fora de la classe.
     *
     * @return Nova llista amb les fitxes actuals del faristol
     */
    public ArrayList<Fitxa> obtenirFitxes() {
        return new ArrayList<>(fitxes); // Retornem còpia per evitar modificació externa
    }

    /**
     * Retorna la mida màxima del faristol.
     *
     * @return Mida del faristol
     */
    public int obtenirSize() {return size;}

    /**
     * Retorna la fitxa situada en una posició concreta del faristol.
     *
     * @param index Posició de la fitxa a obtenir (començant per 0)
     * @return La fitxa a la posició especificada
     * @throws IndexOutOfBoundsException si l’índex és negatiu o excedeix la mida del faristol
     */
    public Fitxa obtenirFitxa(int index) {
        if (index < 0 || index >= fitxes.size()) {
            throw new IndexOutOfBoundsException("Índex fora de rang al faristol.");
        }
        return fitxes.get(index);
    }

    public Fitxa obtenirFitxa(String lletra) {
        for (Fitxa fitxa : fitxes) {
            if (fitxa.obtenirLletra().equals(lletra)) {
                return fitxa;
            }
        }
        throw new IllegalArgumentException("No hi ha cap fitxa amb la lletra " + lletra + " al faristol.");
    }

    /**
     * Retorna el nombre actual de fitxes que hi ha al faristol.
     *
     * @return Nombre de fitxes presents al faristol
     */
    public int obtenirNumFitxes() {
        return fitxes.size();
    }

    /**
     * Afegeix una fitxa al faristol.
     * Si el faristol ja està ple, es llença una excepció.
     *
     * @param fitxa Fitxa a afegir al faristol
     * @throws ExcepcioFaristolPle si el faristol ja ha arribat a la seva capacitat màxima
     */
    public void afegirFitxa(Fitxa fitxa) {
        if (esPle()) {
            throw new ExcepcioFaristolPle("El faristol està ple, no es pot afegir la fitxa " + fitxa);
        }
        fitxes.add(fitxa);
    }

    /**
     * Elimina una fitxa concreta del faristol.
     * Si la fitxa no es troba al faristol, mira que no sigui comodí i es llença una excepció.
     *
     * @param fitxa Fitxa que es vol eliminar
     * @throws ExcepcioFaristolNoConteLaFitxa si la fitxa no és present al faristol
     */
    public void eliminarFitxa(Fitxa fitxa) {
        if (!fitxes.remove(fitxa)) {
            if (fitxa.obtenirPunts() == 0) {
                eliminarFitxa(new Fitxa("#", 0));
            }
            else {
                throw new ExcepcioFaristolNoConteLaFitxa("No es pot eliminar fitxa " + fitxa + ", el faristol no la conté.");
            }
        }
    }

    /**
     * Barreja aleatòriament les fitxes del faristol.
     */
    public void barrejarFitxes() {
        Collections.shuffle(fitxes);
    }


}