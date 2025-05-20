package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;
import edu.upc.prop.clusterxx.presentacio.vistes.GuanyadorVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaIniciVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaPersonalitzacioVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PartidaVista;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CtrlPresentacio {
    private static CtrlPresentacio instance = null;
    private CtrlDomini ctrlDomini;
    private PantallaIniciVista pantallaInici;
    private PantallaPersonalitzacioVista pantallaPersonalitzacioVista;
    private PartidaVista partidaVista;
    private JFrame framePartida;
    private boolean paraulaValida = false;

    public static CtrlPresentacio getInstance() {
        if (instance == null) {
            instance = new CtrlPresentacio();
        }
        return instance;
    }

    private CtrlPresentacio() {}

    public void inicialitzarApp() {
        ctrlDomini = CtrlDomini.getInstance();
        mostrarPantallaInici();
    }

    public void configurarPartida() {
        pantallaPersonalitzacioVista = new PantallaPersonalitzacioVista(this);
        pantallaInici.setVisible(false);
        pantallaPersonalitzacioVista.setVisible(true);
    }

    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors, int[] dificultatsBots) {
        ctrlDomini.inicialitzarPartida(midaTaulell, midaFaristol, idioma.toLowerCase(Locale.ROOT), nomsJugadors, dificultatsBots);
        pantallaPersonalitzacioVista.setVisible(false);
        crearFramePartida();
        actualitzarVistes();
    }

    private void crearFramePartida() {
        framePartida = new JFrame("Partida");
        framePartida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePartida.setLayout(new BorderLayout());
        framePartida.setLocationRelativeTo(null);
    }

    public void passarTorn() {
        ctrlDomini.passarTorn();
        actualitzarVistes();
    }

    public void retirarFitxa() {
        Casella casella = partidaVista.getCasellaSeleccionada();
        ctrlDomini.retirarFitxa(casella.obtenirX(), casella.obtenirY());
        actualitzarVistes();
    }

    public void colocarFitxa() {
        Casella casella = partidaVista.getCasellaSeleccionada();
        Fitxa fitxa = partidaVista.obtenirFitxaSeleccionada();
        Jugada jugada = null;
        String lletraFitxa = (fitxa != null) ? fitxa.obtenirLletra() : "";

        if (fitxa != null && fitxa.esComodi()) {
            lletraFitxa = gestionarComodi(fitxa);
            if (lletraFitxa == null) return; // Cancelado o inválido
        }

        if (casella != null && fitxa != null) {
            jugada = ctrlDomini.colocarFitxa(lletraFitxa, casella.obtenirX(), casella.obtenirY());
            actualitzarVistes();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecciona una casella i una fitxa abans de col·locar.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (jugada != null) paraulaValida = jugada.getJugadaValida();
    }

    private String gestionarComodi(Fitxa fitxa) {
        String input = JOptionPane.showInputDialog(null,
                "Has col·locat un comodí.\nEscriu la lletra que vols assignar-li:",
                "Assignar lletra al comodí",
                JOptionPane.PLAIN_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            char lletra = Character.toUpperCase(input.trim().charAt(0));
            String lletraStr = String.valueOf(lletra);

            if (ctrlDomini.setLletraComodi("#", lletraStr)) {
                return lletraStr;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Introdueix una lletra vàlida.",
                        "Lletra no vàlida",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Has de seleccionar una lletra per al comodí.",
                    "Assignació cancel·lada",
                    JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public void commitParaula() {
        if (paraulaValida) ctrlDomini.commitParaula();
        else {
            JOptionPane.showMessageDialog(null,
                    "La paraula no és vàlida. Torna a intentar-ho.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
        actualitzarVistes();
    }

    public void canviarFitxes() {
        boolean modeActual = partidaVista.getModeCanviFitxes();

        if (!modeActual) {
            partidaVista.setModeCanviFitxes(true);
            JOptionPane.showMessageDialog(null,
                    "Selecciona les fitxes del faristol que vols canviar i torna a prémer el botó.",
                    "Canvi de fitxes",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            ArrayList<Fitxa> fitxesSeleccionades = partidaVista.getFitxesCanviades();

            if (fitxesSeleccionades.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No s'han seleccionat fitxes per canviar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                partidaVista.setModeCanviFitxes(false);
                return;
            }

            String[] lletres = fitxesSeleccionades.stream()
                    .map(f -> String.valueOf(f.obtenirLletra()))
                    .toArray(String[]::new);

            ctrlDomini.canviarFitxes(lletres);
            partidaVista.setModeCanviFitxes(false);
            actualitzarVistes();

            JOptionPane.showMessageDialog(null,
                    "Fitxes canviades correctament.",
                    "Canvi completat",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualitzarVistes() {
        Jugador jugador = ctrlDomini.obtenirJugadorActual();
        System.out.println("Punts jugador actual: " + jugador.obtenirNom() + " -> " + jugador.obtenirPunts());

        if (jugador.esBot()) ctrlDomini.jugadaBot();

        Taulell taulell = ctrlDomini.obtenirTaulell();
        jugador = ctrlDomini.obtenirJugadorActual();

        if (framePartida.getContentPane().getComponentCount() > 0) {
            framePartida.getContentPane().removeAll();
        }

        partidaVista = new PartidaVista(taulell, jugador, Arrays.stream(ctrlDomini.obtenirJugadors()).toList());
        partidaVista.setPassarTornListener(this::passarTorn);
        partidaVista.setColocarListener(e -> colocarFitxa());
        partidaVista.setRetirarFitxaListener(this::retirarFitxa);
        partidaVista.setValidarJugadaListener(this::commitParaula);
        partidaVista.setCanviarFitxesListener(this::canviarFitxes);

        framePartida.add(partidaVista, BorderLayout.CENTER);
        framePartida.pack();
        framePartida.setVisible(true);
        framePartida.revalidate();
        framePartida.repaint();
    }

    public void mostrarFinalPartida(Taulell taulell, List<Jugador> jugadors) {
        GuanyadorVista guanyadorVista = new GuanyadorVista(taulell, jugadors);

        guanyadorVista.setTornarMenuListener(e -> mostrarPantallaInici());
        guanyadorVista.setNovaPartidaListener(e -> configurarPartida());

        JFrame frame = new JFrame("Scrabble - Fi de la Partida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(guanyadorVista);
        frame.setVisible(true);
    }

    private void mostrarPantallaInici() {
        pantallaInici = new PantallaIniciVista(this);
        pantallaInici.setVisible(true);
    }

    public boolean esJugadorActualBot() {
        return ctrlDomini.obtenirJugadorActual().esBot();
    }
}
