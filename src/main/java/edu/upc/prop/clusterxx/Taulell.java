package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorLletra;
import edu.upc.prop.clusterxx.EstrategiaPuntuacio.EstrategiaMultiplicadorParaula;

/**
 * Classe Taulell
 *
 * Representa el taulell de joc de Scrabble com una matriu de caselles.
 * Cada casella pot contenir una fitxa i tenir una estratègia de puntuació associada.
 *
 * La mida del taulell és configurable i pot variar segons la configuració inicial de la partida.
 */
public class Taulell {
    private final int size;
    private final Casella[][] taulell;

    /**
     * Crea un nou taulell de joc amb la mida especificada.
     * La mida ha de ser un nombre imparell per garantir la simetria del taulell.
     * Cada casella del taulell s’inicialitza amb la seva posició i estratègia de puntuació corresponent.
     *
     * @param size Mida del taulell (nombre de files i columnes)
     * @throws IllegalArgumentException si la mida especificada és parella
     */
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

    /**
     * Inicialitza aquest taulell com la còpia d'un altre taulell.
     * Es copien tots els atributs del taulell original.
     *
     * @param copiaTaulell El taulell original del qual es vol fer la còpia.
     */
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

    /**
     * Retorna la matriu completa de caselles que formen el taulell.
     *
     * @return Matriu de caselles del taulell
     */
    public Casella[][] getTaulell() {
        return taulell;
    }

    /**
     * Retorna la casella situada a les coordenades especificades.
     *
     * @param x Fila (coordenada vertical)
     * @param y Columna (coordenada horitzontal)
     * @return Casella situada a (x, y)
     */
    public Casella getCasella(int x, int y) {return taulell[x][y];}

    /**
     * Retorna la mida del taulell (nombre de files i columnes).
     *
     * @return Mida del taulell
     */
    public int getSize() {
        return size;
    }

    /**
     * Comprova si totes les caselles del taulell estan buides.
     *
     * @return {@code true} si el taulell està completament buit, {@code false} altrament
     */
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
    /**
     * Retorna la fitxa situada en una posició especifica del taulell.
     *
     * @param fila La fila de la fitxa
     * @param columna La columna de la fitxa
     * @return La fitxa colocada a (fila, columna)
     */
    public Fitxa obtenirFitxa(int fila, int columna) {
        return taulell[fila][columna].obtenirFitxa();
    }

