package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pantalla d'inici compactada: Benvingut + SCRABBLE amb Fitxes + Botons centrats.
 */
public class PantallaIniciVista extends JFrame {
    private JButton jugarNovaPartidaButton;
    private JButton continuarPartidaButton;
    private JButton estadistiquesButton;
    private Font vt323Font = FontLoader.getCustomFont(36f);
    private Color colorsFons = ColorLoader.getInstance().getColorFons();

    public PantallaIniciVista() {
        setTitle("Scrabble - Pantalla d'Inici");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panell = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(colorsFons);
            }
        };
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        panell.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra els components dins del panell

        // 1. "Benvingut a"
        JLabel labelBenvingut = new JLabel("Benvingut a");
        labelBenvingut.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelBenvingut.setFont(FontLoader.getCustomFont(75f));
        labelBenvingut.setForeground(Color.BLACK);
        panell.add(Box.createRigidArea(new Dimension(0, 10)));
        panell.add(labelBenvingut);

        // 2. Paraula "SCRABBLE" amb FitxaVista
        JPanel panellTitol = new JPanel();
        panellTitol.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0)); // menys separació
        panellTitol.setOpaque(false);

        String paraula = "SCRABBLE";
        for (char c : paraula.toCharArray()) {
            Fitxa fitxa = new Fitxa(String.valueOf(c), obtenirPuntsLletra(c));
            FitxaVista fitxaVista = new FitxaVista(fitxa, 75, 75, 50); // fitxes més petites
            panellTitol.add(fitxaVista);
        }
        panell.add(Box.createRigidArea(new Dimension(0, 5))); // Espai petit
        panell.add(panellTitol);
        panell.add(Box.createRigidArea(new Dimension(0, 15))); // Espai molt petit abans botons

        // 3. Botons
        Font botoFont = FontLoader.getCustomFont(58f);
        jugarNovaPartidaButton = crearBotoRetro("Jugar nova partida", new Color(0, 255, 128), botoFont);
        continuarPartidaButton = crearBotoRetro("Continuar partida", new Color(255, 255, 0), botoFont);
        estadistiquesButton = crearBotoRetro("Estadístiques", new Color(255, 100, 100), botoFont);

        // Panell per als botons amb GridBagLayout per centrar-los
        JPanel panellBotons = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(colorsFons);
            }
        };
        panellBotons.setLayout(new GridBagLayout());  // Canvia a GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;  // Columna 0
        gbc.gridy = 0;  // Fil 0
        gbc.insets = new Insets(5, 0, 5, 0);  // Espai entre els botons

        // Afegir els botons amb espais
        panellBotons.add(jugarNovaPartidaButton, gbc);
        gbc.gridy++;
        panellBotons.add(continuarPartidaButton, gbc);
        gbc.gridy++;
        panellBotons.add(estadistiquesButton, gbc);

        panell.add(panellBotons);

        add(panell);

        // Afegir ActionListener al botó "Estadístiques"
        estadistiquesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quan es clica, s'obre la finestra d'estadístiques
                EstadistiquesVista estadistiquesVista = new EstadistiquesVista();
                estadistiquesVista.setVisible(true);
            }
        });
    }

    private JButton crearBotoRetro(String text, Color colorFons, Font font) {
        JButton boto = new JButton(text);
        boto.setAlignmentX(Component.CENTER_ALIGNMENT);
        boto.setFont(font);
        boto.setBackground(colorFons);
        boto.setForeground(Color.BLACK);
        boto.setFocusPainted(false);
        boto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        Dimension midaBoton = new Dimension(600, 120);
        boto.setPreferredSize(midaBoton); // Estableix una mida fixa
        boto.setMaximumSize(midaBoton);
        boto.setMinimumSize(midaBoton);

        boto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boto.setBackground(boto.getBackground().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorFons);
            }
        });

        return boto;
    }

    private int obtenirPuntsLletra(char lletra) {
        switch (Character.toUpperCase(lletra)) {
            case 'C': return 3;
            default: return 1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaIniciVista vista = new PantallaIniciVista();
            vista.setVisible(true);
        });
    }
}
