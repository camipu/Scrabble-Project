package edu.upc.prop.clusterxx;

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

    public Casella[][] getTaulell() {
        return taulell;
    }

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
    public int calcularPuntuacioParaula(Jugada jugada) {
        int puntuacio = 0;
        int multiplicadorParaula = 1;

        for (Casella c : jugada.getCasellesJugades()) {
            Fitxa f = c.obtenirFitxa();
            int punts = f.obtenirPunts();
            
            if (c.obtenirEstrategia().esMultiplicadorParaula()) {
                multiplicadorParaula *= c.obtenirMultiplicador();
            }
            else {
                punts *= c.obtenirMultiplicador();
            }

            puntuacio += punts;
        }
        puntuacio *= multiplicadorParaula;
        if (jugada.getCasellesJugades().size() == 7) puntuacio += 50;
        return puntuacio;
    }

    public boolean validarJugada(Jugada jugada, DAWG dawg) {
        if (jugada.getCasellesJugades().isEmpty()) return false;
        if (!dawg.conteParaula(jugada.getParaulaFormada())) return false;

        if (esBuit()) {
            // Primera jugada i per tant almenys una de les caselles ha d’estar al mig
            int mig = size / 2;
            for (Casella c : jugada.getCasellesJugades()) {
                if (c.obtenirX() == mig && c.obtenirY() == mig) return true;
            }
            return false;
        }
        else {
            // MIRAR QUE HI HAGIN PARAULES PERPENDICULARS
            // I MIRAR QUE SIGUIN VALIDES
            boolean paraulaTocaParaula = false;

            for (Casella c : jugada.getCasellesJugades()) {
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
            return paraulaTocaParaula;
        }
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
}