    /**
     * Mètode per assignar una fitxa a una casella del taulell.
     *
     * @param fitxa Fitxa que es vol assignar a la casella
     * @param fila Fila de la casella
     * @param columna Columna de la casella
     * @throws IllegalArgumentException si la posció de la casella es fora de límits.
     */
    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            throw new IllegalArgumentException("Posició fora dels límits.");
        }
        else taulell[fila][columna].colocarFitxa(fitxa);
    }

    /**
     * Mètode per desassignar una fitxa d'una casella del taulell.
     *
     * @param fila Fila de la casella
     * @param columna Columna de la casella
     * @throws IllegalArgumentException si la posció de la casella es fora de límits.
     */
    public void retirarFitxa( int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            throw new IllegalArgumentException("Posició fora dels límits.");
        }
        else taulell[fila][columna].retirarFitxa();
    }

    /**
     * Mètode per calcular la puntuació total d'un conjunt de fitxes jugades.
     * Es tenen en compte tant la puntuació de la paraula principal com de les generades per perpendicularitats.
     *
     * @param casellesJugades Llistat de les caselles on s'ha col·locat una fitxa en aquesta jugada.
     * @return Suma de les puntuacions de les paraules perpendiculars i la principal
     */
    public int calcularPuntuacioTotal(List<Casella> casellesJugades) {
        if (casellesJugades.isEmpty()) return 0;
    
        int puntuacio = 0;
    
        puntuacio += calcularPuntuacioParaulaPrincipal(casellesJugades);
        puntuacio += calcularPuntuacioPerpendiculares(casellesJugades);
    
        // El bonus de punts si es col·loquen tantes fitxes com 
        // el faristol el fan Jugador i Bot
    
        return puntuacio;
    }
    /**
     * Calcula la puntuacio de la paraula principal formada durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @return Puntuació total obtinguda per la paraula principal.
     */
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

    /**
     * Calcula la puntuacio de les paraules perpendiculars formades durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @return Puntuació total obtinguda per les paraules perpendiculars.
     */
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


    /**
     * Mètode per construir una instància de la classe jugada.
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param dawg Algorisme DAWG
     * @return Jugada construida per la colocació de les fitxes
     */
    public Jugada construirJugada(List<Casella> casellesJugades, DAWG dawg) {
        String paraulaFormada = construirParaula(casellesJugades);
        System.out.println("PARAULA FORMADA:" + paraulaFormada);
        Boolean paraulaValida = dawg.conteParaula(paraulaFormada);
        Boolean jugadaValida = paraulaValida && jugadaValida(casellesJugades, dawg);
        int puntuacio = calcularPuntuacioTotal(casellesJugades);
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }

    /**
     * Mètode per construir una instància de la classe jugada.
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param dawg Algorisme DAWG
     * @return Jugada construida per la colocació de les fitxes
     */
    public Jugada construirJugadaBot(String paraulaFormada, List<Casella> casellesJugades, DAWG dawg) {
        Boolean jugadaValida = jugadaValida(casellesJugades, dawg);
        int puntuacio = -1;
        if (jugadaValida) {
            puntuacio = calcularPuntuacioTotal(casellesJugades);
        }
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }

    /**
     * Verifica si una jugada es valida segons les regles del joc Scrabble
     * @param casellesJugades Llistat de caselles on s'ha jugat una fitxa.
     * @param dawg Algorisme DAWG
     * @return {@code true} si la jugada és vàlida, {@code false} altrament.
     */
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
    
        // FALTA VERIFICAR TOTES CASELLES JUGADES MATEIXA FILA/COLUMNA/I CONNECTADES

        boolean almenysUnaParaulaValida = false;

        // Verificar totes les paraules formades horitzontalment
        for (int fila = 0; fila < size; fila++) {
            for (int col = 0; col < size; ) {
                // Saltar caselles buides
                while (col < size && taulell[fila][col].esBuida() && !casellaPertanyAJugada(fila, col, casellesJugades)) {
                    col++;
                }
                if (col >= size) break;
                
                // Trobar inici paraula
                int iniciCol = col;
                StringBuilder paraula = new StringBuilder();
                boolean conteCasellaJugada = false;
                
                // Construir paraula
                while (col < size && (!taulell[fila][col].esBuida() || casellaPertanyAJugada(fila, col, casellesJugades))) {
                    if (casellaPertanyAJugada(fila, col, casellesJugades)) {
                        conteCasellaJugada = true;
                        paraula.append(obtenirFitxaDeJugada(fila, col, casellesJugades).obtenirLletra());
                    } else {
                        paraula.append(taulell[fila][col].obtenirFitxa().obtenirLletra());
                    }
                    col++;
                }
                
                // Verificar paraula només si és més llarga d'una lletra i inclou almenys una casella jugada
                if (paraula.length() > 1 && conteCasellaJugada) {
                    if (!dawg.conteParaula(paraula.toString())) {
                        return false;
                    }
                    almenysUnaParaulaValida = true;
                }
            }
        }
        
        // Verificar totes les paraules formades verticalment
        for (int col = 0; col < size; col++) {
            for (int fila = 0; fila < size; ) {
                // Saltar caselles buides
                while (fila < size && taulell[fila][col].esBuida() && !casellaPertanyAJugada(fila, col, casellesJugades)) {
                    fila++;
                }
                if (fila >= size) break;
                
                // Trobar inici paraula
                int iniciFila = fila;
                StringBuilder paraula = new StringBuilder();
                boolean conteCasellaJugada = false;
                
                // Construir paraula
                while (fila < size && (!taulell[fila][col].esBuida() || casellaPertanyAJugada(fila, col, casellesJugades))) {
                    if (casellaPertanyAJugada(fila, col, casellesJugades)) {
                        conteCasellaJugada = true;
                        paraula.append(obtenirFitxaDeJugada(fila, col, casellesJugades).obtenirLletra());
                    } else {
                        paraula.append(taulell[fila][col].obtenirFitxa().obtenirLletra());
                    }
                    fila++;
                }
                
                // Verificar paraula només si és més llarga d'una lletra i inclou almenys una casella jugada
                if (paraula.length() > 1 && conteCasellaJugada) {
                    if (!dawg.conteParaula(paraula.toString())) {
                        return false;
                    }
                    almenysUnaParaulaValida = true;
                }
            }
        }
        
        return almenysUnaParaulaValida;
    }

    /**
     * Mètode que ens diu si una casella ha estat utilitzada en una jugada.
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param jugada LListat de caselles utilitzades en la jugada
     * @return {@code true} si s'ha jugat la casella, {@code false} altrament
     */
    private boolean casellaPertanyAJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return true;
        }
        return false;
    }

    /**
     * Mètode per obtenir la fitxa jugada d'una casella utilitzada a la jugada
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param jugada Llistat de caselles utiltzades en la jugada
     * @return Fitxa de la casella
     */
    private Fitxa obtenirFitxaDeJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return c.obtenirFitxa();
        }
        return null;
    }

    /**
     * Mètode de coloració del taulell
     * @param casella Casella de la qual volem obtenir el color
     * @return Color
     */
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

    /**
     * Construiex la paraula principal formada per les caselles jugades.
     * @param casellesJugades Llistat de les caselles en les que s'ha jugat un fitxa
     * @return La paraula formada
     */
    private String construirParaula(List<Casella> casellesJugades) {
        if (casellesJugades.isEmpty()) return "";
        
        // Cas especial: quan només hi ha una casella jugada
        if (casellesJugades.size() == 1) {
            Casella casellaJugada = casellesJugades.get(0);
            int x = casellaJugada.obtenirX();
            int y = casellaJugada.obtenirY();
            
            // Mirar si forma una paraula horitzontal
            String paraulaHoritzontal = construirParaulaAEix(x, y, true, casellesJugades);
            
            // Mirar si forma una paraula vertical
            String paraulaVertical = construirParaulaAEix(x, y, false, casellesJugades);
            
            // Si ambdues són paraules vàlides (més d'una lletra), retornar la més llarga
            if (paraulaHoritzontal.length() > 1 && paraulaVertical.length() > 1) {
                return paraulaHoritzontal.length() >= paraulaVertical.length() ? 
                    paraulaHoritzontal : paraulaVertical;
            } 
            // Si només una és paraula vàlida, retornar-la
            else if (paraulaHoritzontal.length() > 1) {
                return paraulaHoritzontal;
            } 
            else if (paraulaVertical.length() > 1) {
                return paraulaVertical;
            }
            // Si cap és una paraula vàlida, retornar només la lletra
            else {
                return casellaJugada.obtenirFitxa().obtenirLletra();
            }
        }
        
        // Cas normal: múltiples caselles jugades
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

    /**
     * Construeix la paraula completa en un eix determinat.
     * @param x Fila de la casella central
     * @param y Columna de la casella central
     * @param horitzontal Si volem la paraula horitzontal (true) o vertical (false)
     * @param casellesJugades Llista de caselles jugades
     * @return La paraula completa formada al taulell
     */
    private String construirParaulaAEix(int x, int y, boolean horitzontal, List<Casella> casellesJugades) {
        // Trobar inici de la paraula
        int fila = x;
        int col = y;
        
        // Anar cap enrere fins trobar el principi de la paraula
        while (true) {
            int prevFila = horitzontal ? fila : fila - 1;
            int prevCol = horitzontal ? col - 1 : col;
            
            if (prevFila < 0 || prevCol < 0) break;
            if (taulell[prevFila][prevCol].esBuida() && !casellaPertanyAJugada(prevFila, prevCol, casellesJugades)) break;
            
            if (horitzontal) col--; else fila--;
        }
        
        // Construir la paraula des de l'inici
        StringBuilder paraula = new StringBuilder();
        int f = fila;
        int c = col;
        
        while (f < size && c < size) {
            if (taulell[f][c].esBuida() && !casellaPertanyAJugada(f, c, casellesJugades)) break;
            
            if (casellaPertanyAJugada(f, c, casellesJugades)) {
                paraula.append(obtenirFitxaDeJugada(f, c, casellesJugades).obtenirLletra());
            } else {
                paraula.append(taulell[f][c].obtenirFitxa().obtenirLletra());
            }
            
            if (horitzontal) c++; else f++;
        }
        
        return paraula.toString();
    }

    /**
     * Identifica si la paraula s'està jugant en direcció horitzontal
     * @param caselles Llistat de les caselles de la paraula
     * @return {@code true} si la paraula és horitzontal, {@code false} altrament
     */
    private boolean esJugadaHoritzontal(List<Casella> caselles) {
        int fila = caselles.get(0).obtenirX();
        for (Casella c : caselles) {
            if (c.obtenirX() != fila) return false;
        }
        return true;
    }    
}