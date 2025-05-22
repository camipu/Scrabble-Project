package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista que muestra el resultado final de la partida, con el ganador destacado.
 * Incluye el tablero final, el ranking de jugadores y botones para volver al menú principal.
 */
public class GuanyadorVista extends JPanel {

    private final Taulell taulell;
    private final List<Jugador> jugadors;
    private final Jugador guanyador;
    private final TaulellVista taulellVista;
    private JButton botoTornarMenu;
    private JButton botoNovaPartida;

    /**
     * Crea una nueva vista de fin de partida con el tablero y jugadores especificados.
     * Asume que la lista de jugadores ya viene ordenada por puntuación y el ganador es el primero.
     *
     * @param taulell Tablero final de la partida
     * @param jugadors Lista de jugadores ordenada por puntuación (ranking)
     */
    public GuanyadorVista(Taulell taulell, List<Jugador> jugadors) {
        this.taulell = taulell;
        this.jugadors = jugadors;
        // El ganador es el primero de la lista ordenada
        this.guanyador = jugadors.isEmpty() ? null : jugadors.get(0);

        // Configuración general del panel
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ColorLoader.getInstance().getColorFons());

        // Panel superior con el título y mensaje de ganador
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con el tablero final
        taulellVista = new TaulellVista(taulell);
        taulellVista.setEnabled(false); // Deshabilitar interacción con el tablero
        add(taulellVista, BorderLayout.CENTER);

