package edu.upc.prop.clusterxx.controladors;
import edu.upc.prop.clusterxx.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlPartida {
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private DAWG dawg;
    private boolean acabada;
    private int torn;


    public CtrlPartida(int midaTaulell, int midaFaristol, int dificultat, String idioma, String[] nomsJugadors) {
        acabada = false;
        taulell = new Taulell(midaTaulell);
        sac = new Sac();
        inicialitzarSac(idioma);
        jugadors = new Jugador[nomsJugadors.length];
        for (int i = 0; i < nomsJugadors.length; i++) {
            Faristol faristolJugador = new Faristol(midaFaristol);
            while(faristolJugador.obtenirNumFitxes() < midaFaristol) {
                Fitxa novaFitxa = sac.agafarFitxa();
                faristolJugador.afegirFitxa(novaFitxa);
            }
            jugadors[i] = new Jugador(nomsJugadors[i], faristolJugador);
        }
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

    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        jugadors[torn].obtenirFaristol().eliminarFitxa(fitxa);
        taulell.colocarFitxa(fitxa, fila, columna);
    }

    public void mostrarContingutSac() {
        sac.obtenirSac().forEach((fitxa, quantitat) ->
                System.out.println(fitxa.obtenirLletra() + " -> " + quantitat + " fitxes, " + fitxa.obtenirPunts() + " punts"));
    }
}
