package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.presentacio.vistes.PantallaIniciVista;
import edu.upc.prop.clusterxx.presentacio.vistes.PantallaPersonalitzacioVista;

public class CtrlPresentacio {
    private static CtrlPresentacio instance = null;
    private PantallaIniciVista pantallaInici;
    private PantallaPersonalitzacioVista pantallaPersonalitzacioVista;

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

    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, int[] dificultatsBots, String[] nomsJugadors ) {
        //Passar struct System.out.println("Mida taulell: " + midaTaulell);
        System.out.println("Mida faristol: " + midaFaristol);
        System.out.println("Idioma: " + idioma);
        System.out.println("Noms Bots i dificultats:");
        for (int i = 0; i < dificultatsBots.length; ++i) {
            System.out.println("  " + i + " - Dificultat " + dificultatsBots[i]);
        }
        System.out.println("Noms Jugadors:");
        for (String s : nomsJugadors) {
            System.out.println("  " + s);
        }
    }

    
}
