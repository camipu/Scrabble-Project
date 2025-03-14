//Classe Jugador
package edu.upc.prop.clusterxx;

public class Jugador {
    String nom;
    int punts;
    Faristol faristol;
    Taulell taulell;


    public Jugador() {}
    public Jugador(String nom, Faristol faristol, Taulell taulell) {
        this.nom = nom;
        this.punts = 0;
        this.faristol = faristol;
        this.taulell = taulell;
    }

    public String getNom() {
        return nom;
    }

    public int getPunts() {
        return punts;
    }

    public Faristol getFaristol() {
        return faristol;
    }

    public void afegirPunts(int nousPunts) {
        this.punts += nousPunts;
    }

    /** Crida la funció imprimirFaristol de Faristol */
    public void imprimirFaristol() {
        faristol.imprimirFaristol();
    }

    /** Imprimeix tota la informació del jugador amb colors */
    public void imprimirInfo() {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== INFORMACIÓ DEL JUGADOR ========" + Colors.RESET);
        System.out.println(Colors.CYAN_TEXT + "Nom: " + Colors.RESET + nom);
        System.out.println(Colors.GREEN_TEXT + "Punts: " + Colors.RESET + punts);
        System.out.print(Colors.PURPLE_TEXT + "Faristol: " + Colors.RESET);
        faristol.imprimirFaristol(); // Crida directament la funció de Faristol
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "========================================" + Colors.RESET);
    }

    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (faristol.eliminarFitxa(fitxa)) {  // Si la fitxa estava en el faristol i es pot treure
            System.out.println("Fitxa " + fitxa.getLletra() + " col·locada al taulell.");
            taulell.colocarFitxa(fitxa, fila, columna);
        } else {
            System.out.println("Error: La fitxa no està en el faristol.");
        }
    }






}
