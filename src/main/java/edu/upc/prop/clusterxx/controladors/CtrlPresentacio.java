package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.Casella;
import edu.upc.prop.clusterxx.Fitxa;
import edu.upc.prop.clusterxx.Jugador;
import edu.upc.prop.clusterxx.Taulell;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaIniciVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaPersonalitzacioVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PartidaVista;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class CtrlPresentacio {
    private static CtrlPresentacio instance = null;
    private CtrlDomini ctrlDomini;
    private PantallaIniciVista pantallaInici;
    private PantallaPersonalitzacioVista pantallaPersonalitzacioVista;
    private PartidaVista partidaVista;
    private JFrame framePartida;

    /**
     * Retorna la instància única del controlador de presentació.
     * Si encara no existeix, la crea.
     *
     * @return Instància única de {@code CtrlPresentacio}
     */
    public static CtrlPresentacio getInstance() {
        if (instance == null) {
            instance = new CtrlPresentacio();
        }
        return instance;
    }

    /**
     * Constructor privat de {@code CtrlPresentacio}.
     * Inicialitza la interfície d'usuari.
     */
    private CtrlPresentacio() {
        // Inicialització de la interfície d'usuari
    }

    /**
     * Inicialitza l'aplicació, creant les instàncies necessàries per al funcionament del joc.
     */
    public void inicialitzarApp() {
        ctrlDomini = CtrlDomini.getInstance();
        pantallaInici = new PantallaIniciVista(this);
        // Mostra la finestra
        pantallaInici.setVisible(true);
    }

    /**
     * Canvia a la pantalla de configuració de partida.
     */
    public void configurarPartida() {
        pantallaPersonalitzacioVista = new PantallaPersonalitzacioVista(this);
        pantallaInici.setVisible(false);
        pantallaPersonalitzacioVista.setVisible(true);
    }

    /**
     * Inicialitza una nova partida amb els paràmetres configurats.
     *
     * @param midaTaulell Mida del taulell de joc
     * @param midaFaristol Mida del faristol de fitxes
     * @param idioma Idioma seleccionat per a la partida
     * @param nomsJugadors Noms dels jugadors participants
     * @param dificultatsBots Nivells de dificultat dels bots
     */
    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors, int[] dificultatsBots) {
        ctrlDomini.inicialitzarPartida(midaTaulell, midaFaristol, idioma.toLowerCase(Locale.ROOT), nomsJugadors, dificultatsBots);
        pantallaPersonalitzacioVista.setVisible(false);

        // Inicialitzar frame per a la partida només un cop
        crearFramePartida();

        // Actualitza la vista amb les dades inicials
        actualitzarVistes();

        // Fa la jugada del bot i torna a actualitzar la vista
        ctrlDomini.jugadaBot();
        actualitzarVistes();
    }

    /**
     * Crea el frame principal de la partida.
     */
    private void crearFramePartida() {
        framePartida = new JFrame("Partida");
        framePartida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePartida.setLayout(new BorderLayout());
        framePartida.setLocationRelativeTo(null);
    }

    /**
     * Gestiona el pas de torn entre jugadors.
     */
    public void passarTorn() {
        ctrlDomini.passarTorn();
        actualitzarVistes();

        // Si el següent jugador és un bot, executa la seva jugada automàticament
        if (esJugadorActualBot()) {
            ctrlDomini.jugadaBot();
            actualitzarVistes();
        }
        System.out.println("Passat torn");
    }

    /**
     * Acció per retirar una fitxa seleccionada del taulell.
     * Ara mateix no fa res.
     */
    public void retirarFitxa() {
        // Aquesta funció es pot implementar més endavant
        Casella casella = partidaVista.getCasellaSeleccionada();
        ctrlDomini.retirarFitxa(casella.obtenirX(), casella.obtenirY());
        actualitzarVistes();

    }


    /**
     * Col·loca una fitxa al taulell.
     */
    public void colocarFitxa() {
        Casella casella = partidaVista.getCasellaSeleccionada();
        Fitxa fitxa = partidaVista.obtenirFitxaSeleccionada();

        if (casella != null && fitxa != null) {
            ctrlDomini.colocarFitxa(fitxa.obtenirLletra(), casella.obtenirX(), casella.obtenirY());
            actualitzarVistes(); // Refresca el tablero y faristol
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecciona una casella i una fitxa abans de col·locar.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Actualitza les vistes amb les dades actuals del joc.
     * Evita crear nous frames cada vegada.
     */
    private void actualitzarVistes() {
        Taulell taulell = ctrlDomini.obtenirTaulell();
        Jugador jugador = ctrlDomini.obtenirJugadorActual();

        // Elimina els components anteriors del frame si existeixen
        if (framePartida.getContentPane().getComponentCount() > 0) {
            framePartida.getContentPane().removeAll();
        }


        // Crea o actualitza la vista de partida
        partidaVista = new PartidaVista(taulell, jugador);
        partidaVista.setPassarTornListener(this::passarTorn);
        partidaVista.setColocarListener(e -> colocarFitxa());
        partidaVista.setRetirarFitxaListener(this::retirarFitxa);

        // Afegeix la nova vista al frame
        framePartida.add(partidaVista, BorderLayout.CENTER);

        // Actualitza el frame
        framePartida.pack();
        framePartida.setVisible(true);
        framePartida.revalidate();
        framePartida.repaint();
    }

    public boolean esJugadorActualBot() {
        return ctrlDomini.obtenirJugadorActual().esBot();
    }

}