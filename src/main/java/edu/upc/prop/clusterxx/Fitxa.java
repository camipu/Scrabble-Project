//Classe fitxa
package edu.upc.prop.clusterxx;

public class Fitxa {
    char lletra;
    int punts;
    boolean digraf;

    Fitxa(char lletra, int punts) {
        this.lletra = lletra;
        this.punts = punts;
        if (Character.isLetter(lletra)) digraf = false;
        else digraf = true;
    }

    public char getLletra() {
        return lletra;
    }

    public int getPunts() {
        return punts;
    }

    public boolean esDigraf() {return digraf;}

    @Override
    public String toString() {
        if (lletra == '(') return "CH"; // Si la lletra és '(', representa "CH"
        return String.valueOf(lletra); // Retorna la lletra amb un espai davant
    }


}