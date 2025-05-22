package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vista gràfica d'un Jugador, mostrant la seva informació (nom, punts) i el seu faristol.
 */
public class JugadorVista extends JPanel {

    private final Jugador jugador;
    private final FaristolVista faristolVista;
    private final JLabel nomLabel;
    private final JLabel puntsLabel;

    /**
     * Crea una nova vista de jugador amb la informació del jugador especificat.
     *
     * @param jugador Jugador que es vol visualitzar
     */
    public JugadorVista(Jugador jugador) {
        this.jugador = jugador;

        setLayout(new BorderLayout(10, 10));
        TitledBorder border = BorderFactory.createTitledBorder("Informació del jugador");
        Font fontLletra = FontLoader.getCustomFont(20f);
        border.setTitleFont(fontLletra);
        border.setTitleColor(ColorLoader.getInstance().getColorText());
        setBorder(border);
        setBackground(ColorLoader.getInstance().getColorFons());

        // Panell principal per a la informació del jugador
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(10, 10));
        infoPanel.setBackground(ColorLoader.getInstance().getColorFons());

        // Panell per a nom i puntuació
        JPanel jugadorInfoPanel = new JPanel();
        jugadorInfoPanel.setLayout(new GridLayout(2, 1, 5, 5));
        jugadorInfoPanel.setBackground(ColorLoader.getInstance().getColorFons());

        // Nom del jugador
        nomLabel = new JLabel("Jugador: " + jugador.obtenirNom());
        nomLabel.setFont(FontLoader.getCustomFont(18f));
        nomLabel.setForeground(ColorLoader.getInstance().getColorText());

        // Puntuació del jugador
        puntsLabel = new JLabel("Puntuació: " + jugador.obtenirPunts());
        puntsLabel.setFont(FontLoader.getCustomFont(18f));
        puntsLabel.setForeground(ColorLoader.getInstance().getColorText());

        jugadorInfoPanel.add(nomLabel);
        jugadorInfoPanel.add(puntsLabel);

        // Afegim el panell d'informació a la part superior
        infoPanel.add(jugadorInfoPanel, BorderLayout.NORTH);

        // Creem i afegim la vista del faristol
        faristolVista = new FaristolVista(jugador.obtenirFaristol());

        // Afegim els components al panell principal
        add(infoPanel, BorderLayout.NORTH);
        add(faristolVista, BorderLayout.CENTER);
    }

    /**
     * Actualitza tota la informació visual del jugador, incloent nom, punts i faristol.
     */
    public void actualitzarVista() {
        // Actualitzem les etiquetes d'informació
        nomLabel.setText("Jugador: " + jugador.obtenirNom());
        puntsLabel.setText("Puntuació: " + jugador.obtenirPunts());

        // Actualitzem la vista del faristol
        faristolVista.actualitzarVista();

        // Forcem el repintat de la vista
        revalidate();
        repaint();
    }

    public ArrayList<Fitxa> getFitxesCanviades(){
        return faristolVista.getFitxesCanviades();
    }

    /**
     * Retorna la vista del faristol associada a aquest jugador.
     *
     * @return FaristolVista del jugador
     */
    public FaristolVista obtenirFaristolVista() {
        return faristolVista;
    }

    public Fitxa obtenirFitxaSeleccionada() {
        return faristolVista.obtenirFitxaSeleccionada();
    }

    public void desseleccionarFitxa() {
        faristolVista.desseleccionarFitxa();
    }

    public boolean getModeCanviFitxes() {
        return faristolVista.getModeCanviFitxes();
    }

    public void setModeCanviFitxes(boolean mode) {
        faristolVista.setModeCanviFitxes(mode);
    }


    /**
     * Adapta l'aparença de la vista per destacar quan és el torn del jugador.
     *
     * @param esTorn boolean que indica si és el torn d'aquest jugador
     */
    public void setTornActiu(boolean esTorn) {
        if (esTorn) {
            // Ressaltem la vora del panell amb un color distintiu quan és el torn
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColorLoader.getInstance().getColorSeleccionada(), 3),
                    BorderFactory.createTitledBorder(
                            BorderFactory.createEmptyBorder(),
                            "Informació del jugador - És el teu torn",
                            TitledBorder.DEFAULT_JUSTIFICATION,
                            TitledBorder.DEFAULT_POSITION,
                            FontLoader.getCustomFont(20f),
                            ColorLoader.getInstance().getColorText()
                    )
            ));
        } else {
            // Tornem a l'estat normal quan no és el torn
            TitledBorder border = BorderFactory.createTitledBorder("Informació del jugador");
            border.setTitleFont(FontLoader.getCustomFont(20f));
            border.setTitleColor(ColorLoader.getInstance().getColorText());
            setBorder(border);
        }

        revalidate();
        repaint();
    }
}