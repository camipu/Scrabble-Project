package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.vistes.CasellaVista;

import javax.swing.*;
import java.awt.*;

public class MostraCaselles extends JFrame {

    public MostraCaselles() {
        // Configuració bàsica de la finestra
        setTitle("Mostra de Caselles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panellCaselles = new JPanel(new GridLayout(5, 5, 2, 2));
        panellCaselles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Casella[][] tauler = new Casella[5][5];
        int valor = 5;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                tauler[x][y] = new Casella(x, y, valor);
                valor++;
            }
        }


        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[i].length; j++) {
                CasellaVista casellaVista = new CasellaVista(tauler[i][j]);
                panellCaselles.add(casellaVista);
            }
        }

        // Afegir el panell a la finestra
        add(panellCaselles);

        // Mostrar la finestra
        setVisible(true);
    }

    // Mètode principal per executar la demostració
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MostraCaselles();
        });
    }
}