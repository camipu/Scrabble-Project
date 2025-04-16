package edu.upc.prop.clusterxx;

public class Torn {
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private int numTorn;
    private boolean acabada;


    public Torn(Sac sac, Taulell taulell, Jugador[] jugadors, int torn, boolean acabada) {
        this.sac = new Sac(sac);
        this.taulell = new Taulell(taulell);
        this.jugadors = new Jugador[jugadors.length];
        for (int i = 0; i < jugadors.length; i++) {
            this.jugadors[i] = new Jugador(jugadors[i]);
        }
        this.numTorn = torn;
    }

    public Sac obtenirSac() {return sac;}
    public Taulell obtenirTaulell() {return taulell;}
    public Jugador[] obtenirJugadors() {return jugadors;}
    public int obtenirTorn() {return numTorn;}
    public boolean esAcabada() {return acabada;}

}
