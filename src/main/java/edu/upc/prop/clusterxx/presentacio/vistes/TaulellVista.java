package edu.upc.prop.clusterxx.presentacio.vistes;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Taulell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Component visual que representa el taulell de joc de Scrabble.
 * Versió simplificada que només permet seleccionar una casella.
 */
public class TaulellVista extends JPanel {
    private final Taulell taulell;
    private final CasellaVista[][] casellesVista;
    private CasellaVista casellaSeleccionada = null;
    Pair<Integer, Integer> posSeleccionada = Pair.of(-1, -1);

    // Interfície per notificar selecció de caselles
    public interface SeleccioListener {
        void casellaSeleccionada(Casella casella);
    }

    private SeleccioListener seleccioListener;

    /**
     * Constructor de la vista del taulell.
     *
     * @param taulell El taulell del model a visualitzar.
     */
    public TaulellVista(Taulell taulell) {
        this.taulell = taulell;
        int mida = taulell.getSize();
        this.casellesVista = new CasellaVista[mida][mida];

        inicialitzaComponent();
    }

    /**
     * Inicialitza les propietats visuals i comportaments del component.
     */
    private void inicialitzaComponent() {
        int mida = taulell.getSize();
        setLayout(new GridLayout(mida, mida, 1, 1));
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setBackground(Color.DARK_GRAY);

        // Crear vistes per a cada casella del taulell
        for (int i = 0; i < mida; i++) {
            for (int j = 0; j < mida; j++) {
                Casella casella = taulell.getCasella(i, j);
                CasellaVista casellaVista = new CasellaVista(casella);

                // Afegir listener per a capturar clics a la casella
                final int fila = i;
                final int columna = j;
                casellaVista.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        seleccionarCasella(fila, columna);
                    }
                });

                casellesVista[i][j] = casellaVista;
                add(casellaVista);
            }
        }

        // Destacar casella central
        int mig = mida / 2;
        casellesVista[mig][mig].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
    }

    /**
     * Selecciona una casella específica per coordenades.
     *
     * @param fila La fila de la casella
     * @param columna La columna de la casella
     */
    public void seleccionarCasella(int fila, int columna) {
        // Netejar selecció anterior
        if (!Objects.equals(posSeleccionada, Pair.of(-1, -1))) {
            casellaSeleccionada.setSeleccionada(false);
        }

        // Seleccionar nova casella
        casellaSeleccionada = casellesVista[fila][columna];
        posSeleccionada = Pair.of(fila, columna);
        casellaSeleccionada.setSeleccionada(true);

        System.out.println("Casella seleccionada: " + fila + ", " + columna);

        // Notificar al listener
        if (seleccioListener != null) {
            seleccioListener.casellaSeleccionada(casellaSeleccionada.getCasella());
        }
    }

    /**
     * Estableix el listener per a la selecció de caselles.
     *
     * @param listener El listener a establir
     */
    public void setSeleccioListener(SeleccioListener listener) {
        this.seleccioListener = listener;
    }

    /**
     * Actualitza la visualització de totes les caselles del taulell.
     */
    public void actualitzarTaulell() {
        int mida = taulell.getSize();
        for (int i = 0; i < mida; i++) {
            for (int j = 0; j < mida; j++) {
                casellesVista[i][j].repaint();
            }
        }
    }

    /**
     * Retorna la casella actualment seleccionada.
     *
     * @return La casella seleccionada, o null si no hi ha cap.
     */
    public Casella obtenirCasellaSeleccionada() {
        return casellaSeleccionada != null ? casellaSeleccionada.getCasella() : null;
    }

    /**
     * Retorna la posició de la casella seleccionada.
     *
     * @return La posició de la casella seleccionada com a parell (fila, columna).
     */
    public Pair<Integer, Integer> obtenirPosSeleccionada() {
        return posSeleccionada;
    }
}