package edu.upc.prop.clusterxx.presentacio;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Faristol;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.vistes.PartidaVista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Classe principal per demostrar i provar la vista completa de la partida.
 * Visualitza el taulell, el jugador amb el seu faristol i els botons de control.
 */
public class MostraPartida {

    /**
     * Mètode principal que inicialitza la interfície i mostra la finestra.
     * @param args Arguments de la línia de comandes (no s'utilitzen)
     */
    public static void main(String[] args) {
        // Assegurem que la interfície d'usuari s'executa en el thread d'events
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurem el look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Creem la finestra principal
            crearIVisualitzarGUI();
        });
    }

    /**
     * Crea i mostra la interfície gràfica amb la vista completa de la partida.
     */
    private static void crearIVisualitzarGUI() {
        // Creem un taulell d'exemple (15x15 pel Scrabble estàndard)
        Taulell taulell = crearTaulellExemple();

        // Creem un faristol amb fitxes d'exemple
        Faristol faristol = crearFaristolExemple();

        // Creem un jugador d'exemple
        Jugador jugador = new Jugador("Marc", faristol);
        jugador.afegirPunts(75); // Afegim alguns punts per demostració

        // Creem la vista de la partida
        PartidaVista partidaVista = new PartidaVista(taulell, jugador);

        // Configurem els listeners dels botons
        configurarListenersBotons(partidaVista, jugador, taulell);

        // Creem i configurem la finestra principal
        JFrame frame = new JFrame("Scrabble - Partida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(partidaVista, BorderLayout.CENTER);

        // Creem un panell d'estat a la part inferior
        JPanel panellEstat = crearPanellEstat();
        frame.add(panellEstat, BorderLayout.SOUTH);

        // Apliquem colors de la paleta al frame
        frame.getContentPane().setBackground(ColorLoader.getInstance().getColorFons());

        // Establim la mida i mostrem la finestra
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null); // Centrem la finestra
        frame.setVisible(true);
    }

    /**
     * Crea un taulell d'exemple per a la demostració.
     * @return Taulell precarregat
     */
    private static Taulell crearTaulellExemple() {
        Taulell taulell = new Taulell(15); // Taulell de 15x15 per Scrabble

        // Podríem afegir algunes fitxes al taulell per demostració
        // Per exemple, posar algunes lletres al centre
        // taulell.colocarFitxa(7, 7, new Fitxa("S", 1));

        return taulell;
    }

    /**
     * Crea un faristol amb fitxes d'exemple per a la demostració.
     * @return Faristol amb fitxes precarregades
     */
    private static Faristol crearFaristolExemple() {
        Faristol faristol = new Faristol(7);

        // Afegim algunes fitxes d'exemple
        faristol.afegirFitxa(new Fitxa("A", 1));
        faristol.afegirFitxa(new Fitxa("E", 1));
        faristol.afegirFitxa(new Fitxa("I", 1));
        faristol.afegirFitxa(new Fitxa("O", 1));
        faristol.afegirFitxa(new Fitxa("S", 1));
        faristol.afegirFitxa(new Fitxa("N", 1));
        faristol.afegirFitxa(new Fitxa("T", 1));

        return faristol;
    }

    /**
     * Configura els listeners per als botons de la vista de partida.
     * @param partidaVista Vista de la partida
     * @param jugador Jugador de la partida
     * @param taulell Taulell de la partida
     */
    private static void configurarListenersBotons(PartidaVista partidaVista, Jugador jugador, Taulell taulell) {
        // Listener pel botó de passar torn
        partidaVista.setPassarTornListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Has passat el torn. Espera que juguin els altres jugadors.",
                    "Torn passat",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Listener pel botó de canviar fitxes
        partidaVista.setCanviarFitxesListener(e -> {
            jugador.barrejarFaristol();
            partidaVista.actualitzarVista();
            JOptionPane.showMessageDialog(null,
                    "Has barrejat les teves fitxes.",
                    "Fitxes canviades",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Listener pel botó de validar jugada
        partidaVista.setValidarJugadaListener(e -> {
            // Simulem una validació exitosa
            jugador.afegirPunts(10);
            partidaVista.actualitzarVista();
            JOptionPane.showMessageDialog(null,
                    "Jugada validada! Has guanyat 10 punts.",
                    "Jugada correcta",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Crea un panell d'estat a la part inferior amb informació addicional.
     * @return Panell d'estat configurat
     */
    private static JPanel crearPanellEstat() {
        JPanel panell = new JPanel();
        panell.setLayout(new FlowLayout(FlowLayout.LEFT));
        panell.setBorder(new EmptyBorder(5, 10, 5, 10));
        panell.setBackground(ColorLoader.getInstance().getColorFons()); // Color de fons consistent

        JLabel estatLabel = new JLabel("Estat: És el teu torn. Selecciona una casella i col·loca una fitxa.");
        estatLabel.setFont(FontLoader.getCustomFont(14f));
        estatLabel.setForeground(ColorLoader.getInstance().getColorText()); // Color de text de la paleta

        panell.add(estatLabel);

        return panell;
    }
}