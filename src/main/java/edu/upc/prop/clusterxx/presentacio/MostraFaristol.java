package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.vistes.FaristolVista;

import javax.swing.*;
import java.awt.*;

public class MostraFaristol extends JFrame {

    public MostraFaristol() {
        setTitle("Mostra del Faristol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear un faristol de mida 7
        Faristol faristol = new Faristol(7);

        // Afegim fitxes de prova
        faristol.afegirFitxa(new Fitxa("A", 1));
        faristol.afegirFitxa(new Fitxa("B", 3));
        faristol.afegirFitxa(new Fitxa("C", 3));
        faristol.afegirFitxa(new Fitxa("D", 2));
        faristol.afegirFitxa(new Fitxa("E", 1));
        faristol.afegirFitxa(new Fitxa("F", 4));
        faristol.afegirFitxa(new Fitxa("G", 2));

        // Crear la vista del faristol
        FaristolVista faristolVista = new FaristolVista(faristol);

        // Afegim el faristol a la finestra
        add(faristolVista, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MostraFaristol::new);
    }
}
