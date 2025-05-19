package edu.upc.prop.clusterxx.exceptions;

public class ExcepcioEscriptura extends Exception {
    public ExcepcioEscriptura(String missatge) {
        super(missatge);
    }

    public ExcepcioEscriptura(String missatge, Throwable causa) {
        super(missatge, causa);
    }
}
