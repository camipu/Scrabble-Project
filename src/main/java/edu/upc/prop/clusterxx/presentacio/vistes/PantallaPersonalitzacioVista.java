package edu.upc.prop.clusterxx.presentacio.vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Pantalla de configuració de partida.
 */
public class PantallaPersonalitzacioVista extends JFrame {
    private JComboBox<String> idiomaComboBox;
    private JTextField midaTaulellField;
    private JTextField midaFaristolField;
    private JTextField numBotsField;
    private JTextField numJugadorsField;
    private JPanel botsPanel;
    private JPanel jugadorsPanel;
    private JButton confirmarButton;

    private java.util.List<JTextField> nomsBotsFields = new ArrayList<>();
    private java.util.List<JComboBox<Integer>> dificultatsBotsCombos = new ArrayList<>();
    private java.util.List<JTextField> nomsJugadorsFields = new ArrayList<>();

    private Font vt323Font;  // Font VT323

    public PantallaPersonalitzacioVista() {
        // Intentar carregar la font VT323
        try {
            // Carregar la font VT323 des del fitxer TTF
            vt323Font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/VT323-Regular.ttf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();  // Si no es pot carregar la font, mostrar error
            vt323Font = new Font("Arial", Font.PLAIN, 20);  // Si falla, utilitza la font per defecte
        }

        setTitle("Configuració de la partida");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Configuració general
        JPanel principal = new JPanel();
        principal.setBackground(new Color(240, 248, 255)); // Blau molt suau
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Títol
        JLabel titol = new JLabel("Configura la teva partida", JLabel.CENTER);
        titol.setFont(vt323Font);
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(titol);

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Mida del taulell
        midaTaulellField = crearCampText(principal, "Mida del taulell:");

        // Mida del faristol
        midaFaristolField = crearCampText(principal, "Mida del faristol:");

        // Idioma
        JLabel idiomaLabel = new JLabel("Idioma:");
        idiomaLabel.setFont(vt323Font);
        idiomaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(idiomaLabel);

        idiomaComboBox = new JComboBox<>(new String[]{"Català", "English", "Castellano"});
        idiomaComboBox.setFont(vt323Font);
        idiomaComboBox.setMaximumSize(new Dimension(200, 30));
        idiomaComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(idiomaComboBox);

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Nombre de bots
        numBotsField = crearCampText(principal, "Nombre de bots:");
        numBotsField.addActionListener(e -> actualitzarBots());

        // Nombre de jugadors
        numJugadorsField = crearCampText(principal, "Nombre de jugadors:");
        numJugadorsField.addActionListener(e -> actualitzarJugadors());

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panell de bots
        JLabel botsLabel = new JLabel("Configuració Bots:");
        botsLabel.setFont(vt323Font);
        botsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(botsLabel);

        botsPanel = new JPanel();
        botsPanel.setBackground(new Color(224, 255, 255)); // Blauet molt clar
        botsPanel.setLayout(new BoxLayout(botsPanel, BoxLayout.Y_AXIS));
        principal.add(botsPanel);

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panell de jugadors
        JLabel jugadorsLabel = new JLabel("Configuració Jugadors:");
        jugadorsLabel.setFont(vt323Font);
        jugadorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(jugadorsLabel);

        jugadorsPanel = new JPanel();
        jugadorsPanel.setBackground(new Color(224, 255, 255));
        jugadorsPanel.setLayout(new BoxLayout(jugadorsPanel, BoxLayout.Y_AXIS));
        principal.add(jugadorsPanel);

        principal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botó confirmar
        confirmarButton = new JButton("Confirmar configuració");
        confirmarButton.setFont(vt323Font);
        confirmarButton.setBackground(new Color(144, 238, 144)); // Verd clar
        confirmarButton.setFocusPainted(false);
        confirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarConfiguracio();
            }
        });
        principal.add(confirmarButton);

        add(new JScrollPane(principal));
    }

    private JTextField crearCampText(JPanel panell, String etiqueta) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(vt323Font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panell.add(label);

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(200, 30));
        textField.setFont(vt323Font);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panell.add(textField);

        panell.add(Box.createRigidArea(new Dimension(0, 10)));
        return textField;
    }

    private void actualitzarBots() {
        botsPanel.removeAll();
        nomsBotsFields.clear();
        dificultatsBotsCombos.clear();

        int numBots = obtenirNumero(numBotsField.getText());

        for (int i = 0; i < numBots; ++i) {
            JPanel fila = new JPanel();
            fila.setBackground(new Color(224, 255, 255));
            fila.setLayout(new FlowLayout());

            JTextField nom = new JTextField("Nom Bot " + (i + 1), 10);
            nom.setFont(vt323Font);
            fila.add(nom);

            JComboBox<Integer> dificultat = new JComboBox<>(new Integer[]{1, 2, 3});
            dificultat.setFont(vt323Font);
            fila.add(dificultat);

            nomsBotsFields.add(nom);
            dificultatsBotsCombos.add(dificultat);

            botsPanel.add(fila);
        }
        botsPanel.revalidate();
        botsPanel.repaint();
    }

    private void actualitzarJugadors() {
        jugadorsPanel.removeAll();
        nomsJugadorsFields.clear();

        int numJugadors = obtenirNumero(numJugadorsField.getText());

        for (int i = 0; i < numJugadors; ++i) {
            JTextField nom = new JTextField("Nom Jugador " + (i + 1), 15);
            nom.setFont(vt323Font);
            jugadorsPanel.add(nom);
            nomsJugadorsFields.add(nom);
        }
        jugadorsPanel.revalidate();
        jugadorsPanel.repaint();
    }

    private int obtenirNumero(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void confirmarConfiguracio() {
        int midaTaulell = obtenirNumero(midaTaulellField.getText());
        int midaFaristol = obtenirNumero(midaFaristolField.getText());
        String idioma = (String) idiomaComboBox.getSelectedItem();

        int numBots = nomsBotsFields.size();
        int[] dificultatsBots = new int[numBots];
        String[] nomsBots = new String[numBots];

        for (int i = 0; i < numBots; ++i) {
            nomsBots[i] = nomsBotsFields.get(i).getText().trim();
            dificultatsBots[i] = (int) dificultatsBotsCombos.get(i).getSelectedItem();
        }

        int numJugadors = nomsJugadorsFields.size();
        String[] nomsJugadors = new String[numJugadors];

        for (int i = 0; i < numJugadors; ++i) {
            nomsJugadors[i] = nomsJugadorsFields.get(i).getText().trim();
        }

        // Aquí pots cridar al teu CtrlDomini
        System.out.println("Mida taulell: " + midaTaulell);
        System.out.println("Mida faristol: " + midaFaristol);
        System.out.println("Idioma: " + idioma);
        System.out.println("Noms Bots i dificultats:");
        for (int i = 0; i < nomsBots.length; ++i) {
            System.out.println("  " + nomsBots[i] + " - Dificultat " + dificultatsBots[i]);
        }
        System.out.println("Noms Jugadors:");
        for (String s : nomsJugadors) {
            System.out.println("  " + s);
        }

        // ctrlDomini.inicialitzarPartida(midaTaulell, midaFaristol, idioma, nomsJugadors, dificultatsBots);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaPersonalitzacioVista vista = new PantallaPersonalitzacioVista();
            vista.setVisible(true);
        });
    }
}
