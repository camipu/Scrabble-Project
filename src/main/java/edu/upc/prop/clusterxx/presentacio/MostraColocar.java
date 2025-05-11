package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.vistes.FaristolVista;
import edu.upc.prop.clusterxx.presentacio.vistes.TaulellVista;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostraColocar {

    public static void main(String[] args) {
        // Crear el model
        Taulell taulell = new Taulell(15); // Exemple de mida 15
        Faristol faristol = new Faristol(7); // Faristol amb fitxes
        // Afegim fitxes de prova
        faristol.afegirFitxa(new Fitxa("A", 1));
        faristol.afegirFitxa(new Fitxa("B", 3));
        faristol.afegirFitxa(new Fitxa("C", 3));
        faristol.afegirFitxa(new Fitxa("D", 2));
        faristol.afegirFitxa(new Fitxa("E", 1));
        faristol.afegirFitxa(new Fitxa("F", 4));
        faristol.afegirFitxa(new Fitxa("G", 2));

        // Crear les vistes
        TaulellVista taulellVista = new TaulellVista(taulell);
        FaristolVista faristolVista = new FaristolVista(faristol);

        // Crear el JFrame per a la finestra
        JFrame frame = new JFrame("Scrabble");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el GridBagConstraints per a la disposició
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Omplir l'espai disponible

        // Afegir Taulell a la finestra (ocupant la part superior)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75; // Augmentar el pes per ocupar més espai
        frame.add(taulellVista, gbc);

        // Crear botó Col·locar amb estil millorat
        JButton collocarButton = new JButton("Col·locar");
        collocarButton.setFont(FontLoader.getCustomFont(25f)); // Font més gran
        collocarButton.setBackground(new Color(0, 153, 0)); // Fons verd
        collocarButton.setForeground(Color.WHITE); // Lletra blanca
        collocarButton.setBorder(BorderFactory.createBevelBorder(0)); // Borde més suau
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        frame.add(collocarButton, gbc);

        // Afegir Faristol a la finestra (a la part inferior)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15; // Augmentar pes per afegir més espai al faristol
        frame.add(faristolVista, gbc);

        // Acció per al botó Col·locar
        collocarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenir la fitxa seleccionada del faristol
                Fitxa fitxa = faristolVista.obtenirFitxaSeleccionada();

                // Obtenir la posició seleccionada del taulell
                Pair<Integer, Integer> pos = taulellVista.obtenirPosSeleccionada();

                // Verificar que s'ha seleccionat una fitxa i una casella
                if (fitxa != null && pos != null && pos.getLeft() != -1 && pos.getRight() != -1) {
                    // Col·locar la fitxa al taulell
                    taulell.colocarFitxa(fitxa, pos.getLeft(), pos.getRight());
                    System.out.println("Fitxa col·locada a la posició: " + pos.getLeft() + ", " + pos.getRight());

                    // Actualitzar la vista del taulell
                    taulellVista.actualitzarTaulell();

                    // Desseleccionar la fitxa del faristol
                    faristolVista.desseleccionarFitxa();
                } else {
                    JOptionPane.showMessageDialog(frame, "Has de seleccionar una fitxa i una casella.");
                }
            }
        });

        // Configurar la finestra
        frame.setSize(1000, 800); // Augmentar la mida de la finestra
        frame.setVisible(true);
    }
}
