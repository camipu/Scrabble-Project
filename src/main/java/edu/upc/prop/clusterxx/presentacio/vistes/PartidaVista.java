package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista principal de la partida, que integra el taulell, la informació del jugador
 * i els botons de control del joc.
 */
public class PartidaVista extends JPanel {

    private final Taulell taulell;
    private final Jugador jugador;
    private final TaulellVista taulellVista;
    private final JugadorVista jugadorVista;
    private JButton botoPassar;
    private JButton botoCanviarFitxes;
    private JButton botoValidarJugada;
    private JButton botoColocar;
    private Casella casellaSeleccionada = null;
    private Character lletraSeleccionada = null;

    /**
     * Crea una nova vista de partida amb el taulell i jugador especificats.
     *
     * @param taulell Taulell de la partida
     * @param jugador Jugador actual
     */
    public PartidaVista(Taulell taulell, Jugador jugador) {
        this.taulell = taulell;
        this.jugador = jugador;

        // Establim el layout general de la vista
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ColorLoader.getInstance().getColorFons()); // Utilitzem el color de fons de la paleta

        // Creem la vista del taulell i la posicionem al centre
        taulellVista = new TaulellVista(taulell);
        taulellVista.setSeleccioListener(this::handleCasellaSeleccionada);

        // Creem la vista del jugador i la posicionem a la part inferior
        jugadorVista = new JugadorVista(jugador);

        // Creem el panell de botons i el posicionem a la dreta
        JPanel panellBotons = crearPanellBotons();

        // Afegim tots els components a la vista principal
        add(taulellVista, BorderLayout.CENTER);
        add(jugadorVista, BorderLayout.SOUTH);
        add(panellBotons, BorderLayout.EAST);

        // Establim el torn actiu pel jugador inicial
        jugadorVista.setTornActiu(true);
    }

    /**
     * Crea el panell amb els botons de control del joc.
     *
     * @return Panell amb els botons de control
     */
    // Update the crearPanellBotons method
    private JPanel crearPanellBotons() {
        JPanel panell = new JPanel();
        panell.setLayout(new GridLayout(6, 1, 0, 10)); // Adjusted for 4 buttons
        panell.setBorder(new EmptyBorder(0, 10, 0, 0));
        panell.setBackground(ColorLoader.getInstance().getColorFons());

        Font fontBotons = FontLoader.getCustomFont(16f);
        Color colorFons = ColorLoader.getInstance().getColorFonsFitxa();
        Color colorText = ColorLoader.getInstance().getColorText();

        botoPassar = crearBoto("Passar torn", fontBotons, colorFons, colorText);
        botoCanviarFitxes = crearBoto("Canviar fitxes", fontBotons, colorFons, colorText);
        botoValidarJugada = crearBoto("Validar jugada", fontBotons, colorFons, colorText);
        botoColocar = crearBoto("Col·locar", fontBotons, colorFons, colorText); // New button

        panell.add(Box.createVerticalStrut(50));
        panell.add(botoValidarJugada);
        panell.add(Box.createVerticalStrut(10));
        panell.add(botoCanviarFitxes);
        panell.add(Box.createVerticalStrut(10));
        panell.add(botoPassar);
        panell.add(Box.createVerticalStrut(10));
        panell.add(botoColocar); // Add the new button

        return panell;
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
                BorderFactory.createLineBorder(ColorLoader.getInstance().getColorText().darker(), 1),
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

    // Add this method to set the ActionListener for the "Col·locar" button
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
     * Actualitza tota la vista de la partida, incloent taulell i jugador.
     */
    public void actualitzarVista() {
        taulellVista.actualitzarTaulell();
        jugadorVista.actualitzarVista();
    }
}