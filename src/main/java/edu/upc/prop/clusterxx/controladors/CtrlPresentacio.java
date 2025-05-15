package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaIniciVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaPersonalitzacioVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PartidaVista;
import edu.upc.prop.clusterxx.presentacio.vistes.TaulellVista;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class CtrlPresentacio {
    private static CtrlPresentacio instance = null;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private PantallaIniciVista pantallaInici;
    private PantallaPersonalitzacioVista pantallaPersonalitzacioVista;
    PartidaVista partidaVista;

    /**
     * Retorna la instància única del controlador de presentació.
     * Si encara no existeix, la crea.
     *W
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
        pantallaInici = new PantallaIniciVista(this);
        // Mostra la finestra
        pantallaInici.setVisible(true);
    }

    public void configurarPartida() {
        pantallaPersonalitzacioVista = new PantallaPersonalitzacioVista(this);
        pantallaInici.setVisible(false);
        pantallaPersonalitzacioVista.setVisible(true);
    }


    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors, int[] dificultatsBots) {
        ctrlDomini.inicialitzarPartida(midaTaulell, midaFaristol, idioma.toLowerCase(Locale.ROOT), nomsJugadors, dificultatsBots);
        pantallaPersonalitzacioVista.setVisible(false);

        Taulell taulell = ctrlDomini.obtenirTaulell();
        Jugador jugador = ctrlDomini.obtenirJugadorActual();
        ctrlDomini.jugadaBot();

        partidaVista = new PartidaVista(taulell, jugador);
        partidaVista.setPassarTornListener(() -> passarTorn()); // Set listener


        JFrame frame = new JFrame("Partida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(partidaVista, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void passarTorn() {
        System.out.println("Pasando turno camila quiere café!!!!");
    }


}
