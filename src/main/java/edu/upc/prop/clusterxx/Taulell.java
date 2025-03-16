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
                taulell[i][j] = new Casella(i, j, assignarEstrategia(i, j));
            }
        }
    }


    private EstrategiaPuntuacio assignarEstrategia(int i, int j) {
        // Triple paraula (TW)
        if ((i == 0 || i == 7 || i == 14) && (j == 0 || j == 7 || j == 14)) {
            return new EstrategiaMultiplicadorParaula(3);
        }

        // Doble paraula (DW)
        if (i == j || i + j == 14 || (i == 7 && j == 7)) {
            return new EstrategiaMultiplicadorParaula(2);
        }

        // Triple lletra (TL)
        if ((i == 1 || i == 5 || i == 9 || i == 13) && (j == 1 || j == 5 || j == 9 || j == 13)) {
            return new EstrategiaMultiplicadorLletra(3);
        }

        // Doble lletra (DL)
        if (((i == 0 || i == 14) && (j == 3 || j == 11)) ||
                ((i == 2 || i == 12) && (j == 6 || j == 8)) ||
                ((i == 3 || i == 11) && (j == 0 || j == 7 || j == 14)) ||
                ((i == 6 || i == 8) && (j == 2 || j == 6 || j == 8 || j == 12)) ||
                ((i == 7) && (j == 3 || j == 11))) {
            return new EstrategiaMultiplicadorLletra(2);
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




}
