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
        this.lletra = lletra;
        if (punts < 0) {
            throw new IllegalArgumentException("Els punts no poden ser negatius.");
        }
        this.punts = punts;
        this.digraf = lletra.length() > 1;
    }

    /**
     * Inicialitza aquesta fitxa com la copia d'un altre.
     * Es copien tots els atributs.
     *
     * @param copiaFaristol Faristol original del qual es vol fer la còpia
     */
    public Fitxa(Fitxa copiaFitxa) {
        this.lletra = copiaFitxa.obtenirLletra();
        this.punts = copiaFitxa.obtenirPunts();
        this.digraf = copiaFitxa.esDigraf();
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

    /**
     * Compara aquesta fitxa amb un altre objecte per determinar si són iguals.
     * Dues fitxes són iguals si tenen la mateixa lletra i els mateixos punts.
     *
     * @param obj Objecte amb el qual es vol comparar aquesta fitxa
     * @return {@code true} si l’objecte comparat és una fitxa amb la mateixa lletra i punts,
     *         {@code false} altrament
     */
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

    /**
     * Genera un codi hash per a aquesta fitxa, basat en la seva lletra i puntuació.
     * Aquest mètode és coherent amb {@link #equals(Object)}, de manera que dues fitxes iguals
     * generaran el mateix codi hash.
     *
     * @return Codi hash de la fitxa
     */
    @Override
    public int hashCode() {
        return Objects.hash(lletra, punts);
    }
}
