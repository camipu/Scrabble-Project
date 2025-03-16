//Classe Jugador
package edu.upc.prop.clusterxx;

public class Jugador {
    String nom;
    int punts;
    Faristol faristol;
    Joc joc;
    Taulell taulell;


    public Jugador() {}
    public Jugador(String nom, Joc joc, Taulell taulell) {
        this.nom = nom;
        this.punts = 0;
        this.joc = joc;
        this.faristol = new Faristol(joc.getSac());
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

    public void afegirFitxa(char lletra) {
        faristol.afegirFitxa(lletra);
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

    public void colocarFitxa(char lletra, int fila, int columna) {
        Fitxa fitxa = null;

        // Busquem la fitxa al faristol per la lletra
        for (Fitxa f : faristol.obtenirFitxes()) {
            if (f.getLletra() == lletra) {
                fitxa = f;
                break;
            }
        }

        if (fitxa != null) {
            // Si la fitxa existeix al faristol, la traiem i la col·loquem al taulell
            if (faristol.eliminarFitxa(lletra)) {
                taulell.colocarFitxa(fitxa, fila, columna);
            } else {
                System.out.println("Error: No s'ha pogut eliminar la fitxa del faristol.");
            }
        } else {
            System.out.println("Error: La fitxa amb la lletra '" + lletra + "' no es troba al faristol.");
        }
    }






}
