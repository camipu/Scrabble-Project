package edu.upc.prop.clusterxx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Taulell {
    private final int size;
    private final Casella[][] taulell;
    private Joc joc;

    public Taulell(int size, Joc joc) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("La mida del tauler ha de ser imparella per garantir simetria.");
        }

        this.size = size;
        taulell = new Casella[size][size];
        this.joc = joc;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                taulell[i][j] = new Casella(i, j, assignarEstrategia(i, j, size));
            }
        }
    }


    private EstrategiaPuntuacio assignarEstrategia(int i, int j, int size) {
        int centro = size / 2;
        int offset = size / 4;
        // Triple paraula (TW)
        if ((i == 0 || i == centro || i == size - 1) && (j == 0 || j == centro || j == size - 1)) {
            return new EstrategiaMultiplicadorParaula(3);
        }

        // Doble paraula (DW) aqui potser falta afegir que i = centro && j = centro
        if (i == j || i + j == size - 1) {
            return new EstrategiaMultiplicadorParaula(2);
        }

        // Triple lletra (TL)
        if ((i == offset || i == size - 1 - offset) && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }
        if ((i == offset || i == size - 1 - offset) && j == centro || i == centro && (j == offset || j == size - 1 - offset)) {
            return new EstrategiaMultiplicadorLletra(3);
        }

        // Doble lletra (DL)
        if (i == centro || j == centro) {
            if (i != centro || j != centro) {
                return new EstrategiaMultiplicadorLletra(2);
            }
        }

        return new EstrategiaNormal();
    }


    public Casella[][] getTaulell() {
        return taulell;
    }

    public int getSize() {return size;}


    public void colocarFitxa(Fitxa fitxa, int fila, int columna) {
        if (fila < 0 || fila >= size || columna < 0 || columna >= size) {
            System.out.println(" Posició fora dels límits.");
        }
        if(!taulell[fila][columna].colocarFitxa(fitxa)) {
            System.out.println("Error: Casella ocupada.");
        }
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
        if (casella.getEstrategia() instanceof EstrategiaMultiplicadorParaula) {
            EstrategiaMultiplicadorParaula estrategia = (EstrategiaMultiplicadorParaula) casella.getEstrategia();
            return estrategia.getMultiplicador() == 3 ? Colors.RED_BACKGROUND : Colors.PURPLE_BACKGROUND;
        } else if (casella.getEstrategia() instanceof EstrategiaMultiplicadorLletra) {
            EstrategiaMultiplicadorLletra estrategia = (EstrategiaMultiplicadorLletra) casella.getEstrategia();
            return estrategia.getMultiplicador() == 3 ? Colors.BLUE_BACKGROUND : Colors.CYAN_BACKGROUND;
        } else {
            // Caselles normals -> Blanc brillant (si el terminal ho suporta)
            return "\033[107m";  // Alternativa més brillant per a WHITE_BACKGROUND
        }
    }

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
            HashMap<String, Integer> palabras = new HashMap<String, Integer>();
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{x, y});

            while (!queue.isEmpty()) {
                int[] pos = queue.poll();
                int i = pos[0], j = pos[1];

                // Formar la palabra recorriendo en la dirección (dx, dy)
                StringBuilder palabra = new StringBuilder();
                int puntos = 0;
                while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
                    palabra.insert(0, taulell[i][j]); // Construir palabra desde atrás
                    puntos += taulell[i][j].calcularPunts();
                    i -= dx;
                    j -= dy;
                }

                // Ahora ir en la otra dirección para completar la palabra
                i = pos[0] + dx;
                j = pos[1] + dy;
                while (i >= 0 && i < size && j >= 0 && j < size && !taulell[i][j].esBuida()) {
                    palabra.append(taulell[i][j]);
                    puntos += taulell[i][j].calcularPunts();
                    i += dx;
                    j += dy;
                }

                // Si la palabra es válida, agregarla
                //boolean aux = joc.obtenirDiccionari().esParaulaValida(palabra.toString().toUpperCase())
                if (palabra.length() > 2) {
                    palabras.put(palabra.toString(),puntos);
                }
            }

            return palabras;
        }

}
