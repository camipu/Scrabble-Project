package edu.upc.prop.clusterxx.controladors;

import edu.upc.prop.clusterxx.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlPartida {
    private static CtrlPartida instance = null;
    private CtrlJugadaBot ctrlBot = null;
    private HistorialJoc historial;
    private Sac sac;
    private Taulell taulell;
    private Jugador[] jugadors;
    private DAWG dawg;
    private boolean acabada;
    private int torn;

    // Lista de caselles que conté les fitxes del torn actual
    private List<Casella> casellasTorn = new ArrayList<>();

    //Puntaje acumulado del turno actual
    private int puntuacioTorn = 0;

    private int tornsSenseCanvi = -1;

    public static CtrlPartida getInstance() {
        if (instance == null) {
            instance = new CtrlPartida();
        }
        return instance;
    }

    private CtrlPartida() {
    }

    public void inicialitzarDawg(String idioma) {
        List<String> palabras = new ArrayList<>();
        List<String> tokens = new ArrayList<>();

        try {
            // Leer palabras desde el recurso
            InputStream inputParaules = getClass().getClassLoader().getResourceAsStream(idioma + "/" + idioma + ".txt");
            if (inputParaules == null) {
                throw new RuntimeException("No s'ha pogut trobar el fitxer: " + idioma + "/" + idioma + ".txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputParaules))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.isEmpty()) {
                        palabras.add(linea);
                    }
                }
            }

            // Leer tokens desde el recurso (solo la primera palabra de cada línea)
            InputStream inputFitxes = getClass().getClassLoader().getResourceAsStream(idioma + "/fitxes" + idioma + ".txt");
            if (inputFitxes == null) {
                throw new RuntimeException("No s'ha pogut trobar el fitxer: " + idioma + "/fitxes" + idioma + ".txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputFitxes))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.startsWith("#")) continue;
                    String[] partes = linea.split("\\s+");
                    if (partes.length > 0) {
                        tokens.add(partes[0]);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al llegir els fitxers: " + e.getMessage(), e);
        }

        // Inicializar DAWG con los tokens
        dawg = new DAWG(tokens, palabras);
        System.out.print("Se ha inicializado el DAWG\n");
        System.out.print("Tokens: " + tokens + "\n");
    }



    private void inicialitzarCtrlBot() {
        ctrlBot = CtrlJugadaBot.getInstance();
    }

    public void inicialitzarPartida(int midaTaulell, int midaFaristol, String idioma, String[] nomsJugadors,int[] dificultatsBots) {
        acabada = false;
        torn = 0;
        inicialitzarTaulell(midaTaulell);
        inicialitzarDawg(idioma);
        sac = new Sac();
        inicialitzarSac(idioma);
        inicialitzarJugadors(nomsJugadors,dificultatsBots,midaFaristol);
        inicialitzarCtrlBot();
        historial = new HistorialJoc(new java.util.Date());
        passarTorn();
    }

    public void recuperarTorn(Torn nouTorn) {
        acabada = nouTorn.esAcabada();
        torn = nouTorn.obtenirTorn();
        taulell = nouTorn.obtenirTaulell();
        sac = nouTorn.obtenirSac();
        jugadors = nouTorn.obtenirJugadors();
    }


    public boolean acabada() {return acabada;}

    private void ordenarJugadors(){
        for (int i = 0; i < jugadors.length; ++i) {
            for (int j = 0; j < jugadors.length - 1; ++j) {
                if (jugadors[j].obtenirPunts() < jugadors[j + 1].obtenirPunts()) {
                    Jugador temp = jugadors[j];
                    jugadors[j] = jugadors[j + 1];
                    jugadors[j + 1] = temp;
                }
            }
        }
    }

    public void acabarPartida() {
        acabada = true;
        ordenarJugadors();
    }


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
        ++torn;
        ++tornsSenseCanvi;
        historial.afegirTorn(new Torn(sac, taulell, jugadors, torn, acabada));
        acabada = esFinalDePartida();
    }

    public void undo() {
        if(torn >= 1) {
            historial.retirarTorn();
            recuperarTorn(historial.obtenirTorn(torn-1));
        }
    }

    public boolean esFinalDePartida() {
        return (sac.esBuit() && jugadors[torn%jugadors.length].obtenirFaristol().esBuit()) || tornsSenseCanvi >= 6;
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

    private void inicialitzarJugadors(String[] nomsJugadors, int[] dificultatsBots, int midaFaristol) {
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

    private void inicialitzarTaulell(int midaTaulell) {
        taulell = new Taulell(midaTaulell);
    }

    private void inicialitzarFaristol(Jugador jugador) {
        while(!jugador.faristolPle()) {
            Fitxa novaFitxa = sac.agafarFitxa();
            jugador.afegirFitxa(novaFitxa);
        }
    }


    public void agafarDelSac(){
        Fitxa novaFitxa = sac.agafarFitxa();
        jugadors[torn%jugadors.length].afegirFitxa(novaFitxa);
    }

    public void canviarFitxes(int[] fitxesCanviades) {
        List<Fitxa> fitxesCanviadesAux = new ArrayList<>();
        for (int i = 0; i < fitxesCanviades.length; ++i) {
            Fitxa novaFitxa = sac.agafarFitxa();
            fitxesCanviadesAux.add(novaFitxa);
        }
        for (int index : fitxesCanviades) {
            Fitxa aux = jugadors[torn%jugadors.length].obtenirFaristol().obtenirFitxa(index);
            sac.afegirFitxa(aux);
            jugadors[torn%jugadors.length].eliminarFitxa(aux);
        }
        for (Fitxa fitxa : fitxesCanviadesAux) {
            jugadors[torn%jugadors.length].afegirFitxa(fitxa);
        }
    }

    public Jugada colocarFitxa(int fitxa, int fila, int columna) {
        Fitxa aux = jugadors[torn%jugadors.length].obtenirFaristol().obtenirFitxa(fitxa);
        jugadors[torn%jugadors.length].eliminarFitxa(aux);
        taulell.colocarFitxa(aux, fila, columna);
        casellasTorn.add(taulell.getCasella(fila, columna));
        Jugada jugada = taulell.construirJugada(casellasTorn, dawg);
        puntuacioTorn += jugada.getPuntuacio();
        return jugada;
    }


    public void retirarFitxa(int fila, int columna) {
        jugadors[torn%jugadors.length].afegirFitxa(taulell.obtenirFitxa(fila, columna));
        casellasTorn.remove(taulell.getCasella(fila, columna));
        taulell.retirarFitxa(fila, columna);
    }


}
