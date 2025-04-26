package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.vistes.CasellaVista;
import edu.upc.prop.clusterxx.presentacio.vistes.FitxaVista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MostraCaselles extends JFrame {

    public MostraCaselles() {
        // Configuració bàsica de la finestra
        setTitle("Mostra de Caselles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800); // Fes la finestra una mica més gran
        setLocationRelativeTo(null);

        // Panell principal amb BorderLayout
        JPanel panellPrincipal = new JPanel(new BorderLayout(10, 10));

        // Panell de les caselles
        JPanel panellCaselles = new JPanel(new GridLayout(15, 15, 2, 2));
        panellCaselles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Casella[][] tauler = new Casella[15][15];
        int mida = 15;
        for (int x = 0; x < mida; x++) {
            for (int y = 0; y < mida; y++) {
                tauler[x][y] = new Casella(x, y, mida); // Pots posar-hi un altre valor si vols
            }
        }

        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[i].length; j++) {
                CasellaVista casellaVista = new CasellaVista(tauler[i][j]);
                panellCaselles.add(casellaVista);
            }
        }

        // Panell de fitxes (a sota)
        JPanel panellFitxes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panellFitxes.setBorder(BorderFactory.createTitledBorder("Fitxes disponibles"));

        // Crear unes quantes fitxes d'exemple
        List<Fitxa> fitxes = new ArrayList<>();
        fitxes.add(new Fitxa("A", 1));
        fitxes.add(new Fitxa("B", 3));
        fitxes.add(new Fitxa("C", 2));
        fitxes.add(new Fitxa("D", 4));
        fitxes.add(new Fitxa("E", 1));

        // Afegir cada fitxa al panell de fitxes
        for (Fitxa fitxa : fitxes) {
            FitxaVista fitxaVista = new FitxaVista(fitxa);
            panellFitxes.add(fitxaVista);
        }

        // Afegim panells al panell principal
        panellPrincipal.add(panellCaselles, BorderLayout.CENTER);
        panellPrincipal.add(panellFitxes, BorderLayout.SOUTH);

        // Afegir el panell principal a la finestra
        add(panellPrincipal);

        // Mostrar la finestra
        setVisible(true);
    }

    // Mètode principal per executar la demostració
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MostraCaselles());
    }
}
