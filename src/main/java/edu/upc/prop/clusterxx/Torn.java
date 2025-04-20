package edu.upc.prop.clusterxx;

/**
 * Representa un torn dins d’una partida de Scrabble.
 * Conté la informació necessària per gestionar el torn actual,
 * incloent el sac de fitxes, el taulell, els jugadors, el número de torn i si el torn ha acabat.
 */
public class Torn {
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private int numTorn;
    private boolean acabada;
    private int tornsSenseCanvis;

    /**
     * Crea un nou torn amb una còpia completa de l’estat actual de la partida.
     * Es clonen el sac, el taulell i els jugadors per evitar efectes col·laterals.
     *
     * @param sac Sac de fitxes en l’estat actual
     * @param taulell Taulell del joc en el moment del torn
     * @param jugadors Jugadors participants en la partida
     * @param torn Número de torn actual
     * @param acabada Indica si el torn ja s’ha completat
     */
    public Torn(Sac sac, Taulell taulell, Jugador[] jugadors, int torn, boolean acabada, int tornsSenseCanvis) {
        this.sac = new Sac(sac);
        this.taulell = new Taulell(taulell);
        this.jugadors = new Jugador[jugadors.length];
        for (int i = 0; i < jugadors.length; i++) {
            this.jugadors[i] = new Jugador(jugadors[i]);
        }
        this.numTorn = torn;
        this.acabada = acabada;
        this.tornsSenseCanvis = tornsSenseCanvis;
    }

    public boolean jugadorBot(int torn) {
        System.out.println("Jugador torn " + torn + ": " + jugadors[torn%jugadors.length].obtenirNom());
        System.out.println("És bot?: " + jugadors[torn%jugadors.length].esBot());
        return jugadors[torn%jugadors.length].esBot();
    }

    public int obtenirTornsSenseCanvis() {
        return tornsSenseCanvis;
    }

    /**
     * Retorna el sac de fitxes associat a aquest torn.
     *
     * @return Sac del torn
     */
    public Sac obtenirSac() {return sac;}

    /**
     * Retorna el taulell del joc en l’estat actual d’aquest torn.
     *
     * @return Taulell del torn
     */
    public Taulell obtenirTaulell() {return taulell;}

    /**
     * Retorna la llista de jugadors participants en aquest torn.
     *
     * @return Array de jugadors
     */
    public Jugador[] obtenirJugadors() {return jugadors;}

    /**
     * Retorna el número d’aquest torn dins de la partida.
     *
     * @return Número de torn
     */
    public int obtenirTorn() {return numTorn;}

    /**
     * Indica si aquest torn ja ha estat completat.
     *
     * @return {@code true} si el torn s’ha acabat, {@code false} altrament
     */
    public boolean esAcabada() {return acabada;}

}
