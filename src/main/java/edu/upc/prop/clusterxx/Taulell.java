package edu.upc.prop.clusterxx;

import java.util.*;

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

    public int getSize() {return size;}


    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            System.out.println(" Posició fora dels límits.");
        }
        else taulell[fila][columna].colocarFitxa(fitxa);
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

    // Pre: un vector con las posiciones de las letras que se están añadiendo al tablero
    // Post: Un map<string, int> con las palabras nuevas y sus respectivas puntuaciones
    public HashMap<String, Integer> buscaPalabrasValidas(int[][] posNuevasLetras){
        HashMap<String,Integer> nuevasPosiblesPalabras = new HashMap<>();
        for (int[] posLetra : posNuevasLetras) {
            int x = posLetra[0];
            int y = posLetra[1];
            HashMap<String,Integer> aux =new HashMap<>();
            aux = buscaPalabra(x, y, 1, 0); //derecha e izquierda
            nuevasPosiblesPalabras.putAll(aux);
            aux = buscaPalabra(x,y,0,1); //arriba, abajo
            nuevasPosiblesPalabras.putAll(aux);

        }
        return nuevasPosiblesPalabras;
    }


    private HashMap<String, Integer> buscaPalabra(int x, int y, int dx, int dy) {
        HashMap<String, Integer> palabras = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int i = pos[0], j = pos[1];
            Vector<Fitxa> vectorDeFichas = new Vector<>();

            // Formar la palabra recorriendo en la dirección inversa (dx, dy)
            StringBuilder palabra = new StringBuilder();
            int puntos = 0;
            Stack<Fitxa> fichasInversas = new Stack<>();

            while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
                palabra.insert(0, taulell[i][j].toString().trim()); // Insertar letra al inicio
                fichasInversas.push(taulell[i][j].obtenirFitxa()); // Guardar ficha en orden inverso
                i -= dx;
                j -= dy;
            }

            // Agregar las fichas inversas al vector en orden correcto
            while (!fichasInversas.isEmpty()) {
                vectorDeFichas.add(fichasInversas.pop());
            }

            // Ahora ir en la dirección normal para completar la palabra
            i = pos[0] + dx;
            j = pos[1] + dy;
            while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
                palabra.append(taulell[i][j].toString().trim()); // Agregar letra al final
                vectorDeFichas.add(taulell[i][j].obtenirFitxa()); // Guardar ficha en orden correcto
                i += dx;
                j += dy;
            }

            String palabraFinal = palabra.toString().replaceAll("\\s+", ""); // Eliminar espacios extra
            // Si la palabra es válida, agregarla
            if (palabra.length() > 2) {
                puntos = calcularPuntuacioParaula(palabraFinal,vectorDeFichas,x,y,dx==1);
                palabras.put(palabraFinal, puntos);
            }
        }

        return palabras;
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
    
    /**
     * Comprova si totes les paraules perpendiculars creades són vàlides
     */
    public boolean paraulesPerpendicularesValides(Jugada jugada, DAWG dawg) {
        Casella[][] caselles = this.getTaulell();
        int fila = jugada.getFila();
        int columna = jugada.getColumna();
        boolean horitzontal = jugada.esHoritzontal();
        String paraula = jugada.getParaula();
        int mida = this.getSize();
        List<Fitxa> fitxesUtilitzades = jugada.getFitxesUtilitzades();
        int indexFitxa = 0;

        for (int i = 0; i < paraula.length(); i++) {
            int f = horitzontal ? fila : fila + i;
            int c = horitzontal ? columna + i : columna;
            
            if (f >= mida || c >= mida) continue;
            Casella casella = caselles[f][c];

            if (casella.esBuida()) {
                // Verificar si tenim prou fitxes abans d'intentar accedir
                if (indexFitxa >= fitxesUtilitzades.size()) {
                    return false; // No hi ha prou fitxes per completar la paraula
                }
                
                // Verificar paraula perpendicular només on col·loquem fitxa nova
                StringBuilder perpendicular = new StringBuilder();
                String lletraActual = fitxesUtilitzades.get(indexFitxa++).obtenirLletra();

                // Comprovar cap amunt/esquerra
                int df = horitzontal ? -1 : 0;
                int dc = horitzontal ? 0 : -1;
                int nf = f + df;
                int nc = c + dc;
                
                while (nf >= 0 && nc >= 0 && !caselles[nf][nc].esBuida()) {
                    perpendicular.insert(0, caselles[nf][nc].obtenirFitxa().obtenirLletra());
                    nf += df;
                    nc += dc;
                }
                
                // Afegir la lletra actual que estem col·locant
                perpendicular.append(lletraActual);
                
                // Comprovar cap avall/dreta
                df = horitzontal ? 1 : 0;
                dc = horitzontal ? 0 : 1;
                nf = f + df;
                nc = c + dc;
                
                while (nf < mida && nc < mida && !caselles[nf][nc].esBuida()) {
                    perpendicular.append(caselles[nf][nc].obtenirFitxa().obtenirLletra());
                    nf += df;
                    nc += dc;
                }
                
                // Si només és la lletra que estem col·locant, no cal comprovar
                // Si té més lletres, ha de ser una paraula vàlida
                if (perpendicular.length() > 1 && !dawg.conteParaula(perpendicular.toString())) {
                    return false;
                }
            }
        }
        return true;
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
    public int calcularPuntuacioParaula(String paraula, List<Fitxa> fitxesUtilitzades, int fila, int columna, boolean horitzontal) {
        Casella[][] caselles = this.getTaulell();
        int mida = this.getSize();
        int puntuacioTotal = 0;
        int multiplicadorParaula = 1;
        int indexFitxa = 0;

        for (int i = 0; i < paraula.length(); i++) {
            int filaActual = horitzontal ? fila : fila + i;
            int columnaActual = horitzontal ? columna + i : columna;
            if (filaActual >= mida || columnaActual >= mida) return 0;

            Casella casella = caselles[filaActual][columnaActual];
            int punts = 0;

            if (casella.esBuida()) {
                if (indexFitxa >= fitxesUtilitzades.size()) return 0;
                Fitxa fitxa = fitxesUtilitzades.get(indexFitxa++);
                punts = fitxa.obtenirPunts();

                if (casella.obtenirEstrategia() instanceof EstrategiaMultiplicadorLletra)
                    punts *= ((EstrategiaMultiplicadorLletra) casella.obtenirEstrategia()).obtenirMultiplicador();
                else if (casella.obtenirEstrategia() instanceof EstrategiaMultiplicadorParaula)
                    multiplicadorParaula *= ((EstrategiaMultiplicadorParaula) casella.obtenirEstrategia()).obtenirMultiplicador();
            }
            puntuacioTotal += punts;
        }
        puntuacioTotal *= multiplicadorParaula;
        if (fitxesUtilitzades.size() == 7) puntuacioTotal += 50;
        return puntuacioTotal;
    }

    /**
     * Calcula la puntuació de les paraules perpendiculars formades
     * @param jugada Jugada a avaluar
     * @param dawg El DAWG amb paraules vàlides
     * @return Puntuació total de les paraules perpendiculars
     */
    public int calcularPuntuacioParaulesPerp(Jugada jugada, DAWG dawg) {
        Casella[][] caselles = this.getTaulell();
        int fila = jugada.getFila();
        int columna = jugada.getColumna();
        boolean horitzontal = jugada.esHoritzontal();
        String paraula = jugada.getParaula();
        int mida = this.getSize();
        List<Fitxa> fitxesUtilitzades = jugada.getFitxesUtilitzades();
        int puntuacioTotal = 0;
        int indexFitxa = 0;

        for (int i = 0; i < paraula.length(); i++) {
            int f = horitzontal ? fila : fila + i;
            int c = horitzontal ? columna + i : columna;
            
            if (f >= mida || c >= mida) continue;
            Casella casella = caselles[f][c];

            if (casella.esBuida()) {
                // Verificar paraula perpendicular només on col·loquem fitxa nova
                StringBuilder perpendicular = new StringBuilder();
                Fitxa fitxaActual = fitxesUtilitzades.get(indexFitxa++);
                List<Fitxa> fitxaUnica = new ArrayList<>();
                fitxaUnica.add(fitxaActual);
                
                // Obtenir la paraula perpendicular sencera
                int df = horitzontal ? -1 : 0;
                int dc = horitzontal ? 0 : -1;
                int nf = f + df;
                int nc = c + dc;
                while (nf >= 0 && nc >= 0 && !caselles[nf][nc].esBuida()) {
                    perpendicular.insert(0, caselles[nf][nc].obtenirFitxa().obtenirLletra());
                    nf += df;
                    nc += dc;
                }
                
                perpendicular.append(fitxaActual.obtenirLletra());
                
                df = horitzontal ? 1 : 0;
                dc = horitzontal ? 0 : 1;
                nf = f + df;
                nc = c + dc;
                while (nf < mida && nc < mida && !caselles[nf][nc].esBuida()) {
                    perpendicular.append(caselles[nf][nc].obtenirFitxa().obtenirLletra());
                    nf += df;
                    nc += dc;
                }
                
                // Si és una paraula perpendicular vàlida (amb més d'una lletra), calculem la puntuació
                if (perpendicular.length() > 1) {
                    int puntuacioPerp = calcularPuntuacioParaula(
                        perpendicular.toString(),
                        fitxaUnica,
                        horitzontal ? f : nf - df,
                        horitzontal ? nc - dc : c,
                        !horitzontal
                    );
                    puntuacioTotal += puntuacioPerp;
                }
            }
        }
        return puntuacioTotal;
    }

    /**
     * Calcula la puntuació total d'una jugada, incloent paraula principal i perpendiculars
     * @param jugada Jugada a avaluar
     * @param paraulaPrincipal Paraula principal formada
     * @param dawg El DAWG amb les paraules vàlides
     * @return Puntuació total
     */
    public int calcularPuntuacioTotal(Jugada jugada, String paraulaPrincipal, DAWG dawg) {
        // Calculem la puntuació de la paraula principal
        int puntuacioTotal = calcularPuntuacioParaula(
            paraulaPrincipal, 
            jugada.getFitxesUtilitzades(), 
            jugada.getFila(), 
            jugada.getColumna(), 
            jugada.esHoritzontal()
        );
        
        // Afegim les puntuacions de les paraules perpendiculars
        puntuacioTotal += calcularPuntuacioParaulesPerp(jugada, dawg);
        
        return puntuacioTotal;
    }
}