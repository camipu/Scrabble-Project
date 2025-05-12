package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.presentacio.vistes.PantallaIniciVista;
import edu.upc.prop.clusterxx.presentacio.vistes.EstadistiquesVista;

public class MainWindow {
    public static void main(String[] args) {
        // Crea una nova instància de la pantalla d'inici
        PantallaIniciVista pantallaInici = new PantallaIniciVista();

        // Mostra la finestra
        pantallaInici.setVisible(true);

        // Crear una instància de les estadístiques per afegir punts
        Estadistiques estadistiques = Estadistiques.getInstance();

        // Afegir puntuacions a diversos jugadors
        estadistiques.afegirPuntuacio(100, "Camila");
        estadistiques.afegirPuntuacio(200, "Jan");
        estadistiques.afegirPuntuacio(150, "Roger");
        estadistiques.afegirPuntuacio(0, "Toni");


    }
}
