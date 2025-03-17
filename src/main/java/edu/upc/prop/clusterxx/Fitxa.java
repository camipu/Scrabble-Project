//Classe fitxa
package edu.upc.prop.clusterxx;

public class Fitxa {
    char lletra;
    int punts;
    boolean digraf;

    public Fitxa(char lletra, int punts) {
        this.lletra = Character.toUpperCase(lletra);
        this.punts = punts;
        digraf = !Character.isLetter(lletra);
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