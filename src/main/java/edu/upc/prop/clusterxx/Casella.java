package edu.upc.prop.clusterxx;

public class Casella {
    /*
     * Si marques un atribut com a final, vol dir que només
     * es pot assignar una vegada i després ja no es pot modificar.
     */
    private final int x;  // Coordenades fixes
    private final int y;
    private Fitxa fitxa;

    public Casella(int x, int y) {
        this.x = x;
        this.y = y;
        this.fitxa = null; // Inicialment buida
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Fitxa getFitxa() {
        return fitxa;
    }

    public boolean esBuida() {
        return fitxa == null;
    }

    public boolean colocarFitxa(Fitxa fitxa) {
        if (esBuida()) {
            this.fitxa = fitxa;
            return true;
        } else {
            return false; // Alternativa a excepció
        }
    }

    //Per quan fem string de casella :)
    @Override
    public String toString() {
        if (esBuida()) return "  ";
        if (fitxa.esDigraf()) return  String.valueOf(fitxa);
        return  " " + String.valueOf(fitxa);
    }
}
