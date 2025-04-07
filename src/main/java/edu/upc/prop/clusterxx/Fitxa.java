package edu.upc.prop.clusterxx;

/**
 * Representa una fitxa del joc, amb una lletra, un valor en punts i 
 * un indicador de si és un dígraf.
 */
public class Fitxa {
    private final String lletra;
    private final int punts;
    private final boolean digraf;

    /**
     * Crea una nova fitxa amb una lletra i un valor de punts.
     * Si la lletra no és una lletra de l'alfabet, es considera un dígraf.
     *
     * @param lletra La lletra de la fitxa.
     * @param punts El valor en punts de la fitxa.
     */
    public Fitxa(String lletra, int punts) {
        this.lletra = lletra.toUpperCase();
        this.punts = punts;
        this.digraf = lletra.length() > 1;
    }

    /**
     * Retorna la lletra de la fitxa.
     *
     * @return La lletra associada a aquesta fitxa.
     */
    public String obtenirLletra() {
        return lletra;
    }

    /**
     * Retorna el valor en punts de la fitxa.
     *
     * @return Els punts que val aquesta fitxa.
     */
    public int obtenirPunts() {
        return punts;
    }

    /**
     * Indica si la fitxa representa un dígraf (és a dir, si no és una lletra).
     *
     * @return Cert si la fitxa és un dígraf, fals en cas contrari.
     */
    public boolean esDigraf() {
        return digraf;
    }


    /**
     * Retorna una representació en cadena de la fitxa.
     * </p>Si la lletra és '(', es representa com "CH". </p>
     *
     * @return Una cadena que representa la fitxa.
     */
    @Override
    public String toString() {
        return lletra;
    }
}
