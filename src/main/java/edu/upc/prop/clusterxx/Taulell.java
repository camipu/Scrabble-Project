package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

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
     * Cada casella del taulell s'inicialitza amb la seva posició i estratègia de puntuació corresponent.
     *
     * @param size Mida del taulell (nombre de files i columnes)
     * @throws IllegalArgumentException si la mida especificada és parella
     */
    public Taulell(int size) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("La mida del tauler ha de ser imparella per garantir simetria.");
        }
        else if (size < 5) {
            throw new IllegalArgumentException("La mida del tauler ha de ser com a mínim 5.");
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
     * Retorna la mida del taulell (nombre de files i columnes).
     *
     * @return Mida del taulell
     */
    public int getSize() {
        return size;
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

    /**
     * Mètode que ens diu si una casella ha estat utilitzada en una jugada.
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param jugada LListat de caselles utilitzades en la jugada
     * @return {@code true} si s'ha jugat la casella, {@code false} altrament
     */
    private Casella casellaPertanyAJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return c;
        }
        return null;
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
     * Verifica si la posició de les caselles jugades és vàlida segons les regles del Scrabble.
     *
     * Una posició es considera vàlida si compleix els següents criteris:
     * - Totes les caselles estan alineades en una mateixa fila o columna
     * - En la primera jugada de la partida, almenys una casella ha d'estar a la posició central del taulell
     * - En les jugades següents, almenys una de les caselles ha de ser adjacent a alguna fitxa ja existent
     *
     * @param casellesJugades Llista de caselles on s'han col·locat noves fitxes
     * @return {@code true} si la posició és vàlida segons les regles, {@code false} altrament
     */
    private boolean posicioValida(List<Casella> casellesJugades) {
        // Comprovar que estan alineades (mateixa fila o mateixa columna)
        boolean mateixaFila = casellesJugades.stream()
            .allMatch(c -> c.obtenirX() == casellesJugades.get(0).obtenirX());
        boolean mateixaColumna = casellesJugades.stream()
            .allMatch(c -> c.obtenirY() == casellesJugades.get(0).obtenirY());

        if (!mateixaFila && !mateixaColumna) return false;

        // Comprovar si és primera jugada i per tant
        // almenys una de les caselles ha d'estar al mig
        int mig = size / 2;
        if (taulell[mig][mig].esBuida()) {
            boolean tocaCentre = false;
            for (Casella c : casellesJugades) {
                if (c.obtenirX() == mig && c.obtenirY() == mig) {
                    tocaCentre = true;
                    break;
                }
            }
            return tocaCentre;
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
            return adjacent;
        }
    }

    /**
     * Construiex la paraula principal formada per les caselles jugades.
     * @param casellesJugades Llistat de les caselles en les que s'ha jugat un fitxa
     * @return La paraula formada
     */
    private String construirParaula(List<Casella> casellesJugades, boolean horitzontal) {
        if (casellesJugades.size() == 1) {
            StringBuilder paraula = new StringBuilder();
            Casella casella = casellesJugades.get(0);
            int fila = casella.obtenirX();
            int columna = casella.obtenirY();

            // Buscar cap a l'esquerra i cap a la dreta (horitzontal)
            int tempColumna = columna;
            while (tempColumna > 0 && (!taulell[fila][tempColumna - 1].esBuida() || null != casellaPertanyAJugada(fila, tempColumna - 1, casellesJugades))) {
                tempColumna--;
            }
            while (tempColumna < size && (!taulell[fila][tempColumna].esBuida() || null != casellaPertanyAJugada(fila, tempColumna, casellesJugades))) {
                Casella currentCasella = casellaPertanyAJugada(fila, tempColumna, casellesJugades);
                if (currentCasella != null) {
                    paraula.append(currentCasella.obtenirFitxa().obtenirLletra());
                } else {
                    paraula.append(taulell[fila][tempColumna].obtenirFitxa().obtenirLletra());
                }
                tempColumna++;
            }

            if (paraula.length() > 1) return paraula.toString();

            // Buscar cap amunt i cap avall (vertical)
            paraula = new StringBuilder();
            int tempFila = fila;
            while (tempFila > 0 && (!taulell[tempFila - 1][columna].esBuida() || null != casellaPertanyAJugada(tempFila - 1, columna, casellesJugades))) {
                tempFila--;
            }
            while (tempFila < size && (!taulell[tempFila][columna].esBuida() || null != casellaPertanyAJugada(tempFila, columna, casellesJugades))) {
                Casella currentCasella = casellaPertanyAJugada(tempFila, columna, casellesJugades);
            if (currentCasella != null) {
                paraula.append(currentCasella.obtenirFitxa().obtenirLletra());
            } else {
                paraula.append(taulell[tempFila][columna].obtenirFitxa().obtenirLletra());
            }
                tempFila++;
            }

            return paraula.toString();
        }


        if (casellesJugades.isEmpty()) return "";
        
        List<Casella> ordenades = new ArrayList<>(casellesJugades);
        ordenades.sort((a, b) ->
            horitzontal
                ? Integer.compare(a.obtenirY(), b.obtenirY())
                : Integer.compare(a.obtenirX(), b.obtenirX())
        );
        
        StringBuilder paraula = new StringBuilder();
        
        // Trobar inici de la paraula
        int fila = ordenades.get(0).obtenirX();
        int col = ordenades.get(0).obtenirY();
        while ((horitzontal ? col : fila) > 0) {
            int prevFila = horitzontal ? fila : fila - 1;
            int prevCol = horitzontal ? col - 1 : col;
            if (taulell[prevFila][prevCol].esBuida() && null == casellaPertanyAJugada(prevFila, prevCol, casellesJugades)) break;
            if (horitzontal) col--; else fila--;
        }

        // Construir la paraula completa
        int f = fila, c = col;
        while (f < size && c < size) {
            Casella casella = casellaPertanyAJugada(f, c, casellesJugades);
            if (taulell[f][c].esBuida() && null == casella) break;

            if (casella != null) {
                paraula.append(casella.obtenirFitxa().obtenirLletra());
            } else {
                paraula.append(taulell[f][c].obtenirFitxa().obtenirLletra());
            }

            if (horitzontal) c++; else f++;
        }
        return paraula.toString();
    }



    /**
     * Valida si les paraules perpendiculars formades per les caselles jugades són vàlides.
     *
     * Per cada casella jugada, construeix la paraula perpendicular a la direcció principal
     * (horitzontal o vertical) i verifica que aquesta paraula existeixi al diccionari DAWG.
     * Només es validen paraules de més d'una lletra.
     *
     * @param casellesJugades Llista de caselles on s'han col·locat noves fitxes
     * @param dawg Diccionari (DAWG) per validar les paraules
     * @param horitzontal {@code true} si la jugada principal és horitzontal (i es comproven paraules verticals),
     *                    {@code false} si la jugada principal és vertical (i es comproven paraules horitzontals)
     * @return {@code true} si totes les paraules perpendiculars són vàlides, {@code false} si alguna no ho és
     */
    private boolean validarParaulesPerpendiculars(List<Casella> casellesJugades, DAWG dawg, boolean horitzontal) {
        for (Casella c : casellesJugades) {
            int x = c.obtenirX(), y = c.obtenirY();
    
            StringBuilder paraula = new StringBuilder();
    
            // Prefix
            int dx = horitzontal ? -1 : 0;
            int dy = horitzontal ? 0 : -1;
            int nx = x + dx, ny = y + dy;
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                paraula.insert(0, taulell[nx][ny].obtenirFitxa().obtenirLletra());
                nx += dx;
                ny += dy;
            }
    
            // Fitxa jugada
            paraula.append(c.obtenirFitxa().obtenirLletra());
    
            // Sufix
            dx = horitzontal ? 1 : 0;
            dy = horitzontal ? 0 : 1;
            nx = x + dx;
            ny = y + dy;
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                paraula.append(taulell[nx][ny].obtenirFitxa().obtenirLletra());
                nx += dx;
                ny += dy;
            }
    
            // Validar si s'ha format una paraula de >1 lletra
            if (paraula.length() > 1) {
                if (!dawg.conteParaula(paraula.toString())) return false;
            }
        }
    
        return true;
    }

    /**
     * Calcula la puntuació de la paraula principal formada durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @return Puntuació total obtinguda per la paraula principal.
     */
    private int calcularPuntuacioParaulaPrincipal(List<Casella> casellesJugades, boolean horitzontal) {
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
            if (taulell[f][c].esBuida() && null == casellaPertanyAJugada(f, c, casellesJugades)) break;
            if (horitzontal) col--; else fila--;
        }
    
        int puntuacio = 0;
        int multiplicadorParaula = 1;
        int f = fila, c = col;
    
        while (f < size && c < size) {
            Casella casella = casellaPertanyAJugada(f, c, casellesJugades);
            if (casella == null) casella = taulell[f][c];
            int puntsCasella = casella.calcularPunts();
    
            EstrategiaPuntuacio estrategia = casella.obtenirEstrategia();
            if (estrategia.esMultiplicadorParaula()) {
                multiplicadorParaula *= estrategia.obtenirMultiplicador();
            }
    
            puntuacio += puntsCasella;
    
            if (horitzontal) c++;
            else f++;
    
            if ((f >= size || c >= size) || (taulell[f][c].esBuida() && null == casellaPertanyAJugada(f, c, casellesJugades))) break;
        }
    
        return puntuacio * multiplicadorParaula;
    }

   /**
     * Calcula la puntuació de les paraules perpendiculars formades durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param horitzontal Direcció principal de la jugada (true si és horitzontal, false si és vertical)
     * @return Puntuació total obtinguda per les paraules perpendiculars.
     */
    private int calcularPuntuacioPerpendiculars(List<Casella> casellesJugades, boolean horitzontal) {
        int total = 0;

        for (Casella c : casellesJugades) {
            int x = c.obtenirX();
            int y = c.obtenirY();
            int puntuacio = 0;
            int multiplicadorParaula = 1;

            // Prefix (cap enrere)
            int dx = horitzontal ? -1 : 0;
            int dy = horitzontal ? 0 : -1;
            int nx = x + dx;
            int ny = y + dy;

            int longitudParaula = 1; // Comencem amb la fitxa jugada

            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                puntuacio += taulell[nx][ny].calcularPunts();
                longitudParaula++;
                nx += dx;
                ny += dy;
            }

            // Fitxa jugada
            EstrategiaPuntuacio estrategia = c.obtenirEstrategia();
            int punts = c.calcularPunts();

            if (estrategia.esMultiplicadorParaula()) {
                multiplicadorParaula *= estrategia.obtenirMultiplicador();
            }

            puntuacio += punts;

            // Sufix (cap endavant)
            dx = horitzontal ? 1 : 0;
            dy = horitzontal ? 0 : 1;
            nx = x + dx;
            ny = y + dy;

            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !taulell[nx][ny].esBuida()) {
                puntuacio += taulell[nx][ny].calcularPunts();
                longitudParaula++;
                nx += dx;
                ny += dy;
            }

            // Només si s'ha format realment una paraula de dues o més lletres
            if (longitudParaula > 1) {
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
        boolean jugadaValida = true;
        boolean jugadaHoritzontal = esJugadaHoritzontal(casellesJugades);

        // Revisar caselles jugades > 0
        if (casellesJugades == null || casellesJugades.isEmpty()) {
            jugadaValida = false;
        }
    
        // Validar posicions (almenys una al centre si és primer torn)
        // Alineades i que conectin amb una com a mínim
        if (!posicioValida(casellesJugades)) {
            jugadaValida = false;
        }

        // Construïm la paraula principal i revisem que sigui correcte
        String paraulaPrincipal = construirParaula(casellesJugades, jugadaHoritzontal);
        jugadaValida = jugadaValida && dawg.conteParaula(paraulaPrincipal);

        // Revisar paraules perpendiculars
        jugadaValida = jugadaValida && validarParaulesPerpendiculars(casellesJugades, dawg, jugadaHoritzontal);

        // Calcular puntuació
        int puntuacio = calcularPuntuacioParaulaPrincipal(casellesJugades, jugadaHoritzontal);
        puntuacio += calcularPuntuacioPerpendiculars(casellesJugades, jugadaHoritzontal);
        return new Jugada(paraulaPrincipal, casellesJugades, puntuacio, jugadaValida);
    }

    /**
     * Mètode per construir una instància de la classe jugada.
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param dawg Algorisme DAWG
     * @return Jugada construida per la colocació de les fitxes
     */
    public Jugada construirJugadaBot(String paraulaFormada, List<Casella> casellesJugades, DAWG dawg, boolean horitzontal) {
        boolean jugadaValida = posicioValida(casellesJugades);

        // No cal validar que la paraulaFormada estigui al
        // DAWG l'algorisme només genera paraules principals correctes
        
        jugadaValida = jugadaValida && validarParaulesPerpendiculars(casellesJugades, dawg, esJugadaHoritzontal(casellesJugades));
        int puntuacio = 0;
        if (jugadaValida) {
            puntuacio += calcularPuntuacioParaulaPrincipal(casellesJugades, horitzontal);
            puntuacio += calcularPuntuacioPerpendiculars(casellesJugades, horitzontal);
        }
        return new Jugada(paraulaFormada, casellesJugades, puntuacio, jugadaValida);
    }
}