package edu.upc.prop.clusterxx.exceptions;

public class ExcepcioLectura extends Exception {
    public ExcepcioLectura(String missatge) {
        super(missatge);
    }

    public ExcepcioLectura(String missatge, Throwable causa) {
        super(missatge, causa);
    }
}
