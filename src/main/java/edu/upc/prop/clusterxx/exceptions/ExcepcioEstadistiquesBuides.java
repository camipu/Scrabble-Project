package edu.upc.prop.clusterxx.exceptions;

/**
 * Excepción personalizada para indicar que las estadísticas están vacías.
 */
public class ExcepcioEstadistiquesBuides extends RuntimeException {
  public ExcepcioEstadistiquesBuides(String message) {
    super(message);
  }
}
