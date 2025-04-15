package edu.upc.prop.clusterxx;

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

    // public static class BooleanWrapper {
    //     public boolean value;
    //     public BooleanWrapper(boolean value) {
    //         this.value = value;
    //     }
    // }

    // // Pre: un vector con las posiciones de las letras que se están añadiendo al tablero
    // // Post: Un map<string, int> con las palabras nuevas y sus respectivas puntuaciones
    // public HashMap<String, Integer> buscaPalabrasValidas(int[][] posNuevasLetras, BooleanWrapper conexa) {
    //     HashMap<String,Integer> nuevasPosiblesPalabras = new HashMap<>();
    //     for (int[] posLetra : posNuevasLetras) {
    //         int x = posLetra[0];
    //         int y = posLetra[1];
    //         HashMap<String,Integer> aux =new HashMap<>();
    //         aux = buscaPalabra(x, y, 1, 0,conexa); //derecha e izquierda
    //         nuevasPosiblesPalabras.putAll(aux);
    //         aux = buscaPalabra(x,y,0,1,conexa); //arriba, abajo
    //         nuevasPosiblesPalabras.putAll(aux);

    //     }
    //     return nuevasPosiblesPalabras;
    // }


    // private HashMap<String, Integer> buscaPalabra(int x, int y, int dx, int dy, BooleanWrapper conexa) {
    //     HashMap<String, Integer> palabras = new HashMap<>();
    //     Vector<Fitxa> vectorDeFichas = new Vector<>();
    //     StringBuilder palabra = new StringBuilder();
    //     Stack<Fitxa> fichasInversas = new Stack<>();

    //     int i = x, j = y;

    //     // Dirección inversa
    //     while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
    //         if (taulell[i][j].esJugada()) conexa.value = true;
    //         palabra.insert(0, taulell[i][j].toString().trim());
    //         fichasInversas.push(taulell[i][j].obtenirFitxa());
    //         i -= dx;
    //         j -= dy;
    //     }

    //     int inicioX = i + dx;
    //     int inicioY = j + dy;

    //     // Fichas en orden correcto
    //     while (!fichasInversas.isEmpty()) {
    //         vectorDeFichas.add(fichasInversas.pop());
    //     }

    //     // Dirección normal
    //     i = x + dx;
    //     j = y + dy;
    //     while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
    //         if (taulell[i][j].esJugada()) conexa.value = true;
    //         palabra.append(taulell[i][j].toString().trim());
    //         vectorDeFichas.add(taulell[i][j].obtenirFitxa());

    //         i += dx;
    //         j += dy;
    //     }

    //     String palabraFinal = palabra.toString().replaceAll("\\s+", "");
    //     if (palabraFinal.length() > 2) {
    //         int puntos = calcularPuntuacioParaula(palabraFinal, vectorDeFichas, inicioX, inicioY, dx == 1);
    //         palabras.put(palabraFinal, puntos);
    //     }

    //     return palabras;
    // }