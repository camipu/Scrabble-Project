package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlPartida;

public class Torn {
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private int torn;


    public Torn(Sac sac, Taulell taulell, Jugador[] jugadors, int torn) {
        this.sac = new Sac(sac);
        this.taulell = taulell;
        this.jugadors = jugadors;
        this.torn = torn;
    }
}
