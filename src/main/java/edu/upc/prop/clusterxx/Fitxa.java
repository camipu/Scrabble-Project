package edu.upc.prop.clusterxx;

import java.util.Objects;

/**
 * Representa una fitxa del joc, amb una lletra, un valor en punts i
 * un indicador de si és un dígraf.
 */
public class Fitxa {
    private String lletra;
    private final int punts;
    private final boolean digraf;

    /**
     * Crea una nova fitxa amb una lletra i un valor de punts.
     * Si la lletra té més d'un caràcter, es considera un dígraf.
     *
     * @param lletra La lletra de la fitxa.
     * @param punts El valor en punts de la fitxa.
     * @throws IllegalArgumentException Si els punts són negatius.
     */
    public Fitxa(String lletra, int punts) {
        this.lletra = lletra;
        if (punts < 0) {
            throw new IllegalArgumentException("Els punts no poden ser negatius.");
        }
        this.punts = punts;
        this.digraf = lletra.length() > 1;
    }

    /**
     * Crea una nova fitxa com a còpia d'una altra.
     * Es copien tots els atributs de la fitxa original.
     *
     * @param copiaFitxa La fitxa original de la qual es vol fer la còpia.
     */
    public Fitxa(Fitxa copiaFitxa) {
        this.lletra = copiaFitxa.obtenirLletra();
        this.punts = copiaFitxa.obtenirPunts();
        this.digraf = copiaFitxa.esDigraf();
    }

    /**
     * Retorna la lletra associada a aquesta fitxa.
     *
     * @return La lletra de la fitxa.
     */
    public String obtenirLletra() {
        return lletra;
    }

    /**
     * Retorna el valor en punts d'aquesta fitxa.
     *
     * @return El valor en punts.
     */
    public int obtenirPunts() {
        return punts;
    }

    /**
     * Indica si la fitxa representa un dígraf (una combinació de dues lletres).
     *
     * @return {@code true} si la fitxa és un dígraf, {@code false} altrament.
     */
    public boolean esDigraf() {
        return digraf;
    }


    /**
     * Assigna una nova lletra a una fitxa comodí.
     * Només es pot fer si la fitxa actual és un comodí ("#").
     *
     * @param lletra La nova lletra a assignar.
     * @throws IllegalArgumentException Si la fitxa no és un comodí.
     */
    public void setLletraComodi(String lletra) {
        if (!this.lletra.equals("#")) {
            throw new IllegalArgumentException("No es pot canviar la lletra d'una fitxa que no és un comodí.");
        }
        else {
            this.lletra = lletra;
        }
    }

    /**
     * Comprova si una fitxa és un comodí.
     * Un comodí és una fitxa amb un valor de punts igual a 0.
     *
     * @return {@code true} si la fitxa és un comodí (punts == 0), {@code false} en cas contrari.
     */
    public boolean esComodi() {
        return this.punts == 0;
    }

    /**
     * Retorna una representació en forma de cadena de la fitxa.
     *
     * @return Una cadena que representa la lletra de la fitxa.
     */
    @Override
    public String toString() {
        return lletra;
    }

    /**
     * Compara aquesta fitxa amb un altre objecte per determinar si són iguals.
     * Dues fitxes són iguals si tenen la mateixa lletra i el mateix valor en punts.
     *
     * @param obj L’objecte amb el qual es vol comparar aquesta fitxa.
     * @return {@code true} si l’objecte comparat és una fitxa amb la mateixa lletra i punts,
     *         {@code false} altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fitxa fitxa = (Fitxa) obj;
        return punts == fitxa.punts && Objects.equals(lletra, fitxa.lletra);
    }

    /**
     * Genera un codi hash per a aquesta fitxa, basat en la lletra i el valor en punts.
     * Aquest mètode és coherent amb {@link #equals(Object)}, de manera que dues fitxes iguals
     * tenen el mateix codi hash.
     *
     * @return El codi hash de la fitxa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(lletra, punts);
    }
}
