package edu.upc.prop.clusterxx.exceptions;

public class ExcepcioCaracterNoReconegut extends RuntimeException {
    public ExcepcioCaracterNoReconegut(String c) {
        super("No s'ha reconegut el caràcter: " + c.toString());
    }
}