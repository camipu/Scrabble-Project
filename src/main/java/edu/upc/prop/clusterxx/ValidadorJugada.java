package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que s'encarrega de validar si una jugada és correcta segons les regles de Scrabble.
 * Revisa la posició de les fitxes, la validesa de la paraula principal i les paraules perpendiculars.
 */
public class ValidadorJugada {

    /**
     * Identifica si la paraula s'està jugant en direcció horitzontal
     * @param caselles Llistat de les caselles de la paraula
     * @return {@code true} si la paraula és horitzontal, {@code false} altrament
     */
    public static boolean esJugadaHoritzontal(List<Casella> caselles) {
        // Si només hi ha una casella, no podem determinar la direcció
        if (caselles.size() == 1) {
            return true; // Per defecte assumim horitzontal
        }
        
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
     * @return La casella si pertany a la jugada, null altrament
     */
    public static Casella casellaPertanyAJugada(int x, int y, List<Casella> jugada) {
        for (Casella c : jugada) {
            if (c.obtenirX() == x && c.obtenirY() == y) return c;
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
     * @param taulell El taulell de joc actual
     * @return {@code true} si la posició és vàlida segons les regles, {@code false} altrament
     */
    public static boolean posicioValida(List<Casella> casellesJugades, Taulell taulell) {
        if (casellesJugades.isEmpty()) return false;
        
        int size = taulell.getSize();
        Casella[][] caselles = taulell.getTaulell();
        
        // Comprovar que estan alineades (mateixa fila o mateixa columna)
        // Si només hi ha una fitxa, considerem que està alineada
        if (casellesJugades.size() > 1) {
            boolean mateixaFila = true;
            boolean mateixaColumna = true;
            int filaBase = casellesJugades.get(0).obtenirX();
            int colBase = casellesJugades.get(0).obtenirY();
            
            for (int i = 1; i < casellesJugades.size(); i++) {
                Casella c = casellesJugades.get(i);
                if (c.obtenirX() != filaBase) mateixaFila = false;
                if (c.obtenirY() != colBase) mateixaColumna = false;
            }
            
            // Si no estan en la mateixa fila NI en la mateixa columna, la jugada no és vàlida
            if (!mateixaFila && !mateixaColumna) return false;
        }

        // Comprovar si és primera jugada i per tant
        // almenys una de les caselles ha d'estar al mig
        int mig = size / 2;
        if (caselles[mig][mig].esBuida()) {
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
                        // Comprovem que la casella adjacent no sigui buida i no pertanyi a la jugada actual
                        if (!caselles[nf][nc].esBuida() && casellaPertanyAJugada(nf, nc, casellesJugades) == null) {
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
     * @param horitzontal Indica si la jugada és en direcció horitzontal
     * @param taulell El taulell de joc actual
     * @return La paraula formada
     */
    public static String construirParaula(List<Casella> casellesJugades, boolean horitzontal, Taulell taulell) {
        int size = taulell.getSize();
        Casella[][] caselles = taulell.getTaulell();
        
        if (casellesJugades.size() == 1) {
            StringBuilder paraula = new StringBuilder();
            Casella casella = casellesJugades.get(0);
            int fila = casella.obtenirX();
            int columna = casella.obtenirY();

            // Buscar cap a l'esquerra i cap a la dreta (horitzontal)
            int tempColumna = columna;
            while (tempColumna > 0 && (!caselles[fila][tempColumna - 1].esBuida() || null != casellaPertanyAJugada(fila, tempColumna - 1, casellesJugades))) {
                tempColumna--;
            }
            while (tempColumna < size && (!caselles[fila][tempColumna].esBuida() || null != casellaPertanyAJugada(fila, tempColumna, casellesJugades))) {
                Casella currentCasella = casellaPertanyAJugada(fila, tempColumna, casellesJugades);
                if (currentCasella != null) {
                    paraula.append(currentCasella.obtenirFitxa().obtenirLletra());
                } else {
                    paraula.append(caselles[fila][tempColumna].obtenirFitxa().obtenirLletra());
                }
                tempColumna++;
            }

            if (paraula.length() > 1) return paraula.toString();

            // Buscar cap amunt i cap avall (vertical)
            paraula = new StringBuilder();
            int tempFila = fila;
            while (tempFila > 0 && (!caselles[tempFila - 1][columna].esBuida() || null != casellaPertanyAJugada(tempFila - 1, columna, casellesJugades))) {
                tempFila--;
            }
            while (tempFila < size && (!caselles[tempFila][columna].esBuida() || null != casellaPertanyAJugada(tempFila, columna, casellesJugades))) {
                Casella currentCasella = casellaPertanyAJugada(tempFila, columna, casellesJugades);
                if (currentCasella != null) {
                    paraula.append(currentCasella.obtenirFitxa().obtenirLletra());
                } else {
                    paraula.append(caselles[tempFila][columna].obtenirFitxa().obtenirLletra());
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
            if (caselles[prevFila][prevCol].esBuida() && null == casellaPertanyAJugada(prevFila, prevCol, casellesJugades)) break;
            if (horitzontal) col--; else fila--;
        }

        // Construir la paraula completa
        int f = fila, c = col;
        while (f < size && c < size) {
            Casella casella = casellaPertanyAJugada(f, c, casellesJugades);
            if (caselles[f][c].esBuida() && null == casella) break;

            if (casella != null) {
                paraula.append(casella.obtenirFitxa().obtenirLletra());
            } else {
                paraula.append(caselles[f][c].obtenirFitxa().obtenirLletra());
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
     * @param taulell El taulell de joc actual
     * @return {@code true} si totes les paraules perpendiculars són vàlides, {@code false} si alguna no ho és
     */
    public static boolean validarParaulesPerpendiculars(List<Casella> casellesJugades, DAWG dawg, boolean horitzontal, Taulell taulell) {
        int size = taulell.getSize();
        Casella[][] caselles = taulell.getTaulell();
        
        for (Casella c : casellesJugades) {
            int x = c.obtenirX(), y = c.obtenirY();
    
            StringBuilder paraula = new StringBuilder();
    
            // Prefix
            int dx = horitzontal ? -1 : 0;
            int dy = horitzontal ? 0 : -1;
            int nx = x + dx, ny = y + dy;
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !caselles[nx][ny].esBuida()) {
                paraula.insert(0, caselles[nx][ny].obtenirFitxa().obtenirLletra());
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
            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !caselles[nx][ny].esBuida()) {
                paraula.append(caselles[nx][ny].obtenirFitxa().obtenirLletra());
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
     * Valida si una jugada és correcta segons les regles de Scrabble.
     * 
     * @param casellesJugades Les caselles on s'han col·locat noves fitxes
     * @param dawg Diccionari (DAWG) per validar les paraules
     * @param taulell El taulell de joc actual
     * @return {@code true} si la jugada és vàlida, {@code false} altrament
     */
    public static boolean validarJugada(List<Casella> casellesJugades, DAWG dawg, Taulell taulell) {        
        // Revisar caselles jugades > 0
        if (casellesJugades == null || casellesJugades.isEmpty()) {
            return false;
        }
            
        // Validar posicions (almenys una al centre si és primer torn)
        // Alineades i que conectin amb una com a mínim
        if (!posicioValida(casellesJugades, taulell)) {
            return false;
        }

        boolean jugadaHoritzontal = esJugadaHoritzontal(casellesJugades);

        // Construïm la paraula principal i revisem que sigui correcte
        String paraulaPrincipal = construirParaula(casellesJugades, jugadaHoritzontal, taulell);
        if (!dawg.conteParaula(paraulaPrincipal)) return false;

        // Revisar paraules perpendiculars
        return validarParaulesPerpendiculars(casellesJugades, dawg, jugadaHoritzontal, taulell);        
    }
}