package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio;
import edu.upc.prop.clusterxx.presentacio.ColorLoader;
import edu.upc.prop.clusterxx.presentacio.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Component visual que representa una casella del tauler de joc.
 * Mostra visualment el tipus de casella (normal, multiplicador de lletra, multiplicador de paraula)
 * i la fitxa que conté si no està buida.
 */
public class CasellaVista extends JPanel {
    private final Casella casella;
    private boolean seleccionada;

    // Colors per als diferents tipus de caselles
    private final Color colorNormal = ColorLoader.getInstance().getColorNormal();
    private final Color colorML2 = ColorLoader.getInstance().getColorML2(); // Blau clar per multiplicador lletra x2
    private final Color colorML3 = ColorLoader.getInstance().getColorML3();
    private final Color colorMP2 = ColorLoader.getInstance().getColorMP2();
    private final Color colorMP3 = ColorLoader.getInstance().getColorMP3();
    private final Color colorSeleccionada = ColorLoader.getInstance().getColorSeleccionada();

    private final Font fontMultiplicador = FontLoader.getCustomFont(20f);

    /**
     * Constructor de la vista d'una casella.
     *
     * @param casella La casella del model a visualitzar.
     */
    public CasellaVista(Casella casella) {
        this.casella = casella;
        this.seleccionada = false;

        inicialitzaComponent();
    }

    /**
     * Inicialitza les propietats visuals i comportaments del component.
     */
    private void inicialitzaComponent() {
        setPreferredSize(new Dimension(40, 40));
        setMinimumSize(new Dimension(40, 40));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actualitzaColor();

        // Afegir listeners per interacció
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionada = !seleccionada;
                actualitzaColor();
                repaint();
            }
        });
    }

    /**
     * Actualitza el color de fons segons el tipus d'estratègia de la casella i si està seleccionada.
     */
    private void actualitzaColor() {
        if (seleccionada) {
            setBackground(colorSeleccionada);
            return;
        }

        EstrategiaPuntuacio estrategia = casella.obtenirEstrategia();
        int multiplicador = casella.obtenirMultiplicador();

        if (estrategia instanceof EstrategiaPuntuacio.EstrategiaNormal) {
            setBackground(colorNormal);
        } else if (estrategia instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra) {
            setBackground(multiplicador == 2 ? colorML2 : colorML3);
        } else if (estrategia instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula) {
            setBackground(multiplicador == 2 ? colorMP2 : colorMP3);
        }
    }

    /**
     * Dibuixa el component, mostrant el multiplicador o la fitxa si la casella en conté.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Si la casella està buida, mostrar el multiplicador (si en té)
        if (casella.esBuida()) {
            EstrategiaPuntuacio estrategia = casella.obtenirEstrategia();
            int multiplicador = casella.obtenirMultiplicador();

            if (multiplicador > 1) {
                g2d.setFont(fontMultiplicador);
                g2d.setColor(Color.BLACK);

                String text = "";
                if (estrategia instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra) {
                    text = "L" + multiplicador;
                } else if (estrategia instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula) {
                    text = "P" + multiplicador;
                }

                FontMetrics fm = g2d.getFontMetrics(fontMultiplicador);
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                g2d.drawString(text, (getWidth() - textWidth) / 2,
                        getHeight() / 2 + fm.getAscent() / 2 - 2);
            }
        } else {
            // Si la casella conté una fitxa, mostrar-la
            String lletra = casella.obtenirFitxa().obtenirLletra();
            int punts = casella.obtenirFitxa().obtenirPunts();

            // Dibuixar fons de la fitxa
            g2d.setColor(new Color(240, 220, 180)); // Color fusta clar
            g2d.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 6, 6);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 6, 6);

            // Dibuixar lletra
            Font fontLletra = new Font("Arial", Font.BOLD, 16);
            g2d.setFont(fontLletra);
            FontMetrics fmLletra = g2d.getFontMetrics(fontLletra);
            int ampleLletra = fmLletra.stringWidth(lletra);

            g2d.drawString(lletra, (getWidth() - ampleLletra) / 2,
                    getHeight() / 2 + fmLletra.getAscent() / 2 - 2);

            // Dibuixar puntuació
            if (punts > 0) {
                Font fontPunts = new Font("Arial", Font.PLAIN, 9);
                g2d.setFont(fontPunts);
                String puntText = String.valueOf(punts);
                g2d.drawString(puntText, getWidth() - 12, getHeight() - 5);
            }
        }
    }

    /**
     * Obté la casella associada a aquesta vista.
     *
     * @return La casella del model.
     */
    public Casella getCasella() {
        return casella;
    }

    /**
     * Comprova si la casella està seleccionada.
     *
     * @return true si la casella està seleccionada, false altrament.
     */
    public boolean estaSeleccionada() {
        return seleccionada;
    }

    /**
     * Estableix si la casella està seleccionada.
     *
     * @param seleccionada true per seleccionar la casella, false per deseleccionar-la.
     */
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        actualitzaColor();
        repaint();
    }
}