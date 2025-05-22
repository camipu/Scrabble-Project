package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Fitxa;
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
    private final List<JPanel> panelsJugadors = new ArrayList<>();
    private final List<JLabel> etiquetesPuntuacions = new ArrayList<>();
    private JPanel panellPuntuacions;
    private JButton botoPassar;
    private JButton botoRetirarFitxa;
    private JButton botoCanviarFitxes;
    private JButton botoValidarJugada;
    private JButton botoColocar;
    private JButton botoGuardarPartida; // Nou botó per guardar partida
    private Casella casellaSeleccionada = null;
    private Character lletraSeleccionada = null;


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

        // Creem el panell de botons de control
        JPanel panellBotons = crearPanellBotons();

        // Creem el botó de guardar partida separat
        JPanel panellGuardar = crearPanellGuardarPartida();

        // Creem el panell de puntuacions
        panellPuntuacions = crearPanellPuntuacions(jugadors);

        // Afegim els panells al panell lateral
        panellLateral.add(panellPuntuacions, BorderLayout.CENTER);
        panellLateral.add(panellBotons, BorderLayout.SOUTH);

        // Afegim tots els components a la vista principal
        add(taulellVista, BorderLayout.CENTER);
        add(jugadorVista, BorderLayout.SOUTH);
        add(panellLateral, BorderLayout.EAST);
        add(panellGuardar, BorderLayout.NORTH); // Botó guardar a la part superior

        // Establim el torn actiu pel jugador inicial
        jugadorVista.setTornActiu(true);
    }

    /**
     * Crea el panell amb el botó de guardar partida.
     *
     * @return Panell amb el botó de guardar partida
     */
    private JPanel crearPanellGuardarPartida() {
        JPanel panell = new JPanel();
        panell.setLayout(new FlowLayout(FlowLayout.CENTER));
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(new EmptyBorder(0, 0, 10, 0));

        Font fontGuardar = FontLoader.getCustomFont(16f).deriveFont(Font.BOLD);
        Color colorFonsGuardar = new Color(46, 125, 50); // Verd fosc per destacar
        Color colorTextGuardar = Color.WHITE;

        botoGuardarPartida = crearBotoEspecial("GUARDAR PARTIDA", fontGuardar, colorFonsGuardar, colorTextGuardar);

        panell.add(botoGuardarPartida);
        return panell;
    }

    /**
     * Crea el panell amb els botons de control del joc.
     *
     * @return Panell amb els botons de control
     */
    private JPanel crearPanellBotons() {
        JPanel panell = new JPanel();
        panell.setLayout(new GridLayout(5, 1, 0, 10)); // De 4 a 5 files
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(crearTitledBorder("Controls"));

        Font fontBotons = FontLoader.getCustomFont(16f);
        Color colorFons = ColorLoader.getInstance().getColorFonsFitxa();
        Color colorText = ColorLoader.getInstance().getColorText();

        botoValidarJugada = crearBoto("Validar jugada", fontBotons, colorFons, colorText);
        botoColocar = crearBoto("Col·locar", fontBotons, colorFons, colorText);
        botoCanviarFitxes = crearBoto("Canviar fitxes", fontBotons, colorFons, colorText);
        botoPassar = crearBoto("Passar torn", fontBotons, colorFons, colorText);
        botoRetirarFitxa = crearBoto("Retirar fitxa", fontBotons, colorFons, colorText); // Nou botó

        // Afegim els botons al panell
        panell.add(botoValidarJugada);
        panell.add(botoColocar);
        panell.add(botoCanviarFitxes);
        panell.add(botoPassar);
        panell.add(botoRetirarFitxa); // Afegim el nou botó

        return panell;
    }

    /**
     * Crea el panell de puntuacions per a tots els jugadors.
     *
     * @param jugadors Llista de jugadors de la partida
     * @return Panell amb les puntuacions dels jugadors
     */
    private JPanel crearPanellPuntuacions(List<Jugador> jugadors) {
        // Panel exterior que contiene todo
        JPanel panellExterior = new JPanel();
        panellExterior.setLayout(new BorderLayout());
        panellExterior.setBackground(ColorLoader.getInstance().getColorFons());
        panellExterior.setBorder(crearTitledBorder("Puntuacions"));

        // Panel interno para las puntuaciones con altura fija
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        panell.setBackground(ColorLoader.getInstance().getColorFons());

        // Calculamos una altura apropiada según el número de jugadores
        int alturaPanel = Math.max(150, jugadors.size() * 40);
        panell.setPreferredSize(new Dimension(0, alturaPanel));

        Font fontPuntuacions = FontLoader.getCustomFont(14f);
        Font fontPuntuacionsNegreta = FontLoader.getCustomFont(15f).deriveFont(Font.BOLD);
        Color colorTextNormal = ColorLoader.getInstance().getColorText();
        Color colorTextActiu = new Color(255, 255, 255);
        Color colorIndicadorActiu = ColorLoader.getInstance().getColorAccent();
        Color colorFonsActiu = new Color(
                colorIndicadorActiu.getRed(),
                colorIndicadorActiu.getGreen(),
                colorIndicadorActiu.getBlue(),
                100); // Semi-transparente

        // Per cada jugador, creem una etiqueta amb la seva puntuació
        panelsJugadors.clear();
        etiquetesPuntuacions.clear();

        for (Jugador j : jugadors) {
            JPanel jugadorPanel = new JPanel();
            jugadorPanel.setLayout(new BorderLayout(5, 5));
            jugadorPanel.setBackground(ColorLoader.getInstance().getColorFons());
            jugadorPanel.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
            jugadorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            // Indicador de torn actual (un panel más grande y más visible)
            JPanel indicadorPanel = new JPanel();
            indicadorPanel.setPreferredSize(new Dimension(20, 20));
            indicadorPanel.setBorder(BorderFactory.createLineBorder(
                    j.equals(jugador) ? colorIndicadorActiu : ColorLoader.getInstance().getColorFons(), 2));
            indicadorPanel.setBackground(j.equals(jugador) ? colorIndicadorActiu : ColorLoader.getInstance().getColorFons());

            if (j.equals(jugador)) {
                jugadorPanel.setBackground(colorFonsActiu);
            }

            jugadorPanel.add(indicadorPanel, BorderLayout.WEST);

            // Nom del jugador
            JLabel nomLabel = new JLabel(j.obtenirNom());
            nomLabel.setFont(j.equals(jugador) ? fontPuntuacionsNegreta : fontPuntuacions);
            nomLabel.setForeground(j.equals(jugador) ? colorTextActiu : colorTextNormal);

            // Puntuació del jugador
            JLabel puntuacioLabel = new JLabel(String.valueOf(j.obtenirPunts()));
            puntuacioLabel.setFont(j.equals(jugador) ? fontPuntuacionsNegreta : fontPuntuacions);
            puntuacioLabel.setForeground(colorIndicadorActiu);

            JPanel infoPanel = new JPanel(new BorderLayout(5, 0));
            infoPanel.setBackground(j.equals(jugador) ? colorFonsActiu : ColorLoader.getInstance().getColorFons());
            infoPanel.add(nomLabel, BorderLayout.WEST);
            infoPanel.add(puntuacioLabel, BorderLayout.EAST);

            jugadorPanel.add(infoPanel, BorderLayout.CENTER);

            // Agregamos un borde redondeado si es el jugador actual
            if (j.equals(jugador)) {
                jugadorPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorIndicadorActiu, 2),
                        BorderFactory.createEmptyBorder(6, 3, 6, 3)
                ));
            }

            panell.add(jugadorPanel);
            panell.add(Box.createVerticalStrut(5));

            // Guardem referències per poder actualitzar després
            panelsJugadors.add(jugadorPanel);
            etiquetesPuntuacions.add(puntuacioLabel);
        }

        // Afegim un JScrollPane en cas que hi hagi molts jugadors
        JScrollPane scrollPane = new JScrollPane(panell);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(ColorLoader.getInstance().getColorFons());

        panellExterior.add(scrollPane, BorderLayout.CENTER);

        return panellExterior;
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
     * Crea un botó especial amb estil destacat (per exemple, per guardar partida).
     *
     * @param text Text del botó
     * @param font Font a utilitzar
     * @param colorFons Color de fons del botó
     * @param colorText Color del text del botó
     * @return Botó configurat amb l'estil especial
     */
    private JButton crearBotoEspecial(String text, Font font, Color colorFons, Color colorText) {
        JButton boto = new JButton(text);
        boto.setFont(font);
        boto.setBackground(colorFons);
        boto.setForeground(colorText);
        boto.setFocusPainted(false);
        boto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFons.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Efecte hover per al botó especial
        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorFons.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorFons);
            }
        });

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
        casellaSeleccionada = casella;
    }

    public Fitxa obtenirFitxaSeleccionada() {
        return jugadorVista.obtenirFitxaSeleccionada();
    }

    public void desseleccionarFitxa() {
        jugadorVista.obtenirFaristolVista().desseleccionarFitxa();
    }

    public void netejarSeleccions() {
        //taulellVista.desseleccionarCasella();       // Limpia la casella seleccionada
        jugadorVista.obtenirFaristolVista().desseleccionarFitxa(); // Limpia la fitxa seleccionada
    }

    /**
     * Obté la casella seleccionada actualment.
     *
     * @return Casella seleccionada
     */
    public Casella getCasellaSeleccionada() {
        return casellaSeleccionada;
    }

    /**
     * Estableix l'ActionListener pel botó de passar torn.
     *
     * @param passarTornAction Runnable a establir
     */
    public void setPassarTornListener(Runnable passarTornAction) {
        botoPassar.addActionListener(e -> passarTornAction.run());
    }

    public void setValidarJugadaListener(Runnable ValidarJugadaAction) {
        botoValidarJugada.addActionListener(e -> ValidarJugadaAction.run());
    }

    public void setRetirarFitxaListener(Runnable retirarFitxaAction) {
        botoRetirarFitxa.addActionListener(e -> retirarFitxaAction.run());
    }

    /**
     * Estableix l'ActionListener pel botó de guardar partida.
     *
     * @param guardarPartidaAction Runnable a establir
     */
    public void setGuardarPartidaListener(Runnable guardarPartidaAction) {
        botoGuardarPartida.addActionListener(e -> guardarPartidaAction.run());
    }

    public ArrayList<Fitxa>  getFitxesCanviades(){
        return jugadorVista.getFitxesCanviades();
    }

    /**
     * Estableix l'ActionListener pel botó de validar jugada.
     *
     * @param listener ActionListener a establir
     */
    public void setValidarJugadaListener(ActionListener listener) {
        botoValidarJugada.addActionListener(listener);
    }

    public boolean getModeCanviFitxes() {
        return jugadorVista.getModeCanviFitxes();
    }

    public void setCanviarFitxesListener(Runnable listener) {
        botoCanviarFitxes.addActionListener(e -> listener.run());
    }

    public void setModeCanviFitxes(boolean mode) {
        jugadorVista.setModeCanviFitxes(mode);
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
        if (jugadors.size() != panelsJugadors.size() || jugadors.size() != etiquetesPuntuacions.size()) {
            return; // Els arrays han de tenir la mateixa mida
        }

        Font fontPuntuacions = FontLoader.getCustomFont(14f);
        Font fontPuntuacionsNegreta = FontLoader.getCustomFont(15f).deriveFont(Font.BOLD);
        Color colorTextNormal = ColorLoader.getInstance().getColorText();
        Color colorTextActiu = new Color(255, 255, 255);
        Color colorIndicadorActiu = ColorLoader.getInstance().getColorAccent();
        Color colorFonsActiu = new Color(
                colorIndicadorActiu.getRed(),
                colorIndicadorActiu.getGreen(),
                colorIndicadorActiu.getBlue(),
                100); // Semi-transparente

        // Recorrem el panell de puntuacions i actualitzem cada component
        for (int i = 0; i < jugadors.size(); i++) {
            Jugador j = jugadors.get(i);
            JPanel jugadorPanel = panelsJugadors.get(i);
            JLabel etiquetaPuntuacio = etiquetesPuntuacions.get(i);

            // Actualitzem el valor de la puntuació
            etiquetaPuntuacio.setText(String.valueOf(j.obtenirPunts()));

            // Actualitzem l'indicador de torn actual
            JPanel indicadorPanel = (JPanel) jugadorPanel.getComponent(0);
            JPanel infoPanel = (JPanel) jugadorPanel.getComponent(1);
            JLabel nomLabel = (JLabel) infoPanel.getComponent(0);

            boolean esTornActual = j.equals(jugadorActual);

            // Actualitzem l'indicador
            indicadorPanel.setBackground(esTornActual ? colorIndicadorActiu : ColorLoader.getInstance().getColorFons());
            indicadorPanel.setBorder(BorderFactory.createLineBorder(
                    esTornActual ? colorIndicadorActiu : ColorLoader.getInstance().getColorFons(), 2));

            // Actualitzem el fons del panel
            jugadorPanel.setBackground(esTornActual ? colorFonsActiu : ColorLoader.getInstance().getColorFons());
            infoPanel.setBackground(esTornActual ? colorFonsActiu : ColorLoader.getInstance().getColorFons());

            // Actualitzem el text
            nomLabel.setFont(esTornActual ? fontPuntuacionsNegreta : fontPuntuacions);
            nomLabel.setForeground(esTornActual ? colorTextActiu : colorTextNormal);
            etiquetaPuntuacio.setFont(esTornActual ? fontPuntuacionsNegreta : fontPuntuacions);

            // Actualitzem el borde
            if (esTornActual) {
                jugadorPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorIndicadorActiu, 2),
                        BorderFactory.createEmptyBorder(6, 3, 6, 3)
                ));
            } else {
                jugadorPanel.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
            }
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