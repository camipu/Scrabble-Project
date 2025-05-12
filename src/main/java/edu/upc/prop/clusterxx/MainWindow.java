package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.controladors.CtrlDomini;

public class MainWindow {
    public static void main(String[] args) {
        CtrlDomini ctrlDomini = CtrlDomini.getInstance();
        ctrlDomini.inicialitzarApp();

        // Crear una instància de les estadístiques per afegir punts
        Estadistiques estadistiques = Estadistiques.getInstance();

        // Afegir puntuacions a diversos jugadors
        estadistiques.afegirPuntuacio(100, "Camila");
        estadistiques.afegirPuntuacio(200, "Jan");
        estadistiques.afegirPuntuacio(150, "Roger");
        estadistiques.afegirPuntuacio(10000, "Toni");


    }
}
