package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlPartida;

public class Torn {
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private int torn;


    public Torn(Sac sac, Taulell taulell, Jugador[] jugadors, int torn) {
        this.sac = new Sac(sac);
        this.taulell = new Taulell(taulell);
        this.jugadors = new Jugador[jugadors.length];
        for (int i = 0; i < jugadors.length; i++) {
            this.jugadors[i] = new Jugador(jugadors[i]);
        }
        this.torn = torn;
    }
}
