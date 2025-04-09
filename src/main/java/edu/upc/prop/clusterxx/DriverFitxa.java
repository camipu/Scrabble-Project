package edu.upc.prop.clusterxx;

public class DriverFitxa {
    public static void main(String[] args) {
        System.out.println("Hola des de DriverFitxa");
        Fitxa f1 = new Fitxa("A", 1);
        System.out.println("Casella creada: " + f1 + " " + f1.obtenirPunts());
    }
}
