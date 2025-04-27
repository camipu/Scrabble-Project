package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Fitxa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FitxaVista extends JPanel {
    private final Fitxa fitxa;
    private boolean seleccionada;
    private final Color colorFons = new Color(240, 220, 180); // Color fusta clar
    private final Color colorSeleccionada = new Color(255, 255, 150); // Groc clar per selecció
    private final Color colorText = Color.BLACK;
    private final Font fontLletra = new Font("VT323", Font.PLAIN, 24);
    private final Font fontPunts = new Font("VT323", Font.PLAIN, 14);

    public FitxaVista(Fitxa fitxa) {
        this.fitxa = fitxa;
        this.seleccionada = false;
        setPreferredSize(new Dimension(50, 50));
        setMinimumSize(new Dimension(50, 50));
        setBackground(colorFons);
        setBorder(BorderFactory.createLineBorder(new Color(120, 100, 60), 2)); // Borde més definit
        setOpaque(true); // Assegura que el fons sigui visible

        // Afegir esdeveniment de selecció
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Canviar l'estat de selecció
                setSeleccionada(!seleccionada);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Habilitar antialiasing per millorar la qualitat del dibuix
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuixar l'efecte de selecció
        if (seleccionada) {
            g2d.setColor(new Color(255, 255, 200, 120)); // Sombra suau
            g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 10, 10);
        }

        // Dibuixar la lletra
        g2d.setColor(colorText);
        g2d.setFont(fontLletra);

        String lletra = fitxa.obtenirLletra();
        FontMetrics fmLletra = g2d.getFontMetrics();
        int ampleLletra = fmLletra.stringWidth(lletra);

        g2d.drawString(lletra, (getWidth() - ampleLletra) / 2, getHeight() / 2 + fmLletra.getAscent() / 2 - 4);

        // Dibuixar els punts
        g2d.setFont(fontPunts);
        String punts = String.valueOf(fitxa.obtenirPunts());
        if (fitxa.obtenirPunts() < 10) punts = " " + punts; // petit padding
        g2d.drawString(punts, getWidth() - 18, getHeight() - 6);
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        setBackground(seleccionada ? colorSeleccionada : colorFons);
        repaint();
    }
}
