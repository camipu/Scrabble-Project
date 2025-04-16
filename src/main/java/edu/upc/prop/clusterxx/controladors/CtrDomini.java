package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Sac;
import edu.upc.prop.clusterxx.Taulell;

public class CtrDomini {
    private static CtrDomini instance = null;
    private CtrlPartida ctrlPartida = null;
    private CtrEstadistica ctrEstadistica = null;


    public static CtrDomini getInstance() {
        if (instance == null) {
            instance = new CtrDomini();
        }
        return instance;
    }

    private CtrDomini(){
        ctrlPartida = CtrlPartida.getInstance();
        ctrEstadistica = CtrEstadistica.getInstance();
    }

    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors,int[] dificultatsBots){
        ctrlPartida.inicialitzarPartida(midaTaulell, midaFaristol, idioma, nomsJugadors, dificultatsBots);
    }

    //Cargar Partida Falta

    public Taulell obtenirTaulell() {
        return ctrlPartida.obtenirTaulell();
    }


    public Jugador[] obtenirJugadors() {
        return ctrlPartida.obtenirJugadors();
    }

    public Jugador obtenirJugadorActual() {
        return ctrlPartida.obtenirJugadorActual();
    }

    public int obtenirTorn() {
        return ctrlPartida.obtenirTorn();
    }

    public Sac obtenirSac() {
        return ctrlPartida.obtenirSac();
    }

    //    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
//        ctrlPartida.colocarFitxa(fitxa,fila,columna);
//    }

    public void passarTorn() {
        ctrlPartida.passarTorn();
    }

    public void undo() {
        ctrlPartida.undo();
    }

    public void guardarPartida(){

    }

    public void acabarPartida() {
        ctrlPartida.acabarPartida();
    }





}

