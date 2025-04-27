package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.presentacio.FontLoader;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Vista gràfica del Faristol, mostrant totes les fitxes disponibles del jugador.
 */
public class FaristolVista extends JPanel {

    private final Faristol faristol;
    private Fitxa fitxaSeleccionada = null;

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

        // Afegir botó de barrejar amb emoji
        JButton barrejarButton = new JButton("Barrejar"); // Emoji de barreja
        barrejarButton.setBackground(ColorLoader.getInstance().getColorSeleccionada()); // Aplicar el color desitjat
        barrejarButton.setForeground(Color.BLACK); // Color del text blanc
        barrejarButton.setFont(FontLoader.getCustomFont(16f)); // Font personalitzada per al botó
        barrejarButton.setPreferredSize(new Dimension(100, 40)); // Mida més petita per al botó
        barrejarButton.setFocusPainted(false); // Evitar que es mostri el focus en fer clic
        barrejarButton.setBorder(BorderFactory.createLineBorder(ColorLoader.getInstance().getColorSeleccionada(), 4)); // Borde personalitzat
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
        // Esborrem les vistes actuals de les fitxes
        JPanel fitxesPanel = (JPanel) ((JPanel) getComponent(0)).getComponent(1);  // Panell que conté les fitxes
        fitxesPanel.removeAll();

        for (Fitxa fitxa : faristol.obtenirFitxes()) { // Recórrer les fitxes
            FitxaVista fitxaVista = new FitxaVista(fitxa);

            fitxaVista.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Si ja n'hi ha una seleccionada, la deseleccionem
                    if (fitxaSeleccionada != null) {
                        // Desseleccionem la fitxa visual que estava seleccionada
                        for (Component component : fitxesPanel.getComponents()) {
                            if (component instanceof FitxaVista) {
                                FitxaVista fv = (FitxaVista) component;
                                if (fv.obtenirFitxa() == fitxaSeleccionada) {
                                    fv.setSeleccionada(false); // Deseleccionem la fitxa anterior
                                }
                            }
                        }
                    }

                    // Actualitzem la fitxa seleccionada
                    fitxaSeleccionada = fitxa;
                    fitxaVista.setSeleccionada(true); // Seleccionem la nova fitxa
                    System.out.println("Fitxa seleccionada del faristol: " + fitxa.obtenirLletra());
                }
            });

            fitxesPanel.add(fitxaVista); // Afegim la vista al panell de fitxes
        }

        fitxesPanel.revalidate();
        fitxesPanel.repaint();
    }

    /**
     * Funció per barrejar les fitxes del faristol.
     */
    public void barrejarFitxes() {
        faristol.barrejarFitxes(); // Cridem el mètode de Faristol per barrejar les fitxes
        actualitzarVista(); // Actualitzem la vista per mostrar les fitxes en el nou ordre
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
