package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.FontLoader;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * Vista principal de la partida, que integra el taulell, la informació del jugador,
 * els botons de control del joc i el panell de puntuacions dels jugadors.
 */
public class PartidaVista extends JPanel {

    private final Taulell taulell;
    private final Jugador jugador;
    private final TaulellVista taulellVista;
    private final JugadorVista jugadorVista;
    private final List<JLabel> etiquetesPuntuacions = new ArrayList<>();
    private JPanel panellPuntuacions;
    private JButton botoPassar;
    private JButton botoCanviarFitxes;
    private JButton botoValidarJugada;
    private JButton botoColocar;

    /**
     * Crea una nova vista de partida amb el taulell i jugador especificats.
     *
     * @param taulell Taulell de la partida
     * @param jugador Jugador actual
     * @param jugadors Llista de tots els jugadors de la partida
     */
    public PartidaVista(Taulell taulell, Jugador jugador, List<Jugador> jugadors) {
        this.taulell = taulell;
        this.jugador = jugador;

        // Establim el layout general de la vista
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ColorLoader.getInstance().getColorFons());

        // Creem la vista del taulell i la posicionem al centre
        taulellVista = new TaulellVista(taulell);
        taulellVista.setSeleccioListener(this::handleCasellaSeleccionada);

        // Creem la vista del jugador i la posicionem a la part inferior
        jugadorVista = new JugadorVista(jugador);

        // Creem el panell lateral que contindrà els botons i puntuacions
        JPanel panellLateral = new JPanel();
        panellLateral.setLayout(new BorderLayout(0, 20));
        panellLateral.setBackground(ColorLoader.getInstance().getColorFons());
        panellLateral.setBorder(new EmptyBorder(0, 10, 0, 0));

        // Creem el panell de botons
        JPanel panellBotons = crearPanellBotons();

        // Creem el panell de puntuacions
        panellPuntuacions = crearPanellPuntuacions(jugadors);

        // Afegim els panells al panell lateral
        panellLateral.add(panellPuntuacions, BorderLayout.CENTER);
        panellLateral.add(panellBotons, BorderLayout.SOUTH);

        // Afegim tots els components a la vista principal
        add(taulellVista, BorderLayout.CENTER);
        add(jugadorVista, BorderLayout.SOUTH);
        add(panellLateral, BorderLayout.EAST);

