package edu.upc.prop.clusterxx.controladors;

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
}