        // Panel lateral con ranking de jugadores
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BorderLayout(0, 20));
        panelLateral.setBackground(ColorLoader.getInstance().getColorFons());
        panelLateral.setBorder(new EmptyBorder(0, 10, 0, 0));

        JPanel panelRanking = crearPanelRanking();
        JPanel panelBotons = crearPanelBotons();

        panelLateral.add(panelRanking, BorderLayout.CENTER);
        panelLateral.add(panelBotons, BorderLayout.SOUTH);

        add(panelLateral, BorderLayout.EAST);
    }

    /**
     * Crea el panel superior con el título y mensaje del ganador.
     *
     * @return Panel configurado con título y mensaje
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorLoader.getInstance().getColorFons());
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Título "Fi de la Partida"
        JLabel titolLabel = new JLabel("Fi de la Partida");
        titolLabel.setFont(FontLoader.getCustomFont(48f).deriveFont(Font.BOLD));
        titolLabel.setForeground(ColorLoader.getInstance().getColorAccent());
        titolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titolLabel);
        panel.add(Box.createVerticalStrut(15));

        // Panel con el mensaje del ganador
        if (guanyador != null) {
            JPanel panelGuanyador = new JPanel();
            panelGuanyador.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelGuanyador.setBackground(new Color(
                    ColorLoader.getInstance().getColorAccent().getRed(),
                    ColorLoader.getInstance().getColorAccent().getGreen(),
                    ColorLoader.getInstance().getColorAccent().getBlue(),
                    50));
            panelGuanyador.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));

            JLabel guanyadorLabel = new JLabel("Guanyador: " + guanyador.obtenirNom() + " - " + guanyador.obtenirPunts() + " punts");
            guanyadorLabel.setFont(FontLoader.getCustomFont(28f).deriveFont(Font.BOLD));
            guanyadorLabel.setForeground(Color.BLACK);

            panelGuanyador.add(guanyadorLabel);
            panel.add(panelGuanyador);
        }

        return panel;
    }

    /**
     * Crea el panel con el ranking de jugadores.
     *
     * @return Panel configurado con el ranking
     */
    private JPanel crearPanelRanking() {
        // Panel exterior que contiene todo
        JPanel panelExterior = new JPanel();
        panelExterior.setLayout(new BorderLayout());
        panelExterior.setBackground(ColorLoader.getInstance().getColorFons());
        panelExterior.setBorder(crearTitledBorder("Ranking Final"));

        // Panel interno para las puntuaciones con altura fija
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorLoader.getInstance().getColorFons());

        // Calculamos una altura apropiada según el número de jugadores
        int alturaPanel = Math.max(200, jugadors.size() * 40);
        panel.setPreferredSize(new Dimension(0, alturaPanel));

        Font fontRanking = FontLoader.getCustomFont(16f);
        Font fontRankingNegreta = FontLoader.getCustomFont(16f).deriveFont(Font.BOLD);
        Color colorTextNormal = ColorLoader.getInstance().getColorText();
        Color colorTextGuanyador = new Color(255, 255, 255);
        Color colorIndicadorGuanyador = ColorLoader.getInstance().getColorAccent();
        Color colorFonsGuanyador = new Color(
                colorIndicadorGuanyador.getRed(),
                colorIndicadorGuanyador.getGreen(),
                colorIndicadorGuanyador.getBlue(),
                100); // Semi-transparente

        // Añadimos el título del ranking
        JLabel tituloRanking = new JLabel("Posició  Jugador  Punts");
        tituloRanking.setFont(fontRankingNegreta);
        tituloRanking.setForeground(ColorLoader.getInstance().getColorAccent());
        tituloRanking.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        panel.add(tituloRanking);

        // Para cada jugador, creamos una etiqueta con su posición y puntuación
        for (int i = 0; i < jugadors.size(); i++) {
            Jugador j = jugadors.get(i);
            boolean esGuanyador = j.equals(guanyador);

            JPanel jugadorPanel = new JPanel();
            jugadorPanel.setLayout(new BorderLayout(5, 5));
            jugadorPanel.setBackground(esGuanyador ? colorFonsGuanyador : ColorLoader.getInstance().getColorFons());
            jugadorPanel.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
            jugadorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            // Posición en el ranking
            JLabel posicioLabel = new JLabel(String.valueOf(i + 1));
            posicioLabel.setFont(esGuanyador ? fontRankingNegreta : fontRanking);
            posicioLabel.setForeground(esGuanyador ? colorTextGuanyador : colorTextNormal);
            posicioLabel.setPreferredSize(new Dimension(30, 20));

            // Nombre del jugador
            JLabel nomLabel = new JLabel(j.obtenirNom());
            nomLabel.setFont(esGuanyador ? fontRankingNegreta : fontRanking);
            nomLabel.setForeground(esGuanyador ? colorTextGuanyador : colorTextNormal);

            // Puntuación del jugador
            JLabel puntuacioLabel = new JLabel(String.valueOf(j.obtenirPunts()));
            puntuacioLabel.setFont(esGuanyador ? fontRankingNegreta : fontRanking);
            puntuacioLabel.setForeground(esGuanyador ? colorIndicadorGuanyador : colorIndicadorGuanyador);

            jugadorPanel.add(posicioLabel, BorderLayout.WEST);

            JPanel infoPanel = new JPanel(new BorderLayout(5, 0));
            infoPanel.setBackground(esGuanyador ? colorFonsGuanyador : ColorLoader.getInstance().getColorFons());
            infoPanel.add(nomLabel, BorderLayout.WEST);
            infoPanel.add(puntuacioLabel, BorderLayout.EAST);

            jugadorPanel.add(infoPanel, BorderLayout.CENTER);

            // Agregamos un borde redondeado y un icono si es el ganador
            if (esGuanyador) {
                jugadorPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorIndicadorGuanyador, 2),
                        BorderFactory.createEmptyBorder(6, 3, 6, 3)
                ));

                // Icono de trofeo para el ganador
                JLabel iconoLabel = new JLabel("\uD83C\uDFC6"); // Emoji de trofeo
                iconoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
                jugadorPanel.add(iconoLabel, BorderLayout.EAST);
            }

            panel.add(jugadorPanel);
            panel.add(Box.createVerticalStrut(5));
        }

        // Añadimos espacio al final
        panel.add(Box.createVerticalGlue());

        // Agregamos un JScrollPane en caso de que haya muchos jugadores
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(ColorLoader.getInstance().getColorFons());

        panelExterior.add(scrollPane, BorderLayout.CENTER);

        return panelExterior;
    }

    /**
     * Crea el panel con los botones de acción.
     *
     * @return Panel configurado con botones
     */
    private JPanel crearPanelBotons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 0, 10));
        panel.setBackground(ColorLoader.getInstance().getColorFons());
        panel.setBorder(crearTitledBorder("Opcions"));

        Font fontBotons = FontLoader.getCustomFont(16f);
        Color colorFons = ColorLoader.getInstance().getColorFonsFitxa();
        Color colorText = ColorLoader.getInstance().getColorText();

        botoNovaPartida = crearBoto("Nova Partida", fontBotons, colorFons, colorText);
        botoTornarMenu = crearBoto("Tornar al Menú", fontBotons, colorFons, colorText);

        panel.add(botoNovaPartida);
        panel.add(botoTornarMenu);

        return panel;
    }

    /**
     * Crea un borde con título para los paneles.
     *
     * @param titol Título del panel
     * @return TitledBorder configurado
     */
    private TitledBorder crearTitledBorder(String titol) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 1),
                titol
        );
        border.setTitleFont(FontLoader.getCustomFont(14f));
        border.setTitleColor(ColorLoader.getInstance().getColorAccent());
        return border;
    }

    /**
     * Crea un botón con el estilo común del juego.
     *
     * @param text Texto del botón
     * @param font Fuente a utilizar
     * @param colorFons Color de fondo del botón
     * @param colorText Color del texto del botón
     * @return Botón configurado con el estilo especificado
     */
    private JButton crearBoto(String text, Font font, Color colorFons, Color colorText) {
        JButton boto = new JButton(text);
        boto.setFont(font);
        boto.setBackground(colorFons);
        boto.setForeground(colorText);
        boto.setFocusPainted(false);
        boto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // Efecto hover
        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorFons.darker());
                boto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorFons);
                boto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });

        return boto;
    }

    /**
     * Establece el ActionListener para el botón de volver al menú principal.
     *
     * @param listener ActionListener a establecer
     */
    public void setTornarMenuListener(ActionListener listener) {
        botoTornarMenu.addActionListener(listener);
    }

    /**
     * Establece el ActionListener para el botón de nueva partida.
     *
     * @param listener ActionListener a establecer
     */
    public void setNovaPartidaListener(ActionListener listener) {
        botoNovaPartida.addActionListener(listener);
    }

}


