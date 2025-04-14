package edu.upc.prop.clusterxx.exceptions;

public class ExcepcioNivellDificultatInvalid extends RuntimeException {
    public ExcepcioNivellDificultatInvalid(int nivell) {
        super("Nivell de dificultat: "+ nivell.toString() +
        " invàlid, el nivell ha de ser entre 1 i 3");
    }
}
