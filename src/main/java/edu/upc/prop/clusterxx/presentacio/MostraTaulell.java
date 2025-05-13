package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.vistes.TaulellVista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MostraTaulell {
    public static void main(String[] args) {
        // Crear una instància del taulell
        Taulell taulell = new Taulell(15); // Mida del taulell 15x15
        taulell.colocarFitxa(new Fitxa("A", 1),1, 1);

        // Inicialitzar la vista del taulell
        TaulellVista taulellVista = new TaulellVista(taulell);

        // Crear la finestra principal
        JFrame frame = new JFrame("Joc de Scrabble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);  // Ajustar la mida de la finestra
        frame.setLayout(new BorderLayout());

        // Afegir la vista del taulell al contenidor de la finestra
        frame.add(taulellVista, BorderLayout.CENTER);

        // Mostrar la finestra
        frame.setVisible(true);
    }
}
