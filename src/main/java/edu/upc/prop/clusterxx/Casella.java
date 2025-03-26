package edu.upc.prop.clusterxx;

public class Casella {
    private final int x;
    private final int y;
    private Fitxa fitxa;
    private boolean casellaJugada;
    private final EstrategiaPuntuacio estrategia;

    public Casella(int x, int y, EstrategiaPuntuacio estrategia) {
        this.x = x;
        this.y = y;
        this.fitxa = null;
        this.estrategia = estrategia;
        this.casellaJugada = false;
    }

    public int calcularPunts() {
        return estrategia.calcularPunts(fitxa);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Fitxa getFitxa() { return fitxa; }
    public EstrategiaPuntuacio getEstrategia() {return estrategia;}
    public int getMultiplicador() {
        return estrategia.getMultiplicador();
    }


    public boolean esBuida() { return fitxa == null; }

    public boolean colocarFitxa(Fitxa fitxa) {
        if (esBuida()) {
            this.fitxa = fitxa;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return esBuida() ? "  " : " " + fitxa;
    }
}
