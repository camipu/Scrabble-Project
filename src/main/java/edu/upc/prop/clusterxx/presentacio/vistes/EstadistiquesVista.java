package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.Estadistiques;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class EstadistiquesVista extends JFrame {

    private final Estadistiques estadistiques = Estadistiques.getInstance();
    private final Font fontLletra = FontLoader.getCustomFont(24f); // Font més gran i elegant
    private final ColorLoader colorLoader = ColorLoader.getInstance(); // Obtenim la instància de ColorLoader

    public EstadistiquesVista() {
        setTitle("Leaderboard");
        setSize(600, 400);
        setLocationRelativeTo(null); // Centra la finestra
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panell per mostrar la leaderboard
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setBackground(colorLoader.getColorFons()); // Utilitzem el color fons de ColorLoader

        // Obtenim les puntuacions i les ordenem
        Map<String, Integer> puntuacions = estadistiques.getPuntuacions();
        TreeMap<Integer, String> sortedScores = new TreeMap<>((a, b) -> b.compareTo(a)); // Ordena per puntuació descendent

        for (Map.Entry<String, Integer> entry : puntuacions.entrySet()) {
            sortedScores.put(entry.getValue(), entry.getKey());
        }

        // Afegir cada jugador a la vista
        for (Map.Entry<Integer, String> entry : sortedScores.entrySet()) {
            String jugador = entry.getValue();
            Integer punts = entry.getKey();
            JLabel label = new JLabel(jugador + " - " + punts + " punts");
            label.setFont(fontLletra);
            label.setForeground(colorLoader.getColorText()); // Utilitzem el color de text de ColorLoader
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardPanel.add(label);
        }

        // Afegir el panell de la leaderboard al centre de la finestra
        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Botó de tancar
        JButton closeButton = new JButton("Tancar");
        closeButton.setFont(fontLletra);
        closeButton.setBackground(colorLoader.getColorSeleccionada()); // Utilitzem el color seleccionat per al botó
        closeButton.setForeground(Color.BLACK); // El text del botó en negre per contrastar
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose()); // Tancar la finestra
        leaderboardPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espai
        leaderboardPanel.add(closeButton);
    }
}
