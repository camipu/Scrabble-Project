package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.controladors.CtrlPresentacio;
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
    private JButton sortirButton;
    private Font vt323Font = FontLoader.getCustomFont(36f);
    private Color colorsFons = ColorLoader.getInstance().getColorFons();

    private CtrlPresentacio ctrlPresentacio;

    public PantallaIniciVista(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
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
        jugarNovaPartidaButton = crearBotoRetro("Jugar nova partida", new Color(0, 255, 128), botoFont, 600, 120);
        continuarPartidaButton = crearBotoRetro("Continuar partida", new Color(255, 255, 0), botoFont, 600, 120);
        estadistiquesButton = crearBotoRetro("Estadístiques", new Color(255, 100, 100), botoFont, 600, 120);
        sortirButton = crearBotoRetro("Sortir", new Color(255, 0, 0), botoFont, 400, 80);

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

        // Afegir espai addicional abans del botó Sortir
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 20, 0);  // Més espai a dalt per separar-lo
        panellBotons.add(sortirButton, gbc);

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

        // Afegir ActionListener al botó "Jugar nova partida"
        jugarNovaPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPresentacio.configurarPartida();
            }
        });

        // Afegir ActionListener al botó "Sortir"
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tancar l'aplicació quan es clica el botó Sortir
                System.exit(0);
            }
        });
    }

    private JButton crearBotoRetro(String text, Color colorFons, Font font, int w, int h) {
        JButton boto = new JButton(text);
        boto.setAlignmentX(Component.CENTER_ALIGNMENT);
        boto.setFont(font);
        boto.setBackground(colorFons);
        boto.setForeground(Color.BLACK);
        boto.setFocusPainted(false);
        boto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        Dimension midaBoton = new Dimension(w, h);
        boto.setPreferredSize(midaBoton); // Estableix una mida fixa
        boto.setMaximumSize(midaBoton);
        boto.setMinimumSize(midaBoton);
    
        boto.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        // Store the original background color
        Color originalColor = colorFons;
    
        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boto.setBackground(originalColor.darker());
                boto.setForeground(Color.WHITE); // Change text color on hover
                boto.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Change border color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boto.setBackground(originalColor);
                boto.setForeground(Color.BLACK); // Reset text color
                boto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Reset border color
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
}