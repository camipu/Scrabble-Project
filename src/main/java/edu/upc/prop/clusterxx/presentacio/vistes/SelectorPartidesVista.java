package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * Vista per seleccionar partides guardades amb una interfície elegant i intuïtiva.
 * Mostra una llista de partides disponibles amb efectes visuals i interacció fluida.
 */
public class SelectorPartidesVista extends JPanel {

    private final List<String> nomsPartides;
    private Consumer<Integer> seleccioListener;
    private JPanel panellPartides;
    private int partidaSeleccionada = -1;
    private JButton botoCarregar;
    private JButton botoCancelar;

    /**
     * Crea una nova vista de selecció de partides.
     *
     * @param nomsPartides Llista amb els noms de les partides guardades
     */
    public SelectorPartidesVista(List<String> nomsPartides) {
        this.nomsPartides = nomsPartides;

        inicialitzarVista();
        crearComponents();
        configurarLayout();
    }

    /**
     * Inicialitza la configuració bàsica de la vista.
     */
    private void inicialitzarVista() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(ColorLoader.getInstance().getColorFons());
    }

    /**
     * Crea tots els components de la vista.
     */
    private void crearComponents() {
        // Títol principal
        JLabel titol = crearTitol();

        // Panell de partides
        panellPartides = crearPanellPartides();

        // Panell de botons
        JPanel panellBotons = crearPanellBotons();

        // Afegir components
        add(titol, BorderLayout.NORTH);
        add(crearScrollPane(), BorderLayout.CENTER);
        add(panellBotons, BorderLayout.SOUTH);
    }

    /**
     * Crea el títol principal de la vista.
     */
    private JLabel crearTitol() {
        JLabel titol = new JLabel("Selecciona una Partida Guardada");
        titol.setFont(FontLoader.getCustomFont(28f).deriveFont(Font.BOLD));
        titol.setForeground(ColorLoader.getInstance().getColorAccent());
        titol.setHorizontalAlignment(SwingConstants.CENTER);
        titol.setBorder(new EmptyBorder(0, 0, 20, 0));
        return titol;
    }

    /**
     * Crea el panell principal que conté les partides.
     */
    private JPanel crearPanellPartides() {
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (nomsPartides.isEmpty()) {
            afegirMissatgeNoPartides(panell);
        } else {
            afegirPartidesAlPanell(panell);
        }

        return panell;
    }

    /**
     * Afegeix un missatge quan no hi ha partides guardades.
     */
    private void afegirMissatgeNoPartides(JPanel panell) {
        JPanel missatgePanel = new JPanel();
        missatgePanel.setLayout(new BoxLayout(missatgePanel, BoxLayout.Y_AXIS));
        missatgePanel.setBackground(ColorLoader.getInstance().getColorFons());
        missatgePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel("📂");
        iconLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel missatgeLabel = new JLabel("No hi ha partides guardades");
        missatgeLabel.setFont(FontLoader.getCustomFont(18f));
        missatgeLabel.setForeground(ColorLoader.getInstance().getColorText());
        missatgeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel submissatgeLabel = new JLabel("Comença una nova partida per poder guardar-la!");
        submissatgeLabel.setFont(FontLoader.getCustomFont(14f));
        submissatgeLabel.setForeground(ColorLoader.getInstance().getColorText().brighter());
        submissatgeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        missatgePanel.add(Box.createVerticalStrut(50));
        missatgePanel.add(iconLabel);
        missatgePanel.add(Box.createVerticalStrut(20));
        missatgePanel.add(missatgeLabel);
        missatgePanel.add(Box.createVerticalStrut(10));
        missatgePanel.add(submissatgeLabel);
        missatgePanel.add(Box.createVerticalStrut(50));

        panell.add(missatgePanel);
    }

    /**
     * Afegeix les partides al panell principal.
     */
    private void afegirPartidesAlPanell(JPanel panell) {
        for (int i = 0; i < nomsPartides.size(); i++) {
            JPanel partidaPanel = crearPanellPartida(nomsPartides.get(i), i);
            panell.add(partidaPanel);
            panell.add(Box.createVerticalStrut(10));
        }
    }

    /**
     * Crea un panell per a una partida individual.
     */
    private JPanel crearPanellPartida(String nomPartida, int index) {
        JPanel panell = new JPanel();
        panell.setLayout(new BorderLayout(15, 10));
        panell.setBackground(ColorLoader.getInstance().getColorFonsFitxa());
        panell.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent().darker(), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));
        panell.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panell.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icona de partida
        JLabel iconaLabel = new JLabel("⚛︎");
        iconaLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        // Nom de la partida
        JLabel nomLabel = new JLabel(nomPartida);
        nomLabel.setFont(FontLoader.getCustomFont(18f).deriveFont(Font.BOLD));
        nomLabel.setForeground(ColorLoader.getInstance().getColorText());

        // Data/info addicional (placeholder)
        JLabel infoLabel = new JLabel("Partida guardada");
        infoLabel.setFont(FontLoader.getCustomFont(12f));
        infoLabel.setForeground(ColorLoader.getInstance().getColorText().brighter());

        // Panell de text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(ColorLoader.getInstance().getColorFonsFitxa());
        textPanel.add(nomLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(infoLabel);

        // Indicador de selecció
        JLabel indicadorLabel = new JLabel("▶");
        indicadorLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        indicadorLabel.setForeground(ColorLoader.getInstance().getColorAccent());
        indicadorLabel.setVisible(false);

        panell.add(iconaLabel, BorderLayout.WEST);
        panell.add(textPanel, BorderLayout.CENTER);
        panell.add(indicadorLabel, BorderLayout.EAST);

        // Efectes d'interacció
        afegirEfectesInteraccio(panell, indicadorLabel, index);

        return panell;
    }

    /**
     * Afegeix els efectes d'interacció (hover, click) a un panell de partida.
     */
    private void afegirEfectesInteraccio(JPanel panell, JLabel indicador, int index) {
        Color colorOriginal = panell.getBackground();
        Color colorHover = ColorLoader.getInstance().getColorAccent().brighter();
        Color colorSeleccionat = ColorLoader.getInstance().getColorAccent();

        panell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (partidaSeleccionada != index) {
                    panell.setBackground(colorHover);
                    panell.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 2),
                            new EmptyBorder(14, 19, 14, 19)
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (partidaSeleccionada != index) {
                    panell.setBackground(colorOriginal);
                    panell.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent().darker(), 1),
                            new EmptyBorder(15, 20, 15, 20)
                    ));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarPartida(index);
            }
        });
    }

    /**
     * Selecciona una partida i actualitza la interfície.
     */
    private void seleccionarPartida(int index) {
        // Desseleccionar la partida anterior
        if (partidaSeleccionada >= 0 && partidaSeleccionada < panellPartides.getComponentCount()) {
            actualitzarEstatPartida(partidaSeleccionada, false);
        }

        // Seleccionar la nova partida
        partidaSeleccionada = index;
        actualitzarEstatPartida(index, true);

        // Habilitar botó de càrrega
        botoCarregar.setEnabled(true);

        // Cridar el listener si està definit
        if (seleccioListener != null) {
            seleccioListener.accept(index);
        }
    }

    /**
     * Actualitza l'estat visual d'una partida (seleccionada/no seleccionada).
     */
    private void actualitzarEstatPartida(int index, boolean seleccionada) {
        if (index < 0 || index * 2 >= panellPartides.getComponentCount()) return;

        JPanel partidaPanel = (JPanel) panellPartides.getComponent(index * 2);
        JLabel indicador = (JLabel) partidaPanel.getComponent(2);

        Color colorFons = seleccionada ?
                ColorLoader.getInstance().getColorAccent() :
                ColorLoader.getInstance().getColorFonsFitxa();

        Color colorBorde = seleccionada ?
                ColorLoader.getInstance().getColorAccent().darker() :
                ColorLoader.getInstance().getColorAccent().darker();

        int gruixBorde = seleccionada ? 3 : 1;

        partidaPanel.setBackground(colorFons);
        partidaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, gruixBorde),
                new EmptyBorder(15 - gruixBorde + 1, 20 - gruixBorde + 1, 15 - gruixBorde + 1, 20 - gruixBorde + 1)
        ));

        indicador.setVisible(seleccionada);

        partidaPanel.repaint();
    }

    /**
     * Crea el scroll pane per al panell de partides.
     */
    private JScrollPane crearScrollPane() {
        JScrollPane scrollPane = new JScrollPane(panellPartides);
        scrollPane.setBorder(crearTitledBorder("Partides Disponibles"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(ColorLoader.getInstance().getColorFons());
        scrollPane.setPreferredSize(new Dimension(0, 400));
        return scrollPane;
    }

    /**
     * Crea el panell amb els botons d'acció.
     */
    private JPanel crearPanellBotons() {
        JPanel panell = new JPanel();
        panell.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panell.setBackground(ColorLoader.getInstance().getColorFons());
        panell.setBorder(new EmptyBorder(20, 0, 0, 0));

        Font fontBotons = FontLoader.getCustomFont(16f).deriveFont(Font.BOLD);

        // Botó Carregar
        botoCarregar = new JButton("Carregar Partida");
        botoCarregar.setFont(fontBotons);
        botoCarregar.setBackground(new Color(46, 125, 50));
        botoCarregar.setForeground(Color.WHITE);
        botoCarregar.setFocusPainted(false);
        botoCarregar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(27, 94, 32), 2),
                new EmptyBorder(12, 25, 12, 25)
        ));
        botoCarregar.setEnabled(false);
        botoCarregar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botó Cancel·lar
        botoCancelar = new JButton("Cancel·lar");
        botoCancelar.setFont(fontBotons);
        botoCancelar.setBackground(new Color(183, 28, 28));
        botoCancelar.setForeground(Color.WHITE);
        botoCancelar.setFocusPainted(false);
        botoCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(136, 14, 14), 2),
                new EmptyBorder(12, 25, 12, 25)
        ));
        botoCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectes hover
        afegirEfecteHover(botoCarregar, new Color(46, 125, 50), new Color(67, 160, 71));
        afegirEfecteHover(botoCancelar, new Color(183, 28, 28), new Color(229, 57, 53));

        panell.add(botoCarregar);
        panell.add(botoCancelar);

        return panell;
    }

    /**
     * Afegeix efecte hover a un botó.
     */
    private void afegirEfecteHover(JButton boto, Color colorOriginal, Color colorHover) {
        boto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boto.isEnabled()) {
                    boto.setBackground(colorHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boto.setBackground(colorOriginal);
            }
        });
    }

    /**
     * Crea un borde amb títol per als panells.
     */
    private TitledBorder crearTitledBorder(String titol) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ColorLoader.getInstance().getColorAccent(), 2),
                titol
        );
        border.setTitleFont(FontLoader.getCustomFont(16f).deriveFont(Font.BOLD));
        border.setTitleColor(ColorLoader.getInstance().getColorAccent());
        return border;
    }

    /**
     * Configura el layout final de la vista.
     */
    private void configurarLayout() {
        setPreferredSize(new Dimension(600, 500));
    }

    // --- MÈTODES PÚBLICS PER AL CONTROLADOR ---

    /**
     * Estableix el listener que serà cridat quan es seleccioni una partida.
     *
     * @param listener Consumer que rebrà l'índex de la partida seleccionada
     */
    public void setSeleccioListener(Consumer<Integer> listener) {
        this.seleccioListener = listener;
    }

    /**
     * Estableix el listener pel botó de carregar partida.
     *
     * @param listener Runnable que s'executarà quan es premi carregar
     */
    public void setCarregarListener(Runnable listener) {
        botoCarregar.addActionListener(e -> {
            if (partidaSeleccionada >= 0) {
                listener.run();
            }
        });
    }

    /**
     * Estableix el listener pel botó de cancel·lar.
     *
     * @param listener Runnable que s'executarà quan es premi cancel·lar
     */
    public void setCancelarListener(Runnable listener) {
        botoCancelar.addActionListener(e -> listener.run());
    }

    /**
     * Obté l'índex de la partida seleccionada actualment.
     *
     * @return Índex de la partida seleccionada, o -1 si no n'hi ha cap
     */
    public int getPartidaSeleccionada() {
        return partidaSeleccionada;
    }

    /**
     * Obté el nom de la partida seleccionada actualment.
     *
     * @return Nom de la partida seleccionada, o null si no n'hi ha cap
     */
    public String getNomPartidaSeleccionada() {
        if (partidaSeleccionada >= 0 && partidaSeleccionada < nomsPartides.size()) {
            return nomsPartides.get(partidaSeleccionada);
        }
        return null;
    }
}