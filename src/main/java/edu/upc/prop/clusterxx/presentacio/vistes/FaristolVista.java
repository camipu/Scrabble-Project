package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Vista gràfica del Faristol, mostrant totes les fitxes disponibles del jugador.
 */
public class FaristolVista extends JPanel {

    private final Faristol faristol;
    private Fitxa fitxaSeleccionada = null;

    public FaristolVista(Faristol faristol) {
        this.faristol = faristol;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        TitledBorder border = BorderFactory.createTitledBorder("El teu faristol");
        Font fontLletra = FontLoader.getCustomFont(20f);
        border.setTitleFont(fontLletra);
        setBorder(border);


        actualitzarVista();
    }

    /**
     * Actualitza el contingut gràfic del faristol.
     * Esborra i recrea totes les fitxes visuals.
     */
    public void actualitzarVista() {
        removeAll();
        for (Fitxa fitxa : faristol.obtenirFitxes()) {
            FitxaVista fitxaVista = new FitxaVista(fitxa);
            fitxaVista.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fitxaSeleccionada = fitxa;
                    System.out.println("Fitxa seleccionada del faristol: " + fitxa.obtenirLletra());
                }
            });
            add(fitxaVista);
        }
        revalidate();
        repaint();
    }

    /**
     * Retorna la fitxa seleccionada pel jugador.
     * Després d'agafar-la, la deselecciona automàticament.
     *
     * @return Fitxa seleccionada o null si no n'hi ha cap
     */
    public Fitxa obtenirFitxaSeleccionada() {
        Fitxa seleccionada = fitxaSeleccionada;
        fitxaSeleccionada = null;
        return seleccionada;
    }

    /**
     * Deselecciona manualment la fitxa seleccionada, si cal.
     */
    public void desseleccionarFitxa() {
        fitxaSeleccionada = null;
    }
}
