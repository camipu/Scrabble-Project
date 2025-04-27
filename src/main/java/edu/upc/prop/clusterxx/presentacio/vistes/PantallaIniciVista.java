package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

/**
 * Pantalla d'inici per al joc de Scrabble.
 * Conté tres botons per a les opcions: "Jugar nova partida", "Continuar partida", "Estadístiques".
 */
public class PantallaIniciVista extends JFrame {
    private JButton jugarNovaPartidaButton;
    private JButton continuarPartidaButton;
    private JButton estadistiquesButton;
    private Font vt323Font = FontLoader.getCustomFont(20f);  // Font VT323

    public PantallaIniciVista() {
        // Configuració de la finestra
        setTitle("Scrabble - Pantalla d'Inici");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la finestra a la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // La finestra no es pot redimensionar

        // Creació del panell principal
        JPanel panell = new JPanel();
        panell.setLayout(new GridLayout(4, 1, 10, 10));
        panell.setBackground(new Color(255, 245, 230)); // Color de fons bonic (beix clar)

        // Títol
        JLabel títol = new JLabel("Benvingut a Scrabble!", JLabel.CENTER);
        títol.setFont(vt323Font);  // Aplicar la font VT323
        títol.setForeground(new Color(34, 34, 34)); // Color text fosc
        panell.add(títol);

        // Botó per jugar nova partida
        jugarNovaPartidaButton = new JButton("Jugar nova partida");
        jugarNovaPartidaButton.setFont(vt323Font);  // Aplicar la font VT323
        jugarNovaPartidaButton.setBackground(new Color(102, 204, 255)); // Blau cel
        jugarNovaPartidaButton.setFocusPainted(false);
        jugarNovaPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí es fa la transició per començar una nova partida
                System.out.println("Nova partida iniciada");
            }
        });
        panell.add(jugarNovaPartidaButton);

        // Botó per continuar partida
        continuarPartidaButton = new JButton("Continuar partida");
        continuarPartidaButton.setFont(vt323Font);  // Aplicar la font VT323
        continuarPartidaButton.setBackground(new Color(255, 205, 85)); // Groc daurat
        continuarPartidaButton.setFocusPainted(false);
        continuarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí es fa la transició per continuar una partida existent
                System.out.println("Partida continuada");
            }
        });
        panell.add(continuarPartidaButton);

        // Botó per mostrar estadístiques
        estadistiquesButton = new JButton("Estadístiques");
        estadistiquesButton.setFont(vt323Font);  // Aplicar la font VT323
        estadistiquesButton.setBackground(new Color(255, 102, 102)); // Vermell suau
        estadistiquesButton.setFocusPainted(false);
        estadistiquesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí es fa la transició per mostrar les estadístiques del joc
                System.out.println("Visualitzant estadístiques");
            }
        });
        panell.add(estadistiquesButton);

        // Afegir el panell a la finestra
        add(panell);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear i mostrar la finestra d'inici
                PantallaIniciVista vista = new PantallaIniciVista();
                vista.setVisible(true);
            }
        });
    }
}