        // Establim el torn actiu pel jugador inicial
        jugadorVista.setTornActiu(true);
    }

    /**
     * Constructor alternatiu per compatibilitat amb codi anterior.
     *
     * @param taulell Taulell de la partida
     * @param jugador Jugador actual
     */
    public PartidaVista(Taulell taulell, Jugador jugador) {
        this(taulell, jugador, List.of(jugador));
    }

    /**
     * Crea el panell amb els botons de control del joc.
     *
     * @return Panell amb els botons de control
     */
    private JPanel crearPanellBotons() {
        JPanel panell = new JPanel();
        panell.setLayout(new GridLayout(4, 1, 0, 10));
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(crearTitledBorder("Controls"));

        Font fontBotons = FontLoader.getCustomFont(16f);
        Color colorFons = ColorLoader.getInstance().getColorFonsFitxa();
        Color colorText = ColorLoader.getInstance().getColorText();

        botoValidarJugada = crearBoto("Validar jugada", fontBotons, colorFons, colorText);
        botoColocar = crearBoto("Col·locar", fontBotons, colorFons, colorText);
        botoCanviarFitxes = crearBoto("Canviar fitxes", fontBotons, colorFons, colorText);
        botoPassar = crearBoto("Passar torn", fontBotons, colorFons, colorText);

        panell.add(botoValidarJugada);
        panell.add(botoColocar);
        panell.add(botoCanviarFitxes);
        panell.add(botoPassar);

        return panell;
    }

    /**
     * Crea el panell de puntuacions per a tots els jugadors.
     *
     * @param jugadors Llista de jugadors de la partida
     * @return Panell amb les puntuacions dels jugadors
     */
    private JPanel crearPanellPuntuacions(List<Jugador> jugadors) {
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(crearTitledBorder("Puntuacions"));

        Font fontPuntuacions = FontLoader.getCustomFont(14f);

        // Per cada jugador, creem una etiqueta amb la seva puntuació
        for (Jugador j : jugadors) {
            JPanel jugadorPanel = new JPanel();
            jugadorPanel.setLayout(new BorderLayout(5, 5));
            jugadorPanel.setBackground(ColorLoader.getInstance().getColorFons());
            jugadorPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Indicador de torn actual
            JPanel indicadorPanel = new JPanel();
            indicadorPanel.setPreferredSize(new Dimension(15, 15));
            indicadorPanel.setBackground(j.equals(jugador) ?
                    ColorLoader.getInstance().getColorAccent() :
                    ColorLoader.getInstance().getColorFons());
            jugadorPanel.add(indicadorPanel, BorderLayout.WEST);

            // Nom del jugador
            JLabel nomLabel = new JLabel(j.obtenirNom());
            nomLabel.setFont(fontPuntuacions);
            nomLabel.setForeground(ColorLoader.getInstance().getColorText());

            // Puntuació del jugador
            JLabel puntuacioLabel = new JLabel(String.valueOf(j.obtenirPunts()));
            puntuacioLabel.setFont(fontPuntuacions);
            puntuacioLabel.setForeground(ColorLoader.getInstance().getColorAccent());

            JPanel infoPanel = new JPanel(new BorderLayout(5, 0));
            infoPanel.setBackground(ColorLoader.getInstance().getColorFons());
            infoPanel.add(nomLabel, BorderLayout.WEST);
            infoPanel.add(puntuacioLabel, BorderLayout.EAST);

            jugadorPanel.add(infoPanel, BorderLayout.CENTER);

            panell.add(jugadorPanel);
            panell.add(Box.createVerticalStrut(5));

            // Guardem la referència a l'etiqueta de puntuació per actualitzar-la després
            etiquetesPuntuacions.add(puntuacioLabel);
        }

        // Afegim un espai expandible al final per alinear els elements a la part superior
        panell.add(Box.createVerticalGlue());

        return panell;
    }

    /**
     * Crea un borde con título para los paneles.
     *
     * @param titol Títol del panell
     * @return TitledBorder configurat
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
     * Crea un botó amb l'estil comú del joc.
     *
     * @param text Text del botó
     * @param font Font a utilitzar
     * @param colorFons Color de fons del botó
     * @param colorText Color del text del botó
     * @return Botó configurat amb l'estil especificat
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
        return boto;
    }

    /**
     * Gestiona l'event de selecció d'una casella al taulell.
     *
     * @param casella Casella seleccionada
     */
    private void handleCasellaSeleccionada(Casella casella) {
        // Aquí es pot implementar la lògica quan es selecciona una casella
        System.out.println("Casella seleccionada a PartidaVista: " + casella);
    }

    /**
     * Estableix l'ActionListener pel botó de passar torn.
     *
     * @param passarTornAction Runnable a establir
     */
    public void setPassarTornListener(Runnable passarTornAction) {
        botoPassar.addActionListener(e -> passarTornAction.run());
    }

    /**
     * Estableix l'ActionListener pel botó de canviar fitxes.
     *
     * @param listener ActionListener a establir
     */
    public void setCanviarFitxesListener(ActionListener listener) {
        botoCanviarFitxes.addActionListener(listener);
    }

    /**
     * Estableix l'ActionListener pel botó de validar jugada.
     *
     * @param listener ActionListener a establir
     */
    public void setValidarJugadaListener(ActionListener listener) {
        botoValidarJugada.addActionListener(listener);
    }

    /**
     * Estableix l'ActionListener pel botó de col·locar.
     *
     * @param listener ActionListener a establir
     */
    public void setColocarListener(ActionListener listener) {
        botoColocar.addActionListener(listener);
    }

    /**
     * Obté la vista del taulell.
     *
     * @return TaulellVista associada a aquesta partida
     */
    public TaulellVista getTaulellVista() {
        return taulellVista;
    }

    /**
     * Obté la vista del jugador.
     *
     * @return JugadorVista associada a aquesta partida
     */
    public JugadorVista getJugadorVista() {
        return jugadorVista;
    }

    /**
     * Actualitza les puntuacions de tots els jugadors al panell de puntuacions.
     *
     * @param jugadors Llista de jugadors amb les puntuacions actualitzades
     * @param jugadorActual Jugador que té el torn actualment
     */
    public void actualitzarPuntuacions(List<Jugador> jugadors, Jugador jugadorActual) {
        if (jugadors.size() != etiquetesPuntuacions.size()) {
            return; // Els arrays han de tenir la mateixa mida
        }

        // Recorrem el panell de puntuacions i actualitzem cada component
        for (int i = 0; i < jugadors.size(); i++) {
            Jugador j = jugadors.get(i);
            JLabel etiqueta = etiquetesPuntuacions.get(i);

            // Actualitzem el valor de la puntuació
            etiqueta.setText(String.valueOf(j.obtenirPunts()));

            // Actualitzem l'indicador de torn actual
            JPanel jugadorPanel = (JPanel) panellPuntuacions.getComponent(i * 2); // Compensem pels Box.createVerticalStrut
            JPanel indicadorPanel = (JPanel) jugadorPanel.getComponent(0);
            indicadorPanel.setBackground(j.equals(jugadorActual) ?
                    ColorLoader.getInstance().getColorAccent() :
                    ColorLoader.getInstance().getColorFons());
        }

        panellPuntuacions.revalidate();
        panellPuntuacions.repaint();
    }

    /**
     * Actualitza tota la vista de la partida, incloent taulell i jugador.
     */
    public void actualitzarVista() {
        taulellVista.actualitzarTaulell();
        jugadorVista.actualitzarVista();
    }
}