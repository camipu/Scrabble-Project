package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.vistes.FitxaVista;

import javax.swing.*;
import java.awt.*;

public class MostraFitxes extends JFrame {

    public MostraFitxes() {
        // Configuració bàsica de la finestra
        setTitle("Mostra de Fitxes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 100);
        setLocationRelativeTo(null);

        // Crear panell per contenir les fitxes
        JPanel panellFitxes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Crear algunes fitxes d'exemple
        Fitxa[] fitxes = {
                new Fitxa("A", 1),
                new Fitxa("B", 3),
                new Fitxa("C", 2),
                new Fitxa("CH", 5),  // Dígraf
                new Fitxa("X", 8),
                new Fitxa("Z", 10),
                new Fitxa("#", 0)    // Comodí
        };

        // Afegir les vistes de les fitxes al panell
        for (Fitxa fitxa : fitxes) {
            FitxaVista fitxaVista = new FitxaVista(fitxa);
            panellFitxes.add(fitxaVista);
        }

        // Afegir el panell a la finestra
        add(panellFitxes);

        // Mostrar la finestra
        setVisible(true);
    }

    // Mètode principal per executar la demostració
    public static void main(String[] args) {
        // Executar en l'EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new MostraFitxes();
        });
    }
}