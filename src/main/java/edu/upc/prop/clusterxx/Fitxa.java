package edu.upc.prop.clusterxx;
import java.util.Objects;
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
     * Si la lletra te mida > 1, es considera dígraf.
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
     * Indica si la fitxa representa un dígraf.
     *
     * @return Cert si la fitxa és un dígraf, fals en cas contrari.
     */
    public boolean esDigraf() {
        return digraf;
    }


    /**
     * Retorna una representació en cadena de la fitxa.
     *
     * @return Una cadena que representa la fitxa.
     */
    @Override
    public String toString() {
        return lletra;
    }

    // Implementació de equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; //mateix objecte
        }
        if (obj == null || getClass() != obj.getClass()) { //null o diferent classe
            return false;
        }
        Fitxa fitxa = (Fitxa) obj; //cast
        return punts == fitxa.punts && Objects.equals(lletra, fitxa.lletra);
    }

    // Implementació de hashCode
    @Override
    public int hashCode() {
        return Objects.hash(lletra, punts); // Generem un hash basat en les propietats de la fitxa
    }
}
