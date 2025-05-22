package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlDomini;
import edu.upc.prop.clusterxx.controladors.CtrlPresentacio;

public class MainWindow {
    public static void main(String[] args) {
        CtrlPresentacio ctrlPresentacio = CtrlPresentacio.getInstance();
        ctrlPresentacio.inicialitzarApp();
    }
}
