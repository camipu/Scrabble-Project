package edu.upc.prop.clusterxx;

public class Joc {
    int numJugadors;
    Jugador[] jugadors;
    Diccionari diccionari;
    Sac sac;


    public Joc(int numJugadors, String idioma) {
        this.numJugadors = numJugadors;
        jugadors = new Jugador[numJugadors];
        this.diccionari= new Diccionari(idioma);
        this.sac = new Sac(idioma);
    }

    public boolean validarParaula(String paraula) {
        return diccionari.esParaulaValida(paraula);
    }

    public void imprimirSac() {
        sac.mostrarContingut();
    }

    public Sac getSac() {
        return sac;
    }
}
