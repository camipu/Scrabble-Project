package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlPartida {
    private static CtrlPartida instance = null;
    private HistorialJoc historial;
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private DAWG dawg;
    private boolean acabada;
    private int torn;

    // Lista de caselles que conté les fitxes del torn actual
    private List<Casella> fitxesTorn = new ArrayList<>();

    public static CtrlPartida getInstance() {
        if (instance == null) {
            instance = new CtrlPartida();
        }
        return instance;
    }

    private CtrlPartida() {
    }

    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors,int[] dificultatsBots) {
        acabada = false;
        torn = 1;
        taulell = new Taulell(midaTaulell);
        sac = new Sac();
        inicialitzarSac(idioma);
        inicialitzarJugadors(midaFaristol, nomsJugadors, dificultatsBots);

        historial = new HistorialJoc();
        historial.afegirTorn(new Torn(sac, taulell, jugadors, torn, acabada));
    }

    public void inicialitzarTorn(Torn nouTorn) {
        acabada = nouTorn.esAcabada();
        torn = nouTorn.obtenirTorn();
        taulell = nouTorn.obtenirTaulell();
        sac = nouTorn.obtenirSac();
        jugadors = nouTorn.obtenirJugadors();
    }


    public boolean acabada() {return acabada;}
    public Sac obtenirSac() {
        return sac;
    }

    public Taulell obtenirTaulell() {
        return taulell;
    }

    public Jugador[] obtenirJugadors() {
        return jugadors;
    }

    public Jugador obtenirJugadorActual() {
        return jugadors[torn];
    }

    public int obtenirTorn() {
        return torn;
    }

    public void passarTorn() {
        historial.afegirTorn(new Torn(sac, taulell, jugadors, torn, acabada));
        ++torn;
    }

    public void undo() {
        if(torn >= 1) {
            historial.retirarTorn();
            inicialitzarTorn(historial.obtenirTorn(torn-1));
        }
    }


    private void inicialitzarSac(String idioma) {
        String nomFitxer = "/" + idioma + "/fitxes" + idioma + ".txt";
        InputStream input = getClass().getResourceAsStream(nomFitxer);
        if (input == null) {
            throw new RuntimeException("No s'ha pogut trobar el fitxer: " + nomFitxer);
        }
        List<String> lines = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.toList());


        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Format incorrecte al fitxer: " + line);
            }

            try {
                String lletra = parts[0];
                int quantitat = Integer.parseInt(parts[1]);
                int punts = Integer.parseInt(parts[2]);

                if (quantitat <= 0 || punts < 0) {
                    throw new IllegalArgumentException("Quantitat o punts invàlids a la línia: " + line);
                }

                for (int i = 0; i < quantitat; i++) {
                    sac.afegirFitxa(new Fitxa(lletra, punts));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error de format numèric a la línia: " + line, e);
            }
        }
    }

    private void inicialitzarJugadors(int midaFaristol, String[] nomsJugadors, int[] dificultatsBots) {
        jugadors = new Jugador[nomsJugadors.length + dificultatsBots.length];
        int i;
        for (i = 0; i < nomsJugadors.length; i++) {
            Faristol faristolJugador = new Faristol(midaFaristol);
            jugadors[i] = new Jugador(nomsJugadors[i], faristolJugador);
            inicialitzarFaristol(jugadors[i]);
        }
        for (int j = i; j < dificultatsBots.length + i; ++j) {
            Faristol faristolBot = new Faristol(midaFaristol);
            jugadors[j] = new Bot("bot" + (j - i + 1), faristolBot, dificultatsBots[j-i]) ;
            inicialitzarFaristol(jugadors[j]);
        }
    }

    private void inicialitzarFaristol(Jugador jugador) {
        while(!jugador.faristolPle()) {
            Fitxa novaFitxa = sac.agafarFitxa();
            jugador.afegirFitxa(novaFitxa);
        }
    }


//    public boolean colocarFitxa(Fitxa fitxa, int fila, int columna) {
//        jugadors[torn%jugadors.length].eliminarFitxa(fitxa);
//        taulell.colocarFitxa(fitxa, fila, columna);
//        fitxesTorn.add(taulell.obtenirFitxa(fila, columna));
//
//
//        int[][] pos = {{fila, columna}};
//        return true;
//        HashMap<String,Integer> nuevasPosiblesPalabras = new HashMap<>();
//        Taulell.BooleanWrapper connex = new Taulell.BooleanWrapper(false);
//        nuevasPosiblesPalabras = taulell.buscaPalabrasValidas(pos,connex);
//        if (connex.getValue()) {
//            for (String palabra : nuevasPosiblesPalabras.keySet()) {
//                if (!dawg.conteParaula(palabra)) {
//                    return false;
//                }
//            }
//            return true;
//        } else {
//            return taulell.esBuit() && dawg.conteParaula(fitxa.obtenirLletra());
//        }
//    }
//
//    public void retirarFitxa(int fila, int columna) {
//        jugadors[torn%jugadors.length].afegirFitxa(taulell.obtenirFitxa(fila, columna));
//        fitxesTorn.remove(taulell.obtenirFitxa(fila, columna));
//        taulell.retirarFitxa(fila, columna);
//    }
//
//    public void mostrarContingutSac() {
//        sac.obtenirSac().forEach((fitxa, quantitat) ->
//                System.out.println(fitxa.obtenirLletra() + " -> " + quantitat + " fitxes, " + fitxa.obtenirPunts() + " punts"));
//    }


}
