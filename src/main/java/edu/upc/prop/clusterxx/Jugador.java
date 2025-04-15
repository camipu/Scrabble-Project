//Classe Jugador
package edu.upc.prop.clusterxx;

public class Jugador {
    String nom;
    int punts;
    Faristol faristol;


    public Jugador() {}
    public Jugador(String nom, Faristol faristol) {
        this.nom = nom;
        this.punts = 0;
        this.faristol = faristol;
    }

    public String obtenirNom() {
        return nom;
    }

    public int obtenirPunts() {
        return punts;
    }

    public Faristol obtenirFaristol() {
        return faristol;
    }

    public void afegirPunts(int nousPunts) {
        this.punts += nousPunts;
    }
    public void eliminarPunts(int nousPunts) {
        this.punts -= nousPunts;
    }

    public void afegirFitxa(Fitxa fitxa) {
        faristol.afegirFitxa(fitxa);
    }

    public void eliminarFitxa(Fitxa fitxa) {
        faristol.eliminarFitxa(fitxa);
    }

    /*
    Driver
     */
    public void imprimirFaristol() {
        faristol.imprimirFaristol();
    }
    public void imprimirInfo() {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== INFORMACIÓ DEL JUGADOR ========" + Colors.RESET);
        System.out.println(Colors.CYAN_TEXT + "Nom: " + Colors.RESET + nom);
        System.out.println(Colors.GREEN_TEXT + "Punts: " + Colors.RESET + punts);
        System.out.print(Colors.PURPLE_TEXT + "Faristol: " + Colors.RESET);
        faristol.imprimirFaristol(); // Crida directament la funció de Faristol
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "========================================" + Colors.RESET);
    }
}