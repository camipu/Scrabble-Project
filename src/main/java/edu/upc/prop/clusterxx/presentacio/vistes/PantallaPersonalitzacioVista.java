package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.controladors.CtrlPresentacio;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import java.awt.*;

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
    private JButton aplicarBotsButton;
    private JButton aplicarJugadorsButton;

    private java.util.List<JTextField> nomsBotsFields = new ArrayList<>();
    private java.util.List<JComboBox<Integer>> dificultatsBotsCombos = new ArrayList<>();
    private java.util.List<JTextField> nomsJugadorsFields = new ArrayList<>();

    private final Font vt323Font = FontLoader.getCustomFont(20f);
    private final ColorLoader colorLoader = ColorLoader.getInstance();
    private CtrlPresentacio ctrlPresentacio;

    public PantallaPersonalitzacioVista(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        setTitle("Configuració de la partida");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        // Configuració general
        JPanel principal = new JPanel();
        principal.setBackground(colorLoader.getColorFons());
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Títol
        JLabel titol = new JLabel("Configura la teva partida", JLabel.CENTER);
        titol.setFont(FontLoader.getCustomFont(40f));
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(titol);

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Paràmetres bàsics
        JPanel parametresPanel = new JPanel();
        parametresPanel.setBackground(colorLoader.getColorFons());
        parametresPanel.setLayout(new GridLayout(5, 2, 10, 10));
        parametresPanel.setMaximumSize(new Dimension(450, 200));
        parametresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mida del taulell
        JLabel taulellLabel = new JLabel("Mida del taulell:");
        taulellLabel.setFont(vt323Font);
        parametresPanel.add(taulellLabel);

        midaTaulellField = new JTextField();
        midaTaulellField.setFont(vt323Font);
        parametresPanel.add(midaTaulellField);

        // Mida del faristol
        JLabel faristolLabel = new JLabel("Mida del faristol:");
        faristolLabel.setFont(vt323Font);
        parametresPanel.add(faristolLabel);

        midaFaristolField = new JTextField();
        midaFaristolField.setFont(vt323Font);
        parametresPanel.add(midaFaristolField);

        // Idioma
        JLabel idiomaLabel = new JLabel("Idioma:");
        idiomaLabel.setFont(vt323Font);
        parametresPanel.add(idiomaLabel);

        idiomaComboBox = new JComboBox<>(new String[]{"Català", "English", "Castellano"});
        idiomaComboBox.setFont(vt323Font);
        parametresPanel.add(idiomaComboBox);

        // Nombre de bots
        JLabel botsLabel = new JLabel("Nombre de bots:");
        botsLabel.setFont(vt323Font);
        parametresPanel.add(botsLabel);

        JPanel botsInputPanel = new JPanel(new BorderLayout(5, 0));
        botsInputPanel.setBackground(colorLoader.getColorFons());

        numBotsField = new JTextField();
        numBotsField.setFont(vt323Font);
        botsInputPanel.add(numBotsField, BorderLayout.CENTER);

        aplicarBotsButton = new JButton("Aplicar");
        aplicarBotsButton.setFont(vt323Font);
        aplicarBotsButton.setBackground(colorLoader.getColorSeleccionada());
        aplicarBotsButton.addActionListener(e -> actualitzarBots());
        botsInputPanel.add(aplicarBotsButton, BorderLayout.EAST);

        parametresPanel.add(botsInputPanel);

        // Nombre de jugadors
        JLabel jugadorsLabel = new JLabel("Nombre de jugadors:");
        jugadorsLabel.setFont(vt323Font);
        parametresPanel.add(jugadorsLabel);

        JPanel jugadorsInputPanel = new JPanel(new BorderLayout(5, 0));
        jugadorsInputPanel.setBackground(colorLoader.getColorFons());

        numJugadorsField = new JTextField();
        numJugadorsField.setFont(vt323Font);
        jugadorsInputPanel.add(numJugadorsField, BorderLayout.CENTER);

        aplicarJugadorsButton = new JButton("Aplicar");
        aplicarJugadorsButton.setFont(vt323Font);
        aplicarJugadorsButton.setBackground(colorLoader.getColorSeleccionada());
        aplicarJugadorsButton.addActionListener(e -> actualitzarJugadors());
        jugadorsInputPanel.add(aplicarJugadorsButton, BorderLayout.EAST);

        parametresPanel.add(jugadorsInputPanel);

        principal.add(parametresPanel);
        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panell de bots
        JLabel botsPanelLabel = new JLabel("Configuració Bots:");
        botsPanelLabel.setFont(vt323Font);
        botsPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(botsPanelLabel);

        botsPanel = new JPanel();
        botsPanel.setBackground(colorLoader.getColorFonsFitxa());
        botsPanel.setLayout(new BoxLayout(botsPanel, BoxLayout.Y_AXIS));
        botsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane botsScrollPane = new JScrollPane(botsPanel);
        botsScrollPane.setMaximumSize(new Dimension(500, 150));
        botsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(botsScrollPane);

        principal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panell de jugadors
        JLabel jugadorsPanelLabel = new JLabel("Configuració Jugadors:");
        jugadorsPanelLabel.setFont(vt323Font);
        jugadorsPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(jugadorsPanelLabel);

        jugadorsPanel = new JPanel();
        jugadorsPanel.setBackground(colorLoader.getColorFonsFitxa());
        jugadorsPanel.setLayout(new BoxLayout(jugadorsPanel, BoxLayout.Y_AXIS));
        jugadorsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane jugadorsScrollPane = new JScrollPane(jugadorsPanel);
        jugadorsScrollPane.setMaximumSize(new Dimension(500, 150));
        jugadorsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(jugadorsScrollPane);

        principal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botó confirmar
        confirmarButton = new JButton("Confirmar configuració");
        confirmarButton.setFont(vt323Font);
        confirmarButton.setBackground(colorLoader.getColorSeleccionada());
        confirmarButton.setFocusPainted(false);
        confirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmarButton.addActionListener(e -> confirmarConfiguracio());
        principal.add(confirmarButton);

        add(new JScrollPane(principal));
        // Botó configuració predeterminada
        JButton botoDefault = new JButton("Configuració Predeterminada");
        botoDefault.setFont(vt323Font);
        botoDefault.setBackground(colorLoader.getColorAccent());
        botoDefault.setFocusPainted(false);
        botoDefault.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoDefault.addActionListener(e -> aplicarConfiguracioPredeterminada());
        principal.add(Box.createRigidArea(new Dimension(0, 10)));
        principal.add(botoDefault);

    }

    private void aplicarConfiguracioPredeterminada() {
        // Assignar valors bàsics
        midaTaulellField.setText("15");
        midaFaristolField.setText("7");
        idiomaComboBox.setSelectedIndex(0); // Català

        // Bots
        numBotsField.setText("1");
        actualitzarBots(); // Això reconstrueix el panell
        if (!nomsBotsFields.isEmpty()) {
            nomsBotsFields.get(0).setText("Bot 1");
            dificultatsBotsCombos.get(0).setSelectedItem(1);
        }

        // Jugadors
        numJugadorsField.setText("1");
        actualitzarJugadors(); // Això reconstrueix el panell
        if (!nomsJugadorsFields.isEmpty()) {
            nomsJugadorsFields.get(0).setText("Loser");
        }
    }


    private void actualitzarBots() {
        botsPanel.removeAll();
        nomsBotsFields.clear();
        dificultatsBotsCombos.clear();

        int numBots = obtenirNumero(numBotsField.getText());
        if (numBots <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Introdueix un nombre vàlid de bots (major que 0)",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Titols de les columnes
        JPanel headerPanel = new JPanel(new GridLayout(1, 2));
        headerPanel.setBackground(colorLoader.getColorFonsFitxa());
        headerPanel.setMaximumSize(new Dimension(450, 30));

        JLabel nomLabel = new JLabel("Nom del Bot", JLabel.CENTER);
        nomLabel.setFont(vt323Font);
        headerPanel.add(nomLabel);

        JLabel dificultatLabel = new JLabel("Dificultat", JLabel.CENTER);
        dificultatLabel.setFont(vt323Font);
        headerPanel.add(dificultatLabel);

        botsPanel.add(headerPanel);
        botsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (int i = 0; i < numBots; ++i) {
            JPanel fila = new JPanel(new GridLayout(1, 2, 10, 0));
            fila.setBackground(colorLoader.getColorFonsFitxa());
            fila.setMaximumSize(new Dimension(450, 30));

            JTextField nom = new JTextField("Bot " + (i + 1));
            nom.setFont(vt323Font);
            fila.add(nom);

            JComboBox<Integer> dificultat = new JComboBox<>(new Integer[]{1, 2, 3});
            dificultat.setFont(vt323Font);
            fila.add(dificultat);

            nomsBotsFields.add(nom);
            dificultatsBotsCombos.add(dificultat);

            botsPanel.add(fila);
            botsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        botsPanel.revalidate();
        botsPanel.repaint();
    }

    private void actualitzarJugadors() {
        jugadorsPanel.removeAll();
        nomsJugadorsFields.clear();

        int numJugadors = obtenirNumero(numJugadorsField.getText());
        if (numJugadors <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Introdueix un nombre vàlid de jugadors (major que 0)",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Títol de la columna
        JLabel headerLabel = new JLabel("Nom del Jugador", JLabel.CENTER);
        headerLabel.setFont(vt323Font);
        headerLabel.setMaximumSize(new Dimension(450, 30));
        jugadorsPanel.add(headerLabel);
        jugadorsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (int i = 0; i < numJugadors; ++i) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setBackground(colorLoader.getColorFonsFitxa());
            fila.setMaximumSize(new Dimension(450, 30));

            JLabel indexLabel = new JLabel("Jugador " + (i + 1) + ":", JLabel.LEFT);
            indexLabel.setFont(vt323Font);
            indexLabel.setPreferredSize(new Dimension(100, 30));
            fila.add(indexLabel, BorderLayout.WEST);

            JTextField nom = new JTextField();
            nom.setFont(vt323Font);
            fila.add(nom, BorderLayout.CENTER);

            nomsJugadorsFields.add(nom);

            jugadorsPanel.add(fila);
            jugadorsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
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
        // Validar tots els camps
        int midaTaulell = obtenirNumero(midaTaulellField.getText());
        int midaFaristol = obtenirNumero(midaFaristolField.getText());
        String idioma = (String) idiomaComboBox.getSelectedItem();

        if (midaTaulell <= 0 || midaFaristol <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La mida del taulell i del faristol han de ser valors positius",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nomsBotsFields.isEmpty() && nomsJugadorsFields.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Configura almenys un bot o un jugador abans de continuar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Recollir informació dels bots
        int numBots = nomsBotsFields.size();
        int[] dificultatsBots = new int[numBots];
        String[] nomsBots = new String[numBots];

        for (int i = 0; i < numBots; ++i) {
            nomsBots[i] = nomsBotsFields.get(i).getText().trim();
            dificultatsBots[i] = (int) dificultatsBotsCombos.get(i).getSelectedItem();

            if (nomsBots[i].isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tots els bots han de tenir un nom",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Recollir informació dels jugadors
        int numJugadors = nomsJugadorsFields.size();
        String[] nomsJugadors = new String[numJugadors];

        for (int i = 0; i < numJugadors; ++i) {
            nomsJugadors[i] = nomsJugadorsFields.get(i).getText().trim();

            if (nomsJugadors[i].isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tots els jugadors han de tenir un nom",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        

        ctrlPresentacio.inicialitzarPartida(
                midaTaulell,
                midaFaristol,
                idioma,
                nomsJugadors,
                dificultatsBots
        );

        JOptionPane.showMessageDialog(this,
                "Configuració completada correctament",
                "Èxit", JOptionPane.INFORMATION_MESSAGE);
    }
}