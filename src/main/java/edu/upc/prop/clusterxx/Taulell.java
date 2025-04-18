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
            throw new IllegalArgumentException("Posició fora dels límits.");
        }
        else taulell[fila][columna].colocarFitxa(fitxa);
    }

    public void retirarFitxa( int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            throw new IllegalArgumentException("Posició fora dels límits.");
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

    public int calcularPuntuacioTotal(List<Casella> casellesJugades) {
        if (casellesJugades.isEmpty()) return 0;
    
        int puntuacio = 0;
    
        puntuacio += calcularPuntuacioParaulaPrincipal(casellesJugades);
        puntuacio += calcularPuntuacioPerpendiculares(casellesJugades);
    
        // El bonus de punts si es col·loquen tantes fitxes com 
        // el faristol el fan Jugador i Bot
    
        return puntuacio;
    }
    
    private int calcularPuntuacioParaulaPrincipal(List<Casella> casellesJugades) {
        boolean horitzontal = esJugadaHoritzontal(casellesJugades);
        List<Casella> ordenades = new ArrayList<>(casellesJugades);
    
        ordenades.sort((a, b) -> horitzontal ?
                Integer.compare(a.obtenirY(), b.obtenirY()) :
                Integer.compare(a.obtenirX(), b.obtenirX()));
    
        int fila = ordenades.get(0).obtenirX();
        int col = ordenades.get(0).obtenirY();
    
        // Busquem inici de paraula
        while ((horitzontal ? col : fila) > 0) {
            int f = horitzontal ? fila : fila - 1;
            int c = horitzontal ? col - 1 : col;
            if (taulell[f][c].esBuida() && !casellaPertanyAJugada(f, c, casellesJugades)) break;
            if (horitzontal) col--; else fila--;
        }
    
        int puntuacio = 0;
        int multiplicadorParaula = 1;
        int f = fila, c = col;
    
        while (f < size && c < size) {
            Casella casella = taulell[f][c];
            boolean esNova = casellaPertanyAJugada(f, c, casellesJugades);
            Fitxa fitxa = esNova ? obtenirFitxaDeJugada(f, c, casellesJugades) : casella.obtenirFitxa();
    
            int puntsFitxa = fitxa.obtenirPunts();
            int multiplicadorLletra = 1;
    
            if (esNova) {
                EstrategiaPuntuacio estrategia = casella.obtenirEstrategia();
                if (estrategia.esMultiplicadorParaula()) {
                    multiplicadorParaula *= estrategia.obtenirMultiplicador();
                } else {
                    multiplicadorLletra = estrategia.obtenirMultiplicador();
                }
            }
    
            puntuacio += puntsFitxa * multiplicadorLletra;
    
            if (horitzontal) c++; else f++;
    
            if ((f >= size || c >= size) || (taulell[f][c].esBuida() && !casellaPertanyAJugada(f, c, casellesJugades))) break;
        }
    
        return puntuacio * multiplicadorParaula;
    }

    private int calcularPuntuacioPerpendiculares(List<Casella> casellesJugades) {
        int total = 0;
        boolean horitzontal = esJugadaHoritzontal(casellesJugades);
    
        for (Casella c : casellesJugades) {
            int x = c.obtenirX(), y = c.obtenirY();
            int puntuacio = 0;
            int multiplicadorParaula = 1;
            boolean hiHaFitxaNova = false;
        
            int dx = horitzontal ? -1 : 0;
            int dy = horitzontal ? 0 : -1;
            int nx = x + dx, ny = y + dy;
        
            // Prefix
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                puntuacio += taulell[nx][ny].obtenirFitxa().obtenirPunts();
                nx += dx;
                ny += dy;
            }
        
            // Lletra jugada (sí o sí és nova)
            EstrategiaPuntuacio estrategia = c.obtenirEstrategia();
            int punts = c.obtenirFitxa().obtenirPunts();
        
            if (estrategia.esMultiplicadorParaula()) {
                multiplicadorParaula *= estrategia.obtenirMultiplicador();
            } else {
                punts *= estrategia.obtenirMultiplicador();
            }
            puntuacio += punts;
            hiHaFitxaNova = true; // perquè estem tractant la casella nova
        
            // Sufix
            dx = horitzontal ? 1 : 0;
            dy = horitzontal ? 0 : 1;
            nx = x + dx;
            ny = y + dy;
        
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                puntuacio += taulell[nx][ny].obtenirFitxa().obtenirPunts();
                nx += dx;
                ny += dy;
            }
        
            // Només sumem si la paraula perpendicular és més llarga que 1 i conté almenys una fitxa nova
            if (hiHaFitxaNova && (puntuacio > c.obtenirFitxa().obtenirPunts())) {
                total += puntuacio * multiplicadorParaula;
            }
        }
    
        return total;
    }    
    
    
    // FUNCIO PER CrtlPartida
    public Jugada construirJugada(List<Casella> casellesJugades, DAWG dawg) {
        String paraulaFormada = construirParaula(casellesJugades);
        Boolean paraulaValida = dawg.conteParaula(paraulaFormada);
        Boolean jugadaValida = paraulaValida && jugadaValida(casellesJugades, dawg);
        int puntuacio = calcularPuntuacioTotal(casellesJugades);
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }

    // Funcio per CrtlBot
    public Jugada construirJugadaBot(String paraulaFormada, List<Casella> casellesJugades, DAWG dawg) {
        Boolean jugadaValida = jugadaValida(casellesJugades, dawg);
        int puntuacio = -1;
        if (jugadaValida) {
            puntuacio = calcularPuntuacioTotal(casellesJugades);
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
        } else {
            // Almenys una fitxa ha de tocar una ja existent
            boolean adjacent = false;
    
            for (Casella c : casellesJugades) {
                int f = c.obtenirX();
                int col = c.obtenirY();
                int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
                for (int[] d : dirs) {
                    int nf = f + d[0];
                    int nc = col + d[1];
                    if (nf >= 0 && nf < size && nc >= 0 && nc < size) {
                        if (!taulell[nf][nc].esBuida()) {
                            adjacent = true;
                            break;
                        }
                    }
                }
                if (adjacent) break;
            }
            if (!adjacent) return false;
        }
    
        // Verificació de paraules perpendiculars
        boolean horitzontal = esJugadaHoritzontal(casellesJugades);

        for (Casella c : casellesJugades) {
            int x = c.obtenirX(), y = c.obtenirY();
            StringBuilder paraulaPerp = new StringBuilder();

            // Direcció perpendicular: si jugada és horitzontal, mirem vertical (canviem X); si no, canviem Y
            int dx = horitzontal ? -1 : 0;
            int dy = horitzontal ? 0 : -1;
            int nx = x + dx, ny = y + dy;

            // Prefix
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                paraulaPerp.insert(0, taulell[nx][ny].obtenirFitxa().obtenirLletra());
                nx += dx;
                ny += dy;
            }

            // Lletra jugada
            paraulaPerp.append(c.obtenirFitxa().obtenirLletra());

            // Sufix
            dx = horitzontal ? 1 : 0;
            dy = horitzontal ? 0 : 1;
            nx = x + dx;
            ny = y + dy;
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                paraulaPerp.append(taulell[nx][ny].obtenirFitxa().obtenirLletra());
                nx += dx;
                ny += dy;
            }

            if (paraulaPerp.length() > 1 && !dawg.conteParaula(paraulaPerp.toString())) {
                return false;
            }
        }

        // Verifica la paraula principal (amb lletres ja col·locades també)
        boolean esHoritzontal = esJugadaHoritzontal(casellesJugades);
        Casella primer = casellesJugades.get(0);
        int fila = primer.obtenirX(), col = primer.obtenirY();

        // Troba l'inici real de la paraula
        if (esHoritzontal) {
            while (col > 0 && !taulell[fila][col - 1].esBuida()) col--;
        } else {
            while (fila > 0 && !taulell[fila - 1][col].esBuida()) fila--;
        }

        StringBuilder paraula = new StringBuilder();
        while (fila < size && col < size) {
            Casella actual = taulell[fila][col];
            boolean esDeJugada = casellaPertanyAJugada(fila, col, casellesJugades);
            if (actual.esBuida() && !esDeJugada) break;

            Fitxa f = esDeJugada ? obtenirFitxaDeJugada(fila, col, casellesJugades) : actual.obtenirFitxa();
            paraula.append(f.obtenirLletra());

            if (esHoritzontal) col++;
            else fila++;
        }

        return dawg.conteParaula(paraula.toString());
    }

    private boolean casellaPertanyAJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return true;
        }
        return false;
    }
    
    private Fitxa obtenirFitxaDeJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return c.obtenirFitxa();
        }
        return null;
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