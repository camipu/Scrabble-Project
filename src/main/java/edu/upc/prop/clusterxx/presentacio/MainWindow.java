package edu.upc.prop.clusterxx.presentacio;


import edu.upc.prop.clusterxx.Fitxa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow() {

    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear una fitxa
        Fitxa fitxa = new Fitxa("LL", 8);

        // Crear el panell de la fitxa
        JLabel fitxaLabel = new JLabel(fitxa.obtenirLletra(), SwingConstants.CENTER);
        fitxaLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        fitxaLabel.setOpaque(true);
        fitxaLabel.setBackground(Color.WHITE);
        fitxaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        fitxaLabel.setPreferredSize(new Dimension(150, 150));

        // Mostrar també els punts petits a sota (opcional)
        JLabel puntsLabel = new JLabel(fitxa.obtenirPunts() + " punts", SwingConstants.CENTER);
        puntsLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        // Agrupar fitxa i punts en un panell vertical
        JPanel fitxaPanel = new JPanel();
        fitxaPanel.setLayout(new BorderLayout());
        fitxaPanel.add(fitxaLabel, BorderLayout.CENTER);
        fitxaPanel.add(puntsLabel, BorderLayout.SOUTH);

        // Botó
        JButton button = new JButton("Fes clic");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this, "Has clicat el botó!");
            }
        });

        panel.add(fitxaPanel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        this.add(panel);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
