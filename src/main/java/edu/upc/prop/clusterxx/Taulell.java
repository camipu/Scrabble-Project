package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorLletra;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorParaula;

public class Taulell {
    private final int size;
    private final Casella[][] taulell;

    public Taulell(int size) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("La mida del tauler ha de ser imparella per garantir simetria.");
        }

        this.size = size;
        taulell = new Casella[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                taulell[i][j] = new Casella(i, j, size);
            }
        }
    }

    public Taulell(Taulell copiaTaulell) {
        this.size = copiaTaulell.getSize();
        this.taulell = new Casella[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Casella original = copiaTaulell.getCasella(i, j);
                this.taulell[i][j] = new Casella(original); // copia profunda
            }
        }
    }


    public Casella[][] getTaulell() {
        return taulell;
    }

    public Casella getCasella(int x, int y) {return taulell[x][y];}

    public int getSize() {
        return size;
    }

    public boolean esBuit() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!taulell[i][j].esBuida()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Fitxa obtenirFitxa(int fila, int columna) {
        return taulell[fila][columna].obtenirFitxa();
    }

    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            System.out.println(" Posició fora dels límits.");
        }
        else taulell[fila][columna].colocarFitxa(fitxa);
    }

    public void retirarFitxa( int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            System.out.println(" Posició fora dels límits.");
        }
        else taulell[fila][columna].retirarFitxa();
    }

    public void imprimirTaulell() {
        for (int i = 0; i < size; ++i) {
            System.out.print("+----");
        }
        System.out.println("+");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Casella casella = taulell[i][j];
                String colorFons = obtenirColorFons(casella);
                System.out.print("|" + colorFons + Colors.BLACK_TEXT + " " + casella + " " + Colors.RESET);
            }
            System.out.println("|");

            for (int j = 0; j < size; ++j) {
                System.out.print("+----");
            }
            System.out.println("+");
        }
    }

    /**
     * Calcula la puntuació d'una paraula segons les fitxes utilitzades i la posició al tauler
     * @param paraula Paraula formada
     * @param fitxesUtilitzades Fitxes utilitzades per formar la paraula
     * @param fila Fila inicial
     * @param columna Columna inicial
     * @param horitzontal Direcció de la paraula
     * @return Puntuació total de la jugada
     */
    private int calcularPuntuacioParaula(List<Casella> casellesJugades) {
        if (casellesJugades.isEmpty()) return 0;
    
        int puntuacio = 0;
        int multiplicadorParaula = 1;
    
        // Determinar si la jugada és horitzontal
        boolean horitzontal = esJugadaHoritzontal(casellesJugades);
    
        // Ordenar caselles per la direcció
        casellesJugades.sort((a, b) ->
            horitzontal ? Integer.compare(a.obtenirY(), b.obtenirY())
                        : Integer.compare(a.obtenirX(), b.obtenirX())
        );
    
        // Extrems de la paraula (completa)
        int fila = casellesJugades.get(0).obtenirX();
        int col = casellesJugades.get(0).obtenirY();
        int inici = horitzontal ? col : fila;
    
        // ─── buscar límit inicial cap enrere ─────────────────────────
        int i = inici - 1;
        while (i >= 0) {
            int f = horitzontal ? fila : i;
            int c = horitzontal ? i   : col;

            boolean buida = taulell[f][c].esBuida();
            if (buida) {                             // si la casella és buida…
                boolean hiHaFitxaNova = false;
                for (Casella cj : casellesJugades) { // …mirem si s’hi posa fitxa ara
                    if (cj.obtenirX() == f && cj.obtenirY() == c) {
                        hiHaFitxaNova = true;
                        break;
                    }
                }
                if (!hiHaFitxaNova) break;           // casella realment buida → límit
            }
            i--;
        }
        int iniciParaula = i + 1;                    // primer índex vàlid

        // ─── buscar límit final cap endavant ─────────────────────────
        i = inici + 1;
        int mida = taulell.length;
        while (i < mida) {
            int f = horitzontal ? fila : i;
            int c = horitzontal ? i   : col;

            boolean buida = taulell[f][c].esBuida();
            if (buida) {
                boolean hiHaFitxaNova = false;
                for (Casella cj : casellesJugades) {
                    if (cj.obtenirX() == f && cj.obtenirY() == c) {
                        hiHaFitxaNova = true;
                        break;
                    }
                }
                if (!hiHaFitxaNova) break;
            }
            i++;
        }
        int finalParaula = i - 1;                    // darrer índex vàlid
    
        // Recorre tota la paraula (ja col·locada + nova)
        for (int pos = iniciParaula; pos <= finalParaula; pos++) {
            int f = horitzontal ? fila : pos;
            int c = horitzontal ? pos : col;
            Casella actual = taulell[f][c];
            int puntsFitxa = 0;
            int multiplicadorLletra = 1;
            
            if (!actual.esBuida()) {
                // Casella ja ocupada
                // PER ALGUN MOTIU MULTIPLICA LO DE SOTA!
                puntsFitxa = actual.calcularPunts();
            } else {
                // Busquem la fitxa en les caselles jugades
                for (Casella casellaJugada : casellesJugades) {
                    if (casellaJugada.obtenirX() == f && casellaJugada.obtenirY() == c) {
                        puntsFitxa = casellaJugada.obtenirFitxa().obtenirPunts();
                        
                        // Apliquem estratègia de puntuació
                        EstrategiaPuntuacio estrategia = casellaJugada.obtenirEstrategia();
                        if (!estrategia.esMultiplicadorParaula()) {
                            multiplicadorLletra = estrategia.obtenirMultiplicador();
                        } else {
                            multiplicadorParaula *= estrategia.obtenirMultiplicador();
                        }
                        break;
                    }
                }
            }
            // Afegim els punts d'aquesta fitxa
            puntuacio += puntsFitxa * multiplicadorLletra;
        }
    
        // Apliquem el multiplicador total de paraula
        puntuacio *= multiplicadorParaula;
        
        // Apliquem el bonus de 50 punts si utilitzem 7 fitxes
        if (casellesJugades.size() == 7) puntuacio += 50;
        
        return puntuacio;
    }
    
    // FUNCIO PER CrtlPartida
    public Jugada construirJugada(List<Casella> casellesJugades, DAWG dawg) {
        String paraulaFormada = construirParaula(casellesJugades);
        Boolean jugadaValida = dawg.conteParaula(paraulaFormada);
        jugadaValida = jugadaValida && jugadaValida(casellesJugades, dawg);
        int puntuacio = calcularPuntuacioParaula(casellesJugades);
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }

    // Funcio per CrtlBot
    public Jugada construirJugadaBot(String paraulaFormada, List<Casella> casellesJugades, DAWG dawg) {
        Boolean jugadaValida = jugadaValida(casellesJugades, dawg);
        int puntuacio = -1;
        if (jugadaValida) {
            puntuacio = calcularPuntuacioParaula(casellesJugades);
        }
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }

    private boolean jugadaValida(List<Casella> casellesJugades, DAWG dawg) {
        if (casellesJugades.isEmpty()) return false;

        // NO mira si paraula jugada és vàlida, això ja ho fa construirJugada

        if (esBuit()) {
            // Primera jugada i per tant almenys una de les caselles ha d’estar al mig
            int mig = size / 2;
            boolean tocaCentre = false;
            for (Casella c : casellesJugades) {
                if (c.obtenirX() == mig && c.obtenirY() == mig) {
                    tocaCentre = true;
                    break;
                }
            }
            if (!tocaCentre) return false;            
        }
        else {
            // MIRAR QUE HI HAGIN PARAULES PERPENDICULARS
            // I MIRAR QUE SIGUIN VALIDES
            boolean paraulaTocaParaula = false;

            for (Casella c : casellesJugades) {
                int f = c.obtenirX();
                int col = c.obtenirY();
    
                boolean fitxaTocaParaula = false;

                int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
                for (int[] d : dirs) {
                    int nf = f + d[0], nc = col + d[1];
                    if (nf >= 0 && nf < size && nc >= 0 && nc < size && !taulell[nf][nc].esBuida()) {
                        fitxaTocaParaula = true;
                        paraulaTocaParaula = true;
                        break;
                    }
                }

                if (fitxaTocaParaula) {
                    // Verifica paraula vertical (si estem posant horitzontal) i viceversa
                    StringBuilder perpendicular = new StringBuilder();
        
                    // Cap enrere
                    int nf = f - 1;
                    while (nf >= 0 && !taulell[nf][col].esBuida()) {
                        perpendicular.insert(0, taulell[nf][col].obtenirFitxa().obtenirLletra());
                        nf--;
                    }
        
                    // Afegim la fitxa col·locada
                    perpendicular.append(c.obtenirFitxa().obtenirLletra());
        
                    // Cap endavant
                    nf = f + 1;
                    while (nf < size && !taulell[nf][col].esBuida()) {
                        perpendicular.append(taulell[nf][col].obtenirFitxa().obtenirLletra());
                        nf++;
                    }
        
                    if (!dawg.conteParaula(perpendicular.toString())) return false;
                }
            }
            if (!paraulaTocaParaula) return false;
        }
        return true;
    }

    private String obtenirColorFons(Casella casella) {
        if (casella.obtenirEstrategia() instanceof EstrategiaMultiplicadorParaula) {
            EstrategiaMultiplicadorParaula estrategia = (EstrategiaMultiplicadorParaula) casella.obtenirEstrategia();
            return estrategia.obtenirMultiplicador() == 3 ? Colors.RED_BACKGROUND : Colors.PURPLE_BACKGROUND;
        } else if (casella.obtenirEstrategia() instanceof EstrategiaMultiplicadorLletra) {
            EstrategiaMultiplicadorLletra estrategia = (EstrategiaMultiplicadorLletra) casella.obtenirEstrategia();
            return estrategia.obtenirMultiplicador() == 3 ? Colors.BLUE_BACKGROUND : Colors.CYAN_BACKGROUND;
        } else {
            // Caselles normals -> Blanc brillant (si el terminal ho suporta)
            return "\033[107m";  // Alternativa més brillant per a WHITE_BACKGROUND
        }
    }

    private String construirParaula(List<Casella> casellesJugades) {
        if (casellesJugades.isEmpty()) return "";
    
        boolean esHoritzontal = esJugadaHoritzontal(casellesJugades);
    
        List<Casella> ordenades = new ArrayList<>(casellesJugades);
        ordenades.sort((a, b) ->
            esHoritzontal
                ? Integer.compare(a.obtenirY(), b.obtenirY())
                : Integer.compare(a.obtenirX(), b.obtenirX())
        );
    
        StringBuilder paraula = new StringBuilder();
        for (Casella c : ordenades) {
            paraula.append(c.obtenirFitxa().obtenirLletra());
        }
    
        return paraula.toString();
    }
    
    private boolean esJugadaHoritzontal(List<Casella> caselles) {
        int fila = caselles.get(0).obtenirX();
        for (Casella c : caselles) {
            if (c.obtenirX() != fila) return false;
        }
        return true;
    }    
}