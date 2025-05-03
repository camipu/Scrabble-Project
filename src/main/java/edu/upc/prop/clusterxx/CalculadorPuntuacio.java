package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que s'encarrega de calcular la puntuació d'una jugada al joc de Scrabble.
 * Calcula la puntuació de la paraula principal i de les paraules perpendiculars formades.
 */
public class CalculadorPuntuacio {

    /**
     * Calcula la puntuació total d'una jugada.
     * 
     * @param casellesJugades Les caselles on s'han col·locat noves fitxes
     * @param horitzontal Indica si la jugada és en direcció horitzontal
     * @param taulell El taulell de joc actual
     * @return La puntuació total de la jugada
     */
    public static int calcularPuntuacioJugada(List<Casella> casellesJugades, boolean horitzontal, Taulell taulell) {
        int puntuacio = calcularPuntuacioParaulaPrincipal(casellesJugades, horitzontal, taulell);
        puntuacio += calcularPuntuacioPerpendiculars(casellesJugades, horitzontal, taulell);
        return puntuacio;
    }

    /**
     * Calcula la puntuació de la paraula principal formada durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param horitzontal Indica si la jugada és en direcció horitzontal
     * @param taulell El taulell de joc actual
     * @return Puntuació total obtinguda per la paraula principal.
     */
    private static int calcularPuntuacioParaulaPrincipal(List<Casella> casellesJugades, boolean horitzontal, Taulell taulell) {
        int size = taulell.getSize();
        Casella[][] caselles = taulell.getTaulell();
        
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
            if (caselles[f][c].esBuida() && null == ValidadorJugada.casellaPertanyAJugada(f, c, casellesJugades)) break;
            if (horitzontal) col--; else fila--;
        }
    
        int puntuacio = 0;
        int multiplicadorParaula = 1;
        int f = fila, c = col;
    
        while (f < size && c < size) {
            Casella casella = ValidadorJugada.casellaPertanyAJugada(f, c, casellesJugades);
            if (casella == null) casella = caselles[f][c];
            int puntsCasella = casella.calcularPunts();
    
            EstrategiaPuntuacio estrategia = casella.obtenirEstrategia();
            if (estrategia.esMultiplicadorParaula()) {
                multiplicadorParaula *= estrategia.obtenirMultiplicador();
            }
    
            puntuacio += puntsCasella;
    
            if (horitzontal) c++;
            else f++;
    
            if ((f >= size || c >= size) || (caselles[f][c].esBuida() && null == ValidadorJugada.casellaPertanyAJugada(f, c, casellesJugades))) break;
        }
    
        return puntuacio * multiplicadorParaula;
    }

   /**
     * Calcula la puntuació de les paraules perpendiculars formades durant la jugada
     *
     * @param casellesJugades Llistat de les caselles on s'ha jugat una fitxa
     * @param horitzontal Direcció principal de la jugada (true si és horitzontal, false si és vertical)
     * @param taulell El taulell de joc actual
     * @return Puntuació total obtinguda per les paraules perpendiculars.
     */
    private static int calcularPuntuacioPerpendiculars(List<Casella> casellesJugades, boolean horitzontal, Taulell taulell) {
        int size = taulell.getSize();
        Casella[][] caselles = taulell.getTaulell();
        
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

            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !caselles[nx][ny].esBuida()) {
                puntuacio += caselles[nx][ny].calcularPunts();
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

            while (nx >= 0 && ny >= 0 && nx < size && ny < size && !caselles[nx][ny].esBuida()) {
                puntuacio += caselles[nx][ny].calcularPunts();
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
}