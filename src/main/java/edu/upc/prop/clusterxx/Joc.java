package edu.upc.prop.clusterxx;

public class Joc {
    int numJugadors;
    Jugador[] jugadors;
    Taulell taulell = new Taulell(15);
    Sac sac;

    public Joc(int numJugadors, String idioma, String[] noms) {
        this.numJugadors = numJugadors;
        this.sac = new Sac(idioma);
        this.taulell = new Taulell(15);

        // Inicialización correcta del array de jugadores
        this.jugadors = new Jugador[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            Faristol f = new Faristol(7);
            jugadors[i] = new Jugador(noms[i], f);
        }
    }


    public void imprimirSac() {
        sac.mostrarContingut();
    }

    public Sac obtenirSac() {
        return sac;
    }
    public Taulell obtenirTaulell() {return taulell;}
    public Jugador obtenirPersona(int i) {return jugadors[i];}

    public void colocarFitxa(int numJugador, Fitxa fitxa, int i, int j) {
        taulell.colocarFitxa(fitxa, i, j);
        jugadors[numJugador].eliminarFitxa(fitxa);
    };

    public void imprimirInfoJugadors() {
        for (int i = 0; i < numJugadors; i++) {jugadors[i].imprimirInfo();}
    }
    public void colocarFitxa(Fitxa fitxa, int i, int j) {
        taulell.colocarFitxa(fitxa, i, j);
    }
}
