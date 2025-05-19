package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Vista gràfica del Faristol, mostrant totes les fitxes disponibles del jugador.
 */
public class FaristolVista extends JPanel {

    private final Faristol faristol;
    private Fitxa fitxaSeleccionada = null;
    private boolean modeCanviFitxes = false;
    private final ArrayList<Fitxa> fitxesSeleccionades = new ArrayList<>();

    public FaristolVista(Faristol faristol) {
        this.faristol = faristol;
        setLayout(new BorderLayout(10, 10));
        TitledBorder border = BorderFactory.createTitledBorder("El teu faristol");
        Font fontLletra = FontLoader.getCustomFont(20f);
        border.setTitleFont(fontLletra);
        setBorder(border);

        // Panell per a botons i fitxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Afegir botó de barrejar
        JButton barrejarButton = new JButton("Barrejar");
        barrejarButton.setBackground(ColorLoader.getInstance().getColorSeleccionada());
        barrejarButton.setForeground(Color.BLACK);
        barrejarButton.setFont(FontLoader.getCustomFont(16f));
        barrejarButton.setPreferredSize(new Dimension(100, 40));
        barrejarButton.setFocusPainted(false);
        barrejarButton.setBorder(BorderFactory.createLineBorder(ColorLoader.getInstance().getColorSeleccionada(), 4));
        barrejarButton.addActionListener(e -> barrejarFitxes());

        // Panell per als botons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(barrejarButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Panell per mostrar les fitxes
        JPanel fitxesPanel = new JPanel();
        fitxesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainPanel.add(fitxesPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);

        actualitzarVista();
    }

    /**
     * Actualitza el contingut gràfic del faristol.
     * Esborra i recrea totes les fitxes visuals.
     */
    public void actualitzarVista() {
        JPanel fitxesPanel = (JPanel) ((JPanel) getComponent(0)).getComponent(1);
        fitxesPanel.removeAll();

        for (Fitxa fitxa : faristol.obtenirFitxes()) {
            FitxaVista fitxaVista = new FitxaVista(fitxa);

            fitxaVista.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!modeCanviFitxes) {
                        // Mode normal: només una fitxa seleccionada
                        if (fitxaSeleccionada != null) {
                            for (Component component : fitxesPanel.getComponents()) {
                                if (component instanceof FitxaVista) {
                                    FitxaVista fv = (FitxaVista) component;
                                    if (fv.obtenirFitxa() == fitxaSeleccionada) {
                                        fv.setSeleccionada(false);
                                    }
                                }
                            }
                        }

                        fitxaSeleccionada = fitxa;
                        fitxaVista.setSeleccionada(true);
                        System.out.println("Fitxa seleccionada del faristol: " + fitxa.obtenirLletra());
                    } else {
                        // Mode canvi de fitxes: selecció múltiple
                        if (fitxesSeleccionades.contains(fitxa)) {
                            fitxesSeleccionades.remove(fitxa);
                            fitxaVista.setSeleccionada(false);
                            System.out.println("Fitxa desseleccionada per canvi: " + fitxa.obtenirLletra());
                        } else {
                            fitxesSeleccionades.add(fitxa);
                            fitxaVista.setSeleccionada(true);
                            System.out.println("Fitxa seleccionada per canvi: " + fitxa.obtenirLletra());
                        }
                    }
                }
            });

            fitxesPanel.add(fitxaVista);
        }

        fitxesPanel.revalidate();
        fitxesPanel.repaint();
    }

    /**
     * Funció per barrejar les fitxes del faristol.
     */
    public void barrejarFitxes() {
        faristol.barrejarFitxes();
        actualitzarVista();
    }

    /**
     * Retorna la fitxa seleccionada pel jugador en mode normal.
     * Després d'agafar-la, la deselecciona automàticament.
     */
    public Fitxa obtenirFitxaSeleccionada() {
        Fitxa seleccionada = fitxaSeleccionada;
        fitxaSeleccionada = null;
        return seleccionada;
    }

    /**
     * Retorna la llista de fitxes seleccionades en mode canvi.
     */
    public ArrayList<Fitxa> getFitxesCanviades() {
        return new ArrayList<>(fitxesSeleccionades);
    }

    /**
     * Deselecciona manualment la fitxa seleccionada en mode normal.
     */
    public void desseleccionarFitxa() {
        fitxaSeleccionada = null;
    }


    /**
     * Deselecciona totes les fitxes seleccionades en mode canvi.
     */
    public void desseleccionarFitxesCanvi() {
        fitxesSeleccionades.clear();
    }

    public boolean getModeCanviFitxes() {
        return modeCanviFitxes;
    }

    /**
     * Habilita o deshabilita el mode de canvi de fitxes.
     * @param mode true per activar el mode canvi, false per desactivar-lo
     */
    public void setModeCanviFitxes(boolean mode) {
        this.modeCanviFitxes = mode;
        fitxaSeleccionada = null;
        fitxesSeleccionades.clear();
        actualitzarVista();
    }
}
